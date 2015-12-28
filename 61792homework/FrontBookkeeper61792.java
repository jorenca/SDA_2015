package theRoyalProgrammer;

import java.util.HashMap;
import java.util.LinkedList;

public class FrontBookkeeper61792 implements IFrontBookkeeper {

	private static HashMap<String, LinkedList<Integer>> units = new HashMap<>();
	private static HashMap<String, String> connected = new HashMap<>();

	@Override
	public String updateFront(String[] news) {
		processingNews(news);
		return null;
	}

	public void processingNews(String[] news) {
		for (String newLine : news) {
			if (newLine.contains(" = ")) {
				unitAssignment(newLine);
			} else if (newLine.contains(" attached ")) {
				unitAttachment(newLine);
			} else if (newLine.contains(" after ")) {
				unitPositionalAttachment(newLine);
			} else if (newLine.contains(" died ")) {
				soldierDeath(newLine);
			} else if (newLine.contains("show ")) {
				unitDisplay(newLine);
			} else if (newLine.contains("show soldiers ")) {
				soldierDisplay(newLine);
			} else {
				continue;
			}
		}
	}

	public static void unitAssignment(String newLine) {

		String[] separatedStrings = newLine.split(" = ");
		String[] splitted = separatedStrings[1].replaceAll("\\[", "").replaceAll("\\]", "").split(", ");

		LinkedList<Integer> soldiers = new LinkedList<>();

		for (String element : splitted) {
			try {
				soldiers.add(Integer.parseInt(element));
			} catch (NumberFormatException newNumbFormEx) {
			}
			;

		}

		units.put(separatedStrings[0], soldiers);
	}

	public static void unitAttachment(String newLine) {
		String[] separatedStrings = newLine.split(" attached to ");

		LinkedList<Integer> partOne = units.get(separatedStrings[0]);
		LinkedList<Integer> partTwo = units.get(separatedStrings[1]);

		if (containsPartOfList(partTwo, partOne)) {
			partTwo.addAll(partOne);
		}

		removeAndConnect(separatedStrings[0], separatedStrings[1]);
	}

	public static void unitPositionalAttachment(String newLine) {

		String[] separatedStrings = newLine.split(" attached to ");
		String[] newString = separatedStrings[1].split(" after soldier ");
		String firstUnit = separatedStrings[0];
		String secondUnit = newString[0];
		int numberOfSoldier = Integer.parseInt(newString[1]);

		LinkedList<Integer> firstList = units.get(firstUnit);
		LinkedList<Integer> secondList = units.get(secondUnit);

		int startIndex = 0;
		for (int i = 0; i < secondList.size(); i++) {
			if (secondList.get(i) == numberOfSoldier) {
				startIndex = i;
				break;
			}
		}
		int firstListIndex;
		int secondListIndex;
		for (firstListIndex = 0, secondListIndex = startIndex + 1; firstListIndex < firstList
				.size(); secondListIndex++, firstListIndex++) {
			secondList.add(secondListIndex, firstList.get(firstListIndex));
		}

		removeAndConnect(separatedStrings[0], separatedStrings[1]);
	}

	public static void removeAndConnect(String firstString, String secondString) {
		if (connected.containsKey(firstString)) {
			removeFromConnected(firstString);
		}
		connected.put(firstString, secondString);
	}

	public static void soldierDeath(String newLine) {
		String[] separatedStrings = newLine.split(" ");
		String[] indexes = separatedStrings[1].split("\\..");
		String unitName = separatedStrings[3];
		int begin = Integer.parseInt(indexes[0]);
		int end = Integer.parseInt(indexes[1]);

		LinkedList<Integer> listOfSoldiers = units.get(unitName);
		LinkedList<Integer> deadSoldiers = new LinkedList<>();

		int beginIndex = 0;
		for (int i = 0; i < listOfSoldiers.size(); i++) {
			if (listOfSoldiers.get(i) == begin) {
				beginIndex = i;
				break;
			}
		}

		int endIndex = 0;
		for (int i = 0; i < listOfSoldiers.size(); i++) {
			if (listOfSoldiers.get(i) == end) {
				endIndex = i;
				break;
			}
		}

		for (int i = 0; i < listOfSoldiers.size(); i++) {
			if (i == beginIndex && beginIndex <= endIndex) {
				deadSoldiers.add(listOfSoldiers.get(beginIndex));
				beginIndex++;
			}
		}

		listOfSoldiers.removeAll(deadSoldiers);
		for (LinkedList<Integer> allUnits : units.values()) {
			if (allUnits.containsAll(deadSoldiers)) {
				allUnits.removeAll(deadSoldiers);
			}
		}
	}

	public static void unitDisplay(String newLine) {
		String[] separatedStrings = newLine.split(" ");
		LinkedList<Integer> soldiers = units.get(separatedStrings[1]);
		System.out.println(soldiers.toString());
	}

	public static void soldierDisplay(String newLine) {
		String[] separatedStrings = newLine.split(" ");
		int number = Integer.parseInt(separatedStrings[2]);

		LinkedList<String> brigades = new LinkedList<>();

		for (String str : units.keySet()) {
			LinkedList<Integer> list = units.get(str);
			if (list.contains(number)) {
				brigades.add(str);
			}
		}
		String brigade = brigades.toString();
		brigade = brigade.replace("[", "");
		brigade = brigade.replace("]", "");
		System.out.println(brigade);
	}

	public static boolean containsPartOfList(LinkedList<Integer> firstList, LinkedList<Integer> partOfList) {
		for (int i : partOfList) {
			if (firstList.contains(i)) {
				return false;
			}
		}
		return true;
	}

	public static void removeFromConnected(String newUnit) {

		String isPart = connected.get(newUnit);
		LinkedList<Integer> firstList = units.get(isPart);
		LinkedList<Integer> partOfList = units.get(newUnit);
		firstList.removeAll(partOfList);
	}
}
