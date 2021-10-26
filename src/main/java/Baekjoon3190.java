import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Baekjoon3190 {
	static int N, K, L;
	static int[][] map;
	static HashMap<Integer, String> command = new HashMap();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		N = Integer.valueOf(br.readLine());
		K = Integer.valueOf(br.readLine());

		map = new int[N][N];
		for (int i = 0; i < K; i++) {
			String[] data = br.readLine().split(" ");
			int x = Integer.valueOf(data[0]);
			int y = Integer.valueOf(data[1]);
			map[x-1][y-1] = Integer.MAX_VALUE;
		}

		L = Integer.valueOf(br.readLine());
		for (int i = 0; i < L; i++) {
			String[] data = br.readLine().split(" ");
			int x = Integer.valueOf(data[0]);
			command.put(x, data[1]);
		}

		int ans = play();
		bw.write(ans + "");
		bw.flush();
		bw.close();
		br.close();
	}

	public static int play() {
		int time = 0;
		int d = 1;
		// ╩С ©Л го аб
		int[][] dirction = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
		int x = 0;
		int y = 0;
		
		map[x][y] = 1;
		while (true) {
			time++;
			
			x += dirction[d][0];
			y += dirction[d][1];
			
			if (x < 0 || y < 0)
				break;
			if (x >= N || y >= N)
				break;
			if (map[x][y] > 0 && map[x][y] != Integer.MAX_VALUE)
				break;
			
			if(map[x][y] != Integer.MAX_VALUE) {
				int min = Integer.MAX_VALUE;
				int minX = 0;
				int minY = 0;
				for(int i = 0; i < N; i++) {
					for(int j = 0; j<N; j++) {
						if(map[i][j] > 0 && map[i][j] < min) {
							min = map[i][j];
							minX = i;
							minY = j;
						}
					}
				}
				
				map[minX][minY] = 0;
			}
			map[x][y] = time+1;
			
			if(command.containsKey(time)) {
				String dir = command.get(time);
				if(dir.equals("L")) {
					d = (d + 3) % 4;
				} else {
					d = (d + 1) % 4;
				}
			}
		}

		return time;
	}
}
