package world.cepi.kstom.adventure

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun String.asMini(placeholders: Map<String, String> = mapOf()): Component = MiniMessage.get().parse(this, placeholders)

fun Audience.sendMiniMessage(miniMessage: String, placeholders: Map<String, String> = mapOf()): Unit =
    this.sendMessage(miniMessage.asMini(placeholders))

fun Component.plainText(): String = PlainTextComponentSerializer.plainText().serialize(this)
fun Component.legacyAmpersand(): String = LegacyComponentSerializer.legacyAmpersand().serialize(this)
fun Component.noItalic() = decoration(TextDecoration.ITALIC, false)

fun component(vararg components: Component) = components.fold(Component.text()) { acc, item ->
    acc.append(item)
}.build()

fun String.color(color: TextColor) = Component.text(this, color)
fun Int.color(color: TextColor) = Component.text(this, color)
fun Byte.color(color: TextColor) = Component.text(this.toString(), color)
fun Short.color(color: TextColor) = Component.text(this.toString(), color)
fun Long.color(color: TextColor) = Component.text(this, color)
fun Double.color(color: TextColor) = Component.text(this, color)
fun Boolean.color(color: TextColor) = Component.text(this, color)

fun String.asComponent() = Component.text(this)
fun Int.asComponent() = Component.text(this)
fun Byte.asComponent() = Component.text(this.toString())
fun Short.asComponent() = Component.text(this.toString())
fun Long.asComponent() = Component.text(this)
fun Double.asComponent() = Component.text(this)
fun Boolean.asComponent() = Component.text(this)
