package sda.homework;

import java.util.HashMap;

import sda.grading.IFrontBookkeeper;

public class FrontBookkeeper61869 implements IFrontBookkeeper {

	public static final String AFTER = "after";
	public static final String SOLDIER = "soldier";
	public static final String SHOW = "show";
	public static final String SOLDIERS = "soldiers";
	public static final String ATTACHED = "attached";
	private HashMap<String, Unit> units = new HashMap<>();
	private HashMap<String, Soldier> boici = new HashMap<>();

	public void unitAssignment(String unitName, String[] soldiers) {
		Unit unit = new Unit(unitName);
		this.units.put(unitName, unit);
		// System.out.println(unitName);
		for (String soldier : soldiers) {
			// System.out.println(soldier + "->" + unit.getName());
			Soldier boec = new Soldier(soldier, unit);
			boici.put(soldier, boec);
			unit.soldiers.add(boec);
		}
	}

	public void unitAssignment(String unitName) {
		Unit unit = new Unit(unitName);
		this.units.put(unitName, unit);
	}

	public void unitAttachment(Unit unit1, Unit unit2) {
		unit2.attach(unit1);
	}

	public void unitAttachment(Unit unit1, Unit unit2, Soldier soldier) {
		unit2.attach(unit1, soldier);
	}

	@Override
	public String updateFront(String[] news) {
		StringBuilder output = new StringBuilder();
		for (String newNews : news) {
			String[] function = newNews.split(" ");
			if (function[1].equals("=")) {
				if (function.length > 3) {
					String[] soldiers = new String[function.length - 2];
					for (int i = 3; i < function.length - 1; i++) {
						soldiers[i - 2] = function[i].replace(",", "");
					}
					soldiers[0] = function[2].replace("[", "").replace(",", "");
					soldiers[soldiers.length - 1] = function[function.length - 1]
							.replace("]", "");
					unitAssignment(function[0], soldiers);
				} else {
					if (function[2].length() > 2) {
						String[] soldiers = new String[1];
						soldiers[0] = function[2].replace("[", "").replace("]",
								"");
						unitAssignment(function[0], soldiers);
					} else {
						unitAssignment(function[0]);
					}
				}
			}

			if (function[1].equals(ATTACHED)
					&& !(function[function.length - 3].contains(AFTER))) {
				Unit unit1 = this.units.get(function[0]);
				Unit unit2 = this.units.get(function[3]);
				unitAttachment(unit1, unit2);
			}

			if (function[1].equals(ATTACHED)
					&& function[function.length - 3].equals(AFTER)) {

				Unit unit1 = this.units.get(function[0]);
				Unit unit2 = this.units.get(function[3]);
				Soldier soldier = this.boici.get(function[function.length - 1]);
				unitAttachment(unit1, unit2, soldier);

			}

			if (function[0].equals(SOLDIERS)) {
				Unit unit = units.get(function[3]);
				String[] str = function[1].split("[..]+", 0);
				unit.DeadSoldiers(str[0], str[1]);

			}

			if (function[0].equals(SHOW)) {
				if (function[1].equals(SOLDIER)) {
					Soldier boec = boici.get(function[2]);
					output.append(boec.show());
					output.append(System.lineSeparator());
				} else {
					Unit unit = units.get(function[1]);
					output.append(unit.show());
					output.append(System.lineSeparator());
				}
			}

		}

		return output.toString();
	}

}
