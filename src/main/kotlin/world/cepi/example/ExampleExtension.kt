package world.cepi.example

import net.minestom.server.extensions.Extension;

class ExampleExtension : Extension() {

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

}