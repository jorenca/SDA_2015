import java.util.LinkedList;

public class DFS {
	public static int n = 5;
	public static int bestDist = 9999999;
	public static int[][] m = new int[n][n];
	public static boolean[] v = new boolean[n];
	public static LinkedList<Integer> path = new LinkedList<Integer>();
	public static LinkedList<Integer> bestPath = new LinkedList<Integer>();

	public static void dfs(int curr, int end, int dist) {
		v[curr] = true;
		path.addLast(curr);
		System.out.println("Currenly at node " + curr);
		if (curr == end && bestDist > dist) {
			bestDist = dist;
			bestPath.clear();
			for (int node : path)
				bestPath.addLast(node);
			System.out.println("Returing (found) from node " + curr);
			return;
		}

		for (int i = 0; i < n; i++) {
			if (m[curr][i] != 0 && !v[i]) {
				dfs(i, end, dist + m[curr][i]);
			}
		}

		v[curr] = false;
		path.removeLast();
		System.out.println("Returning from node " + curr);
	}

	public static void main(String[] args) {

		m[0][1] = m[1][0] = 8;
		m[0][2] = m[2][0] = 1;
		m[1][2] = m[2][1] = 4;
		m[1][3] = m[3][1] = 2;
		m[2][4] = m[4][2] = 1;
		m[3][4] = m[4][3] = 3;

		dfs(0, 4, 0);

		System.out.println(bestDist);
		for (int node : bestPath) {
			System.out.print(node + " -> ");
		}

	}

}
