package world.cepi.kstom

import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ColoredText
import net.minestom.server.chat.RichMessage

public fun ColoredText.asRich(): RichMessage = RichMessage.of(this)
public fun String.asColored(): ColoredText = ColoredText.of(this)
public fun String.asRich(): RichMessage = this.asColored().asRich()

/**
 * Adds a string to a color
 * @param string String to add to the corresponding color
 */
public operator fun ChatColor.plus(string: String): String = this.toString() + string

/**
 * Adds a string to another color
 * @param color Color to add to the corresponding color
 */
public operator fun ChatColor.plus(color: ChatColor): String = this.toString() + color

/**
 * Translates any code with a special text.
 *
 * @param char The char to replace and reformat.
 *
 * @return A [ColoredText] which was translated from others.
 */
public fun String.translateColorCodes(char: Char = '&'): ColoredText = ColoredText.ofLegacy(this, char)