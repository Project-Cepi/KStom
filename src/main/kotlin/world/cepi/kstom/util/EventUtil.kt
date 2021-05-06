package world.cepi.kstom

import kotlinx.coroutines.launch
import net.minestom.server.event.CancellableEvent
import net.minestom.server.event.Event
import net.minestom.server.event.handler.EventHandler
import world.cepi.kstom.util.IOScope

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
 * Calls a cancellable event (reified function)
 *
 * @param E the event type
 * @param event The event object to call
 * @param callback The result if the event wasn't cancelled
 *
 */
public inline fun <reified E> EventHandler.callCancellableEvent(
    event: E,
    noinline callback: () -> Unit
): Unit where E : CancellableEvent, E : Event = callCancellableEvent(E::class.java, event, callback)

/**
 * Calls a event (reified function)
 *
 * @param E the event type
 * @param event The event object to call
 *
 */
public inline fun <reified E> EventHandler.callEvent(
    event: E,
    noinline callback: () -> Unit
): Unit where E : CancellableEvent, E : Event = callEvent(E::class.java, event)

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