package level.`7`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.time.seconds

fun main() {
//    val reader = BufferedReader(InputStreamReader(System.`in`))
//    val writer = BufferedWriter(OutputStreamWriter(System.out))
//
//    val data = mutableListOf<Int>()
//    val n = reader.readLine().toInt()
//    repeat(n) { data.add(reader.readLine().toInt()) }

//    val data = mutableListOf<Int>(5, 2, 7, 43, 45, 1, 54, 5347, 98, 324, 6, 8, 65, 4568)
    val data = mutableListOf<Int>(
        1, 2, 3, 4, 5, 6, 20,
        19, 15, 18, 17, 13, 12, 11,
        30, 31, 32, 38, 40,
        21, 14, 90
    )
//    binaryInsertionSort(data)
//    println(data)
//    binaryInsertionSortDesc(data)
//    println(data)
//    if (c.compare(pivot, a[mid]) < 0)
//        right = mid;
//    else
//        left = mid + 1;
    println("total data size: ${data.size}")
    println("total data last idx: ${data.lastIndex}")
    blockSort(data, 4)
    println(data)

//    binaryInsertionSort(data, asc)
//    println(data)
//    binaryInsertionSort(data, desc)
//    println(data)

//    val com = Comparator { o1, o2 -> if(o1 > o2) 0 else 1 }

//    data.forEach { writer.write("$it\n") }
//    writer.flush()
}

private fun blockSort(data: MutableList<Int>, min: Int) {
    var start = 0
    var end = min
    val stack = mutableListOf<Pair<Int, Int>>()
    while (start < data.size) {
        val a = makeRun(data, start, end, min)
        insertStack(data, stack, start to a.first-1)
        start = a.first
        end = a.second
        println(start)
        println(end)
    }

//    if (start < data.lastIndex) {
//        if (data[start] <= data[start + 1]) {
//            binaryInsertionSort(data.subList(start, data.size), asc) // ASC
//        } else {
//            binaryInsertionSort(data.subList(start, data.size), desc) // DESC
//        }
//    }
}


private fun insertStack(data: MutableList<Int>, stack: MutableList<Pair<Int, Int>>, range: Pair<Int, Int>) {
    println("\nRANGE: $range")
    if(stack.size < 2) stack.add(range)
    else {
        val aRun = range
        val bRun = stack.last()
        val cRun = stack[stack.lastIndex-1]

        val aSize = range.second - range.first
        val bSize = bRun.second - bRun.first
        val cSize = cRun.second - cRun.first

        println("a $aRun, b: $bRun, c: $cRun")
        println("a size: $aSize, b size: $bSize, c size: $cSize")
        // condition 1
        if (!(bSize > aSize && cSize > bSize + aSize)) {
            // a c 중 크기가 작은 run과 b 병합
            if(aSize < cSize) {
                if(bSize < aSize)
                    mergeLo(data, stack, aRun)
                else
                    mergeHi(data, stack, aRun)
            } else {
                if(bSize > cSize)
                    mergeLo(data, stack, cRun)
                else
                    mergeHi(data, stack, cRun)
            }
        }
        stack.add(range)

    }
}

private fun mergeLo(data: MutableList<Int>, stack: MutableList<Pair<Int, Int>>, range: Pair<Int, Int>) {
    val leftRun = range
    val rightRun = stack.last()
    stack.removeAt(stack.lastIndex)

    println("Run left: $leftRun, right: $rightRun")
    val copy = data.subList(leftRun.first, leftRun.second+1).toMutableList()

    var idx = leftRun.first
    var leftIndex = 0
    var rightIndex = rightRun.first

    println("Before sort: ${data.subList(leftRun.first, rightRun.second+1)}")
    while(leftIndex >= copy.lastIndex && rightIndex >= rightRun.second) {
        println("index: $leftIndex, $rightIndex")
        println("compare : ${data[leftIndex]} vs ${copy[rightIndex]}")
        println("${data.subList(leftRun.first, rightRun.second+1)}")
        if(data[leftIndex] < copy[rightIndex]) {
            data[idx] = data[rightIndex]
            rightIndex++
        } else {
            data[idx] = copy[leftIndex]
            leftIndex++
        }
        idx++
    }

    while(leftIndex >= copy.lastIndex) {
        println("index: $leftIndex, $rightIndex")
        println("compare : ${data[leftIndex]}")
        println("${data.subList(leftRun.first, rightRun.second+1)}")
        data[idx] = copy[leftIndex]
        idx++
        leftIndex++
    }

    while(rightIndex >= rightRun.second) {
        println("index: $leftIndex, $rightIndex")
        println("compare : ${data[leftIndex]} vs ${copy[rightIndex]}")
        println("${data.subList(leftRun.first, rightRun.second+1)}")
        data[idx] = data[rightIndex]
        idx++
        rightIndex++
    }

    println("After sort: ${data.subList(leftRun.first, rightRun.second+1)}")
}

private fun mergeHi(data: MutableList<Int>, stack: MutableList<Pair<Int, Int>>, range: Pair<Int, Int>) {
    val leftRun = stack.last()
    stack.removeAt(stack.lastIndex)
    val rightRun = range
    println("Run left: $leftRun, right: $rightRun")
    val copy = data.subList(rightRun.first, rightRun.second+1).toMutableList()

    var idx = rightRun.second
    var leftIndex = leftRun.second
    var rightIndex = copy.lastIndex

    println("Before sort: ${data.subList(leftRun.first, rightRun.second+1)}")
    while(leftIndex >= leftRun.first && rightIndex >= 0) {
        println("index: $leftIndex, $rightIndex")
        println("compare : ${data[leftIndex]} vs ${copy[rightIndex]}")
        println("${data.subList(leftRun.first, rightRun.second+1)}")
        if(data[leftIndex] < copy[rightIndex]) {
            data[idx] = copy[rightIndex]

            rightIndex--
        } else {
            data[idx] = data[leftIndex]
            leftIndex--
        }
        idx--
    }

    while(leftIndex >= leftRun.first) {
        println("index: $leftIndex, $rightIndex")
        println("compare : ${data[leftIndex]}")
        println("${data.subList(leftRun.first, rightRun.second+1)}")
        data[idx] = data[leftIndex]
        idx--
        leftIndex--
    }

    while(rightIndex >= 0) {
        println("index: $leftIndex, $rightIndex")
        println("compare : ${data[leftIndex]} vs ${copy[rightIndex]}")
        println("${data.subList(leftRun.first, rightRun.second+1)}")
        data[idx] = copy[rightIndex]
        idx--
        rightIndex--
    }

    println("After sort: ${data.subList(leftRun.first, rightRun.second+1)}")
}

private fun makeRun(data: MutableList<Int>, start: Int, end: Int, min: Int): Pair<Int, Int> {
    var lastIdx = end
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

    println("start: $start, end: $end, start val: ${data[start]}")
    val comparator =
        if (data[start] <= data[start + 1]) {
            print("ASC min: ")
            asc
        } else {
            print("DESC min: ")
            desc
        }
    binaryInsertionSort(data.subList(start, end + 1), comparator)

    for (j in end until data.lastIndex) {
        if (comparator.compare(data[j], data[j + 1]) >= 0) lastIdx++
        else break
    }

//    println(data.lastIndex)
//    println("last idx: $lastIdx")
//    println("b reverse $data")
    if(data[start] > data[start + 1]) { // DESC to ASC
        if(lastIdx+1 < data.lastIndex) {
            data.subList(start, lastIdx + 1).reverse()
        } else {
            data.subList(start, data.lastIndex + 1).reverse()
        }
    }
//    println("a reverse $data")
//
//    println("max: $data")
    val r = if(lastIdx + min > data.lastIndex) data.lastIndex else lastIdx + min
    return (lastIdx + 1) to r
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


class Level11 {
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