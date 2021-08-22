package world.cepi.kstom.util

import net.minestom.server.inventory.Inventory
import net.minestom.server.item.ItemStack

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

fun Inventory.setItemStacks(vararg itemStacksToAdd: Pair<Int, ItemStack>) {
    val contents = this.itemStacks
    itemStacksToAdd.forEach { contents[it.first] = it.second }
    this.copyContents(contents)
}

// Allows use of x and y for inventories, only useful for hopper or rowed chest inventories

fun Inventory.getSlotNumber(x: Int, y: Int): Int {
    val slotNumber = y * 9 + x

    if (x > 8 || x < 0 || slotNumber > this.size || slotNumber < this.size) throw IndexOutOfBoundsException()

    return slotNumber
}

fun Inventory.setItemStack(x: Int, y: Int, item: ItemStack) = this.setItemStack(getSlotNumber(x, y), item)
fun Inventory.getItemStack(x: Int, y: Int): ItemStack = this.getItemStack(getSlotNumber(x, y))