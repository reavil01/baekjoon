package level.`7`

class Level16 {
    fun `백준 11047번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val (n, k) = reader.readLine().split(" ").map { it.toInt() }
        val coins = Array(n) { reader.readLine().toInt() }
        val sortedCoins = coins.sorted().reversed()

        var remain = k
        var cnt = 0
        for (coin in sortedCoins) {
            val numOfCoin = remain / coin
            if (numOfCoin > 0) {
                cnt += numOfCoin
                remain -= numOfCoin * coin
            }
        }
        writer.write("$cnt")
        writer.flush()
    }

    fun `백준 1931번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val schedules = Array(n) { reader.readLine().split(" ").map { it.toInt() } }
        val sortedSchedules = schedules.sortedWith { a, b ->
            when {
                a[1] > b[1] -> 1
                a[1] < b[1] -> -1
                else ->
                    when {
                        a[0] > b[0] -> 1
                        a[0] < b[0] -> -1
                        else -> 0
                    }
            }
        }

        var cnt = 0
        var lastTime = 0
        for (schedule in sortedSchedules) {
            if (schedule[0] >= lastTime) {
                cnt += 1
                lastTime = schedule[1]
            }
        }
        writer.write("$cnt")
        writer.flush()
    }

    fun `백준 11399번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val times = reader.readLine().split(" ").map{it.toInt()}.sorted()

        var total = 0
        for(i in times.indices) {
            total += times[i]*(n-i)
        }
        writer.write("$total")
        writer.flush()
    }

    fun `백준 1541번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val formula = reader.readLine()
        val numbers = formula.split("[-+]".toRegex()).map { it.toInt() }

        var total = numbers[0]
        var acc = 0
        var idx = 1
        var flag = false
        for(c in formula) {
            if(c == '-') {
                if(flag) {
                    total -= acc
                    acc = 0
                }
                acc += numbers[idx]
                flag = true
                idx++
            }else if(c == '+') {
                if(flag) acc += numbers[idx]
                else total += numbers[idx]
                idx++
            }
        }
        if(acc > 0) total -= acc
        writer.write("$total")
        writer.flush()
    }
}