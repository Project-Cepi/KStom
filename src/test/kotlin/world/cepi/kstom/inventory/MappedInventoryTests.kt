package world.cepi.kstom.inventory

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.kyori.adventure.text.Component
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.kstom.util.get
import world.cepi.kstom.util.mapItemStacks

class MappedInventoryTests: StringSpec ({
    "Mapped inventory should have correct items" {
        val shape = listOf(
            "#########",
            "000000000",
            "111",
            "22222222222",
            "3"
        )

        val map = mapOf(
            '#' to ItemStack.of(Material.FEATHER),
            '0' to ItemStack.of(Material.LEATHER),
            '1' to ItemStack.of(Material.ARROW),
            '2' to ItemStack.of(Material.RED_CONCRETE),
            '3' to ItemStack.of(Material.DIAMOND)
        )

        val inventory = Inventory(InventoryType.CHEST_4_ROW, Component.empty())
        inventory.mapItemStacks(shape, map)

        inventory[0..8].all { it.material == Material.FEATHER } shouldBe true
        inventory[9..17].all { it.material == Material.LEATHER } shouldBe true
        inventory[18..20].all { it.material == Material.ARROW } shouldBe true
        inventory[21..26].all { it.material == Material.AIR } shouldBe true
        inventory[27..35].all { it.material == Material.RED_CONCRETE } shouldBe true
    }
})