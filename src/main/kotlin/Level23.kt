fun main() {

}

class Level23 {
    fun `백준11066번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val t = reader.readLine().toInt()

        repeat(t) {
            val n = reader.readLine().toInt()
            val cost = reader.readLine().split(" ").map { it.toInt() }

            val dp = Array(n) { x -> IntArray(n) { 0 } }

            fun calcMinCost(i: Int, j: Int) =
                (i + 1..j).minOf { k ->
                    dp[i][k - 1] + dp[k][j]
                } + cost.subList(i, j + 1).sum()

            for (range in 1 until n) {
                for (i in 0 until n) {
                    if (i + range >= n) continue
                    dp[i][i + range] = calcMinCost(i, i + range)
                }
            }

            writer.write("${dp[0][n - 1]}\n")
        }
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 11049번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val matrix = Array(n) {
            reader.readLine().split(" ").map { it.toInt() }
        }

        val dp = Array(n) { x -> IntArray(n) { y -> if (x == y) 0 else Int.MAX_VALUE } }

        for (d in 1 until n) {
            for (i in 0 until n) {
                val j = i + d
                if (j >= n) continue

                dp[i][j] = (i until j).minOf { k ->
                    dp[i][k] + dp[k + 1][j] + matrix[i][0] * matrix[k][1] * matrix[j][1]
                }
            }
        }
        writer.write("${dp[0][n - 1]}")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 1520번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (m, n) = reader.readLine().split(" ").map { it.toInt() }
        val map = Array(m) {
            reader.readLine().split(" ").map { it.toInt() }
        }

        // direction
        val xDir = listOf(1, 0, -1, 0)
        val yDir = listOf(0, 1, 0, -1)

        val route = Array(m) { IntArray(n) { -1 } }

        fun searchRoute(i: Int, j: Int): Int {
            if (i == m - 1 && j == n - 1) return 1

            if (route[i][j] == -1) {
                route[i][j] = 0
                for ((x, y) in xDir.zip(yDir)) {
                    val newX = i + x
                    val newY = j + y
                    if (newX < 0 || newY < 0) continue
                    if (newX == m || newY == n) continue
                    if (map[newX][newY] < map[i][j]) {
                        route[i][j] += searchRoute(newX, newY)
                    }
                }
            }

            return route[i][j]
        }

        val ans = searchRoute(0, 0)
        writer.write("$ans")

        reader.close()
        writer.close()
    }

    fun `백준 10942번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val n = reader.readLine().toInt()
        val values = reader.readLine().split(" ").map { it.toInt() }
        val m = reader.readLine().toInt()
        val questions = Array(m) { reader.readLine().split(" ").map { it.toInt() } }

        val dp = Array(n) { BooleanArray(n) }

        for (range in 0 until n) {
            for (i in 0 until n) {
                val j = i + range

                if (j >= n) continue

                when {
                    i == j -> dp[i][j] = true
                    j - i == 1 -> dp[i][j] = values[i] == values[j]
                    else -> dp[i][j] = values[i] == values[j] && dp[i + 1][j - 1]
                }
            }
        }

        questions.forEach {
            writer.write("${if (dp[it[0] - 1][it[1] - 1]) 1 else 0}\n")
        }
        writer.flush()

        reader.close()
        writer.close()
    }
}