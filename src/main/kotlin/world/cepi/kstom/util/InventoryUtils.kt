package world.cepi.kstom.util

import net.minestom.server.inventory.Inventory
import net.minestom.server.item.ItemStack
import kotlin.math.max
import kotlin.math.min

fun Inventory.clone(): Inventory {
    val clonedInventory = Inventory(this.inventoryType, this.title)
    clonedInventory.copyContents(this.itemStacks)
    clonedInventory.inventoryConditions.addAll(this.inventoryConditions)
    return clonedInventory
}

fun Inventory.setItemStacks(itemStacksToAdd: Map<Int, ItemStack>) {
    val contents = this.itemStacks
    itemStacksToAdd.forEach { contents[it.key] = it.value }
    this.copyContents(contents)
}

fun Inventory.setItemStacks(vararg itemStacksToAdd: Pair<Int, ItemStack>) = setItemStacks(itemStacksToAdd.toMap())

/**
 * Allows you to map specific ItemStack's to a character. Useful for creating configurable inventories.
 */
fun Inventory.mapItemStacks(shape: List<String>, mappings: Map<Char, ItemStack>) {
    for (y in 0 until min(this.size / 9, shape.size)) {
        val row = shape[y]
        val characters = row.toCharArray()

        for (x in 0 until min(9, characters.size)) {
            val currentCharacter = characters[x]
            this.setItemStack(x, y, mappings[currentCharacter] ?: ItemStack.AIR)
        }
    }
}

/**
 * Allows use of x and y for inventories. Won't work properly for inventories other than hopper or rowed chests
 */
fun Inventory.getSlotNumber(x: Int, y: Int): Int {
    val slotNumber = y * 9 + x

    if (x > 8 || x < 0 || slotNumber > this.size || slotNumber < 0) throw IndexOutOfBoundsException()

    return slotNumber
}

operator fun Inventory.get(slot: Int) = this.getItemStack(slot)

operator fun Inventory.get(range: IntRange): List<ItemStack> {
    val itemStacks = mutableListOf<ItemStack>()

    for (slot in max(0, range.first) until min(this.size, range.last)) {
        itemStacks.add(this[slot])
    }

    return itemStacks
}

operator fun Inventory.set(slot: Int, itemStack: ItemStack) = this.setItemStack(slot, itemStack)

/**
 * Sets an ItemStack using X and Y. Won't work properly for inventories other than hopper or rowed chests
 */
fun Inventory.setItemStack(x: Int, y: Int, item: ItemStack) = run { this[getSlotNumber(x, y)] = item }
/**
 * Gets an ItemStack using X and Y. Won't work properly for inventories other than hopper or rowed chests
 */
fun Inventory.getItemStack(x: Int, y: Int): ItemStack = this[getSlotNumber(x, y)]