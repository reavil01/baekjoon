import java.util.*
import kotlin.math.min

fun main() {

}

class Level25 {
    fun `백준 1753번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (V, E) = reader.readLine().split(" ").map { it.toInt() }
        val K = reader.readLine().toInt() - 1

        val minDist = IntArray(V) { Int.MAX_VALUE }

        val weights = Array(V) { mutableListOf<Pair<Int, Int>>() }
        repeat(E) {
            val (u, v, w) = reader.readLine().split(" ").map { it.toInt() - 1 }
            weights[u].add(v to w + 1)
        }

        fun dijkstra(src: Int) {
            val queue = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
            minDist[src] = 0
            queue.add(src to 0)

            while (queue.isNotEmpty()) {
                val (current, currentDist) = queue.poll()

                if (minDist[current] < currentDist) continue

                weights[current].forEach { (dst, dstDist) ->
                    if (currentDist + dstDist < minDist[dst]) {
                        minDist[dst] = currentDist + dstDist
                        queue.add(dst to minDist[dst])
                    }
                }
            }
        }

        dijkstra(K)

        minDist.forEach {
            val ans = if (it == Int.MAX_VALUE) "INF" else "$it"
            writer.write("$ans\n")
        }
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 1504번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (V, E) = reader.readLine().split(" ").map { it.toInt() }

        val weights = Array(V) { mutableListOf<Pair<Int, Int>>() }
        repeat(E) {
            val (u, v, w) = reader.readLine().split(" ").map { it.toInt() - 1 }
            weights[u].add(v to w + 1)
            weights[v].add(u to w + 1)
        }
        val (v1, v2) = reader.readLine().split(" ").map { it.toInt() - 1 }

        fun dijkstra(src: Int): IntArray {
            val minDist = IntArray(V) { Int.MAX_VALUE }
            val queue = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
            minDist[src] = 0
            queue.add(src to 0)

            while (queue.isNotEmpty()) {
                val (current, currentDist) = queue.poll()

                if (minDist[current] < currentDist) continue

                weights[current].forEach { (dst, dstDist) ->
                    if (currentDist + dstDist < minDist[dst]) {
                        minDist[dst] = currentDist + dstDist
                        queue.add(dst to minDist[dst])
                    }
                }
            }

            return minDist
        }

        val startMinDist = dijkstra(0)
        val v1MinDist = dijkstra(v1)
        val v2MinDist = dijkstra(v2)

        val result = if (
            listOf(startMinDist[v1], v1MinDist[v2], v2MinDist[V - 1]).any { it == Int.MAX_VALUE }
            && listOf(startMinDist[v2], v2MinDist[v1], v1MinDist[V - 1]).any { it == Int.MAX_VALUE }
        ) {
            -1
        } else {
            min(
                startMinDist[v1] + v1MinDist[v2] + v2MinDist[V - 1],
                startMinDist[v2] + v2MinDist[v1] + v1MinDist[V - 1]
            )
        }

        writer.write("$result")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 9370번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val T = reader.readLine().toInt()

        repeat(T) {
            val (n, m, t) = reader.readLine().split(" ").map { it.toInt() }
            val (src, v1, v2) = reader.readLine().split(" ").map { it.toInt() - 1 }
            val weights = Array(n) { mutableListOf<Pair<Int, Int>>() }
            repeat(m) {
                val (u, v, w) = reader.readLine().split(" ").map { it.toInt() - 1 }
                weights[u].add(v to w + 1)
                weights[v].add(u to w + 1)
            }
            val candidates = IntArray(t) { reader.readLine().toInt() - 1 }

            fun dijkstra(src: Int): IntArray {
                val minCost = IntArray(n) { Int.MAX_VALUE }
                val queue = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
                minCost[src] = 0
                queue.add(src to 0)

                while (queue.isNotEmpty()) {
                    val (current, currentDist) = queue.poll()

                    if (currentDist > minCost[current]) continue
                    weights[current].forEach { (dst, dstDist) ->
                        if (minCost[dst] > currentDist + dstDist) {
                            minCost[dst] = currentDist + dstDist
                            queue.add(dst to minCost[dst])
                        }
                    }
                }
                return minCost
            }

            val start = dijkstra(src)
            val v1Cost = dijkstra(v1)
            val v2Cost = dijkstra(v2)
            val crossDist = v1Cost[v2]

            val minV2ToCandidates = candidates
                .filter { v2Cost[it] != Int.MAX_VALUE }
                .filter { v2Cost[it] + start[v1] + crossDist == start[it] }
            val minV1ToCandidates = candidates
                .filter { v1Cost[it] != Int.MAX_VALUE }
                .filter { v1Cost[it] + start[v2] + crossDist == start[it] }

            val result = (minV1ToCandidates + minV2ToCandidates).distinct().sorted()
            result.forEach { writer.write("${it + 1} ") }
            writer.newLine()
        }
        writer.flush()

        reader.close()
        writer.close()
    }
}