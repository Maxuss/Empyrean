package net.empyrean.event.client.event.client

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory

object RenderTickEvent {
    @JvmField
    val START: Event<Start> = EventFactory.createArrayBacked(
        Start::class.java
    ) { callbacks ->
        Start { timer ->
            for(callback in callbacks) {
                callback.onStart(timer)
            }
        }
    }

    fun interface Start {
        fun onStart(timer: Float)
    }
}