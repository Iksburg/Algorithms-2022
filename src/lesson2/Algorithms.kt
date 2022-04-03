@file:Suppress("UNUSED_PARAMETER")

package lesson2

import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
// Ресурсоемкость алгоритма: O(M*N); Трудоемкость алгоритма: O(M*N), M и N - длины строк.

fun longestCommonSubstring(first: String, second: String): String {
    if (first == "" || second == "") {
        return ""
    } else if (first == second) {
        return first
    } else {
        val intersectionMatrix: Array<Array<Int>> = Array(first.length + 1) { Array(second.length + 1) { 0 } }
        var maxLength = 0
        var indexOfMaxLength = 0
        for (i in 1 until intersectionMatrix.size) {
            for (j in 1 until intersectionMatrix[i].size) {
                if (first[i - 1] == second[j - 1]) {
                    if (i != 1 && j != 1) {
                        intersectionMatrix[i][j] = intersectionMatrix[i - 1][j - 1] + 1
                    } else {
                        intersectionMatrix[i][j] = 1
                    }
                    if (intersectionMatrix[i][j] > maxLength) {
                        maxLength = intersectionMatrix[i][j]
                        indexOfMaxLength = i
                    }
                }
            }
        }
        return if (maxLength != 0) {
            first.substring(indexOfMaxLength - maxLength, indexOfMaxLength)
        } else {
            ""
        }
    }
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
// Ресурсоемкость алгоритма: O(N); Трудоемкость алгоритма: O(Nlog(logN)).

fun calcPrimesNumber(limit: Int): Int {
    if (limit <= 1) {
        return 0
    } else {
        var result = 0
        val arrayOfPrimes = BooleanArray(limit + 1) { true }
        arrayOfPrimes[0] = false
        arrayOfPrimes[1] = false

        for (i in 2..floor(sqrt(limit.toDouble())).toInt()) {
            if (arrayOfPrimes[i]) {
                var p = 2
                while (i * p < arrayOfPrimes.size) {
                    if (arrayOfPrimes[i * p]) arrayOfPrimes[i * p] = false
                    p++
                }
            }
        }

        for (i in arrayOfPrimes.indices) {
            if (arrayOfPrimes[i]) result++
        }
        return result
    }
}
