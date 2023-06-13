package net.empyrean.commands.api

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.*
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.tree.CommandNode
import net.empyrean.mixin.CommandContextAccessorMixin
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.ChatFormatting
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.commands.CommandRuntimeException
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.*
import net.minecraft.commands.arguments.coordinates.BlockPosArgument
import net.minecraft.commands.arguments.coordinates.Coordinates
import net.minecraft.commands.arguments.coordinates.Vec2Argument
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.commands.arguments.selector.EntitySelector
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.util.function.Predicate

typealias ArgDsl<S, T> = EmpyreanArgBuilder<S, T>.() -> Unit
typealias ArgDslTyped<S, A> = EmpyreanArgBuilder<S, RequiredArgumentBuilder<S, A>>.(it: CommandContext<S>.() -> A) -> Unit
typealias KCommand<SRC> = CommandContext<SRC>.() -> Unit // non-int return type command

typealias Arg<SRC, A> = EmpyreanArgBuilder<SRC, A>

class EmpyreanCommandException(msg: String): CommandRuntimeException(Component.literal(msg))

class EmpyreanArgBuilder<SRC, A : ArgumentBuilder<SRC, *>>(var arg: A) :
    ArgumentBuilder<SRC, EmpyreanArgBuilder<SRC, A>>() {

    val subArgs = mutableListOf<EmpyreanArgBuilder<SRC, *>>()

    fun finalize(): A {
        for (subArg in subArgs) {
            arg.then(subArg.build())
        }
        return arg
    }

    /**
     * Specifies a literal argument.
     */
    fun literal(word: String, func: ArgDsl<SRC, LiteralArgumentBuilder<SRC>> = {}): LiteralArgumentBuilder<SRC> {
        val newArg = EmpyreanArgBuilder<SRC, LiteralArgumentBuilder<SRC>>(LiteralArgumentBuilder.literal(word)).apply(func)
        newArg.finalize()
        subArgs.add(newArg)
        return newArg.arg
    }

    override fun requires(requirement: Predicate<SRC>?): EmpyreanArgBuilder<SRC, A> {
        arg.requires(requirement)
        return super.requires(requirement)
    }

    fun withRequirement(pred: SRC.() -> Boolean, newArg: ArgumentBuilder<SRC, *>, func: ArgDsl<SRC, ArgumentBuilder<SRC, *>>) {
        val built = EmpyreanArgBuilder(newArg).apply {
            arg.requires(pred)
        }.apply(func)
        built.finalize()
        subArgs.add(built)
    }

    /**
     * Specifies a generic required argument.
     */
    inline fun <reified ARG> argument(
        type: ArgumentType<ARG>,
        word: String,
        items: SuggestionProvider<SRC>? = null,
        func: ArgDslTyped<SRC, ARG> = {}
    ): RequiredArgumentBuilder<SRC, ARG> {
        val req = EmpyreanArgBuilder<SRC, RequiredArgumentBuilder<SRC, ARG>>(RequiredArgumentBuilder.argument(word, type)).apply {
            func { getArgument(this@apply.arg.name, ARG::class.java) }
        }

        items?.let {
            req.arg.suggests(it)
        }

        req.finalize()
        subArgs.add(req)
        return req.arg
    }

    // Pre-made argument methods. If you want a custom one, use `argument()` above

    fun argBlockPos(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Coordinates> = {}
    ) = argument(BlockPosArgument.blockPos(), word, items, func)

    fun argBool(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Boolean> = {}
    ) = argument(BoolArgumentType.bool(), word, items, func)

    fun argColor(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, ChatFormatting> = {}
    ) = argument(ColorArgument.color(), word, items, func)

    fun argFloat(
        word: String, range: ClosedFloatingPointRange<Float>? = null,
        items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Float> = {}
    ) = argument(if (range != null) FloatArgumentType.floatArg(range.start, range.endInclusive) else FloatArgumentType.floatArg(), word, items, func)

    fun argIdentifier(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, ResourceLocation> = {}
    ) = argument(ResourceLocationArgument.id(), word, items, func)

    fun argInt(
        word: String, range: IntRange? = null,
        items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Int> = {}
    ) = argument(if (range != null) IntegerArgumentType.integer(range.first, range.last) else IntegerArgumentType.integer(), word, items, func)

    fun argIntRange(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, MinMaxBounds.Ints> = {}
    ) = argument(RangeArgument.intRange(), word, items, func)

    fun argString(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, String> = {}
    ) = argument(StringArgumentType.string(), word, items, func)

    fun argDouble(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Double> = {}
    ) = argument(DoubleArgumentType.doubleArg(), word, items, func)

    fun argPlayer(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, EntitySelector> = {}
    ) = argument(EntityArgument.player(), word, items, func)

    fun argPlayers(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, EntitySelector> = {}
    ) = argument(EntityArgument.players(), word, items, func)

    fun argEntity(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, EntitySelector> = {}
    ) = argument(EntityArgument.entity(), word, items, func)

    fun argEntities(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, EntitySelector> = {}
    ) = argument(EntityArgument.entities(), word, items, func)

    fun argAngle(
        word: String, items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, AngleArgument.SingleAngle> = {}
    ) = argument(AngleArgument.angle(), word, items, func)

    fun argVec2(
        word: String, centerInts: Boolean? = null,
        items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Coordinates> = {}
    ) = argument(centerInts?.let { Vec2Argument.vec2(it) } ?: Vec2Argument.vec2(), word, items, func)

    fun argVec3(
        word: String, centerInts: Boolean? = null,
        items: SuggestionProvider<SRC>? = null, func: ArgDslTyped<SRC, Coordinates> = {}
    ) = argument(centerInts?.let { Vec3Argument.vec3(it) } ?: Vec3Argument.vec3(), word, items, func)

    /**
     * A shortcut for creating a literal argument.
     * @see [literal]
     */
    operator fun String.invoke(func: ArgDsl<SRC, LiteralArgumentBuilder<SRC>>) {
        literal(this, func)
    }

    @Suppress("UNCHECKED_CAST")
    override fun executes(command: Command<SRC>?): EmpyreanArgBuilder<SRC, A> {
        return EmpyreanArgBuilder(arg.executes(command) as A)
    }

    fun executes(kCommand: KCommand<SRC>) {
        executes(Command<SRC> {
            kCommand(it)
            1
        })
    }

    // Alternate shortcuts for `runs`.

    /**
     * Specifies a command to execute when the string literal is called.
     */
    infix fun String.runs(kcmd: KCommand<SRC>) = this { executes(kcmd) }
    infix fun String.runs(cmd: Command<SRC>) = this { executes(cmd) }

    // this runs (required and literal variants)

    inline infix fun <reified ARG> EmpyreanArgBuilder<SRC, RequiredArgumentBuilder<SRC, ARG>>
            .runs(noinline cmd: CommandContext<SRC>.(it: CommandContext<SRC>.() -> ARG) -> Unit) {
        arg.runs(cmd)
    }

    infix fun EmpyreanArgBuilder<SRC, LiteralArgumentBuilder<SRC>>.runs(
        cmd: KCommand<SRC>
    ) {
        arg.runs(cmd)
    }

    infix fun EmpyreanArgBuilder<SRC, LiteralArgumentBuilder<SRC>>.runs(
        cmd: Command<SRC>
    ) {
        arg.runs(cmd)
    }

    // Required Args runs

    inline fun <reified ARG> CommandContext<SRC>.argFrom(req: RequiredArgumentBuilder<SRC, ARG>): ARG {
        return getArgument(req.name, ARG::class.java)
    }

    inline infix fun <reified ARG> RequiredArgumentBuilder<SRC, ARG>.runs(noinline cmd: CommandContext<SRC>.(it: CommandContext<SRC>.() -> ARG) -> Unit) {
        val runContext: CommandContext<SRC>.() -> ARG = {
            getArgument(name, ARG::class.java)
        }

        executes {
            it.cmd(runContext)
            1
        }
    }

    inline infix fun <reified ARG> RequiredArgumentBuilder<SRC, ARG>.runs(cmd: Command<SRC>) {
        executes(cmd)
    }

    // Literal Args runs

    infix fun LiteralArgumentBuilder<SRC>.runs(kcmd: KCommand<SRC>) {
        executes {
            kcmd(it)
            1
        }
    }

    infix fun LiteralArgumentBuilder<SRC>.runs(cmd: Command<SRC>) {
        executes(cmd)
    }

    // Misc builder methods

    override fun getThis(): EmpyreanArgBuilder<SRC, A> = this

    override fun build(): CommandNode<SRC> {
        return arg.build()
    }

}

@Environment(EnvType.SERVER)
inline fun command(name: String, initializer: EmpyreanArgBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>>.() -> Unit): LiteralArgumentBuilder<CommandSourceStack> {
    val command = EmpyreanArgBuilder(LiteralArgumentBuilder.literal<CommandSourceStack>(name))
    command.initializer()
    command.finalize()
    return command.arg
}

// The ..ArgBuilder is used for type inference
fun <SRC : SharedSuggestionProvider> Arg<SRC, *>.suggests(func: (CommandContext<SRC>) -> List<Any>): SuggestionProvider<SRC> {
    return SuggestionProvider<SRC> { ctx, builder ->
        func(ctx).map { it.toString() }.forEach { builder.suggest(it) }
        builder.buildFuture()
    }
}

fun <SRC: SharedSuggestionProvider> Arg<SRC, *>.suggests(forArg: String, func: (CommandContext<SRC>) -> List<Any>): SuggestionProvider<SRC> {
    return SuggestionProvider<SRC> { ctx, builder ->
        val accessor = ctx as CommandContextAccessorMixin
        val arg = accessor.arguments[forArg]?.toString() ?: ""
        func(ctx).filter { it.toString().contains(arg) }.forEach { builder.suggest(it.toString()) }
        builder.buildFuture()
    }
}

