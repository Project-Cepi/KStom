package world.cepi.kstom

import net.minestom.server.MinecraftServer

fun old() {
    MinecraftServer.getSchedulerManager()
}

fun new() {
    Manager.scheduler
}