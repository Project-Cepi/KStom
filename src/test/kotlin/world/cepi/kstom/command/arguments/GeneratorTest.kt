package world.cepi.kstom.command.arguments

import org.junit.jupiter.api.Test

class GeneratorTest {

    sealed class Animal {
        sealed class Cat(whiskerCount: Int)
        sealed class Dog(tailLength: Double)
    }

    @Test
    fun `ensure sealed classes generate correctly`() {
        argumentsFromClass<Animal>()
    }

}