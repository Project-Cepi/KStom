package world.cepi.kstom.event

import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventListener
import net.minestom.server.event.entity.EntityTickEvent
import net.minestom.server.potion.PotionEffect
import world.cepi.kstom.Manager

const val extensionName = "TestExtension"

fun old() {
    MinecraftServer.getGlobalEventHandler().addListener(EntityTickEvent::class.java) {
        it.entity.removeEffect(PotionEffect.ABSORPTION)
    }

    MinecraftServer.getExtensionManager().getExtension(extensionName)?.eventNode?.addListener(EntityTickEvent::class.java) {
        it.entity.removeEffect(PotionEffect.ABSORPTION)
    }

    MinecraftServer.getGlobalEventHandler().addListener(
        EventListener.builder(EntityTickEvent::class.java)
            .expireCount(50)
            .expireWhen { event -> event.entity.isCustomNameVisible }
            .filter { event -> event.entity.isGlowing }
            .handler { event -> event.entity.setGravity(5.0, .5) }
            .build()
    )
}

fun new() {
    Manager.globalEvent.listenOnly<EntityTickEvent> {
        entity.removeEffect(PotionEffect.ABSORPTION)
    }

    Manager.extension.getExtension(extensionName)?.eventNode?.listenOnly<EntityTickEvent> {
        entity.removeEffect(PotionEffect.ABSORPTION)
    }

    Manager.globalEvent.listen<EntityTickEvent> {
        expireCount = 50
        removeWhen { entity.isCustomNameVisible }
        filters += { entity.isGlowing }
        handler {
            entity.setGravity(5.0, .5)
        }
    }
}