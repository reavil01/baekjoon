package level.`7`

import java.util.*
import kotlin.IllegalArgumentException


class Level19 {
    fun `백준 18258번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val queue: Queue<Int> = LinkedList()
        repeat(n) {
            val input = reader.readLine()
            if (input.contains(" "))
                queue.add(input.split(" ")[1].toInt())
            else {
                val ans = when (input) {
                    "pop" -> if (queue.isEmpty()) -1 else queue.poll()
                    "size" -> queue.size
                    "empty" -> if (queue.isEmpty()) 1 else 0
                    "front" -> if (queue.isEmpty()) -1 else queue.peek()
                    "back" -> if (queue.isEmpty()) -1 else queue.last()
                    else -> throw IllegalArgumentException("올바르지 않은 입력입니다!")
                }
                writer.write("$ans\n")
            }
        }

        writer.flush()
    }

    fun `백준 2164번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val queue: Queue<Int> = LinkedList()
        queue.addAll((1..n))
        while (queue.size > 1) {
            queue.poll()
            queue.add(queue.poll())
        }
        writer.write("${queue.first()}\n")
        writer.flush()
    }

    fun `백준 11866번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, k) = reader.readLine().split(" ").map { it.toInt() }
        val queue: Queue<Int> = LinkedList()
        val ans = mutableListOf<Int>()

        queue.addAll((1..n))
        while (queue.size > 0) {
            for (j in 0 until k - 1) {
                queue.add(queue.poll())
            }
            ans.add(queue.poll())
        }
        var str = ans.toString().replace("[", "<").replace("]", ">")
        writer.write("$str")
        writer.flush()
    }

    fun `백준 1966번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val t = reader.readLine().toInt()

        repeat(t) {
            val (n, k) = reader.readLine().split(" ").map { it.toInt() }
            val data = reader.readLine().split(" ").map { it.toInt() }
            val queue: Queue<Int> = LinkedList()
            queue.addAll(data)

            var idx = k
            val sortedData = data.sorted().reversed()
            var cnt = 0
            for (i in sortedData.indices) {
                val data = sortedData[i]
                while (true) {
                    val f = queue.poll()
                    idx -= 1

                    if (data != f) {
                        queue.add(f)
                        if (idx < 0) idx = queue.size - 1
                    } else {
                        cnt += 1
                        break
                    }
                }
                if (idx == -1) break
            }
            writer.write("$cnt\n")
        }
        writer.flush()
    }

    fun `백준 10866`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val deque: Deque<Int> = LinkedList()

        repeat(n) {
            val input = reader.readLine()
            if (input.contains("push")) {
                val (command, x) = input.split(" ")
                when (command) {
                    "push_front" -> deque.addFirst(x.toInt())
                    "push_back" -> deque.addLast(x.toInt())
                    else -> throw IllegalArgumentException("잘못된 입력입니다")
                }
            } else {
                val ans = when (input) {
                    "pop_front" -> if (deque.isEmpty()) -1 else deque.pollFirst()
                    "pop_back" -> if (deque.isEmpty()) -1 else deque.pollLast()
                    "size" -> deque.size
                    "empty" -> if (deque.isEmpty()) 1 else 0
                    "front" -> if (deque.isEmpty()) -1 else deque.first()
                    "back" -> if (deque.isEmpty()) -1 else deque.last()
                    else -> throw IllegalArgumentException("잘못된 입력입니다")
                }
                writer.write("$ans\n")
            }
        }
        writer.flush()
    }

    fun `백준 1021번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, m) = reader.readLine().split(" ").map { it.toInt() }
        val targets = reader.readLine().split(" ").map { it.toInt() }

        val deque: Deque<Int> = LinkedList()
        deque.addAll((1..n))

        var ans = 0
        targets
            .forEach {
                val idx = deque.indexOf(it)
                ans += if (idx <= deque.size / 2) {
                    repeat(idx) {
                        deque.addLast(deque.pollFirst())
                    }
                    idx
                } else {
                    repeat(deque.size - idx) {
                        deque.addFirst(deque.pollLast())
                    }
                    (deque.size - idx)
                }
                deque.pollFirst()
            }
        writer.write("$ans\n")
        writer.flush()
    }

    fun `백준 5430번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val t = reader.readLine().toInt()

        repeat(t) {
            val p = reader.readLine().toCharArray()
            val n = reader.readLine().toInt()
            val deque: Deque<Int> = LinkedList()
            val input = reader.readLine()
            if (n > 0) {
                val data = input.replace("[\\[\\]]".toRegex(), "").split(",").map { it.toInt() }
                deque.addAll(data)
            }

            var direction = true
            var error = false
            p.forEach {
                when (it) {
                    'R' -> direction = !direction
                    'D' -> {
                        when {
                            deque.isEmpty() -> error = true
                            direction -> deque.pollFirst()
                            else -> deque.pollLast()
                        }
                    }
                    else -> throw IllegalArgumentException("잘못된 입력입니다.")
                }
            }
            val ans = if (error)
                "error"
            else {
                if (direction) deque.toString().replace(" ", "")
                else deque.reversed().toString().replace(" ", "")
            }
            writer.write("$ans\n")
        }
        writer.flush()
    }
}