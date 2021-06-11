package world.cepi.kstom

import net.minestom.server.extensions.Extension

class KStom : Extension() {

    override fun initialize() {
        logger.info("[KStom] has been enabled!")
    }

    override fun terminate() {
        logger.info("[KStom] has been disabled!")
    }

}