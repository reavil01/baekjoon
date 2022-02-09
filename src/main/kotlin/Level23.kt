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
}