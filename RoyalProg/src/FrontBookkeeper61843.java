
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FrontBookkeeper61843 implements IFrontBookkeeper {

	private Map<String, List<String>> regiments;
	private Map<String, String> attachments;
	private Map<String, List<String>> attachedTo;

	public FrontBookkeeper61843() {
		regiments = new LinkedHashMap<>();
		attachments = new HashMap<>();
		attachedTo = new HashMap<>();
	}

	@Override
	public String updateFront(String[] news) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < news.length; i++) {
			if (news[i].contains("=")) {
				assignDivision(news[i]);
			} else if (news[i].contains("died heroically")) {
				killSoldiers(news[i]);
			} else if (news[i].contains("show soldier")) {
				// System.out.println(showAllDivisionsOfSoldier(news[i]));
				sb.append(showAllDivisionsOfSoldier(news[i]));
				sb.append("\n");
				continue;
			} else if (news[i].contains("show")) {
				// System.out.println(showDivision(news[i]));
				sb.append(showDivision(news[i]));
				sb.append("\n");
			} else if (news[i].contains("attached to") && news[i].contains("after soldier")) {
				attachAfterSoldier(news[i]);
				continue;
			} else if (news[i].contains("attached to")) {
				attachToDivision(news[i]);
			}
		}
		return sb.toString();
	}

	// NE RABOTI
	private void killSoldiers(String killedSoldiersLine) {
		// soldiers 2..3 from division_Dimitroff died heroically
		String[] splitted = killedSoldiersLine.split(" ");
		String rangeSoldiers = splitted[1];
		String[] splittedSoldiers = rangeSoldiers.split("[..]");
		int startSoldier = Integer.parseInt(splittedSoldiers[0]);
		int endSoldier = Integer.parseInt(splittedSoldiers[2]);
		// System.out.println(startSoldier);
		// System.out.println(endSoldier);
		String topDivision = splitted[3];
		// System.out.println(topDivision);
		Collection<String> nums = new ArrayList<>();
		for (int i = startSoldier; i <= endSoldier; i++) {
			nums.add(String.valueOf(i));
		}
		// for (int j = 0; j < attachedTo.get(topDivision).size(); j++) {
		// String current = attachedTo.get(topDivision).get(j);
		// while (current != null) {
		// regiments.get(current).removeAll(nums);
		// current = attachedTo.get(current).get(0);
		// }
		// }
		String current = topDivision;
		List<String> attached = attachedTo.get(current);
		regiments.get(current).removeAll(nums);
		for (int i = 0; i < attached.size(); i++) {
			delete(attachedTo.get(current).get(i), nums);
		}

		// regiments.get(current).removeAll(nums);
		// current = attachedTo.get(current);
		// }
	}

	private void delete(String reg, Collection<String> digits) {
		if (reg == null) {
			return;
		}

		regiments.get(reg).removeAll(digits);
		if (attachedTo.get(reg) != null) {
			for (int i = 0; i < attachedTo.get(reg).size(); i++) {
				delete(attachedTo.get(reg).get(i), digits);
			}
		}
	}

	private void attachAfterSoldier(String attachLine) {
		String[] splitted = attachLine.split(" ");
		String unit1 = splitted[0];
		String unit2 = splitted[3];
		String soldier = splitted[6];
		int indexSoldier = regiments.get(unit2).indexOf(soldier);
		// System.out.println(unit1);
		// System.out.println(unit2);
		// System.out.println(soldier);
		if (attachedTo.get(unit2) == null) {
			attachedTo.put(unit2, new ArrayList<>());
			attachedTo.get(unit2).add(unit1);
		}
		attachedTo.get(unit2).add(unit1);

		if (attachments.get(unit1) == null) {
			attachments.put(unit1, unit2);
			regiments.get(unit2).addAll(indexSoldier + 1, regiments.get(unit1));
			return;
		}
		String attachedToE = attachments.get(unit1);
		while (attachedToE != null) {
			regiments.get(attachedToE).removeAll(regiments.get(unit1));
			attachedToE = attachments.get(attachedToE);
		}

		regiments.get(unit2).addAll(indexSoldier + 1, regiments.get(unit1));
		attachments.put(unit1, unit2);
	}

	private void attachToDivision(String attachLine) {
		String unit1 = attachLine.substring(0, attachLine.indexOf("attached") - 1);
		String unit2 = attachLine.substring(attachLine.lastIndexOf(" ") + 1);
		// System.out.println(unit1);
		// System.out.println(unit2);
		if (attachedTo.get(unit2) == null) {
			attachedTo.put(unit2, new ArrayList<>());
			attachedTo.get(unit2).add(unit1);
		}
		attachedTo.get(unit2).add(unit1);

		if (attachments.get(unit1) == null) {
			attachments.put(unit1, unit2);
			regiments.get(unit2).addAll(regiments.get(unit1));
			return;
		}
		String attachedToE = attachments.get(unit1);
		while (attachedToE != null) {
			regiments.get(attachedToE).removeAll(regiments.get(unit1));
			attachedToE = attachments.get(attachedToE);
		}
		regiments.get(unit2).addAll(regiments.get(unit1));
		attachments.put(unit1, unit2);
	}

	private String showAllDivisionsOfSoldier(String soldierLine) {
		String soldier = soldierLine.substring(soldierLine.lastIndexOf(' ') + 1);
		StringBuilder sb = new StringBuilder();
		for (Entry<String, List<String>> entry : regiments.entrySet()) {
			List<String> division = entry.getValue();
			if (division.contains(soldier)) {
				sb.append(entry.getKey());
				sb.append(", ");
			}
		}
		return sb.substring(0, sb.toString().lastIndexOf(','));
	}

	private String showDivision(String showDivLine) {
		String divisionName = showDivLine.substring(showDivLine.indexOf(' ') + 1);
		return regiments.get(divisionName).toString();
	}

	private void assignDivision(String assigmentLine) {
		// regiment_Stoykov = [1, 2, 3]
		String regiment = assigmentLine.substring(0, assigmentLine.indexOf('=') - 1);
		String arrayNumbers = assigmentLine.substring(assigmentLine.indexOf('[') + 1, assigmentLine.indexOf(']'));
		if (arrayNumbers.length() == 0) {
			regiments.put(regiment, new ArrayList<>());
			return;
		}

		String[] arr = arrayNumbers.split(", ");
		regiments.put(regiment, new ArrayList<>(Arrays.asList(arr)));
		return;
	}

	public static void main(String[] args) {
		IFrontBookkeeper book = new FrontBookkeeper61843();
		String[] news = { "regiment_Stoykov = [1, 2, 3]", "show regiment_Stoykov", "regiment_Chaushev = [13]",
				"show soldier 13", "division_Dimitroff = []", "regiment_Stoykov attached to division_Dimitroff",
				"regiment_Chaushev attached to division_Dimitroff", "show division_Dimitroff", "show soldier 13",
				"brigade_Ignatov = []", "regiment_Stoykov attached to brigade_Ignatov",
				"regiment_Chaushev attached to brigade_Ignatov after soldier 3", "show brigade_Ignatov",
				"show division_Dimitroff", "brigade_Ignatov attached to division_Dimitroff", "show division_Dimitroff",
				"soldiers 2..3 from division_Dimitroff died heroically", "show regiment_Stoykov",
				"show brigade_Ignatov", "show division_Dimitroff" };
		// double start = System.currentTimeMillis();
		System.out.println(book.updateFront(news));
		// System.out.println((System.currentTimeMillis() - start) / 1000.0);
	}

}
