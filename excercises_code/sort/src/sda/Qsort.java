package sda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Qsort {
	public static List<Integer> sort(List<Integer> or) {

		if (or.size() <= 1) {
			return or;
		}

		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();

		int pivot = or.get(0);
		int pcount = 0;

		for (int x : or) {
			if (x > pivot) {
				right.add(x);
			} else if (x < pivot) {
				left.add(x);
			} else
				pcount++;
		}

		left = sort(left);
		right = sort(right);

		List<Integer> result = new ArrayList<>();
		result.addAll(left);
		for (int i = 0; i < pcount; i++)
			result.add(pivot);
		result.addAll(right);
		return result;
	}

	public static void main(String[] args) {

		Integer[] a = new Integer[] { 5, 5, 5, 3, 3, 3, 4, 4, 1, 1, 2, 2, 7 };
		List<Integer> sorted = sort(Arrays.asList(a));

		System.out.println(sorted);

	}

}
