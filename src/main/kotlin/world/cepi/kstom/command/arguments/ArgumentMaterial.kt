package world.cepi.kstom.command.arguments

import net.minestom.server.command.builder.NodeMaker
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.minecraft.ArgumentItemStack
import net.minestom.server.item.Material

class ArgumentMaterial(id: String) : Argument<Material>(id) {

    override fun parse(input: String) = ArgumentItemStack.staticParse(input).material
    override fun processNodes(nodeMaker: NodeMaker, executable: Boolean) {
        val argumentNode = simpleArgumentNode(this, executable, false, false)
        argumentNode.parser = "minecraft:item_stack"

        nodeMaker.addNodes(arrayOf(argumentNode))
    }

}