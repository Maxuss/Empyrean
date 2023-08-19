package net.empyrean.game

import kotlinx.serialization.Serializable
import net.empyrean.game.state.ProgressionState
import net.empyrean.nbt.decodeNbt
import net.empyrean.nbt.encodeNbt
import net.minecraft.nbt.NbtIo
import java.nio.file.Path

@Serializable
class GameData(
    var progressionState: ProgressionState
) {
    companion object {
        val GAME_DATA_STORAGE_PATH = "empyrean.dat"

        fun default(): GameData {

            return GameData(ProgressionState.PRE_BOSS)
        }

        fun load(base: Path): GameData {
            val file = base.resolve(GAME_DATA_STORAGE_PATH).toFile()
            if(!file.exists()) {
                return default().also { it.save(base) }
            }
            return decodeNbt(NbtIo.readCompressed(file))
        }
    }

    fun save(base: Path) {
        val file = base.resolve(GAME_DATA_STORAGE_PATH).toFile()
        if(!file.exists())
            file.createNewFile()
        NbtIo.writeCompressed(encodeNbt(this), file)
    }
}