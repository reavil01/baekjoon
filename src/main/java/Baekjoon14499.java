import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Baekjoon14499 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int[] arg = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		int n = arg[0];
		int m = arg[1];
		int x = arg[2];
		int y = arg[3];
		int k = arg[4];
		
		int[][] map = new int[n][m];
		for(int i = 0; i < n; i++) {
			map[i] = Arrays.stream(br.readLine().split(" "))
					.mapToInt(Integer::parseInt)
					.toArray();
		}
		
		int[] commands = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();;
		
		int[] dice = new int[6];
		
		for(int c : commands) {
			boolean flag = false;
			switch(c) {
				case 1:
					if(y + 1 < m) {
						flag = true;
						y++;
					}
					break;
				case 2:
					if(y - 1 >= 0) {
						flag = true;
						y--;
					}
					break;
				case 3:
					if(x - 1 >= 0) {
						flag = true;
						x--;
					}
					break;
				case 4:
					if(x + 1 < n) {
						flag = true;
						x++;
					}
					break;
			}
			
			if(flag) {
				dice  = rollDice(dice, c);
				if(map[x][y] == 0) {
					map[x][y] = dice[0];
				} else {
					dice[0] = map[x][y];
					map[x][y] = 0;
				}
				bw.write(dice[5] + "\n");
			}
		}

		bw.flush();
		bw.close();
		br.close();
	}
	
	public static int[] rollDice(int[] dice, int dir) {
		int[] newDice = new int[6];
		switch(dir) {
			case 1:
				newDice[0] = dice[2];
				newDice[1] = dice[1];
				newDice[2] = dice[5];
				newDice[3] = dice[0];
				newDice[4] = dice[4];
				newDice[5] = dice[3];
				break;
			case 2:
				newDice[0] = dice[3];
				newDice[1] = dice[1];
				newDice[2] = dice[0];
				newDice[3] = dice[5];
				newDice[4] = dice[4];
				newDice[5] = dice[2];
				break;
			case 3:
				newDice[0] = dice[1];
				newDice[1] = dice[5];
				newDice[2] = dice[2];
				newDice[3] = dice[3];
				newDice[4] = dice[0];
				newDice[5] = dice[4];
				break;
			case 4:
				newDice[0] = dice[4];
				newDice[1] = dice[0];
				newDice[2] = dice[2];
				newDice[3] = dice[3];
				newDice[4] = dice[5];
				newDice[5] = dice[1];
				break;
		}
		
		return newDice;
	}
}
