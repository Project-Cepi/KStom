package world.cepi.kstom

import kotlinx.coroutines.launch
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
public inline fun <reified E : Event> EventHandler.addEventCallback(
    eventClass: KClass<E>,
    crossinline eventCallback: E.() -> Unit
): Boolean = addEventCallback(eventClass.java) { it.eventCallback() }

/**
 * Adds an event to an event handler using a Kotlin class, using Generics.
 *
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element is unique, false if it isn't and it wasn't added
 *
 */
public inline fun <reified E: Event> EventHandler.addEventCallback(
    crossinline eventCallback: E.() -> Unit
): Boolean = addEventCallback(E::class.java) { it.eventCallback() }


/**
 * Adds an event to an asynchronous event handler using a Kotlin class, using Generics.
 *
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element is unique, false if it isn't and it wasn't added
 *
 */
public inline fun <reified E: Event> EventHandler.event(
    crossinline eventCallback: suspend E.() -> Unit
): Boolean = addEventCallback(E::class.java) { IOScope.launch { it.eventCallback() } }