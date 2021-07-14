package world.cepi.kstom.tree

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BranchTest {

    @Test
    fun `ensure branch method works correctly`() {
        val basicNode = CombinationNode(0).also {
            it.addItemsToLastNodes(1)
            it.addItemsToLastNodes(2)
        } // Should be a tree of 0, 1, and 2

        assertEquals(0, basicNode.value)

        val lastNodes = basicNode.findLastNodes()

        assertEquals(1, lastNodes.size)

        val branchedNodes = basicNode.findLastNodes().map { it.branch() }

        assertEquals(1, branchedNodes.size) // Only one combination
        assertEquals(2, branchedNodes[0].size) // 2 objects in combination
    }

}