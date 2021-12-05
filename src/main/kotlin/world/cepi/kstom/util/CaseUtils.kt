package world.cepi.kstom.util

fun String.pascalToTitle() = Regex("[A-Z][a-z]+")
    .findAll(this).map { it.value }.joinToString(" ")

