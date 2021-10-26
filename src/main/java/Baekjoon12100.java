import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Baekjoon12100 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int N = Integer.valueOf(br.readLine());
		int[][] map = new int[N][N];

		for (int i = 0; i < N; i++) {
			String[] data = br.readLine().split(" ");
			map[i] = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
		}

		int ans = search(map, 0);
		bw.write(ans + "");
		bw.flush();
		bw.close();
		br.close();
	}

	public static int search(int[][] map, int depth) {
		int max = 0;
		if (depth >= 5) {
			int ans = Arrays
					.stream(map)
					.flatMapToInt(x -> Arrays.stream(x))
					.max()
					.getAsInt();
			if(ans > max) max = ans;
			return max;
		}

		for (int i = 0; i < 4; i++) {
			int ans = search(move(map, i), depth + 1);
			if(ans > max) max = ans;
		}

		return max;
	}

	public static int[][] move(int[][] map, int d) {
		int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int[][] ans = new int[map.length][map.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				ans[i][j] = map[i][j];
			}
		}
		
		int preX, preY, curX, curY;
		switch (d) {
			case 0: 
				for(int i = 0; i < ans.length; i++) {
					preX = 0;
					preY = i;
					curX = preX + dir[d][0];
					curY = preY + dir[d][1];
					
					while(true) {
						while(curX < ans.length) {
							if(ans[curX][curY] != 0) {
								break;
							}
							curX += dir[d][0];
							curY += dir[d][1];
						}
						
						if (curX >= ans.length)
							break;
	
						if (ans[preX][preY] == 0) {
							ans[preX][preY] = ans[curX][curY];
							ans[curX][curY] = 0;
						} else if (ans[preX][preY] == ans[curX][curY]) {
							ans[preX][preY] *= 2;
							ans[curX][curY] = 0;
							preX += dir[d][0];
							preY += dir[d][1];
						} else {
							preX += dir[d][0];
							preY += dir[d][1];
							curX = preX + dir[d][0];
							curY = preY + dir[d][1];
						}
					}
				}
				break;
			case 3:
				for(int i = 0; i < ans.length; i++) {
					preX = i;
					preY = ans.length - 1;
					curX = preX + dir[d][0];
					curY = preY + dir[d][1];
					
					while(true) {
						while(curY >= 0) {
							if(ans[curX][curY] != 0) {
								break;
							}
							curX += dir[d][0];
							curY += dir[d][1];
						}

						if (curY < 0)
							break;
	
						if (ans[preX][preY] == 0) {
							ans[preX][preY] = ans[curX][curY];
							ans[curX][curY] = 0;
						} else if (ans[preX][preY] == ans[curX][curY]) {
							ans[preX][preY] *= 2;
							ans[curX][curY] = 0;
							preX += dir[d][0];
							preY += dir[d][1];
						} else {
							preX += dir[d][0];
							preY += dir[d][1];
							curX = preX + dir[d][0];
							curY = preY + dir[d][1];
						}
					}
				}
				break;
			case 1: 
				for(int i = ans.length - 1; i >= 0; i--) {
					preX = ans.length-1;
					preY = i;
					curX = preX + dir[d][0];
					curY = preY + dir[d][1];
					
					while(true) {
						while(curX >= 0) {
							if(ans[curX][curY] != 0) {
								break;
							}
							curX += dir[d][0];
							curY += dir[d][1];
						}
						
						if (curX < 0)
							break;
	
						if (ans[preX][preY] == 0) {
							ans[preX][preY] = ans[curX][curY];
							ans[curX][curY] = 0;
						} else if (ans[preX][preY] == ans[curX][curY]) {
							ans[preX][preY] *= 2;
							ans[curX][curY] = 0;
							preX += dir[d][0];
							preY += dir[d][1];
						} else {
							preX += dir[d][0];
							preY += dir[d][1];
							curX = preX + dir[d][0];
							curY = preY + dir[d][1];
						}
					}
				}
				break;
			case 2:
				for(int i = 0; i < ans.length; i++) {
					preX = i;
					preY = 0;
					curX = preX + dir[d][0];
					curY = preY + dir[d][1];
					
					while(true) {
						while(curY < ans.length) { 
							if(ans[curX][curY] != 0) {
								break;
							}
							curX += dir[d][0];
							curY += dir[d][1];
						}
						
						if (curY >= ans.length)
							break;
						
						if (ans[preX][preY] == 0) {
							ans[preX][preY] = ans[curX][curY];
							ans[curX][curY] = 0;
						} else if (ans[preX][preY] == ans[curX][curY]) {
							ans[preX][preY] *= 2;
							ans[curX][curY] = 0;
							preX += dir[d][0];
							preY += dir[d][1];
						} else {
							preX += dir[d][0];
							preY += dir[d][1];
							curX = preX + dir[d][0];
							curY = preY + dir[d][1];
						}
					}
				}
				break;
		}
		return ans;
	}
}
