package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import world.cepi.kstom.adventure.asMini

class KLore internal constructor(private val tagResolver: TagResolver = TagResolver.empty()) {

    internal val list = mutableListOf<Component>()

    operator fun Component.unaryPlus() {
        list.add(this)
    }

    operator fun String.unaryPlus() {
        list.add(this.asMini(tagResolver))
    }

}