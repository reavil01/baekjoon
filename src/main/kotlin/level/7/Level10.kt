package level.`7`

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import kotlin.math.pow

fun main() {

}

class Level10 {
    fun `백준 10872`() {
        val n = readLine()!!.toInt()
        println(factorial(n))
    }

    private fun factorial(n: Int): Int {
        if (n == 0) return 1
        return n * factorial(n - 1)
    }

    fun `백준 10870`() {
        val n = readLine()!!.toInt()
        println(fibonacci(n))
    }

    private fun fibonacci(n: Int): Int {
        if (n == 0) return 0
        if (n == 1) return 1
        return fibonacci(n - 1) + fibonacci(n - 2)
    }

    fun `백준 2447`() {
        val writer = BufferedWriter(OutputStreamWriter(System.out))

        val n = readLine()!!.toInt()
        val matrix = Array(n) { BooleanArray(n) }

        drawStar(0, 0, n, matrix)
        matrix
            .forEach { booleanArr ->
                booleanArr
                    .forEach { boolean ->
                        if (boolean) writer.write("*")
                        else writer.write(" ")
                    }
                writer.newLine()
            }

        writer.flush()
    }


    private fun drawStar(row: Int, col: Int, size: Int, matrix: Array<BooleanArray>) {
        if (size == 1) {
            matrix[row][col] = true
            return
        }

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (i != 1 || j != 1)
                    drawStar(row + size / 3 * i, col + size / 3 * j, size / 3, matrix)
            }
        }
    }

    val writer = BufferedWriter(OutputStreamWriter(System.out))

    fun `백준 11729`() {
        val n = readLine()!!.toInt()
        val total = 2.0.pow(n).toInt() - 1
        writer.write(total.toString())
        writer.newLine()
        hanoi(1, 3, n)

        writer.flush()
    }

    private fun hanoi(from: Int, to: Int, n: Int) {
        if (n == 1) {
            writer.write("$from $to\n")
            return
        }

        hanoi(from, 6 - from - to, n - 1)
        writer.write("$from $to\n")
        hanoi(6 - from - to, to, n - 1)
    }
}