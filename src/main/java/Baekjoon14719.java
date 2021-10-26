import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Baekjoon14719 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int[] size = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::valueOf)
				.toArray();
		int H = size[0];
		int W = size[1];
		
		int[] heights = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::valueOf)
				.toArray();
		
		int ans = 0;
		for(int h = H; h > 0; h--) {
			int temp = 0;
			boolean isCount = false;
			for(int i = 0; i < W; i++) {
				if(isCount && heights[i] >= h) {
					ans += temp;
					temp = 0;
					continue;
				}
				
				if(heights[i] >= h) {
					isCount = true;
					continue;
				}
				
				if(isCount) {
					temp++;
				}
			}
		}
		bw.write(ans + "");
		bw.flush();
		bw.close();
		br.close();
	}
}
