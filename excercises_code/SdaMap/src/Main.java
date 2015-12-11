public class Main {

	public static void main(String[] args) {
		MyMap<Double, String> map = new MyMap<>();

		map.put(1.1, "world");
		map.put(1.2, "world2");
		map.put(2.2, "world3");
		map.put(3.3, "world4");

		System.out.println(map.get(2.2));
		map.remove(2.2);
		System.out.println(map.get(2.2));

	}

}
