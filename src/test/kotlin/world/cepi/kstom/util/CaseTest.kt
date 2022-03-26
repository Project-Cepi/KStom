package world.cepi.kstom.util

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CaseTest : StringSpec({

    "pascal case to title case" {
        "pascalCase".pascalToTitle() shouldBe "Pascal Case"
    }

    "snake case to title case" {
        "snake_case".snakeToTitle() shouldBe "Snake Case"
    }

})