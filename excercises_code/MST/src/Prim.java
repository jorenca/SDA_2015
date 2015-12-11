import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {
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

	public Set<Rebro> solve(List<Rebro> in, int n) {

		PriorityQueue<Rebro> options = new PriorityQueue<>();
		final int initialVryh = 0;
		for (Rebro r : in) {
			if (r.a == initialVryh || r.b == initialVryh) {
				options.add(r);
			}
		}

		Set<Rebro> mst = new HashSet<>();
		int postaveniRebra = 0;
		Set<Integer> vk = new HashSet<>();
		vk.add(initialVryh);
		while (!options.isEmpty() && postaveniRebra < n - 1) {
			Rebro best = options.poll();

			if (!vk.contains(best.a) || !vk.contains(best.b)) {

				int novVryh = vk.contains(best.a) ? best.b : best.a;
				for (Rebro r : in) {
					if (r.a == novVryh || r.b == novVryh) {
						options.add(r);
					}
				}
				vk.add(novVryh);
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

		Prim k = new Prim();
		Set<Rebro> mst = k.solve(in, 5);
		for (Rebro r : mst) {
			System.out.println(r);
		}
	}
}
