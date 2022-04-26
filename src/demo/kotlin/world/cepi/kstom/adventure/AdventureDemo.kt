package world.cepi.kstom.adventure

import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.adventure.audience.Audiences

fun old() {
    Audiences.all().sendMessage(MiniMessage.miniMessage().deserialize("<red>Hello World!"))
    MiniMessage.miniMessage().deserialize("<red>Hello World!")
}

fun new() {
    Audiences.all().sendMiniMessage("<red>Hello World!")
    "<red>Hello World!".asMini()
}