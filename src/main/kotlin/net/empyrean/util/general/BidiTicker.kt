package net.empyrean.util.general

class BidiTicker(
    val min: Float,
    val max: Float,
    val step: Float,
    val start: Float = min
) {
    @Volatile
    var currentValue: Float = min
        private set
    private var currentDirection: Direction = Direction.INC

    enum class Direction {
        INC,
        DEC
    }

    fun tick(): Float {
        currentValue = if(currentDirection == Direction.INC) currentValue + step else currentValue - step
        if(currentValue < min) {
            currentValue = min
            currentDirection = Direction.INC
        } else if(currentValue > max) {
            currentValue = max
            currentDirection = Direction.DEC
        }
        return currentValue
    }
}