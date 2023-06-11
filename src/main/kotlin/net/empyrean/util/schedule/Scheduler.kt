package net.empyrean.util.schedule

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

@Suppress("UNCHECKED_CAST")
object Scheduler {
    private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(4)

    fun runTaskLater(delay: Duration, task: () -> Unit): ScheduledTask {
        return ScheduledTask(executor.schedule(task, delay.inWholeMilliseconds, TimeUnit.MILLISECONDS) as ScheduledFuture<Unit>)
    }

    fun <V> runTaskLater(delay: Duration, task: () -> V): ScheduledValueTask<V> {
        return ScheduledValueTask(executor.schedule(task, delay.inWholeMilliseconds, TimeUnit.MILLISECONDS) as ScheduledFuture<V>)
    }
}