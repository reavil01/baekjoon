import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Baekjoon13460 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] nm = br.readLine().split(" ");
		int n = Integer.valueOf(nm[0]);
		int m = Integer.valueOf(nm[1]);

		char[][] map = new char[n][m];
		boolean[][][][] visit = new boolean[10][10][10][10];
		int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
		
		for (int i = 0; i < n; i++) {
			map[i] = br.readLine().toCharArray();
		}

		Point red = null, blue = null, hole = null;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 'B')
					blue = new Point(i, j);
				if (map[i][j] == 'R')
					red = new Point(i, j);
				if (map[i][j] == 'O')
					hole = new Point(i, j);
			}
		}

		int ans = findPath(map, dir, visit, hole, red, blue);
		if(ans == Integer.MAX_VALUE)
			ans = -1;
		bw.write(ans+"");

		bw.flush();
		bw.close();
		br.close();
	}

	static Point move(char[][] map, int[][] dir, Point point, int d) {
		Point p = new Point(point.row, point.col);
		
		while (true) {
			p.row += dir[d][0];
			p.col += dir[d][1];
			if (map[p.row][p.col] == '#') {
				p.row -= dir[d][0];
				p.col -= dir[d][1];
				break;
			} else if (map[p.row][p.col] == 'O') {
				break;
			}
		}
		
		return p;
	}

	static int findPath(char[][] map, int[][] dir, boolean[][][][] visit, Point hole, Point red, Point blue) {
		Queue<Positions> queue = new LinkedList<Positions>();
		queue.add(new Positions(red, blue, 0));
		visit[red.row][red.col][blue.row][blue.col] = true;
		
		while(!queue.isEmpty()) {
			Positions p = queue.poll();
			
			if (p.depth > 10) return -1;
			
			if(p.red.isOverlap(hole)) return p.depth;
			
			for(int i = 0; i < 4; i++) {
				Point r = move(map, dir, p.red, i);
				Point b = move(map, dir, p.blue, i);
				
				if(b.isOverlap(hole)) continue;
				
				if(r.isOverlap(b)) {
					switch(i) {
					case 0: 
						if (p.red.row < p.blue.row) b.row++; else r.row++; break;
					case 1: 
						if (p.red.col < p.blue.col) r.col--; else b.col--; break;
					case 2: 
						if (p.red.row < p.blue.row) r.row--; else b.row--; break;
					case 3: 
						if (p.red.col < p.blue.col) b.col++; else r.col++; break;
					}
				}
				
				if(!visit[r.row][r.col][b.row][b.col]) {
					visit[r.row][r.col][b.row][b.col] = true;
					queue.add(new Positions(r, b, p.depth+1));
				}
			}
		}
		
		return -1;
	}

		
}

class Point {
	int row, col;

	Point(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public String toString() {
		return String.format("Point(%d, %d)", row, col);
	}

	public boolean isOverlap(Point p) {
		if (this.row == p.row && this.col == p.col) {
			return true;
		}
		return false;
	}
}

class Positions {
	Point red, blue;
	int depth;
	
	Positions(Point r, Point b, int depth) {
		this.red = r;
		this.blue = b;
		this.depth = depth;
	}
}