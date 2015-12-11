package sda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MSort {
	public static List<Integer> sort(List<Integer> or) {
		List<List<Integer>> kupchinki = new ArrayList<>();

		for (int x : or) {
			List<Integer> kupchinka = new ArrayList<>();
			kupchinka.add(x);
			kupchinki.add(kupchinka);
		}
		if (kupchinki.size() % 2 == 1) {
			kupchinki.add(new ArrayList<>());
		}

		// List<List<Integer>> result = new ArrayList<>();
		while (kupchinki.size() > 1) {
			List<List<Integer>> sletiKupchinki = new ArrayList<>();
			for (int i = 0; i < kupchinki.size(); i += 2) {
				List<Integer> first = kupchinki.get(i);
				List<Integer> second = kupchinki.get(i + 1);

				List<Integer> merged = new ArrayList<Integer>();
				while (first.size() > 0 || second.size() > 0) {
					if (first.size() > 0 && second.size() > 0) {
						if (first.get(0) > second.get(0)) {
							merged.add(first.get(0));
							first.remove(0);
						} else {
							merged.add(second.get(0));
							second.remove(0);
						}
					} else if (first.size() > 0) {
						merged.add(first.get(0));
						first.remove(0);
					} else {
						merged.add(second.get(0));
						second.remove(0);
					}
				}
				sletiKupchinki.add(merged);
			}

			kupchinki = sletiKupchinki;
		}

		return kupchinki.get(0);
	}

	public static void main(String[] args) {

		Integer[] a = new Integer[] { 5, 5, 5, 3, 3, 3, 4, 4, 1, 1, 2, 2, 7 };
		List<Integer> sorted = sort(Arrays.asList(a));

		System.out.println(sorted);

	}

}
