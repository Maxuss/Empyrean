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

    @JvmField
    val END: Event<End> = EventFactory.createArrayBacked(
        End::class.java
    ) { callbacks ->
        End {
            for(callback in callbacks) {
                callback.invoke()
            }
        }
    }

    fun interface Start {
        fun onStart(timer: Float)
    }

    fun interface End {
        fun invoke()
    }
}