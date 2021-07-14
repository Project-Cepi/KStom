package world.cepi.kstom.adventure

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

fun String.asMini(): Component = MiniMessage.get().parse(this)
fun Audience.sendMiniMessage(miniMessage: String): Unit = this.sendMessage(miniMessage.asMini())