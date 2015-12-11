package sda;

import java.util.Arrays;

public class SSort {
	public static void sort(int[] or) {

		for (int j = 0; j < or.length / 2 + 1; j++) {
			int maxpos = j;
			int max = or[j];
			int minpos = j;
			int min = or[j];
			for (int i = j; i < or.length - j; i++) {
				if (or[i] > max) {
					max = or[i];
					maxpos = i;
				}
				if (or[i] < min) {
					min = or[i];
					minpos = i;
				}
			}
			int temp = or[or.length - j - 1];
			or[or.length - j - 1] = max;
			or[maxpos] = temp;

			temp = or[j];
			or[j] = min;
			or[minpos] = temp;
		}
	}

	public static void main(String[] args) {
		int[] a = new int[] { 5, 3, 4, 1, 2, 7 };
		sort(a);

		System.out.println(Arrays.toString(a));
	}
}
