package world.cepi.kstom.tree

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BranchTest : StringSpec({
    "branches should work correctly" {
        val basicNode = CombinationNode(0).also {
            it.addItemsToLastNodes(1)
            it.addItemsToLastNodes(2)
        } // Should be a tree of 0, 1, and 2

        basicNode.value shouldBe 0

        val lastNodes = basicNode.findLastNodes()

        lastNodes.size shouldBe 1

        val branchedNodes = basicNode.findLastNodes().map { it.branch() }

        branchedNodes.size shouldBe 1 // Only one combination
        branchedNodes[0].size shouldBe 2 // 2 objects in combination
    }
})