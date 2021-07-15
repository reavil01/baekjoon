package level.`7`

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
}