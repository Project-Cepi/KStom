package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.minestom.server.item.Material
import net.minestom.server.item.metadata.WrittenBookMeta

@kotlinx.serialization.Serializable
class CoolClass(val num: Int, val coolProperty: String)

val item = item<WrittenBookMeta.Builder, WrittenBookMeta>(material = Material.WRITTEN_BOOK, amount = 5) {
    displayName = Component.text("Hey!")
    displayName(Component.text("Hello!")) // or this

    damage = 5
    unbreakable = true

    lore {
        +"Hello"
        +"<red>Minestom!</red>"
    }

    lore(Component.text("Hello!")) // or this

    title("My first book")
    author("Notch")
    pages(Component.text("This is the first page"))

    this["complexData"] = CoolClass(5, "Hello")
}.withAmount(7).and {
    displayName(Component.text("Hay!"))
}

val simpleItem = item(Material.BLUE_BANNER) {
    displayName(Component.text("Simple item!"))
}