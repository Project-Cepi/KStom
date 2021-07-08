package world.cepi.kstom.tree

class CombinationNode<T>(var value: T) {

    private val currentNodes: MutableList<CombinationNode<T>> = mutableListOf()

    val isLast: Boolean
        get() = currentNodes.isEmpty()

    fun addNode(vararg nodes: CombinationNode<T>) {

        nodes
            .let { currentNodes.addAll(nodes) }
    }

    fun addNode(vararg items: T) {
        addNode(*items.map { CombinationNode(it) }.toTypedArray())
    }

    /**
     * Generates all possible combinations for this node.
     *
     * Example:
     *
     * test
     * - testA
     * -- testC
     * -- testD
     * - testB
     *
     * will generate:
     *
     * test testA testC
     * test testA testD
     * test testB
     */
    fun traverseAndGenerate(): List<List<T>> {

        if (isLast) return emptyList()

        // currentNodes are test, with (testA / testB) and so forth
        val nodes: MutableList<List<T>> = mutableListOf()
        currentNodes.forEach {
            // test, traverse,
            if (it.isLast) {
                nodes.add(listOf(it.value))
            }

            nodes.addAll(it.traverseAndGenerate())
        }

        return nodes
    }

}