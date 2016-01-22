import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FrontBookkeeper61864 implements IFrontBookkeeper {

	private Map<String, LinkedList<Integer>> units;
	private Map<String, String> attachments;

    public FrontBookkeeper61864() {
        attachments = new HashMap<>();
        units = new HashMap<>();
    }

	@Override
	public void updateFront(String[] news) {
		for (String command : news) {
			if (command.contains("=")) {
				this.assign(command);
			} else if (command.contains("attached")) {
				this.attach(command);
			} else if (command.contains("show")) {
				this.show(command);
			} else if (command.contains("died")) {
				this.die(command);
			}
		}
	}

	private void attach(String command) {
		String unitFirst = command.substring(0, command.indexOf(' '));
		String unitSecond;
		int index;

		if (command.contains("after soldier")) {
			int soldierID = Integer.parseInt(command.substring(command.lastIndexOf(' ') + 1));
			unitSecond = command.substring(command.indexOf("to") + 3, command.indexOf("after") - 1);
			index = units.get(unitSecond).indexOf(soldierID) + 1;
		} else {
			unitSecond = command.substring(command.lastIndexOf(' ') + 1);
			index = units.get(unitSecond).size();

		}

		attach(unitFirst, unitSecond, index);
	}

	private void attach(String unitFirst, String unitSecond, int index) {
		String attachm = attachments.get(unitFirst);
		if (attachments.containsKey(unitFirst)) {
			units.get(attachm).removeAll(units.get(unitFirst));
			attachments.replace(unitFirst, unitSecond);

		} else {
			attachments.put(unitFirst, unitSecond);
		}
		units.get(unitSecond).addAll(index, units.get(unitFirst));
	}

	private void show(String command) {
		int index = command.lastIndexOf(' ') + 1;

		if (command.contains("soldier")) {
			int soldierID = Integer.parseInt(command.substring(index));
			LinkedList<String> list = new LinkedList<>();

			for (Map.Entry<String, LinkedList<Integer>> entry : units.entrySet()) {
				if (entry.getValue().contains(soldierID)) {
					list.add(entry.getKey());
				}
			}
			printAttachments(list);

		} else {
			String name = command.substring(index);
			System.out.println(units.get(name));
		}
	}

	private void printAttachments(LinkedList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i != list.size() - 1) {
				System.out.print(list.get(i) + ", ");
			} else {
				System.out.print(list.get(i) + "\n");
			}
		}
	}

	private void assign(String command) {
		int arrayBegin = command.indexOf('[') + 1;
		int arrayEnd = command.indexOf(']');
		int nameIndex = command.indexOf(' ');
		String name = command.substring(0, nameIndex);

		LinkedList<Integer> soldiersList = new LinkedList<>();

		if (arrayBegin == arrayEnd) {
			units.put(name, soldiersList);
		} else {
			String[] soldiersArray = command.substring(arrayBegin, arrayEnd).split("[ ,]+");

			for (int i = 0; i < soldiersArray.length; i++) {
				soldiersList.add(Integer.parseInt(soldiersArray[i]));
			}
			units.put(name, soldiersList);

		}
	}

	private void die(String command) {
		int low = Integer.parseInt(command.substring(command.indexOf(' ') + 1, command.indexOf('.')));
		int high = Integer.parseInt(command.substring(command.lastIndexOf('.') + 1, command.indexOf("from") - 1));
		String unit = command.substring(command.indexOf("from") + 5, command.indexOf("died") - 1);

		LinkedList<Integer> list = new LinkedList<>();
		int temp = low;
		for (int i = low; i <= high; i++) {
			list.add(temp++);
		}

		Queue<String> queue = new LinkedList<>();
		queue.add(unit);

		while (!queue.isEmpty()) {
			String currentUnit = queue.poll();
			for (Map.Entry<String, String> entry : attachments.entrySet()) {
				if (entry.getValue().equals(currentUnit)) {
					units.get(currentUnit).removeAll(list);
					units.get(entry.getKey()).removeAll(list);
					queue.add(entry.getKey());
					break;
				}
			}
		}

	}

	public static void main(String[] args) {
		String[] news = { "regiment_Stoykov = [1, 2, 3]", "show regiment_Stoykov", "regiment_Chaushev = [13]",
				"show soldier 13", "division_Dimitroff = []", "regiment_Stoykov attached to division_Dimitroff",
				"regiment_Chaushev attached to division_Dimitroff", "show division_Dimitroff", "show soldier 13",
				"brigade_Ignatov = []", "regiment_Stoykov attached to brigade_Ignatov",
				"regiment_Chaushev attached to brigade_Ignatov after soldier 3", "show brigade_Ignatov",
				"show division_Dimitroff", "brigade_Ignatov attached to division_Dimitroff", "show division_Dimitroff",
				"soldiers 2..3 from division_Dimitroff died heroically ", "show regiment_Stoykov",
				"show brigade_Ignatov", "show division_Dimitroff" };
		FrontBookkeeper61864 f = new FrontBookkeeper61864();
		f.updateFront(news);
	}
}
