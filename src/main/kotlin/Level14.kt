import kotlin.math.abs
import kotlin.system.exitProcess

fun main() {

}

class Level14 {
    val writer = System.out.bufferedWriter()

    fun `백준 15649`() {
        val reader = System.`in`.bufferedReader()

        val (n, m) = reader.readLine().split(" ").map { it.toInt() }
        val visited = BooleanArray(n) { false }
        val data = IntArray(m)

        makeSequence(data, visited, n, m)
        writer.flush()
    }


    private fun makeSequence(data: IntArray, visited: BooleanArray, end: Int, left: Int) {
        println("[MAKE SEQ] DATA: $data, VISIT: $visited, END: $end, LEFT: $left")

        if (left == 0) {
            printSequence(data)
            return
        }

        for (i in 1..end) {
            if (!visited[i - 1]) {
                visited[i - 1] = true
                data[data.size - left] = i
                makeSequence(data, visited, end, left - 1)
                visited[i - 1] = false
            }
        }
    }

    private fun printSequence(data: IntArray) {
        data.forEach {
            writer.write("$it ")
        }
        writer.newLine()
    }

    fun `백준 15650`() {
        val reader = System.`in`.bufferedReader()

        val (n, m) = reader.readLine().split(" ").map { it.toInt() }
        val visited = BooleanArray(n) { false }
        val data = IntArray(m)

        makeSequenceAsc(data, visited, n, m)
        writer.flush()
    }

    private fun makeSequenceAsc(data: IntArray, visited: BooleanArray, end: Int, left: Int) {
        if (left == 0) {
            printSequence(data)
            return
        }

        val last = data[(data.size - left - 1).coerceAtLeast(0)]
        for (i in last + 1..end) {
            data[data.size - left] = i
            visited[i - 1] = true
            makeSequenceAsc(data, visited, end, left - 1)
            visited[i - 1] = false
        }
    }

    fun `백준 15651`() {
        val reader = System.`in`.bufferedReader()

        val (n, m) = reader.readLine().split(" ").map { it.toInt() }
        val visited = BooleanArray(n) { false }
        val data = IntArray(m)

        makeSequenceAscAllowDuplication(data, visited, n, m)
        writer.flush()
        reader.close()
        writer.close()
    }

    private fun makeSequenceAscAllowDuplication(data: IntArray, visited: BooleanArray, end: Int, left: Int) {
        if (left == 0) {
            printSequence(data)
            return
        }

        for (i in 1..end) {
            data[data.size - left] = i
            visited[i - 1] = true
            makeSequenceAscAllowDuplication(data, visited, end, left - 1)
            visited[i - 1] = false
        }
    }

    fun `백준 15652`() {
        val reader = System.`in`.bufferedReader()

        val (n, m) = reader.readLine().split(" ").map { it.toInt() }
        val data = IntArray(m)

        makeSequenceAllowDuplicationAndNonIncreasing(data, n, m)
        writer.flush()
        reader.close()
        writer.close()
    }

    private fun makeSequenceAllowDuplicationAndNonIncreasing(data: IntArray, end: Int, left: Int) {
        if (left == 0) {
            printSequence(data)
            return
        }

        val last = data[(data.size - left - 1).coerceAtLeast(0)]
        val start = last.coerceAtLeast(1)
        for (i in start..end) {
            data[data.size - left] = i
            makeSequenceAllowDuplicationAndNonIncreasing(data, end, left - 1)
        }
    }

    fun `백준 9663`() {
        val writer = System.out.bufferedWriter()
        val reader = System.`in`.bufferedReader()
        val n = reader.readLine().toInt()

        val result = nQueen(n)

        writer.write("$result")
        writer.flush()
        writer.close()
        reader.close()
    }

    private fun nQueen(n: Int): Int {
        val positions = IntArray(n)

        var result = 0
        for (i in 0 until n) {
            positions[0] = i
            result += nQueen(positions, n - 1)
        }

        return result
    }

    private fun nQueen(posArr: IntArray, left: Int): Int {
        if (left == 0)
            return 1

        var result = 0
        val idx = posArr.size - left
        for (i in posArr.indices) {
            posArr[idx] = i
            if (isPossible(posArr, idx)) {
                result += nQueen(posArr, left - 1)
            }
        }

        return result
    }

    private fun isPossible(posArr: IntArray, n: Int): Boolean {
        for (i in 0 until n) {
            if (posArr[i] == posArr[n]) return false
            if (posArr[i] - posArr[n] == i - n) return false
            if (posArr[n] - posArr[i] == i - n) return false
        }
        return true
    }

    fun `백준 2580`() {
        val reader = System.`in`.bufferedReader()
        val problem = mutableListOf<Int>()
        repeat(9) {
            problem.addAll(reader.readLine().trim().split(" ").map { it.toInt() })
        }

        val indices = filterTargetIdices(problem)
        findSudokuSolution(problem, indices, 0)
    }

    private fun findSudokuSolution(problem: MutableList<Int>, indices: List<Int>, currentIdx: Int) {
        if (currentIdx > indices.lastIndex) {
            printSudoquAndExit(problem)
        }

        val targetIdx = indices[currentIdx]
        for (i in 1..9) {
            if (isPossibleForSudoqu(problem, targetIdx, i)) {
                problem[targetIdx] = i
                findSudokuSolution(problem, indices, currentIdx + 1)
            }
        }
        problem[targetIdx] = 0
    }

    private fun isPossibleForSudoqu(problem: MutableList<Int>, idx: Int, value: Int): Boolean {
        val col = idx % 9
        val row = idx / 9

//    println("[POSSIBLE] row: $row, row range: ${row*9} ~ ${(row + 1) * 9}")
        if (problem.subList(row * 9, (row + 1) * 9).contains(value)) return false

        for (i in 0 until 9) {
//        println("[POSSIBLE] col: $col, col range: ${i * 9 + col}")
            if (problem[i * 9 + col] == value) return false
        }

        val partRow = row / 3
        val partCol = col / 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val partIdx = ((partRow * 3 + i) * 9) + (partCol * 3 + j)
//            println("[POSSIBLE] part row: $partRow, part col: $partCol, part idx: $partIdx")
                if (problem[partIdx] == value) return false
            }
        }
        return true
    }

    private fun printSudoquAndExit(problem: MutableList<Int>) {
        val writer = System.out.bufferedWriter()
        problem.forEachIndexed { idx, i ->
            writer.write("$i ")
            if (idx % 9 == 8)
                writer.newLine()
        }
        writer.flush()
        exitProcess(0)
    }

    private fun filterTargetIdices(problem: MutableList<Int>) =
        problem.mapIndexed { idx, v -> if (v == 0) idx else null }.filterNotNull()

    fun `백준 14888`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val N = reader.readLine().toInt()
        val nums = reader.readLine()
            .split(" ")
            .map { it.toInt() }
            .toIntArray()
        val operations = reader.readLine()
            .split(" ")
            .map { it.toInt() }
            .toIntArray()

        val (min, max) = calculator(nums, operations, nums[0], 0)
        writer.write("$max\n$min")
        writer.flush()
    }

    private fun calculator(nums: IntArray, operations: IntArray, result: Int, idx: Int): Pair<Int, Int> {
        if (idx == nums.lastIndex)
            return result to result

        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (op in 0..3) {
            if (operations[op] == 0) continue

            val ans = calc(result, nums[idx + 1], op)
            operations[op] -= 1
            val (a, b) = calculator(nums, operations, ans, idx + 1)
            operations[op] += 1

            if (a < min) min = a
            if (b > max) max = b
        }

        return min to max
    }

    private fun calc(a: Int, b: Int, op: Int) = when (op) {
        0 -> a + b
        1 -> a - b
        2 -> a * b
        else -> {
            if (a < 0)
                ((a * -1) / b) * -1
            else a / b
        }
    }

    /**
     * 플레이어가 넷인 경우, [0, 1], [2, 3]으로 나뉘는 케이스와 [2, 3], [0, 1]로 나뉘는 케이스의 중복이 발생함
     */
    fun `백준 14889`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val N = reader.readLine().toInt()
        val info = mutableListOf<IntArray>()

        repeat(N) {
            val data = reader.readLine().split(" ").map { it.toInt() }.toIntArray()
            info.add(data)
        }

        val team = BooleanArray(N)
        val diff = findOptimalTeam(info, team, 0, 0)
        writer.write("$diff")
        writer.flush()
    }

    fun findOptimalTeam(info: MutableList<IntArray>, teamA: BooleanArray, idx: Int, cnt: Int): Int {
        if (cnt == info.size / 2) {
            return calcDifferceScore(info, teamA)
        }

        var minDiff = Int.MAX_VALUE
        for (i in idx..info.lastIndex) {
            teamA[i] = true
            val diff = findOptimalTeam(info, teamA, i + 1, cnt + 1)
            if (diff < minDiff) minDiff = diff
            teamA[i] = false
        }

        return minDiff
    }

    fun calcDifferceScore(info: MutableList<IntArray>, team: BooleanArray): Int {
        var aScore = 0
        var bScore = 0
        for (i in team.indices) {
            for (j in i + 1..team.lastIndex) {
                if (team[i] && team[j]) aScore += info[i][j] + info[j][i]
                if (!team[i] && !team[j]) bScore += info[i][j] + info[j][i]
            }
        }
        return abs(aScore - bScore)
    }
}
