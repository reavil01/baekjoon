import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;

public class Baekjoon14500 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int[] arg = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int n = arg[0];
		int m = arg[1];

		int[][] map = new int[n][m];
		for (int i = 0; i < n; i++) {
			map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}

		int ans = search(map);

		bw.write(ans + "");
		bw.flush();
		bw.close();
		br.close();
	}

	public static int search(int[][] map) {
		int ans = 0;

		HashSet<Integer> visit = new HashSet<Integer>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				visit.clear();
				int key = i * 1000 + j;
				visit.add(key);
				int a = findMaxArea(map, visit, i, j, 0, 0);
				visit.remove(key);
				int b = find(map, i, j);
				if (a > ans)
					ans = a;
				if (b > ans)
					ans = b;
			}
		}
		return ans;
	}

	public static int find(int[][] map, int x, int y) {
		int ans = 0;
		int[][] posX = { { 1, 1, 2 }, { 1, 1, 2 }, { 0, 0, 1 }, { 1, 1, 1 } };
		int[][] posY = { { 0, -1, 0 }, { 0, 1, 0 }, { -1, 1, 0 }, { -1, 0, 1 } };

		for (int i = 0; i < 4; i++) {
			int max = map[x][y];
			for (int j = 0; j < 3; j++) {
				int xx = x + posX[i][j];
				int yy = y + posY[i][j];
				if (xx < 0 || yy < 0 || xx >= map.length || yy >= map[0].length) {
					break;
				}
				max += map[xx][yy];
			}
			if (max > ans)
				ans = max;
		}

		return ans;
	}

	public static int findMaxArea(int[][] map, HashSet<Integer> visit, int x, int y, int depth, int before) {
		if (x < 0 || y < 0 || x >= map.length || y >= map[0].length)
			return 0;

		int ans = before + map[x][y];

		if (visit.size() == 4) {
			return ans;
		}

		int[][] increase = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int max = 0;
		for (int i = 0; i < 4; i++) {
			int newX = x + increase[i][0];
			int newY = y + increase[i][1];
			int newKey = newX * 1000 + newY;
			
			if(visit.contains(newKey)) continue;
			
			visit.add(newKey);
			int temp = findMaxArea(map, visit, newX, newY, depth + 1, ans);
			visit.remove(newKey);
			if (temp > max)
				max = temp;
		}
		
		if (ans < max)
			ans = max;

		return ans;
	}
}
