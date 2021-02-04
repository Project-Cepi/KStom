package world.cepi.kstom

import net.minestom.server.event.Event
import net.minestom.server.event.handler.EventHandler
import kotlin.reflect.KClass

/**
 * Adds an event to an event handler using a Kotlin class
 *
 * @param eventClass The class to listen to from the player.
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element is unique, false if it isn't and it wasn't added
 *
 */
public fun <E : Event> EventHandler.addEventCallback(eventClass: KClass<E>, eventCallback: E.() -> Unit): Boolean =
    this.addEventCallback(eventClass.java, eventCallback)

/**
 * Adds an event to an event handler using a Kotlin class, using Generics.
 *
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element is unique, false if it isn't and it wasn't added
 *
 */
public inline fun <reified E: Event> EventHandler.addEventCallback(noinline eventCallback: E.() -> Unit): Boolean =
    addEventCallback(E::class.java, eventCallback)