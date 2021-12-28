package world.cepi.kstom.command.arguments.generator

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import net.minestom.server.command.builder.arguments.ArgumentBoolean
import net.minestom.server.command.builder.arguments.ArgumentLiteral
import net.minestom.server.command.builder.arguments.number.ArgumentInteger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.kstom.command.arguments.ArgumentPrintableGroup
import world.cepi.kstom.command.arguments.generation.argumentsFromClass
import world.cepi.kstom.command.arguments.generation.generateSyntaxes

class GeneratorTest : StringSpec({
    "basic classes should generate correctly" {
        val catSyntaxes = argumentsFromClass<Animal.Cat>().arguments

        catSyntaxes.size shouldBe 1 // One combination
        catSyntaxes[0].size shouldBe 2 // Two elements


        // Ensure Integer<whiskerCount>
        catSyntaxes[0][0].shouldBeInstanceOf<ArgumentInteger>()
        catSyntaxes[0][0].id shouldBe "whiskerCount"

        // Ensure Boolean<longNails>
        catSyntaxes[0][1].shouldBeInstanceOf<ArgumentBoolean>()
        catSyntaxes[0][1].id shouldBe "longNails"
    }

    "sealed classes should generate correctly" {
        val fullSyntaxes = argumentsFromClass<FetchType>().arguments

        fullSyntaxes.size shouldBe 2

        fullSyntaxes.forEach { // Ensure toFetch is present in each
            it[0].shouldBeInstanceOf<ArgumentBoolean>()
            it[0].id shouldBe "toFetch"
            it.size shouldBe 2

            val firstGroup = it[1] as ArgumentPrintableGroup

            firstGroup[0].shouldBeInstanceOf<ArgumentLiteral>()
        }
    }
}) {

    sealed class Animal {
        class Cat(val whiskerCount: Int, val longNails: Boolean) : Animal()
        class Dog(val tailLength: Double) : Animal()
    }

    class FetchType(val toFetch: Boolean, val animal: Animal)

}