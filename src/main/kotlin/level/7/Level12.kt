package level.`7`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Integer.min
import java.util.*
import kotlin.random.Random.Default.nextInt

fun main() {
//    val reader = BufferedReader(InputStreamReader(System.`in`))
//    val writer = BufferedWriter(OutputStreamWriter(System.out))
//    val n = reader.readLine().toInt()
//    val data = mutableListOf<Int>()
//    repeat(n) { data.add(reader.readLine().toInt()) }
//
//    val min = min(n, 2 * 2 * 2 * 2)
//    val timsort = TimSort()
//    timsort.sort(data, min)
//
//    data.forEach { writer.write("$it\n") }
//    writer.flush()
    timosrtTest()
}

fun timosrtTest() {
    val data = mutableListOf<Int>()
    val n = 10000
    repeat(n) { data.add(nextInt(0, 1000000)) }

    val ans = data.toMutableList()
    ans.sort()

    val min = min(n, 2 * 2 * 2 * 2)
    val timsort = TimSort()
    timsort.sort(data, min)

    if (ans == data) {
        println("정답!")
    } else {
        println("오답!")
    }
}


class Level12 {
    fun `백준 2750`() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val writer = BufferedWriter(OutputStreamWriter(System.out))

        val data = mutableListOf<Int>()
        val n = reader.readLine().toInt()
        repeat(n) { data.add(reader.readLine().toInt()) }

        data.sort()
        data
            .asSequence()
            .forEach { writer.write("$it\n") }
        writer.flush()
    }

    private fun insertionSortAsc(data: MutableList<Int>) {
        for (i in 1 until data.size) {
            val key = data[i]
            for (j in i - 1 downTo 0) {
                if (key < data[j]) {
                    data[j + 1] = data[j]
                    data[j] = key
                } else {
                    break
                }
            }
        }
    }

    private fun binaryInsertionSort(data: MutableList<Int>) {
        for (i in 1 until data.size) {
            val key = data[i]
            val idx = findIndexViaBinarySearch(data.subList(0, i + 1), key)
            shiftAndInsert(data.subList(0, i + 1), idx, key)
        }
    }

    private fun shiftAndInsert(data: MutableList<Int>, index: Int, value: Int) {
        for (i in data.lastIndex downTo index + 1) {
            data[i] = data[i - 1]
        }
        data[index] = value
    }

    private fun findIndexViaBinarySearch(data: MutableList<Int>, value: Int): Int {
        var start = 0
        var end = data.lastIndex

        while (start < end) {
            val idx = (start + end) / 2
            if (value > data[idx]) {
                start = idx + 1
            } else end = idx
        }
        return end
    }
}