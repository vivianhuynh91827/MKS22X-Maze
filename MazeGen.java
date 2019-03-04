import java.io.*;
import java.util.*;

public class MazeGen {

	private boolean[][] fill, linksv, linksh;
	private int rows, cols;
	private boolean generated;

	private int[] randr, randc;

	private static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

	public MazeGen(int height, int width) {
		rows = height;
		cols = width;
		generated = false;
		randr = new int[rows];
		randc = new int[cols];
	}

	public String toString() {
		if (!generated) throw new IllegalStateException("Use generate() before calling writeToFile() or toString()");
		StringBuilder sb = new StringBuilder();

		for (int c = 0; c < cols*2+1; c++) sb.append('#');
		sb.append('\n');

		for (int r = 0; r < rows; r++) {
			sb.append('#');
			for (int c = 0; c < cols-1; c++) {
				if (r == 0 && c == 0) sb.append('S');
				else sb.append(fill[r][c] ? ' ' : '.');

				sb.append(linksh[r][c] ? ' ' : '#');
			}
			if (r == rows-1) sb.append("E#\n");
			else sb.append((fill[r][cols-1] ? ' ' : '.')+"#\n");
			for (int c = 0; c < cols; c++) {
				sb.append('#');
				sb.append(r != rows-1 && linksv[r][c] ? ' ' : '#');
			}
			sb.append('#');
			if (r != rows-1) sb.append('\n');
		}

		return new String(sb);
	}

	public void generate() {
		// setting up data
		fill = new boolean[rows][cols];
		linksh = new boolean[rows][cols-1];
		linksv = new boolean[rows-1][cols];
		generated = true;
		fill[0][0] = true;

		// populate randr and randc
		List<Integer> rand = new ArrayList<>();
		for (int i = 0; i < rows; i++) rand.add(i);
		Collections.shuffle(rand);
		for (int i = 0; i < rows; i++) randr[i] = rand.get(i);
		rand.clear();
		for (int i = 0; i < cols; i++) rand.add(i);
		Collections.shuffle(rand);
		for (int i = 0; i < cols; i++) randc[i] = rand.get(i);

		// recursive solve
		int[] pair = {0, 0};
		while (pair != null) {
			solve(pair[0], pair[1]);
			pair = getSeed();
		}
	}

	private int[] getSeed() {
		int[] out = new int[2];
		int r, c;
		for (int ri = 0; ri < rows; ri++) {
			for (int ci = 0; ci < cols; ci++) {
				r = randr[ri];
				c = randc[ci];
				if (fill[r][c]) {
					for (int[] pair : DIR) {
						if (inRange(r+pair[0], c+pair[1]) && !fill[r+pair[0]][c+pair[1]] && (r != rows-1 || c != cols-1)) {
							out[0] = r;
							out[1] = c;
							return out;
						}
					}
				}
			}
		}
		return null;
	}

	private boolean inRange(int r, int c) {
		return r >= 0 && c >= 0 && r < rows && c < cols;
	}

	private void solve(int r, int c) {
		fill[r][c] = true;
		if (r == rows-1 && c == cols-1) return;
		List<Integer> dir = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			int[] pair = DIR[i];
			if (inRange(r+pair[0], c+pair[1]) && Math.random() < 0.5)
				dir.add(i);
		}
		Collections.shuffle(dir);

		for (int i : dir) {
			int[] pair = DIR[i];
			if (!fill[r+pair[0]][c+pair[1]]) {
				if (pair[0] == 0) linksh[r][pair[1] == 1 ? c : c-1] = true;
				else linksv[pair[0] == 1 ? r : r-1][c] = true;
				solve(r+pair[0], c+pair[1]);
			}
		}
	}

	public void writeToFile(String path) throws IOException {
		FileWriter f = new FileWriter(path);
		f.write(toString());
		f.close();
	}

	public static void main(String[] args) {
		try {
			// generate a maze
			MazeGen g = new MazeGen(20, 20); // create new blank maze
			g.generate(); // note: you can call generate() multiple times to re-generate a maze of the same size
			g.writeToFile("maze3.txt"); // self-explanatory

			// solve the maze
			// Maze m = new Maze("maze1.txt");
			// m.solve();
			// System.out.println(m);

		} catch (IOException e) {}
	}
}
