package level.`7`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Integer.min

fun main() {

}

class Level10 {
    fun `백준 7568`() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val writer = BufferedWriter(OutputStreamWriter(System.out))

        val n = reader.readLine().toInt()
        val dataList = mutableListOf<Pair<Int, Int>>()
        repeat(n) { dataList.add(reader.readLine().split(" ").map { it.toInt() }.let { it[0] to it[1] }) }

        val rank = IntArray(n) { 1 }
        for ((idx, data) in dataList.withIndex()) {
            dataList.forEach {
                if (data.first < it.first && data.second < it.second) {
                    rank[idx] += 1
                }
            }
        }

        rank.forEach { writer.write("$it ") }
        writer.flush()
    }

    val evenType = booleanArrayOf(true, false, true, false, true, false, true, false)
    val oddType = booleanArrayOf(false, true, false, true, false, true, false, true)

    fun `백준 1018`() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val writer = BufferedWriter(OutputStreamWriter(System.out))
        val (n, m) = reader.readLine().split(" ").map { it.toInt() }


        val data = mutableListOf<Boolean>()
        repeat(n) { data.addAll(reader.readLine().map { it != 'B' }) }
        println(data.toString())

        var minChange = Int.MAX_VALUE
        for (x in 0..m - 8) {
            for (y in 0..n - 8) {
                val start = x + (y * m)
                val end = x + (y * m) + 8

                var type1Change = 0
                var type2Change = 0
                for (j in 0 until 8) {
                    //            println(j)
                    println("x: $x, y: $y, j: $j range: ${start + j * m} ~ ${end + j * m}")
                    val sub = data.subList(start + j * m, end + j * m)

                    type1Change += numOfChages(sub, j)
                    type2Change += numOfChages(sub, j + 1)
                }
                val totalChange = min(type1Change, type2Change)
                if (minChange > totalChange) minChange = totalChange
            }
        }
        writer.write("$minChange")
        writer.flush()
    }

    private fun numOfChages(data: MutableList<Boolean>, lineNum: Int) =
        data.foldIndexed(0) { idx, acc, b ->
            val targetType = when (lineNum % 2) {
                0 -> evenType
                else -> oddType
            }
            if (b != targetType[idx]) acc + 1
            else acc + 0
        }

    fun `백준 1436`() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val writer = BufferedWriter(OutputStreamWriter(System.out))

        val n = reader.readLine().toInt()
        var cnt = 0
        for (i in 1..Int.MAX_VALUE) {
            if (i.toString().contains("666")) cnt += 1
            if (cnt == n) {
                writer.write(i.toString())
                break
            }
        }
        writer.flush()
    }
}