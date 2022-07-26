package world.cepi.kstom.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minestom.server.event.Event
import net.minestom.server.event.EventListener
import net.minestom.server.event.EventNode
import world.cepi.kstom.dispatcher.MinestomAsync
import kotlin.coroutines.CoroutineContext

class KEventListener<T : Event>(val eventListener: EventListener.Builder<T>) {

    inner class Filters {
        operator fun plusAssign(lambda: T.() -> Boolean) {
            eventListener.filter(lambda)
        }
    }

    val filters = Filters()

    fun handler(lambda: T.() -> Unit) = eventListener.handler(lambda)

    fun suspendingHandler(context: CoroutineContext = Dispatchers.MinestomAsync, lambda: suspend T.() -> Unit) = eventListener.handler {
        CoroutineScope(context).launch {
            lambda(it)
        }
    }

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

