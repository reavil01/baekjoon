class TimSort {
    fun sort(data: MutableList<Int>, min: Int) {
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

                } else {
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
        println("[Merge 2 runs] before: ${data.subList(leftRun.first, rightRun.second + 1)}")
        if (leftSize < rightSize)
            mergeLo(data, leftRun, rightRun)
        else
            mergeHi(data, leftRun, rightRun)

        println("[Merge 2 runs] after: ${data.subList(leftRun.first, rightRun.second + 1)}")
    }


    private fun mergeLo(data: MutableList<Int>, leftRun: Pair<Int, Int>, rightRun: Pair<Int, Int>) {
        println("[Merge Lo] Run left: $leftRun, right: $rightRun")
        val copy = data.subList(leftRun.first, leftRun.second + 1).toMutableList()

        var idx = leftRun.first
        var leftIndex = 0
        var rightIndex = rightRun.first

        var minGallop = 7
        var gallopLeftCnt = 0
        var gallopRightCnt = 0
        println("Before sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
        while (leftIndex <= copy.lastIndex && rightIndex <= rightRun.second) {
            if (data[rightIndex] > copy[leftIndex]) {
                val step = when {
                    gallopLeftCnt >= minGallop -> {
                        gallopLeftCnt = 0
                        gallopRight(copy, leftIndex, copy.lastIndex, data[rightIndex])
                    }else -> {
                        gallopLeftCnt++
                        leftIndex
                    }
                }

                for(i in leftIndex..step) {
                    data[idx] = copy[i]
                    idx++
                }
                leftIndex = step + 1
                gallopRightCnt = 0
            } else {
                val step = when {
                    gallopRightCnt >= minGallop -> {
                        gallopRightCnt = 0
                        gallopRight(data, rightIndex, rightRun.second, copy[leftIndex])
                    }
                    else -> {
                        gallopRightCnt++
                        rightIndex
                    }
                }

                for(i in rightIndex..step) {
                    data[idx] = data[i]
                    idx++
                }

                rightIndex = step + 1
                gallopLeftCnt = 0
            }
        }

        println("middle sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
        println("left idx: $leftIndex, right idx: $rightIndex, idx: $idx")
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

    private fun gallopRight(data: MutableList<Int>, cur: Int, end: Int, target: Int): Int {
        println("[GALLOP RIGHT] input: current idx: $cur, end idx: $end, target val: $target")
        println("[GALLOP RIGHT] copy data: $data")

        var pivot = cur
        var beforePivot = cur
        var i = 1
        var add = 2
        while (data[pivot] < target) {
            repeat(i) { add *= 2 }
            beforePivot = pivot
            pivot += add
//        println("new pivot $pivot")
            i++
            if (pivot > end) {
                pivot = end
                break
            }
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

        println("[GALLOP Right] middle: start idx: $cur, new idx: $pivot, new val: ${data[pivot]}")
        println("[GALLOP RIGHT] make sub list: ${beforePivot} to $pivot")
        val idx = findIndexViaBinarySearch(data.subList(beforePivot, pivot + 1), target, asc)
        pivot = beforePivot + idx - 1

        println("[GALLOP Right] output: start idx: $cur, new idx: $pivot, new val: ${data[pivot]}")
        return pivot
//    return 0
    }

    private fun gallopLeft(data: MutableList<Int>, start: Int, cur: Int, target: Int): Int {
        println("[GALLOP LEFT] input: start idx: $start, current idx: $cur, target val: $target")
        println("[GALLOP LEFT] copy data: $data")

        var pivot = cur
        var beforePivot = cur
        var i = 1
        var add = 2
        while (data[pivot] > target) {
            repeat(i) { add *= 2 }
            beforePivot = pivot
            pivot -= add
//        println("new pivot $pivot")
            i++
            if (pivot < start) {
                pivot = start
                break
            }
        }

        val asc = Comparator<Int> { a, b ->
            when {
                a < b -> 1
                a > b -> -1
                else -> 0
            }
        }

        val idx = findIndexViaBinarySearch(data.subList(pivot, beforePivot + 1), target, asc)
        pivot += (idx + 1)

        println("[GALLOP LEFT] output: start idx: $start, new idx: $pivot, new val: ${data[pivot]}")
        return pivot
    }

    private fun mergeHi(data: MutableList<Int>, leftRun: Pair<Int, Int>, rightRun: Pair<Int, Int>) {
        println("[Merge Hi] Run left: $leftRun, right: $rightRun")
        val copy = data.subList(rightRun.first, rightRun.second + 1).toMutableList()

        var idx = rightRun.second
        var leftIndex = leftRun.second
        var rightIndex = copy.lastIndex

        var minGallop = 7
        var gallopLeftCnt = 0
        var gallopRightCnt = 0
        println("Before sort: ${data.subList(leftRun.first, rightRun.second + 1)}")
        while (leftIndex >= leftRun.first && rightIndex >= 0) {
            if (data[leftIndex] < copy[rightIndex]) {
                val step = when {
                    gallopLeftCnt >= minGallop -> {
                        gallopLeftCnt = 0
                        gallopLeft(copy, 0, rightIndex, data[leftIndex])
                    }
                    else -> {
                        gallopLeftCnt++
                        rightIndex
                    }
                }

                for (i in rightIndex downTo step) {
                    data[idx] = copy[i]
                    idx--
                }

                rightIndex = step - 1
                gallopRightCnt = 0
            } else {
                val step = when {
                    gallopRightCnt >= minGallop -> {
                        gallopRightCnt = 0
                        gallopLeft(data, leftRun.first, leftIndex, copy[rightIndex])
                    }
                    else -> {
                        gallopLeftCnt++
                        leftIndex
                    }
                }

                for (i in leftIndex downTo step) {
                    data[idx] = data[i]
                    idx--
                }
                println("?? rightIndex: $rightIndex, left idx: $leftIndex, idx: $idx")

                leftIndex = step - 1
                gallopLeftCnt = 0
            }

        }

        while (leftIndex >= leftRun.first) {
            println("remain left: rightIndex: $rightIndex, left idx: $leftIndex, idx: $idx")
            data[idx] = data[leftIndex]
            idx--
            leftIndex--
        }

        while (rightIndex >= 0) {
            println("remain right: rightIndex: $rightIndex, left idx: $leftIndex, idx: $idx")
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

    private fun binaryInsertionSort(data: MutableList<Int>, c: java.util.Comparator<Int>) {
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

    private fun findIndexViaBinarySearch(data: MutableList<Int>, value: Int, c: java.util.Comparator<Int>): Int {
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

}