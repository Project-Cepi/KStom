package world.cepi.kstom.util

fun String.pascalToTitle() = Regex("^[a-z]+|[A-Z][a-z]+").findAll(this)
    .map { it.value.replaceFirstChar(Char::uppercase) }.joinToString(" ")

fun String.snakeToTitle() = this.split("_").joinToString(" ") { it.replaceFirstChar(Char::uppercase) }