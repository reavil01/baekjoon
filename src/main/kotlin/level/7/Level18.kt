package level.`7`

import java.lang.StringBuilder

class Level18 {
    fun `백준 10828번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val stack = mutableListOf<Int>()

        repeat(n) {
            val input = reader.readLine()
            if (input.contains(" ")) {
                stack.add(input.split(" ")[1].toInt())
            } else {
                val result = when (input) {
                    "pop" -> if (stack.size > 0) stack.removeLast() else -1
                    "top" -> if (stack.size > 0) stack.last() else -1
                    "size" -> stack.size
                    "empty" -> if (stack.isEmpty()) 1 else 0
                    else -> ""
                }
                writer.write("${result}\n")
            }
        }

        writer.flush()
    }

    fun `백준 10773번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val stack = mutableListOf<Int>()

        repeat(n) {
            when (val input = reader.readLine().toInt()) {
                0 -> stack.removeLast()
                else -> stack.add(input)
            }
        }

        writer.write("${stack.sum()}\n")
        writer.flush()
    }

    fun `백준 9012번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        repeat(n) {
            val input = reader.readLine().toCharArray()

            var ans = "YES"
            val stack = mutableListOf<Int>()
            input.forEach {
                when (it) {
                    '(' -> stack.add(1)
                    else ->
                        if (stack.size == 0) ans = "NO"
                        else stack.removeLast()
                }
            }
            if (stack.isNotEmpty()) ans = "NO"

            writer.write("$ans\n")
        }

        writer.flush()
    }

    fun `백준 4949번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        while (true) {
            val input = reader.readLine()
            if (input == ".") break

            var ans = "yes"
            val stack = mutableListOf<Int>()
            input
                .toCharArray()
                .forEach {
                    when (it) {
                        '(' -> stack.add(1)
                        '[' -> stack.add(2)
                        ')' -> if (stack.size == 0 || stack.removeLast() != 1) ans = "no"
                        ']' -> if (stack.size == 0 || stack.removeLast() != 2) ans = "no"
                        else -> ""
                    }
                }
            if (stack.isNotEmpty()) ans = "no"

            writer.write("$ans\n")
        }
        writer.flush()
    }

    fun `백준 1874번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val target = Array(n) { reader.readLine().toInt() }

        val stack = mutableListOf<Int>()
        val ans = StringBuilder()
        var pointer = 0
        (1..n).forEach {
            stack.add(it)
            ans.append("+\n")
            while (stack.isNotEmpty() && stack.last() == target[pointer]) {
                pointer++
                stack.removeLast()
                ans.append("-\n")
            }
        }

        if (stack.isNotEmpty()) {
            writer.write("NO\n")
        } else {
            writer.write(ans.toString())
        }

        writer.flush()
    }

    fun `백준 17298번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val nums = reader.readLine().split(" ").map { it.toInt() }

        val stack = mutableListOf<Int>()
        val ans = Array(n) { 0 }
        for (i in 0 until nums.lastIndex) {
            stack.add(i)
            while (stack.isNotEmpty() && nums[stack.last()] < nums[i + 1]) {
                ans[stack.removeLast()] = nums[i + 1]
            }
        }
        while (stack.isNotEmpty()) {
            ans[stack.removeLast()] = -1
        }
        ans[nums.lastIndex] = -1

        ans.forEach { writer.write("$it ") }
        writer.flush()
    }
}