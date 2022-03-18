package world.cepi.kstom.schema

import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagWritable
import world.cepi.kstom.nbt.TagBoolean
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class DelegatedSingleSchemable<T>(val defaultValue: T?, val tagGenerator: (String) -> Tag<T>) {

    var tag: SingleSchemable<T>? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): SingleSchemable<T> {

        if (tag == null) {
            tag = SingleSchemable(tagGenerator(property.name), defaultValue)
        }

        return tag!!
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class SingleSchemable<T>(val tag: Tag<T>, val defaultValue: T?): Schemable {
    override fun apply(block: Block) = block.withTag(tag, defaultValue)
    override fun apply(writer: TagWritable) = writer.setTag(tag, defaultValue)
}

interface Schemable {
    fun apply(block: Block): Block
    fun apply(writer: TagWritable)
}

abstract class Schema {
    protected open val properties: List<Schemable>? = null

    fun grabProps(): List<Schemable> {
        return properties ?: this::class.memberProperties.filterIsInstance<Schemable>()
    }
}

fun <T> schema(tag: Tag<T>, defaultValue: T?) = SingleSchemable(tag, defaultValue)

fun schemaInt(defaultValue: Int?) = DelegatedSingleSchemable(defaultValue) { Tag.Integer(it) }
fun schemaString(defaultValue: String?) = DelegatedSingleSchemable(defaultValue) { Tag.String(it) }
fun schemaFlag(defaultValue: Boolean?) = DelegatedSingleSchemable(defaultValue) { TagBoolean(it) }

fun <T : Schema> Block.applySchema(schema: T): Block =
    schema.grabProps().fold(this) { block, prop ->
        prop.apply(block)
    }

fun <T : Schema> Block.withSchema(clazz: KClass<T>) {}