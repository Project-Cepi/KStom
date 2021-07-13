package world.cepi.kstom.command.arguments

import org.junit.jupiter.api.Test

class GeneratorTest {

    sealed class Animal {
        sealed class Cat(whiskerCount: Int) : Animal()
        sealed class Dog(tailLength: Double) : Animal()
    }

    class FetchType(val toFetch: Boolean, val animal: Animal)

    @Test
    fun `ensure sealed classes generate correctly`() {
        println(argumentFromClass<FetchType>().args)
    }

}