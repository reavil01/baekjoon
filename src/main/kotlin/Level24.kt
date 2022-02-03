import java.util.*
import kotlin.collections.HashMap

fun main() {

}


class Level24 {
    fun `백준 1260번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, m, v) = reader.readLine().split(" ").map { it.toInt() }

        val edges = HashMap<Int, MutableList<Int>>()
        repeat(m) {
            val (s, d) = reader.readLine().split(" ").map { it.toInt() }
            if (edges[s].isNullOrEmpty()) edges[s] = mutableListOf()
            edges[s]!!.add(d)
            if (edges[d].isNullOrEmpty()) edges[d] = mutableListOf()
            edges[d]!!.add(s)
        }
        edges.forEach { it.value.sort() }


        val dfsVisit = Array(n + 1) { false }
        val dfsPath = mutableListOf<Int>()
        dfs(edges, dfsVisit, v, dfsPath)

        val bfsVisit = Array(n + 1) { false }
        val bfsPath = mutableListOf<Int>()
        bfs(edges, bfsVisit, v, bfsPath)

        dfsPath.forEach {
            writer.write(it.toString())
            writer.write(" ")
        }
        writer.newLine()
        bfsPath.forEach {
            writer.write(it.toString())
            writer.write(" ")
        }
        writer.flush()
        writer.close()
        reader.close()
    }

    fun bfs(edges: HashMap<Int, MutableList<Int>>, visit: Array<Boolean>, v: Int, path: MutableList<Int>) {
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        visit[v] = true
        path.add(v)

        if (edges[v].isNullOrEmpty()) return
        edges[v]!!.forEach { queue.add(v to it) }

        while (queue.isNotEmpty()) {
            val (s, d) = queue.poll()
            if (!visit[d]) {
                visit[d] = true
                path.add(d)
            }

            edges[d]!!.forEach {
                if (!visit[it]) queue.add(d to it)
            }
        }
    }

    fun dfs(edges: HashMap<Int, MutableList<Int>>, visit: Array<Boolean>, s: Int, path: MutableList<Int>) {
        visit[s] = true
        path.add(s)

        if (edges[s].isNullOrEmpty()) return
        for (i in edges[s]!!.indices) {
            val d = edges[s]!![i]
            if (visit[d]) continue
            else dfs(edges, visit, d, path)
        }
    }

    fun `백준 2606번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val n = reader.readLine().toInt()
        val e = reader.readLine().toInt()
        val edges = HashMap<Int, MutableList<Int>>()
        repeat(e) {
            val (s, d) = reader.readLine().split(" ").map { it.toInt() }
            if (edges[s].isNullOrEmpty()) edges[s] = mutableListOf()
            edges[s]!!.add(d)
            if (edges[d].isNullOrEmpty()) edges[d] = mutableListOf()
            edges[d]!!.add(s)
        }

        val bfsVisit = Array(n + 1) { false }
        val bfsPath = mutableListOf<Int>()
        bfs(edges, bfsVisit, 1, bfsPath)

        writer.write((bfsPath.size - 1).toString())
        writer.flush()
        writer.close()
        reader.close()
    }

    fun `백준 2667번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val n = reader.readLine().toInt()
        val map = Array(n) {
            reader.readLine().toCharArray().map { it.toString().toInt() }.toIntArray()
        }

        val result = findComplex(map)
        result.sort()
        writer.write(result.size.toString())
        result.forEach {
            writer.newLine()
            writer.write(it.toString())
        }
        writer.flush()
        writer.close()
        reader.close()
    }

    fun findComplex(map: Array<IntArray>): MutableList<Int> {
        val complexSizeList = mutableListOf<Int>()
        val visit = Array(map.size) { BooleanArray(map.size) }

        val queue: Queue<Pair<Int, Int>> = LinkedList()
        val xDir = arrayOf(-1, 0, 1, 0)
        val yDir = arrayOf(0, -1, 0, 1)

        for (i in map.indices) {
            for (j in map.indices) {
                if (visit[i][j]) continue

                if (map[i][j] == 1) {
                    var size = 0
                    queue.add(i to j)

                    while (queue.isNotEmpty()) {
                        val (x, y) = queue.poll()
                        if (visit[x][y]) continue
                        visit[x][y] = true
                        if (map[x][y] == 1) {
                            size++
                            for (d in 0 until 4) {
                                val newX = x + xDir[d]
                                val newY = y + yDir[d]

                                if (0 <= newX && newX < map.size && 0 <= newY && newY < map.size) {
                                    if (visit[newX][newY]) continue
                                    queue.add(newX to newY)
                                }
                            }
                        }

                    }
                    if (size > 0) complexSizeList.add(size)
                }

                visit[i][j] = true
            }
        }

        return complexSizeList
    }

    fun `백준 1012번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val t = reader.readLine().toInt()

        repeat(t) {
            val (m, n, k) = reader.readLine().split(" ").map { it.toInt() }
            val map = Array(m) { IntArray(n) }
            repeat(k) {
                val (x, y) = reader.readLine().split(" ").map { it.toInt() }
                map[x][y] = 1
            }
            val ans = countWarms(map)
            writer.write(ans.size.toString())
            writer.newLine()
        }
        writer.flush()
        writer.close()
        reader.close()
    }

    fun countWarms(map: Array<IntArray>): MutableList<Int> {
        val warmsCountList = mutableListOf<Int>()
        val visit = Array(map.size) { BooleanArray(map[0].size) }

        val queue: Queue<Pair<Int, Int>> = LinkedList()
        val xDir = arrayOf(-1, 0, 1, 0)
        val yDir = arrayOf(0, -1, 0, 1)

        for (i in map.indices) {
            for (j in map[0].indices) {
                if (visit[i][j]) continue

                if (map[i][j] == 1) {
                    var size = 0
                    queue.add(i to j)

                    while (queue.isNotEmpty()) {
                        val (x, y) = queue.poll()
                        if (visit[x][y]) continue
                        visit[x][y] = true
                        if (map[x][y] == 1) {
                            size++
                            for (d in 0 until 4) {
                                val newX = x + xDir[d]
                                val newY = y + yDir[d]

                                if (0 <= newX && newX < map.size && 0 <= newY && newY < map[0].size) {
                                    if (visit[newX][newY]) continue
                                    queue.add(newX to newY)
                                }
                            }
                        }

                    }
                    if (size > 0) warmsCountList.add(size)
                }

                visit[i][j] = true
            }
        }

        return warmsCountList
    }

    fun `백준 2178번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, m) = reader.readLine().split(" ").map { it.toInt() }

        val map = Array(n) { reader.readLine().chunked(1).map { it.toInt() }.toList() }
        val visit = Array(n) { BooleanArray(m) { false } }

        // direction
        val xPos = listOf(-1, 0, 1, 0)
        val yPos = listOf(0, -1, 0, 1)

        // set init job
        val queue: Queue<Triple<Int, Int, Int>> = LinkedList()
        queue.add(Triple(0, 0, 0))

        var minCost = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val (x, y, score) = queue.poll()
            if (x == n - 1 && y == m - 1) {
                if (minCost > score + 1) minCost = score + 1
            }

            for ((xMove, yMove) in xPos.zip(yPos)) {
                val newX = x + xMove
                val newY = y + yMove

                if (newX < 0 || newY < 0) continue
                if (newX == n || newY == m) continue

                if (map[newX][newY] == 1 && !visit[newX][newY]) {
                    visit[newX][newY] = true
                    queue.add(Triple(newX, newY, score + 1))
                }
            }
        }
        writer.write("$minCost")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 7576번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (m, n) = reader.readLine().split(" ").map { it.toInt() }

        val map = Array(n) { reader.readLine().split(" ").map { it.toInt() }.toMutableList() }

        // direction
        val xPos = listOf(-1, 0, 1, 0)
        val yPos = listOf(0, -1, 0, 1)

        // init queue
        val queue: Queue<Triple<Int, Int, Int>> = LinkedList()
        map.forEachIndexed { x, list -> list.forEachIndexed { y, i -> if (i == 1) queue.add(Triple(x, y, 0)) } }

        var lastStep = 0
        while (queue.isNotEmpty()) {
            val (x, y, step) = queue.poll()
            if (step > lastStep) lastStep = step

            for ((xMove, yMove) in xPos.zip(yPos)) {
                val newX = x + xMove
                val newY = y + yMove

                if (newX < 0 || newY < 0) continue
                if (newX == n || newY == m) continue

                if (map[newX][newY] == 0) {
                    map[newX][newY] = 1
                    queue.add(Triple(newX, newY, step + 1))
                }
            }
        }

        // propagation check
        if (map.any { it.contains(0) })
            lastStep = -1

        writer.write("$lastStep")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 7569번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (m, n, h) = reader.readLine().split(" ").map { it.toInt() }

        val map = Array(h) { Array(n) { reader.readLine().split(" ").map { it.toInt() }.toMutableList() } }

        // direction
        val xPos = listOf(-1, 0, 1, 0, 0, 0)
        val yPos = listOf(0, -1, 0, 1, 0, 0)
        val zPos = listOf(0, 0, 0, 0, 1, -1)

        // init queue
        val queue: Queue<List<Int>> = LinkedList()
        map.forEachIndexed { z, list ->
            list.forEachIndexed { x, list2 ->
                list2.forEachIndexed { y, i -> if (i == 1) queue.add(listOf(x, y, z, 0)) }
            }
        }

        var lastStep = 0
        while (queue.isNotEmpty()) {
            val (x, y, z, step) = queue.poll()
            if (step > lastStep) lastStep = step

            for (i in xPos.indices) {
                val newX = x + xPos[i]
                val newY = y + yPos[i]
                val newZ = z + zPos[i]

                if (newX < 0 || newY < 0 || newZ < 0) continue
                if (newX == n || newY == m || newZ == h) continue

                if (map[newZ][newX][newY] == 0) {
                    map[newZ][newX][newY] = 1
                    queue.add(listOf(newX, newY, newZ, step + 1))
                }
            }
        }

        // propagation check
        if (map.any { it.any { l -> l.contains(0) } })
            lastStep = -1

        writer.write("$lastStep")
        writer.flush()

        reader.close()
        writer.close()
    }
}