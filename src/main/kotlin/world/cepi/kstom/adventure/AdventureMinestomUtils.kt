package world.cepi.kstom.adventure

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

public fun String.asMini(): Component = MiniMessage.get().parse(this)
public fun Audience.sendMiniMessage(miniMessage: String): Unit = this.sendMessage(miniMessage.asMini())
fun Component.legacyAmpersand(): String = LegacyComponentSerializer.legacyAmpersand().serialize(this)