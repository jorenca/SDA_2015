package sda.homework;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import sda.grading.IFrontBookkeeper;

public class FrontBookkeeper61838 implements IFrontBookkeeper {
	private class Army {
		private Map<String, LinkedList<String>> army; // key - unit name, value
														// - soldiers in unit
		private Map<String, LinkedList<String>> soldiers; // key - soldier id,
															// value - units
		private Map<String, String> attached; // if the unit (key) is attached
												// to other unit already the
												// value is the unit it is
												// attached to, else it's "OK"

		public Army() {
			army = new HashMap<>();
			soldiers = new HashMap<>();
			attached = new HashMap<>();
		}

		public void addUnit(String unitName, LinkedList<String> unit) {
			if (!army.containsKey(unitName)) {
				army.put(unitName, unit);
				attached.put(unitName, "ok");
			} else {
				army.get(unitName).addAll(unit);
			}
			for (String soldier : unit) {
				if (!soldiers.containsKey(soldier)) {
					LinkedList<String> units = new LinkedList<>();
					units.add(unitName);
					soldiers.put(soldier, units);
				} else {
					soldiers.get(soldier).add(unitName);
				}
			}
		}

		public String showSoldier(String soldier) {
			String unitsPartOf = soldiers.get(soldier).toString();
			return unitsPartOf.substring(1, unitsPartOf.length() - 1);
		}

		// fckn UGLY
		public String showUnit(String unit) {
			// return army.get(unit).toString(); it's working but....
			String result = "";
			if (army.get(unit).size() > 1) {
				for (String soldiers : army.get(unit)) {
					if (!soldiers.equals("")) {
						result += soldiers + ", ";
					}
				}
				result = result.substring(0, result.length() - 2);
			}
			return "[" + result + "]";
		}

		public void attachUnit(String unit1, String unit2) {
			if (attached.get(unit1).equals("ok")) {
				addUnit(unit2, army.get(unit1));
				attached.put(unit1, unit2);
			} else {
				String alreadyAttachedTo = attached.get(unit1);
				army.get(alreadyAttachedTo).removeAll(army.get(unit1));
				addUnit(unit2, army.get(unit1));
			}
		}

		public void killSoldiers(int x, int y, String unitName) {
			for (int i = x; i <= y; i++) {
				String soldier = new Integer(i).toString();
				if (soldiers.containsKey(soldier)) {
					soldiers.remove(soldier);
					for (String unit : army.keySet()) {
						army.get(unit).remove(soldier);
					}
				} else {
					continue;
				}
			}
		}
	}

	String result = "";

	@Override
	public String updateFront(String[] news) {
		Army army = new Army();

		for (String currentNews : news) {
			String[] newsFromFront = currentNews.split(" ");

			if (currentNews.contains("=")) {
				String soldiers = currentNews.substring(newsFromFront[0]
						.length() + 4);
				String unitName = newsFromFront[0];
				LinkedList<String> soldiersList = new LinkedList<>();
				for (String soldier : soldiers.substring(0,
						soldiers.length() - 1).split(", ")) {
					soldiersList.add(soldier);
				}
				army.addUnit(unitName, soldiersList);

			} else if (currentNews.contains("attached")) {
				if (newsFromFront.length == 4) {
					army.attachUnit(newsFromFront[0], newsFromFront[3]);
				} else { // third command
				}

			} else if (currentNews.contains("died")) {
				String[] killed = newsFromFront[1].replace("..", " ")
						.split(" "); // äîñòà ãðîçíî
				System.out.println(newsFromFront[3]);

				army.killSoldiers(Integer.parseInt(killed[0]),
						Integer.parseInt(killed[1]), newsFromFront[3]);
			} else if (currentNews.contains("show")) {
				if (newsFromFront.length == 2) {
					String unit = newsFromFront[1];
					result += army.showUnit(unit) + "\n";
				} else if (newsFromFront.length > 2) {
					result += army.showSoldier(newsFromFront[2]) + "\n";
				}
			}
		}
		return result;
	}
}
