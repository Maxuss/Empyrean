package net.empyrean.util.entity

import net.empyrean.util.pos.BlockIterator
import net.empyrean.util.pos.block
import net.empyrean.util.pos.pos
import net.empyrean.util.text.Text
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3

fun raycast(from: LivingEntity, distance: Int): Vec3 {
    return try {
        val eyes = from.eyePosition
        val level = from.level()

        val iterator = BlockIterator(eyes, from.forward, distance)
        var currentPos: Vec3 = Vec3.ZERO
        while (iterator.hasNext()) {
            currentPos = iterator.next()
            if (currentPos.pos.block(level).isSolid) {
                break
            }
        }
        currentPos
    } catch (e: IllegalStateException) {
        from.sendSystemMessage(Text.of("<red>There are blocks in the way!"))
        from.position()
    }
}