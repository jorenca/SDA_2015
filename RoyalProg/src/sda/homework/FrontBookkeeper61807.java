package sda.homework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sda.grading.IFrontBookkeeper;

public class FrontBookkeeper61807 implements IFrontBookkeeper {
	private Map<Integer, LinkedList<String>> soldiers;
	private Map<String, LinkedList<Integer>> units;
	/*
	 * Map with unit that was attached to another unit. Key - attached unit
	 * Value - received unit
	 */
	private Map<String, String> attachedUnits;

	public FrontBookkeeper61807() {
		soldiers = new HashMap<>();
		units = new HashMap<>();
		attachedUnits = new HashMap<>();
	}

	@Override
	public String updateFront(String[] news) {
		StringBuilder info = new StringBuilder();
		for (String item : news) {
			Pattern pattern = Pattern.compile("(.*) = \\[(.*)\\]");
			Matcher matcher = pattern.matcher(item);
			if (matcher.find()) {
				addNewUnit(matcher.group(1), matcher.group(2));
				continue;
			}
			pattern = Pattern
					.compile("(.*) attached to (.*) after soldier (.*)");
			matcher = pattern.matcher(item);
			if (matcher.find()) {
				attachAfter(matcher.group(1), matcher.group(2),
						matcher.group(3));
				continue;
			}
			pattern = Pattern.compile("(.*) attached to (.*)");
			matcher = pattern.matcher(item);
			if (matcher.find()) {
				attachDevisions(matcher.group(1), matcher.group(2));
				continue;
			}
			pattern = Pattern
					.compile("soldiers (.*)\\.\\.(.*) from (.*) died heroically");
			matcher = pattern.matcher(item);
			if (matcher.find()) {
				victims(matcher.group(1), matcher.group(2));
				continue;
			}
			pattern = Pattern.compile("show soldier (.*)");
			matcher = pattern.matcher(item);
			if (matcher.find()) {
				if (info.length() > 0) {
					info.append("\n");
				}
				info.append(showSoldier(matcher.group(1)));
				continue;
			}
			pattern = Pattern.compile("show (.*)");
			matcher = pattern.matcher(item);
			if (matcher.find()) {
				if (info.length() > 0) {
					info.append("\n");
				}
				info.append(showUnit(matcher.group(1)));
				continue;
			}
		}
		return info.toString();
	}

	private void addNewUnit(String unit, String soldiers) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(soldiers);
		this.units.put(unit, new LinkedList<Integer>());
		while (m.find()) {
			int soldierId = Integer.parseInt(m.group());
			this.soldiers.put(soldierId, new LinkedList<>(Arrays.asList(unit)));
			this.units.get(unit).add(soldierId);
		}
	}

	private void attachDevisions(String firstUnit, String secondUnit) {
		for (Integer solder : this.units.get(firstUnit)) {
			// LinkedList<Integer> list = this.units.get(secondUnit);
			this.units.get(secondUnit).add(solder);
			this.soldiers.get(solder).add(secondUnit);
			if (attachedUnits.get(firstUnit) != null) {
				String detachedUnit = attachedUnits.get(firstUnit);
				this.soldiers.get(solder).remove(detachedUnit);
				this.units.get(detachedUnit).remove(solder);
			}
		}
		// Add new combination for relation between units
		attachedUnits.put(firstUnit, secondUnit);
	}

	private void attachAfter(String firstUnit, String secondUnit,
			String soldierId) {
		int solderIndentifier = Integer.parseInt(soldierId);
		int solderPosition = this.units.get(secondUnit).indexOf(
				solderIndentifier) + 1;
		for (Integer solder : this.units.get(firstUnit)) {
			this.units.get(secondUnit).add(solderPosition, solder);
			solderPosition++;
			this.soldiers.get(solder).add(secondUnit);
			if (attachedUnits.get(firstUnit) != null) {
				String detachedUnit = attachedUnits.get(firstUnit);
				this.soldiers.get(solder).remove(detachedUnit);
				this.units.get(detachedUnit).remove(solder);
			}
		}
		// Add new combination for relation between units
		attachedUnits.put(firstUnit, secondUnit);
	}

	private void victims(String fisrtSoldier, String secondSoldier) {
		int begin = Integer.parseInt(fisrtSoldier);
		int end = Integer.parseInt(secondSoldier);
		while (begin <= end) {
			for (String unit : this.soldiers.get(begin)) {
				int solderIndex = this.units.get(unit).indexOf(begin);
				this.units.get(unit).remove(solderIndex);
			}
			this.soldiers.remove(begin);
			begin++;
		}
	}

	private String showUnit(String unit) {
		String unitInfo = this.units.get(unit).toString();
		return unitInfo;
	}

	private String showSoldier(String soldier) {
		String soldiersInfo = this.soldiers.get(Integer.parseInt(soldier))
				.toString();
		return soldiersInfo.replace("[", "").replace("]", "");
	}

	public void clean() {
		soldiers = new HashMap<>();
		units = new HashMap<>();
	}
}
