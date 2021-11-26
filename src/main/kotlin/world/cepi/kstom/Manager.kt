package world.cepi.kstom

import net.kyori.adventure.bossbar.BossBar
import net.minestom.server.MinecraftServer
import net.minestom.server.MinecraftServer.*
import net.minestom.server.UpdateManager
import net.minestom.server.advancements.AdvancementManager
import net.minestom.server.adventure.bossbar.BossBarManager
import net.minestom.server.command.CommandManager
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.exception.ExceptionManager
import net.minestom.server.extensions.ExtensionManager
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.BlockManager
import net.minestom.server.listener.manager.PacketListenerManager
import net.minestom.server.monitoring.BenchmarkManager
import net.minestom.server.network.ConnectionManager
import net.minestom.server.recipe.RecipeManager
import net.minestom.server.scoreboard.TeamManager
import net.minestom.server.timer.SchedulerManager
import net.minestom.server.world.DimensionTypeManager
import net.minestom.server.world.biomes.BiomeManager

/**
 * Shorthand utilities for MinecraftServer
 */
object Manager {

    val packetListener: PacketListenerManager get() = getPacketListenerManager()
    val exception: ExceptionManager get() = getExceptionManager()
    val connection: ConnectionManager get() = getConnectionManager()
    val instance: InstanceManager get() = getInstanceManager()
    val block: BlockManager get() = getBlockManager()
    val command: CommandManager get() = getCommandManager()
    val recipe: RecipeManager get() = getRecipeManager()
    val team: TeamManager get() = getTeamManager()
    val scheduler: SchedulerManager get() = getSchedulerManager()
    val benchmark: BenchmarkManager get() = getBenchmarkManager()
    val dimensionType: DimensionTypeManager get() = getDimensionTypeManager()
    val biome: BiomeManager get() = getBiomeManager()
    val advancement: AdvancementManager get() = getAdvancementManager()
    val bossBar: BossBarManager get() = getBossBarManager()
    val extension: ExtensionManager get() = getExtensionManager()
    val update: UpdateManager get() = getUpdateManager()
    val globalEvent: GlobalEventHandler get() = getGlobalEventHandler()

}