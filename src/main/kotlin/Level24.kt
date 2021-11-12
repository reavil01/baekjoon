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

        if(edges[v].isNullOrEmpty()) return
        edges[v]!!.forEach { queue.add(v to it) }

        while (queue.isNotEmpty()) {
            val (s, d) = queue.poll()
            if(!visit[d]) {
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

        if(edges[s].isNullOrEmpty()) return
        for (i in edges[s]!!.indices) {
            val d = edges[s]!![i]
            if (visit[d]) continue
            else dfs(edges, visit, d, path)
        }
    }
}