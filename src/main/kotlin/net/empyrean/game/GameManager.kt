package net.empyrean.game

import net.fabricmc.loader.api.FabricLoader

object GameManager {
    var gameData: GameData = GameData.default()

    fun init() {
        gameData = GameData.load(FabricLoader.getInstance().gameDir)
    }
}