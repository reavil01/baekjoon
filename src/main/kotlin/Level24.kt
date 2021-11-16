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
}