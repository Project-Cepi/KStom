package world.cepi.kstom.extension

import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.extensions.Extension
import org.slf4j.Logger
import java.nio.file.Path
import kotlin.reflect.KClass

/**
 * Kotlin utilities that can be attatched per extension class.
 */
class ExtensionCompanion<T: Extension>(val extensionClass: KClass<out Extension>) {

    val instance: T
        get() = ExtensionUtils.currentExtension() as T

    val eventNode: EventNode<Event>
        get() = instance.eventNode

    val logger: Logger
        get() = instance.logger

    val dataDirectory: Path
        get() = instance.dataDirectory

}