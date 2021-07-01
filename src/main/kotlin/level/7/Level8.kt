package level.`7`

import kotlin.math.ceil

fun main() {

}

class Level8 {
    fun `백준 1712`() {
        val (baseCost, cost, price) = readLine()!!.split(" ").map { it.toInt() }
        val result = when {
            cost >= price -> -1
            else -> (baseCost / (price - cost)) + 1
        }
        println(result)
    }

    fun `백준 2292`() {
        val target = readLine()!!.toInt() - 1
        for (i in 0..100000) {
            if (target <= i * i * 3 + i * 3) {
                println(i + 1)
                break
            }
        }
    }

    fun `백준 1193`() {
        val input = readLine()!!.toInt()
        val n = (1..100000)
            .first {
                input * 2 <= it * it + it
            }

        val step = (input * 2 - ((n - 1) * (n - 1) + (n - 1))) / 2

        val a = n - step + 1
        val b = step
        when (n % 2) {
            0 -> println("$b/$a")
            else -> println("$a/$b")
        }
    }

    fun `백준 2869`() {
        val (A, B, V) = readLine()!!.split(" ").map { it.toInt() }
        val result = ceil((V - A) / (A - B).toDouble()) + 1
        println(result.toInt())
    }

    fun `백준 10250`() {
        val n = readLine()!!.toInt()
        for (i in 1..n) {
            val (H, W, N) = readLine()!!.split(" ").map { it.toInt() }
            val (col, row) = when (N % H == 0) {
                true -> N / H to H
                false -> N / H + 1 to N % H
            }
            println("${row}${"%02d".format(col)}")
        }
    }
}