package net.empyrean.util.schedule

import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

data class ScheduledTask(private val future: ScheduledFuture<Unit>) {
    fun cancelUnsafe() {
        future.cancel(true)
    }

    fun cancel() {
        future.cancel(false)
    }
}

data class ScheduledValueTask<V>(private val future: ScheduledFuture<V>) {
    fun await(time: Duration): V {
        return future[time.inWholeMilliseconds, TimeUnit.MILLISECONDS]
    }

    fun await(): V {
        return future.get()
    }
}