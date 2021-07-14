package world.cepi.kstom.tree

class CombinationNode<T>(val value: T, val parent: CombinationNode<T>? = null) {

    private val currentNodes: MutableList<CombinationNode<T>> = mutableListOf()

    val isEmpty: Boolean
        get() = currentNodes.isEmpty()

    fun addItems(vararg items: T) =
        currentNodes.addAll(items.map { CombinationNode(it, this) })

    fun addItemsToLastNodes(vararg items: T) = findLastNodes().forEach {
        it.addItems(*items)
    }

    fun findLastNodes(): List<CombinationNode<T>> {

        if (this.isEmpty) return listOf(this)

        return currentNodes.map {
            if (it.isEmpty) listOf(it)
            else it.findLastNodes()
        }.flatten()
    }


    /**
     * Generates all possible combinations for this node.
     *
     * Example:
     *
     * ```
     * $ testA
     * $$ testC
     * $$ testD
     * $$$ testE
     * $$$ testF
     * $ testB
     * ```
     * will generate:
     * ```
     * testA testC
     * testA testD testE
     * testA testD testF
     * testB
     * ```
     */
    fun traverseAndGenerate(): List<List<T>> =
        findLastNodes().map { it.branch() }


    fun branch(): List<T> {

        return parent?.branch()?.toMutableList().also { it?.add(this.value) } ?: emptyList()
    }

    override fun toString(): String = "CombinationNode[$value${
        if (currentNodes.isNotEmpty())
            " ${currentNodes.map { it.toString() }} "
        else ""
    }]"

}