package world.cepi.kstom.adventure

import net.kyori.adventure.platform.minestom.MinestomComponentSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.chat.JsonMessage
import net.minestom.server.command.CommandSender

public fun String.formattedJson(): JsonMessage = MiniMessage.get().parse(this).serialize()
public fun String.asMini(): Component = MiniMessage.get().parse(this)
public fun Component.serialize(): JsonMessage = MinestomComponentSerializer.get().serialize(this)
public fun CommandSender.sendMessage(component: Component): Unit = this.sendMessage(component.serialize())
public fun CommandSender.sendMiniMessage(miniMessage: String): Unit = this.sendMessage(miniMessage.asMini())