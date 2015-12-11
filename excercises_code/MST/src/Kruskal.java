import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Kruskal {
	private static class Rebro implements Comparable<Rebro> {
		public int a;
		public int b;
		public int weight;

		public Rebro(int a, int b, int w) {
			this.a = a;
			this.b = b;
			weight = w;
		}

		@Override
		public int compareTo(Rebro arg0) {
			return weight - arg0.weight;
		}

		@Override
		public String toString() {
			return String.format("From %d to %d, weight %d", a, b, weight);
		}
	}

	private int[] con;

	private boolean isConnected(int a, int b) {
		return con[a] == con[b];
	}

	private void connect(int a, int b) {
		for (int i = 0; i < con.length; i++) {
			if (con[i] == a) {
				con[i] = b;
			}
		}
	}

	private Set<Rebro> solve(List<Rebro> input, int n) {
		con = new int[n];
		for (int i = 0; i < n; i++) {
			con[i] = i;
		}

		PriorityQueue<Rebro> pq = new PriorityQueue<Kruskal.Rebro>(input);

		Set<Rebro> mst = new HashSet<>();
		int postaveniRebra = 0;
		while (!pq.isEmpty() && postaveniRebra < n - 1) {
			Rebro best = pq.poll();
			if (!isConnected(best.a, best.b)) {
				connect(best.a, best.b);
				mst.add(best);
				postaveniRebra++;
			}
		}

		return mst;
	}

	public static void main(String[] args) {
		List<Rebro> in = new ArrayList<>();
		in.add(new Rebro(0, 1, 1));
		in.add(new Rebro(0, 4, 3));
		in.add(new Rebro(1, 4, 2));
		in.add(new Rebro(1, 2, 4));
		in.add(new Rebro(2, 3, 1));
		in.add(new Rebro(3, 4, 5));

		Kruskal k = new Kruskal();
		Set<Rebro> mst = k.solve(in, 5);
		for (Rebro r : mst) {
			System.out.println(r);
		}
	}

}
