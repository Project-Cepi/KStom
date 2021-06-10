package world.cepi.kstom.event

import net.minestom.server.event.Event
import net.minestom.server.event.EventListener
import net.minestom.server.event.EventNode

class KEventListener<T : Event>(val eventListener: EventListener.Builder<T>) {

    inner class Filters {
        operator fun plusAssign(lambda: T.() -> Boolean) {
            eventListener.filter(lambda)
        }
    }

    val filters = Filters()

    fun handler(lambda: T.() -> Unit) = eventListener.handler(lambda)

    fun removeWhen(lambda: T.() -> Boolean) = eventListener.expireWhen(lambda)

    var expireCount: Int
        get() = throw IllegalAccessError("Expire count can't be accessed in the context of a builder.")
        set(value) {
            eventListener.expireCount(value)
        }
}

inline fun <reified E: Event> EventNode<in E>.listen(lambda: KEventListener<E>.() -> Unit) {

    val kEventListener = KEventListener<E>(EventListener.builder(E::class.java))

    lambda(kEventListener)

    this.addListener(
        kEventListener.eventListener.build()
    )
}

inline fun <reified E: Event> EventNode<in E>.listenOnly(noinline lambda: E.() -> Unit) {

    this.addListener(
        E::class.java,
        lambda
    )
}

