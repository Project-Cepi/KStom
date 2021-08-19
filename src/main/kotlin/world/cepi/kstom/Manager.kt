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
public object Manager {

    public val command: CommandManager
        get() = MinecraftServer.getCommandManager()

    public val block: BlockManager
        get() = MinecraftServer.getBlockManager()

    public val instance: InstanceManager
        get() = MinecraftServer.getInstanceManager()

    public val advancement: AdvancementManager
        get() = MinecraftServer.getAdvancementManager()

    public val packetListener: PacketListenerManager
        get() = MinecraftServer.getPacketListenerManager()

    public val recipe: RecipeManager
        get() = MinecraftServer.getRecipeManager()

    public val benchmark: BenchmarkManager
        get() = MinecraftServer.getBenchmarkManager()

    public val biome: BiomeManager
        get() = MinecraftServer.getBiomeManager()

    public val bossBar: BossBarManager
        get() = MinecraftServer.getBossBarManager()

    public val connection: ConnectionManager
        get() = MinecraftServer.getConnectionManager()

    public val data: DataManager
        get() = MinecraftServer.getDataManager()

    public val storage: StorageManager
        get() = MinecraftServer.getStorageManager()

    public val tag: TagManager
        get() = MinecraftServer.getTagManager()

    public val team: TeamManager
        get() = MinecraftServer.getTeamManager()

    public val update: UpdateManager
        get() = MinecraftServer.getUpdateManager()

    public val exception: ExceptionManager
        get() = MinecraftServer.getExceptionManager()

    public val extension: ExtensionManager
        get() = MinecraftServer.getExtensionManager()

    public val scheduler: SchedulerManager
        get() = MinecraftServer.getSchedulerManager()

    public val globalEvent: GlobalEventHandler
        get() = MinecraftServer.getGlobalEventHandler()

}