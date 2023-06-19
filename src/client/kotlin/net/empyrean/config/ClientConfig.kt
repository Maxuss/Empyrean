package net.empyrean.config

import net.empyrean.config.impl.ConfigProvider

object ClientConfig : ConfigProvider() {
    @JvmStatic
    val compactEffectDisplay: Boolean by cfg()

    @JvmStatic
    val displayEffectLength: Boolean by cfg()

    @JvmStatic
    val enableSingleplayer: Boolean by cfg()

    override fun init() {
        category("stable") {
            group("effects") {
                boolean("compactEffectDisplay", true)
                boolean("displayEffectLength", true)
            }
        }
        category("experimental") {
            group("expSingleplayer") {
                boolean("enableSingleplayer", false)
            }
        }
        saveDefault()
        load()
    }

}