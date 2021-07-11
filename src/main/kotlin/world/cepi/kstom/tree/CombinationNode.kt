package world.cepi.kstom.tree

class CombinationNode<T>(var value: T) {

    private val currentNodes: MutableList<CombinationNode<T>> = mutableListOf()

    val isEmpty: Boolean
        get() = currentNodes.isEmpty()

    fun addNode(vararg nodes: CombinationNode<T>) =
        currentNodes.addAll(nodes)

    fun addNode(vararg items: T) =
        addNode(*items.map { CombinationNode(it) }.toTypedArray())

    fun addToLastNodes(vararg nodes: CombinationNode<T>) = findLastNodes().forEach {
        it.addNode(*nodes)
    }

    fun addToLastNodes(vararg items: T) = findLastNodes().forEach {
        it.addNode(*items)
    }

    fun findLastNodes(): List<CombinationNode<T>> = currentNodes.map {
        if (it.isEmpty) listOf(it)
        else it.findLastNodes()
    }.flatten()


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
        return currentNodes.map {
            // test, traverse,
            if (it.isEmpty) {
                listOf(it.value)
            }

            it.traverseAndGenerate()
        }.flatten().let { generatedMap ->
            mutableListOf(listOf(this.value)).also { it.addAll(generatedMap) }
        }
    }

    override fun toString(): String = "CombinationNode[$value - [${currentNodes.map { it.toString() }}]]"

}