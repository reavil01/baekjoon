import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sqrt

val reader = System.`in`.bufferedReader()
val writer = System.out.bufferedWriter()

fun main() {

}

class Level21 {
    fun `백준 1920번`() {
        val n = reader.readLine().toInt()
        val arr = reader.readLine().split(" ").map { it.toInt() }
        val m = reader.readLine().toInt()
        val queries = reader.readLine().split(" ").map { it.toInt() }

        val sortedArr = arr.sorted()
        for (query in queries) {
            val ans = if (sortedArr.containsViaBinarySearch(query)) 1 else 0
            writer.write("$ans\n")
        }
        writer.flush()
    }

    fun List<Int>.containsViaBinarySearch(value: Int): Boolean {
        if (this.isEmpty()) return false

        val pivot = this.size / 2
        if (pivot == 0) return this[pivot] == value

        if (this[pivot] == value) return true
        return if (this[pivot] > value) {
            this.subList(0, pivot).containsViaBinarySearch(value)
        } else {
            this.subList(pivot, this.size).containsViaBinarySearch(value)
        }
    }

    fun `백준 10816번`() {
        val n = reader.readLine().toInt()
        val arr = reader.readLine().split(" ").map { it.toInt() }
        val m = reader.readLine().toInt()
        val queries = reader.readLine().split(" ").map { it.toInt() }

        val count = arr.groupBy { it }.mapValues { it.value.size }
        for (query in queries) {
            val ans = count[query] ?: 0
            writer.write("$ans ")
        }

        writer.flush()
    }

    fun `백준 10816번 - lowerBound 이용, 시간초과`() {
        val n = reader.readLine().toInt()
        val arr = reader.readLine().split(" ").map { it.toInt() }
        val m = reader.readLine().toInt()
        val queries = reader.readLine().split(" ").map { it.toInt() }

        val sortedArr = arr.sorted()
        for (query in queries) {
            val a = sortedArr.lowerBound(query)
            val b = sortedArr.lowerBound(query + 1)
            val ans = b - a
            writer.write("$ans ")
        }

        writer.flush()
    }

    fun List<Int>.lowerBound(value: Int): Int {
        if (this.isEmpty()) return 0

        val pivot = this.size / 2
        if (pivot == 0) return if (this[pivot] < value) 1 else 0

        return if (this[pivot] >= value) {
            this.subList(0, pivot).lowerBound(value)
        } else {
            pivot + this.subList(pivot, this.size).lowerBound(value)
        }
    }

    fun `백준 1654번`() {
        val (k, n) = reader.readLine().split(" ").map { it.toInt() }
        val len = Array(k) { reader.readLine().toLong() }
        len.sort()

        val ans = findMaximumLength(len, n, 1, len.last())
        writer.write("$ans")
        writer.flush()
    }

    fun findMaximumLength(len: Array<Long>, n: Int, start: Long, end: Long): Long {
        val mid = (end + start) / 2
        if (start == end) return start
        if (end - start == 1L) {
            return if (howManyMakeLANs(len, end) >= n) end else start
        }

        val a = howManyMakeLANs(len, mid)
        return if (a >= n) {
            findMaximumLength(len, n, mid, end)
        } else {
            findMaximumLength(len, n, start, mid)
        }
    }

    fun howManyMakeLANs(len: Array<Long>, value: Long): Long {
        if (value == 0L) return 0

        var cnt = 0L
        for (l in len) {
            cnt += l / value
        }
        return cnt
    }

    fun `백준 2805번`() {
        val param = reader.readLine().split(" ")
        val n = param[0].toInt()
        val m = param[1].toLong()
        val height = reader.readLine().split(" ").map { it.toInt() }
        val sortedHeight = height.sorted()

        val ans = findMinimumHeight(sortedHeight, m, 0, sortedHeight.last())
        writer.write("$ans")
        writer.flush()
    }

    fun findMinimumHeight(heightArr: List<Int>, n: Long, start: Int, end: Int): Int {
        val mid = (end + start) / 2

        if (start == end) return start
        if (end - start == 1) {
            return if (isSatisfiedMeters(heightArr, end) == n) end else start
        }

        val meter = isSatisfiedMeters(heightArr, mid)
        return when {
            meter == n -> mid
            meter > n -> findMinimumHeight(heightArr, n, mid, end)
            else -> findMinimumHeight(heightArr, n, start, mid)
        }
    }

    fun isSatisfiedMeters(heightArr: List<Int>, height: Int): Long {
        return heightArr.fold(0L) { acc, it -> acc + (it - height).coerceAtLeast(0) }
    }

    fun `백준 2110번`() {
        val (n, c) = reader.readLine().split(" ").map { it.toInt() }
        val position = Array(n) { reader.readLine().toInt() }
        position.sort()

        val ans = setWifi(position, 1, position.last(), c)
        writer.write("$ans")
        writer.flush()
    }

    fun setWifi(position: Array<Int>, s: Int, e: Int, remain: Int): Int {
        if (s > e) return -1
        if (e - s == 1) {
            return max(setWifi(position, s, s, remain), setWifi(position, e, e, remain))
        }

        val range = (e + s) / 2
        var startIdx = 0
        var cnt = 1
        for (i in 1..position.lastIndex) {
            val d = position[i] - position[startIdx]
            if (d >= range) {
                cnt++
                startIdx = i
            }
        }

        return if (cnt >= remain) {
            if (s == e) range
            else max(range, setWifi(position, range, e, remain))
        } else {
            if (s == e) -1
            else setWifi(position, s, range, remain)
        }
    }
}