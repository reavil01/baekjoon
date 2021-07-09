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
//    repeat(n) { data.add(reader.readLine().toInt()) }

    val data = mutableListOf<Int>()
    val n = 10000
    repeat(n) { data.add(nextInt(0, 1000000)) }
//    val data = mutableListOf<Int>(1)
//    val data = mutableListOf<Int>(5, 2, 7, 43, 45, 1, 54, 5347, 98, 324, 6, 8, 65, 4568)

    val ans = data.toMutableList()
    ans.sort()
    println(data)

//    println("total data size: ${data.size}")
//    println("total data last idx: ${data.lastIndex}")
    val min = min(n, 2 * 2 * 2 * 2)
    timSort(data, min)

    if(ans == data) {
        println("정답!")
    } else {
        println("오답!")
    }
//    data.forEach { writer.write("$it\n") }
//    writer.flush()
}

private fun timSort(data: MutableList<Int>, min: Int) {
    var start = 0
    var end = min
    val stack = mutableListOf<Pair<Int, Int>>()
    while (start < data.size) {
        val a = makeRun(data, start, end, min)
        insertStack(data, stack, start to a.first - 1)
        start = a.first
        end = a.second
    }

    // 스택에 남아있는 나머지 정리
    while (stack.size > 1) {
        val aRun = stack.pop()
        val bRun = stack.pop()
        merge(data, bRun, aRun)
        insertStack(data, stack, bRun.first to aRun.second)
    }
}

private fun Pair<Int, Int>.size() = this.second - this.first
private fun MutableList<Pair<Int, Int>>.pop() = run {
    val last = this.last()
    this.removeAt(lastIndex)
    last
}

private fun insertStack(data: MutableList<Int>, stack: MutableList<Pair<Int, Int>>, range: Pair<Int, Int>) {
//    println("\nRANGE: $range")
//    println("Insert Run: $range")
    println("[INS STACK] Current Stack: $stack, insert range: $range")

    when (stack.size) {
        0 -> stack.add(range)
        1 -> {
            val rightRun = range
            val leftRun = stack.pop()
            val rightSize = rightRun.size()
            val leftSize = leftRun.size()

            if (leftSize <= rightSize) {
                merge(data, leftRun, rightRun)
                stack.add(leftRun.first to rightRun.second)
            } else {
                stack.add(leftRun)
                stack.add(rightRun)
            }
        }
        else -> {
            val rightRun = range
            val midRun = stack.pop()
            val leftRun = stack.pop()

            val rightSize = rightRun.size()
            val midSize = midRun.size()
            val leftSize = leftRun.size()

            println("\n left $leftRun, mid: $midRun, right: $rightRun")
            println("left size: $leftSize, mid size: $midSize, right size: $rightSize")

            if (!(midSize > rightSize && leftSize > midSize + rightSize)) {
                // a c 중 크기가 작은 run과 b 병합
                if (leftSize < rightSize) { // c + b
                    merge(data, leftRun, midRun)
                    insertStack(data, stack, leftRun.first to midRun.second)
                    insertStack(data, stack, rightRun)
                } else { // b + a
                    merge(data, midRun, rightRun)
                    insertStack(data, stack, leftRun)
                    insertStack(data, stack, midRun.first to rightRun.second)
                }

            }else {
                stack.add(leftRun)
                stack.add(midRun)
                stack.add(rightRun)
            }

        }
    }
    println("[INS STACK] new stack: $stack")
}


private fun merge(data: MutableList<Int>, leftRun: Pair<Int, Int>, rightRun: Pair<Int, Int>) {
    val leftSize = leftRun.size()
    val rightSize = rightRun.size()

    println("[Merge 2 runs] left run: $leftRun, right run: $rightRun")
    println("[Merge 2 runs] before: $data")
    if (leftSize < rightSize)
        mergeHi(data, leftRun, rightRun)
    else
        mergeLo(data, leftRun, rightRun)

    println("[Merge 2 runs] after: $data")
}


private fun mergeLo(data: MutableList<Int>, leftRun: Pair<Int, Int>, rightRun: Pair<Int, Int>) {
    println("[Merge Lo] Run left: $leftRun, right: $rightRun")
    val copy = data.subList(leftRun.first, leftRun.second + 1).toMutableList()

    var idx = leftRun.first
    var leftIndex = 0
    var rightIndex = rightRun.first

    println("Before sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
    while (leftIndex <= copy.lastIndex && rightIndex <= rightRun.second) {
        if (data[rightIndex] < copy[leftIndex]) {
            data[idx] = data[rightIndex]
            rightIndex++
        } else {
            data[idx] = copy[leftIndex]
            leftIndex++
        }
        idx++
    }

    while (leftIndex <= copy.lastIndex) {
        data[idx] = copy[leftIndex]
        idx++
        leftIndex++
    }

    while (rightIndex <= rightRun.second) {
        data[idx] = data[rightIndex]
        idx++
        rightIndex++
    }

    println("After sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
}

private fun mergeHi(data: MutableList<Int>, leftRun: Pair<Int, Int>, rightRun: Pair<Int, Int>) {
    println("[Merge Hi] Run left: $leftRun, right: $rightRun")
    val copy = data.subList(rightRun.first, rightRun.second + 1).toMutableList()

    var idx = rightRun.second
    var leftIndex = leftRun.second
    var rightIndex = copy.lastIndex

    println("Before sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
    while (leftIndex >= leftRun.first && rightIndex >= 0) {
        if (data[leftIndex] < copy[rightIndex]) {
            data[idx] = copy[rightIndex]
            rightIndex--
        } else {
            data[idx] = data[leftIndex]
            leftIndex--
        }
        idx--
    }

    while (leftIndex >= leftRun.first) {
        data[idx] = data[leftIndex]
        idx--
        leftIndex--
    }

    while (rightIndex >= 0) {
        data[idx] = copy[rightIndex]
        idx--
        rightIndex--
    }

    println("After sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
}

private fun makeRun(data: MutableList<Int>, start: Int, end: Int, min: Int): Pair<Int, Int> {
    var lastIdx = when {
        end > data.lastIndex -> data.lastIndex
        else -> end
    }

    val asc = Comparator<Int> { a, b ->
        when {
            a < b -> 1
            a > b -> -1
            else -> 0
        }
    }
    val desc = Comparator<Int> { a, b ->
        when {
            a > b -> 1
            a < b -> -1
            else -> 0
        }
    }

//    println("[MAKE RUN] start: $start, end: $end, start val: ${data[start]}")

//    if(d)
    val comparator =
        if (data[start] <= data[lastIdx]) {
//            print("ASC min: ")
            asc
        } else {
//            print("DESC min: ")
            desc
        }
    binaryInsertionSort(data.subList(start, lastIdx + 1), comparator)

    for (j in end until data.lastIndex) {
        if (comparator.compare(data[j], data[j + 1]) >= 0) lastIdx++
        else break
    }

//    println(data.lastIndex)
//    println("[MAKE RUN] last idx: $lastIdx")
//    println("b reverse $data")
    if (data[start] > data[lastIdx]) { // DESC to ASC
//        if (lastIdx < data.lastIndex) {
            data.subList(start, lastIdx + 1).reverse()
//        } else {
//            data.subList(start, data.lastIndex + 1).reverse()
//        }
    }
//    println("[MAKE RUN] SORTED: $data")

//    println("max: $data")
//    val r = if (lastIdx + min > data.lastIndex) data.lastIndex else lastIdx + min
    return (lastIdx + 1) to lastIdx + min
}

private fun binaryInsertionSort(data: MutableList<Int>, c: Comparator<Int>) {
    for (i in 1 until data.size) {
        val key = data[i]
        val idx = findIndexViaBinarySearch(data.subList(0, i + 1), key, c)
        shiftAndInsert(data.subList(0, i + 1), idx, key)
    }
}

private fun shiftAndInsert(data: MutableList<Int>, index: Int, value: Int) {
    for (i in data.lastIndex downTo index + 1) {
        data[i] = data[i - 1]
    }
    data[index] = value
}

private fun findIndexViaBinarySearch(data: MutableList<Int>, value: Int, c: Comparator<Int>): Int {
    var start = 0
    var end = data.lastIndex

    while (start < end) {
        val idx = (start + end) / 2
        if (c.compare(value, data[idx]) < 0) {
            start = idx + 1
        } else end = idx
    }
    return end
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