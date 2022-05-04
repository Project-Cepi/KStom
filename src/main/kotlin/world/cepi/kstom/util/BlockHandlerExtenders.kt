package world.cepi.kstom.util

import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.utils.NamespaceID
import world.cepi.kstom.Manager

fun BlockHandler.register() = Manager.block.registerHandler(this.namespaceId) { this }
fun BlockHandler.register(namespace: String) = Manager.block.registerHandler(namespace) { this }
fun BlockHandler.register(namespace: NamespaceID) = Manager.block.registerHandler(namespace) { this }