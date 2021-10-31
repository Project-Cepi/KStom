package world.cepi.kstom.adventure

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun String.asMini(placeholders: Map<String, String> = mapOf()): Component = MiniMessage.get().parse(this, placeholders)

fun Audience.sendMiniMessage(miniMessage: String, placeholders: Map<String, String> = mapOf()): Unit =
    this.sendMessage(miniMessage.asMini(placeholders))

fun Component.plainText(): String = PlainTextComponentSerializer.plainText().serialize(this)
fun Component.legacyAmpersand(): String = LegacyComponentSerializer.legacyAmpersand().serialize(this)