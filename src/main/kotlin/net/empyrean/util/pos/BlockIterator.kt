package net.empyrean.util.pos

import net.minecraft.world.phys.Vec3

class BlockIterator(from: Vec3, private val modifier: Vec3, private val limit: Int) {
    private var currentIdx = 0
    var currentPos = from
        private set

    fun hasNext(): Boolean {
        println("HAS NEXT? ${currentIdx < limit} $currentPos $modifier")
        return currentIdx < limit
    }

    fun next(): Vec3 {
        currentPos = currentPos.add(modifier)
        currentIdx += 1
        return currentPos
    }
}