
class Level20 {
    fun `백준 2630번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = Array(n) { reader.readLine().split(" ").map { it.toInt() } }

        val (w, b) = countSameColorBoxViaQuadTree(data, 0, 0, n)
        writer.write("$w\n")
        writer.write("$b\n")
        writer.flush()
    }

    fun countSameColorBoxViaQuadTree(data: Array<List<Int>>, sx: Int, sy: Int, len: Int): Pair<Int, Int> {
        if (len == 0)
            return if (data[sx][sy] == 0) 1 to 0 else 0 to 1

        val div = len / 2
        val x = listOf(sx, sx + div, sx, sx + div)
        val y = listOf(sy, sy, sy + div, sy + div)
        var white = 0
        var blue = 0
        for (i in 0 until 4) {
            val (a, b) = countSameColorBoxViaQuadTree(data, x[i], y[i], div)
            white += a
            blue += b
        }

        return when {
            white == 0 -> 0 to 1
            blue == 0 -> 1 to 0
            else -> white to blue
        }
    }

    fun `백준 1992번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = Array(n) { reader.readLine().toCharArray().map { it.toString().toInt() } }
        val ans = compressImageViaQuadTree(data, 0, 0, n)

        writer.write("$ans\n")
        writer.flush()
    }

    fun compressImageViaQuadTree(data: Array<List<Int>>, sx: Int, sy: Int, len: Int): String {
        if (len == 0)
            return data[sx][sy].toString()

        val div = len / 2
        val x = listOf(sx, sx, sx + div, sx + div)
        val y = listOf(sy, sy + div, sy, sy + div)
        var str = ""
        for (i in 0 until 4) {
            str += compressImageViaQuadTree(data, x[i], y[i], div)
        }

        return when {
            str.contains("0000") -> str.replace("0000", "0")
            str.contains("1111") -> str.replace("1111", "1")
            else -> "($str)"
        }
    }

    fun `백준 1780번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = Array(n) { reader.readLine().split(" ").map { it.toInt() } }
        val ans = countSameColorBoxViaNonaTree(data, 0, 0, n)

        writer.write("${ans.first}\n")
        writer.write("${ans.second}\n")
        writer.write("${ans.third}\n")
        writer.flush()
    }

    fun countSameColorBoxViaNonaTree(data: Array<List<Int>>, sx: Int, sy: Int, len: Int): Triple<Int, Int, Int> {
        if (len == 0)
            return when (data[sx][sy]) {
                -1 -> Triple(1, 0, 0)
                0 -> Triple(0, 1, 0)
                else -> Triple(0, 0, 1)
            }

        val div = len / 3
        val x = listOf(sx, sx + div, sx + 2 * div)
        val y = listOf(sy, sy + div, sy + 2 * div)

        val list = Array(3) { 0 }
        for (i in x)
            for (j in y) {
                val triple = countSameColorBoxViaNonaTree(data, i, j, div)
                list[0] += triple.first
                list[1] += triple.second
                list[2] += triple.third
            }

        return when {
            list[1] == 0 && list[2] == 0 -> Triple(1, 0, 0)
            list[0] == 0 && list[2] == 0 -> Triple(0, 1, 0)
            list[0] == 0 && list[1] == 0 -> Triple(0, 0, 1)
            else -> Triple(list[0], list[1], list[2])
        }
    }

    // 함수 호출시 마다 Triple 을 매번 생성해 느린거 같아 배열로 수정해봄.
    // 하지만, 정답을 얻기 위해선 배열도 매번 생성해야 해서 별 차이 없음...
    fun `백준 1780번 - 배열이용`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = Array(n) { reader.readLine().split(" ").map { it.toInt() } }
        val ans = Array(3) { 0 }
        countSameColorBoxViaNonaTree(data, ans, 0, 0, n)

        writer.write("${ans[0]}\n")
        writer.write("${ans[1]}\n")
        writer.write("${ans[2]}\n")
        writer.flush()
    }

    fun countSameColorBoxViaNonaTree(data: Array<List<Int>>, ans: Array<Int>, sx: Int, sy: Int, len: Int) {
        if (len == 0) {
            when (data[sx][sy]) {
                -1 -> ans[0] += 1
                0 -> ans[1] += 1
                else -> ans[2] += 1
            }
            return
        }

        val div = len / 3
        val x = listOf(sx, sx + div, sx + 2 * div)
        val y = listOf(sy, sy + div, sy + 2 * div)

        val arr = Array(3) { 0 }
        for (i in x)
            for (j in y) {
                countSameColorBoxViaNonaTree(data, arr, i, j, div)
            }

        when {
            arr[1] == 0 && arr[2] == 0 -> ans[0] += 1
            arr[0] == 0 && arr[2] == 0 -> ans[1] += 1
            arr[0] == 0 && arr[1] == 0 -> ans[2] += 1
            else -> {
                ans[0] += arr[0]
                ans[1] += arr[1]
                ans[2] += arr[2]
            }
        }
    }

    fun `백준 1629번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val data = reader.readLine().split(" ").map { it.toLong() }

        val ans = fastMultiply(data[0], data[1], data[2])
        writer.write("$ans\n")
        writer.flush()
    }

    fun fastMultiply(src: Long, tar: Long, c: Long): Long {
        if (tar == 0L) return 1
        if (tar == 1L) return src % c

        val a = fastMultiply(src, tar / 2, c) % c
        return when (tar % 2) {
            0L -> (a * a) % c
            else -> ((a * a) % c * (src % c)) % c
        }
    }

    fun `백준 11401번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, k) = reader.readLine().split(" ").map { it.toLong() }
        val p = 1000000007L

        val a = (1..n).fold(1L) { acc, v -> (acc * v) % p }
        val b = (1..k).fold(1L) { acc, v -> (acc * v) % p }
        val c = (1..n - k).fold(1L) { acc, v -> (acc * v) % p }
        val d = fastMultiply(b * c, p - 2, p)
        val ans = (a * d) % p
        writer.write("$ans\n")
        writer.flush()
    }

    fun `백준 2740번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (l1, c1) = reader.readLine().split(" ").map { it.toInt() }
        val m1 = Array(l1) { reader.readLine().split(" ").map { it.toInt() } }
        val (l2, c2) = reader.readLine().split(" ").map { it.toInt() }
        val m2 = Array(l2) { reader.readLine().split(" ").map { it.toInt() } }
        val ans = matrixMultiply(m1, m2)

        val sb = StringBuilder()
        ans.forEach { row ->
            row.forEach { col ->
                sb.append(col)
                sb.append(" ")
            }
            sb.append("\n")
        }
        writer.write(sb.toString())
        writer.flush()
    }

    fun matrixMultiply(m: Array<List<Int>>, n: Array<List<Int>>): Array<Array<Int>> {
        if (m[0].size != n.size)
            throw IllegalArgumentException("곱할 수 없는 행렬 모양!")
        val result = Array(m.size) { Array(n[0].size) { 0 } }

        for (i in m.indices) {
            for (j in n.indices) {
                for (c in n[0].indices) {
                    result[i][c] += m[i][j] * n[j][c]
                }
            }
        }
        return result
    }

    fun `백준 10830번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, k) = reader.readLine().split(" ").map { it.toLong() }
        val m = Array(n.toInt()) { reader.readLine().split(" ").map { it.toInt() }.toTypedArray() }

        val div = 1000
        val ans = powOfMatrix(m, k, div)
        val sb = StringBuilder()

        ans.forEach { row ->
            row.forEach { col ->
                sb.append(col)
                sb.append(" ")
            }
            sb.append("\n")
        }
        writer.write(sb.toString())
        writer.flush()
        writer.flush()
    }

    fun powOfMatrix(m: Array<Array<Int>>, k: Long, div: Int): Array<Array<Int>> {
        if (k == 1L) {
            for (i in m.indices)
                for (j in m[0].indices)
                    m[i][j] %= div
            return m
        }

        val r = powOfMatrix(m, k / 2, div)
        val result = if (k % 2 == 0L) {
            matrixMultiply(r, r, div)
        } else {
            matrixMultiply(matrixMultiply(r, r, div), m, div)
        }

        return result
    }


    fun matrixMultiply(m: Array<Array<Int>>, n: Array<Array<Int>>, div: Int): Array<Array<Int>> {
        if (m[0].size != n.size)
            throw IllegalArgumentException("곱할 수 없는 행렬 모양!")
        val result = Array(m.size) { Array(n[0].size) { 0 } }

        for (i in m.indices) {
            for (j in n.indices) {
                for (c in n[0].indices) {
                    result[i][c] += (m[i][j] * n[j][c]) % div
                }
            }
        }

        for (i in result.indices)
            for (j in result[0].indices)
                result[i][j] %= div

        return result
    }
}