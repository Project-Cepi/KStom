package world.cepi.kstom.command.arguments.annotations

import net.minestom.server.item.Material

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultMaterial(val material: Material)
