import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {

}

class Level7 {
    fun `백준 11654`() {
        readLine()!!.chars().forEach { println(it) }
    }

    fun `백준 11720`() {
        readLine()!!
        val sum = readLine()!!.toCharArray()
            .sumOf { it.toString().toInt() }
        println(sum)
    }

    fun `백준 10809`() {
        val word = readLine()!!
        ('a'.toInt()..'z'.toInt())
            .asSequence()
            .forEach {
                println(word.indexOf(it.toChar()))
            }
    }

    fun `백준 2675`() {
        val n = readLine()!!.toInt()
        for (i in 1..n) {
            val (iter, word) = readLine()!!.split(" ")
            word
                .asSequence()
                .forEach {
                    print(
                        it.toString().repeat(iter.toInt())
                    )
                }
            println()
        }
    }

    fun `백준 1157`() {
        val word = readLine()!!
        val charCount = word.toUpperCase()
            .asSequence()
            .groupingBy { it }
            .eachCount()

        with(charCount) {
            val max = maxOf { it.value }
            val keys = filterValues { it == max }.keys
            val ans = when (keys.size) {
                1 -> keys.first()
                else -> "?"
            }
            println(ans)
        }
    }

    fun `백준 1152 - 272ms`() {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val numOfWords = reader.readLine()
            .trim()
            .split(" ")
            .count {
                !it.isNullOrEmpty()
            }
        println(numOfWords)
    }

    fun `백준 1152 - 340ms`() {
        val numOfWords = readLine()!!
            .trim()
            .split(" ")
            .count {
                !it.isNullOrEmpty()
            }
        println(numOfWords)
    }

    fun `백준 2908`() {
        val result = readLine()!!.split(" ")
            .map {
                it.asIterable()
                    .reversed()
                    .fold("") { acc, it ->
                        acc + it
                    }
            }
            .maxOf { it.toInt() }
        println(result)
    }

    fun `백준 5622`() {
        val code = readLine()!!
            .asSequence()
            .map {
                when (it) {
                    in 'A'..'C' -> 3
                    in 'D'..'F' -> 4
                    in 'G'..'I' -> 5
                    in 'J'..'L' -> 6
                    in 'M'..'O' -> 7
                    in 'P'..'S' -> 8
                    in 'T'..'V' -> 9
                    in 'W'..'Z' -> 10
                    else -> 2
                }
            }
            .sum()
        println(code)
    }

    fun `백준 2941`() {
        val words = listOf(
            "c=", "c-", "dz=", "d-", "lj", "nj", "s=", "z="
        )

        var text = readLine()!!
        for (word in words) {
            text = text.replace(word, ".")
        }
        println(text.length)
    }

    fun `백준 1316`() {
        val n = readLine()!!.toInt()

        var cnt = 0
        for (i in 1..n) {
            val word = readLine()!!
            val distinct = word.fold(word.first().toString()) { acc, c ->
                when (acc.last()) {
                    c -> acc
                    else -> acc + c
                }
            }
            when (distinct.length) {
                distinct.toList().distinct().size -> cnt++
                else -> cnt
            }
        }
        println(cnt)
    }
}