import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

public class Baekjoon1662 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String input = br.readLine();
		Stack<Integer> stack = new Stack<Integer>();
		Stack<Integer> mul = new Stack<Integer>();

		int cnt = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(') {
				cnt--;
				int m = Integer.parseInt(input.charAt(i - 1) + "");
				mul.push(m);
				stack.push(cnt);
				cnt = 0;
			} else if (c == ')') {
				cnt *= mul.pop();
				cnt += stack.pop();
			} else {
				cnt++;
			}
		}

		bw.write(cnt + "");
		bw.flush();
		bw.close();
		br.close();
	}
}
