import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Baekjoon13458 {
	static int N, B, C;
	static int[] person;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		N = Integer.valueOf(br.readLine());
		person = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		int[] arg = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		B = arg[0];
		C = arg[1];
		
		long ans = 0;
		for(int n : person) {
			if(n > 0) {
				n = n - B;
				ans++;
			}
			
			if(n > 0) {
				ans += Math.ceil(n / (C + 0.0));
			}
		}

		bw.write(ans + "");
		bw.flush();
		bw.close();
		br.close();
	}
}
