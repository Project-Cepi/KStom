package world.cepi.kstom.command.arguments.generator

import net.minestom.server.command.builder.arguments.ArgumentBoolean
import net.minestom.server.command.builder.arguments.ArgumentLiteral
import net.minestom.server.command.builder.arguments.number.ArgumentInteger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.kstom.command.arguments.ArgumentPrintableGroup
import world.cepi.kstom.command.arguments.generation.generateSyntaxes

class GeneratorTest {

    sealed class Animal {
        class Cat(val whiskerCount: Int, val longNails: Boolean) : Animal()
        class Dog(val tailLength: Double) : Animal()
    }

    class FetchType(val toFetch: Boolean, val animal: Animal)

    @Test
    fun `basic class should generate correctly`() {
        val catSyntaxes = generateSyntaxes<Animal.Cat>().args

        assertEquals(1, catSyntaxes.size) // One combination
        assertEquals(2, catSyntaxes[0].size) // Two elements


        // Ensure Integer<whiskerCount>
        assertEquals(ArgumentInteger::class, catSyntaxes[0][0]::class)
        assertEquals("whiskerCount", catSyntaxes[0][0].id)

        // Ensure Boolean<longNails>
        assertEquals(ArgumentBoolean::class, catSyntaxes[0][1]::class)
        assertEquals("longNails", catSyntaxes[0][1].id)
    }

    @Test
    fun `semicomplex sealed structure should generate`() {
        val fullSyntaxes = generateSyntaxes<FetchType>().args

        assertEquals(2, fullSyntaxes.size)

        fullSyntaxes.forEach { // Ensure toFetch is present in each
            assertEquals(ArgumentBoolean::class, it[0]::class)
            assertEquals("toFetch", it[0].id)
            assertEquals(2, it.size)

            val firstGroup = it[1] as ArgumentPrintableGroup

            assertEquals(ArgumentLiteral::class, firstGroup[0]::class)
        }
    }

}