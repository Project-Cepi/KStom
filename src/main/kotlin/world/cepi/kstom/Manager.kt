package world.cepi.kstom

import net.minestom.server.MinecraftServer
import net.minestom.server.UpdateManager
import net.minestom.server.advancements.AdvancementManager
import net.minestom.server.adventure.bossbar.BossBarManager
import net.minestom.server.command.CommandManager
import net.minestom.server.data.DataManager
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.exception.ExceptionManager
import net.minestom.server.extensions.ExtensionManager
import net.minestom.server.gamedata.loottables.LootTableManager
import net.minestom.server.gamedata.tags.TagManager
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.BlockManager
import net.minestom.server.listener.manager.PacketListenerManager
import net.minestom.server.monitoring.BenchmarkManager
import net.minestom.server.network.ConnectionManager
import net.minestom.server.recipe.RecipeManager
import net.minestom.server.scoreboard.TeamManager
import net.minestom.server.storage.StorageManager
import net.minestom.server.timer.SchedulerManager
import net.minestom.server.world.biomes.BiomeManager

/**
 * Shorthand utilities for MinecraftServer
 */
object Manager {

    val command: CommandManager
        get() = MinecraftServer.getCommandManager()

    val block: BlockManager
        get() = MinecraftServer.getBlockManager()

    val instance: InstanceManager
        get() = MinecraftServer.getInstanceManager()

    val advancement: AdvancementManager
        get() = MinecraftServer.getAdvancementManager()

    val packetListener: PacketListenerManager
        get() = MinecraftServer.getPacketListenerManager()

    val recipe: RecipeManager
        get() = MinecraftServer.getRecipeManager()

    val benchmark: BenchmarkManager
        get() = MinecraftServer.getBenchmarkManager()

    val biome: BiomeManager
        get() = MinecraftServer.getBiomeManager()

    val bossBar: BossBarManager
        get() = MinecraftServer.getBossBarManager()

    val connection: ConnectionManager
        get() = MinecraftServer.getConnectionManager()

    val data: DataManager
        get() = MinecraftServer.getDataManager()

    val storage: StorageManager
        get() = MinecraftServer.getStorageManager()

    val tag: TagManager
        get() = MinecraftServer.getTagManager()

    val team: TeamManager
        get() = MinecraftServer.getTeamManager()

    val update: UpdateManager
        get() = MinecraftServer.getUpdateManager()

    val exception: ExceptionManager
        get() = MinecraftServer.getExceptionManager()

    val lootTable: LootTableManager
        get() = MinecraftServer.getLootTableManager()

    val extension: ExtensionManager
        get() = MinecraftServer.getExtensionManager()

    val scheduler: SchedulerManager
        get() = MinecraftServer.getSchedulerManager()

    val globalEvent: GlobalEventHandler
        get() = MinecraftServer.getGlobalEventHandler()

}