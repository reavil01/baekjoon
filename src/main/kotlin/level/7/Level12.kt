package level.`7`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Integer.min
import kotlin.random.Random.Default.nextInt

fun main() {

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

    fun `백준 10989`() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val writer = BufferedWriter(OutputStreamWriter(System.out))
        val sorted = IntArray(9999) { 0 }
        val n = reader.readLine().toInt()

        repeat(n) {
            val v = reader.readLine().toInt()
            sorted[v - 1] += 1
        }


        sorted.forEachIndexed { idx, v ->
            for (i in 1..v) {
                writer.write("${idx + 1}\n")
            }
        }
        writer.flush()
    }

    fun `백준 2108`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = mutableListOf<Int>()

        repeat(n) {
            data.add(
                reader.readLine().toInt()
            )
        }


        val mean = data.average()
        val maxCnt = data.groupingBy { it }.eachCount().maxOf { it.value }
        val modes = data.groupingBy { it }.eachCount().filter { it.value == maxCnt }.keys
        val mode = when (modes.size) {
            1 -> modes.first()
            else -> modes.sorted()[1]
        }

        data.sort()
        val media = data[n / 2]
        val diff = data.last() - data.first()

        writer.write("%.0f".format(mean) + "\n")
        writer.write("$media\n")
        writer.write("$mode\n")
        writer.write("$diff\n")
        writer.flush()
    }
}