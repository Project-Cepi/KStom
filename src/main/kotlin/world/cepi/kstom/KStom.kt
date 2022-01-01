package world.cepi.kstom

import net.minestom.server.extensions.Extension
import net.minestom.server.extensions.Extension.LoadStatus.SUCCESS

class KStom : Extension() {

    override fun initialize(): LoadStatus {
        logger.info("[KStom] has been enabled!")
        return SUCCESS
    }

    override fun terminate() {
        logger.info("[KStom] has been disabled!")
    }

}