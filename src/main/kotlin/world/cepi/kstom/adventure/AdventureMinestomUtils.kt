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

fun String.formatMini(tagResolver: TagResolver = TagResolver.empty()): Component = MiniMessage.miniMessage().deserialize(this, tagResolver)

fun Audience.sendMiniMessage(miniMessage: String, templateResolver: TagResolver = TagResolver.empty()): Unit =
    this.sendMessage(miniMessage.asMini(templateResolver))

fun Component.plainText(): String = PlainTextComponentSerializer.plainText().serialize(this)
fun Component.legacyAmpersand(): String = LegacyComponentSerializer.legacyAmpersand().serialize(this)

fun Component.undecorate(decoration: TextDecoration): Component = this.decoration(decoration, TextDecoration.State.FALSE)
fun Component.undecorate(vararg decorations: TextDecoration): Component = this.decorations(decorations.associateWith { TextDecoration.State.FALSE })

fun Component.italic(): Component = this.decorate(TextDecoration.ITALIC)
fun Component.strikethrough(): Component = this.decorate(TextDecoration.STRIKETHROUGH)
fun Component.bold(): Component = this.decorate(TextDecoration.BOLD)
fun Component.obfuscated(): Component = this.decorate(TextDecoration.OBFUSCATED)
fun Component.underlined(): Component = this.decorate(TextDecoration.UNDERLINED)

fun Component.noItalic() = decoration(TextDecoration.ITALIC, false)
fun ComponentBuilder<*, *>.noItalic() = decoration(TextDecoration.ITALIC, false)
fun Component.noStrikethrough(): Component = this.undecorate(TextDecoration.STRIKETHROUGH)
fun Component.noBold(): Component = this.undecorate(TextDecoration.BOLD)
fun Component.noObfuscated(): Component = this.undecorate(TextDecoration.OBFUSCATED)
fun Component.noUnderlined(): Component = this.undecorate(TextDecoration.UNDERLINED)

fun component(vararg components: Component) = components.fold(Component.text().noItalic()) { acc, item ->
    acc.append(item)
}.build()

fun Any.asComponent(color: TextColor) = Component.text(this.toString(), color)
fun Any.asComponent() = Component.text(this.toString())
