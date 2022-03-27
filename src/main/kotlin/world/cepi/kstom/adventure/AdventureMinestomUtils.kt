package world.cepi.kstom.adventure

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentBuilder
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun String.asMini(templateResolver: TagResolver = TagResolver.empty()): Component = MiniMessage.miniMessage()
    .deserialize(this, templateResolver)

fun Audience.sendMiniMessage(miniMessage: String, templateResolver: TagResolver = TagResolver.empty()): Unit =
    this.sendMessage(miniMessage.asMini(templateResolver))

fun Component.plainText(): String = PlainTextComponentSerializer.plainText().serialize(this)
fun Component.legacyAmpersand(): String = LegacyComponentSerializer.legacyAmpersand().serialize(this)
fun Component.noItalic() = decoration(TextDecoration.ITALIC, false)
fun ComponentBuilder<*, *>.noItalic() = decoration(TextDecoration.ITALIC, false)

fun component(vararg components: Component) = components.fold(Component.text().noItalic()) { acc, item ->
    acc.append(item)
}.build()

fun Any.color(color: TextColor) = Component.text(this.toString(), color)
fun Any.asComponent() = Component.text(this.toString())
fun Any.decorate(decoration: TextDecoration) = Component.text(this.toString()).decorate(decoration)