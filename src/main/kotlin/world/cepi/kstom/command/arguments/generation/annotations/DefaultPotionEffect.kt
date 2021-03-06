package world.cepi.kstom.command.arguments.generation.annotations

import net.minestom.server.potion.PotionEffect

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultPotionEffect(val potionEffect: PotionEffect)
