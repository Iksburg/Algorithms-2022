package lesson5

/**
 * Множество(таблица) с открытой адресацией на 2^bits элементов без возможности роста.
 */
class KtOpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {
    init {
        require(bits in 2..31)
    }

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    override var size: Int = 0

    private object Remove

    /**
     * Индекс в таблице, начиная с которого следует искать данный элемент
     */
    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    override fun contains(element: T): Boolean {
        var index = element.startingIndex()
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null && current != Remove) {
            if (current == element) {
                return false
            }
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        return true
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблице, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: [java.util.Set.remove] (Ctrl+Click по remove)
     *
     * Средняя
     */
    // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(N).

    override fun remove(element: T): Boolean {
        val firstIndex = element.startingIndex()
        var index = firstIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                storage[index] = Remove
                size--
                return true
            }
            index = (index + 1) % capacity
            if (index == firstIndex) continue
            current = storage[index]
        }
        return false
    }

    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */
    override fun iterator(): MutableIterator<T> = SetIterator()

    inner class SetIterator : MutableIterator<T> {
        private var index = 0
        private var numberOfElements = 0
        private var currentElement: T? = null

        // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(1).
        override fun hasNext(): Boolean = numberOfElements < size

        // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(N).
        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            while (storage[index] == null || storage[index] is Remove) index++
            currentElement = storage[index] as T?
            index++
            numberOfElements++
            return currentElement as T
        }

        // Ресурсоемкость алгоритма: O(1); Трудоемкость алгоритма: O(1).
        override fun remove() {
            if (currentElement == null || currentElement is Remove) throw IllegalStateException()
            storage[index - 1] = Remove
            numberOfElements--
            size--
        }
    }
}
