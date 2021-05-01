package world.cepi.kstom

import kotlinx.coroutines.launch
import net.minestom.server.event.Event
import net.minestom.server.event.handler.EventHandler
import kotlin.reflect.KClass

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
public inline fun <reified E: Event> EventHandler.asyncEvent(
    crossinline eventCallback: suspend E.() -> Unit
): Boolean = addEventCallback(E::class.java) { IOScope.launch { it.eventCallback() } }

/**
 * Removes an event from an event handler using a Kotlin class, using Generics.
 *
 * @param eventCallback The lambda that runs when the event is triggered
 *
 * @return True if the element was removed, false if it wasn't there
 *
 */
public inline fun <reified E: Event> EventHandler.removeEventCallback(
    crossinline eventCallback: E.() -> Unit
): Boolean = removeEventCallback(E::class.java) { it.eventCallback() }