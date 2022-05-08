package lesson4

import java.util.*


/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> = TrieIterator()

    inner class TrieIterator : MutableIterator<String> {
        private val listOfValues: MutableList<String> = ArrayList()
        private var counter = 0
        private var current: String? = null

        // Ресурсоемкость алгоритма: O(N); Трудоемкость алгоритма: O(N).
        init {
            adding(root, "")
        }

        private fun adding(node: Node?, element: String) {
            for (child in node!!.children.keys) {
                if (child == 0.toChar()) {
                    listOfValues.add(element)
                } else {
                    adding(node.children[child], element + child)
                }
            }
        }

        // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(1).
        override fun hasNext(): Boolean {
            return counter < listOfValues.size
        }

        // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(1).
        override fun next(): String {
            if (counter >= listOfValues.size) throw NoSuchElementException()
            current = listOfValues[counter]
            counter++
            return current as String
        }

        // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(1).
        override fun remove() {
            checkNotNull(current)
            remove(current)
            current = null
        }
    }

}