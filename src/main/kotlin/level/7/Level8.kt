package level.`7`

import java.math.BigInteger
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

    fun `백준 2775`() {
        val iter = readLine()!!.toInt()
        for (i in 1..iter) {
            val k = readLine()!!.toInt()
            val n = readLine()!!.toInt()

            val arr = mutableListOf<IntArray>()
            repeat(k + 1) { arr.add(IntArray(n) { 1 }) }

            for (b in 0 until n) {
                arr[0][b] += b
            }

            for (a in 1..k) {
                for (b in 1 until n) {
                    arr[a][b] = arr[a - 1][b] + arr[a][b - 1]
                }
            }
            println(arr[k][n - 1])
        }
    }

    fun `백준 2839`() {
        val target = readLine()!!.toInt()
        val remain = target % 5
        val numOf5kg = target / 5

        val result = when (remain) {
            0 -> numOf5kg
            1 -> if (numOf5kg - 1 >= 0) numOf5kg + 1 else -1
            2 -> if (numOf5kg - 2 >= 0) numOf5kg + 2 else -1
            3 -> numOf5kg + 1
            4 -> if (numOf5kg - 1 >= 0) numOf5kg + 2 else -1
            else -> -1
        }
        println(result)
    }

    fun `백준 10757`() {
        val sum = readLine()!!
            .split(" ")
            .map { BigInteger(it) }
            .sumOf { it }
        println(sum)
    }
}