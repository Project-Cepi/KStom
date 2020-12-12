package world.cepi.kstom

import net.minestom.server.entity.Player
import net.minestom.server.event.Event
import net.minestom.server.event.EventCallback
import net.minestom.server.event.GlobalEventHandler
import kotlin.reflect.KClass

fun <E : Event> Player.addEventCallback(eventClass: KClass<E>, eventCallback: EventCallback<E>): Boolean {
    val callbacks: MutableCollection<EventCallback<*>> = this.getEventCallbacks(eventClass.java)
    return callbacks.add(eventCallback)
}

fun <E : Event> GlobalEventHandler.addEventCallback(eventClass: KClass<E>, eventCallback: EventCallback<E>): Boolean {
    val callbacks: MutableCollection<EventCallback<*>> = this.getEventCallbacks(eventClass.java)
    return callbacks.add(eventCallback)
}