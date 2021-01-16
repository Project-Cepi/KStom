package world.cepi.kstom

import net.minestom.server.entity.Player
import net.minestom.server.event.Event
import net.minestom.server.event.EventCallback
import net.minestom.server.event.GlobalEventHandler
import kotlin.reflect.KClass

/**
 * Adds an event to the player using a Kotlin class
 *
 * @param eventClass The class to listen to from the player.
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element is unique, false if it isn't and it wasn't added
 *
 */
fun <E : Event> Player.addEventCallback(eventClass: KClass<E>, eventCallback: E.() -> Unit): Boolean {
    return this.addEventCallback(eventClass.java, eventCallback)
}

/**
 * Adds an event to the global event handler using a Kotlin class
 *
 * @param eventClass The class to listen to from the player.
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element is unique, false if it isn't and it wasn't added
 *
 */
fun <E : Event> GlobalEventHandler.addEventCallback(eventClass: KClass<E>, eventCallback: E.() -> Unit): Boolean {
    return this.addEventCallback(eventClass.java, eventCallback)
}