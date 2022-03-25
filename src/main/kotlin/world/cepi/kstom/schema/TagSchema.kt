package world.cepi.kstom.schema

import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagWritable
import world.cepi.kstom.nbt.TagBoolean
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.safeCast

class DelegatedSingleSchemable<T>(val defaultValue: T?, val tagGenerator: (String) -> Tag<T>) {

    var tag: SingleSchemable<T>? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {

        if (tag == null) {
            tag = SingleSchemable(tagGenerator(property.name), defaultValue)
        }

        return tag!!.get((thisRef as Schema).block)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (tag == null) {
            tag = SingleSchemable(tagGenerator(property.name), defaultValue)
        }

        if (thisRef is Schema) {
            thisRef.block = tag!!.apply(thisRef.block, value)
        }
    }
}

class SingleSchemable<T>(val tag: Tag<T>, val defaultValue: T?): Schemable<T> {
    override fun apply(block: Block, value: T?) = block.withTag(tag, value ?: defaultValue)
    override fun apply(writer: TagWritable, value: T?) = writer.setTag(tag, value ?: defaultValue)
    override fun get(block: Block): T? = block.getTag(tag)
}

interface Schemable<T> {
    fun apply(block: Block, value: T?): Block
    fun apply(writer: TagWritable, value: T?)
    fun get(block: Block): T?
}

abstract class Schema {
    lateinit var block: Block

    fun grabPropTags(): List<Schemable<*>> {
        return this::class.declaredMemberProperties
            .asSequence()
            .onEach { it.isAccessible = true }
            .map { it.name to (it as KProperty1<in Schema, *>).getDelegate(this) }
            .map { it.first to DelegatedSingleSchemable::class.safeCast(it.second) }
            .filter { it.second != null }
            .map { it.second!!.tag ?: SingleSchemable(it.second!!.tagGenerator.invoke(it.first) as Tag<Any>, it.second!!.defaultValue as Any) }
            .toList()
    }
}

fun <T> schema(tag: Tag<T>, defaultValue: T?) = SingleSchemable(tag, defaultValue)

fun schemaInt(defaultValue: Int?) = DelegatedSingleSchemable(defaultValue) { Tag.Integer(it) }
fun schemaString(defaultValue: String?) = DelegatedSingleSchemable(defaultValue) { Tag.String(it) }
fun schemaFlag(defaultValue: Boolean?) = DelegatedSingleSchemable(defaultValue) { TagBoolean(it) }

fun <T : Schema> Block.applySchema(schema: T): Block =
    schema.grabPropTags().fold(this) { block, prop ->
        prop.apply(block, null)
    }

fun <T : Schema> Block.withSchema(clazz: KClass<T>, lambda: T.() -> Unit): Block {

    val constructor = try {
        clazz.java.getDeclaredConstructor()
    } catch (e: Exception) {
        throw NullPointerException("You need an empty constructor! (None Found)")
    }

    val instance = constructor.newInstance() as T

    instance.block = this

    lambda(instance)

    return instance.block
}

inline fun <reified T : Schema> Block.withSchema(noinline lambda: T.() -> Unit) = withSchema(T::class, lambda)