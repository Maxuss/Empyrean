package net.empyrean.config.impl

import com.google.common.collect.ImmutableList
import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

abstract class ConfigProvider {
    protected val categories: MutableList<Category> = mutableListOf()
    var values: HashMap<String, Any> = hashMapOf()
    private val json = Json { prettyPrint = true }

    protected fun <T> cfg(): PropertyReference<T> {
        return PropertyReference(WeakReference(this))
    }

    protected inline fun category(name: String, builder: CategoryBuilder.() -> Unit) {
        val ctg = CategoryBuilder(this, name, mutableListOf())
        ctg.apply(builder)
        categories.add(ctg.build())
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> property(key: String): T {
        return values[key] as T
    }

    abstract fun init()

    protected fun load() {
        val file = FabricLoader.getInstance().configDir.resolve("empyrean.json").toFile()
        if (!file.exists()) {
            file.createNewFile()
            return
        }

        val data: HashMap<String, PropertyData> = Json.Default.decodeFromString(serializer(), file.readText())
        values = HashMap(data.toList().associate { it.first to it.second.value })
        categories.forEach {
            it.groups.forEach { grp ->
                grp.properties.forEach { prop ->
                    if (!values.containsKey(prop.name))
                        values[prop.name] = prop.defaultValue!!
                }
            }
        }
    }

    protected fun saveDefault() {
        val file = FabricLoader.getInstance().configDir.resolve("empyrean.json").toFile()
        if (file.exists()) {
            return
        }

        save()
    }

    private fun save() {
        val file = FabricLoader.getInstance().configDir.resolve("empyrean.json").toFile()
        if (!file.exists())
            file.createNewFile()
        val properties = values.toList().associate { it.first to PropertyData(it.second.javaClass.name, it.second) }
        file.writeText(json.encodeToString(serializer(), properties))
    }

    fun screen(parent: Screen?): Screen {
        var builder = YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("config.empyrean.title"))
        for (category in categories) {
            var ctg = ConfigCategory
                .createBuilder()
                .name(Component.translatable("config.empyrean.category.${category.name}"))
                .tooltip(Component.translatable("config.empyrean.category.${category.name}.description"))
            for (group in category.groups) {
                var grp = OptionGroup
                    .createBuilder()
                    .name(Component.translatable("config.empyrean.group.${group.name}"))
                    .description(OptionDescription.of(Component.translatable("config.empyrean.group.${group.name}.tooltip")))
                for (prop in group.properties) {
                    grp = grp.option(prop.createYaclProperty())
                }
                ctg = ctg.group(grp.build())
            }
            builder = builder.category(ctg.build())
        }
        return builder.save(this::save).build().generateScreen(parent)
    }
}

data class Category(
    val name: String,
    val groups: ImmutableList<Group>
)

data class CategoryBuilder(val selfConfig: ConfigProvider, val name: String, val groups: MutableList<Group>) {
    inline fun group(name: String, builder: GroupBuilder.() -> Unit) {
        val group = GroupBuilder(selfConfig, name, mutableListOf())
        group.apply(builder)
        groups.add(group.build())
    }

    fun build(): Category {
        return Category(name, ImmutableList.copyOf(groups))
    }
}

data class Group(
    val name: String,
    val properties: ImmutableList<ConfigProperty<*>>
)

data class GroupBuilder(
    val selfConfig: ConfigProvider,
    val name: String,
    val properties: MutableList<ConfigProperty<*>>
) {
    fun boolean(name: String, default: Boolean) {
        val prop = BooleanProperty(selfConfig, name, default)
        properties.add(prop)
        selfConfig.values[name] = default
    }

    fun build(): Group {
        return Group(name, ImmutableList.copyOf(properties))
    }
}

class BooleanProperty(config: ConfigProvider, name: String, default: Boolean) :
    ConfigProperty<Boolean>(config, name, default) {
    override fun createYaclProperty(): Option<*> {
        return Option
            .createBuilder<Boolean>()
            .name(Component.translatable("config.empyrean.option.$name"))
            .description(
                OptionDescription.of(
                    Component.translatable("config.empyrean.option.$name.tooltip")
                )
            )
            .binding(defaultValue, { config.values[name] as Boolean }, { config.values[name] = it })
            .controller(TickBoxControllerBuilder::create)
            .build()
    }
}

abstract class ConfigProperty<T>(
    protected val config: ConfigProvider,
    val name: String,
    val defaultValue: T
) {
    abstract fun createYaclProperty(): Option<*>
}

@Serializable(with = PropertySerializer::class)
data class PropertyData(val klass: String, val value: Any)

object PropertySerializer : KSerializer<PropertyData> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("PropertyData") {
            element("\$class", serialDescriptor<String>())
            element("value", buildClassSerialDescriptor("Any"))
        }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): PropertyData {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                val classDesc = Class.forName(decodeStringElement(descriptor, 0))
                val de = serializer(classDesc)
                val value = decodeSerializableElement(descriptor, 0, de)
                PropertyData(classDesc.name, value)
            } else {
                decodeElementIndex(descriptor)
                val classDesc = Class.forName(decodeStringElement(descriptor, 0))
                val de = serializer(classDesc)
                val payload = when (val index = decodeElementIndex(descriptor)) {
                    1 -> decodeSerializableElement(descriptor, 1, de)
                    CompositeDecoder.DECODE_DONE -> throw SerializationException("value field is missing")
                    else -> error("Unexpected index: $index")
                }
                PropertyData(classDesc.name, payload)
            }
        }
    }

    override fun serialize(encoder: Encoder, value: PropertyData) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.klass)
            encodeSerializableElement(descriptor, 1, serializer(Class.forName(value.klass)), value.value)
        }
    }
}

data class PropertyReference<T>(val cfg: WeakReference<ConfigProvider>) {
    operator fun getValue(self: Any, prop: KProperty<*>): T {
        return cfg.get()!!.property(prop.name)
    }
}