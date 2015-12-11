import java.util.ArrayList;
import java.util.List;

public class Coins {
	public static int solve(List<Integer> coins, int sum) {
		int[] dp = new int[sum + 1];
		dp[0] = 0;

		for (int i = 1; i <= sum; i++) {
			int min = sum;
			for (int currentCoin : coins) {
				int remain = i - currentCoin;
				if (remain < 0)
					continue;
				int option = dp[remain] + 1;
				if (min > option) {
					min = option;
				}
			}
			dp[i] = min;
		}
		return dp[sum];
	}

	public static void main(String[] args) {
		List<Integer> coins = new ArrayList<>();
		coins.add(1);
		coins.add(3);
		coins.add(5);
		coins.add(8);
		coins.add(10);
		int s = 17;
		System.out.println(solve(coins, s));
	}

}
