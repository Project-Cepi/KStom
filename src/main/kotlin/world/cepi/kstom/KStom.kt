package world.cepi.kstom

import net.minestom.server.extensions.Extension
import net.minestom.server.extensions.Extension.LoadStatus.SUCCESS
import world.cepi.kstom.util.log

class KStom : Extension() {

    override fun initialize(): LoadStatus {
        log.info("[KStom] has been enabled!")
        return SUCCESS
    }

    override fun terminate() {
        log.info("[KStom] has been disabled!")
    }

}