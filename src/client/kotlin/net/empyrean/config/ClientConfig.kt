package net.empyrean.config

import net.empyrean.config.impl.ConfigProvider

object ClientConfig: ConfigProvider() {
    @JvmStatic
    val compactEffectDisplay: Boolean by cfg()
    @JvmStatic
    val alwaysDisplayEffects: Boolean by cfg()
    @JvmStatic
    val enableSingleplayer: Boolean by cfg()

    override fun init() {
        category("stable") {
            group("effects") {
                boolean("compactEffectDisplay", true)
                boolean("alwaysDisplayEffects", false)
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