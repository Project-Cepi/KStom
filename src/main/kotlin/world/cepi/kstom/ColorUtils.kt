package world.cepi.kstom

import net.minestom.server.chat.ColoredText
import net.minestom.server.chat.RichMessage
import java.awt.Color

fun ColoredText.asRich(): RichMessage = RichMessage.of(this)
fun String.asColored(): ColoredText = ColoredText.of(this)
fun String.asRich(): RichMessage = this.asColored().asRich()