package sda.homework.sol61797;

import java.util.ArrayList;
import java.util.Arrays;

import sda.grading.IFrontBookkeeper;

public class FrontBookkeeper61797 implements IFrontBookkeeper {

	private String result = "";

	@Override
	public String updateFront(String[] news) {
		for (int i = 0; i < news.length; i++) {
			processCommand(news[i].split(" "));
		}

		return result;
	}

	private void processCommand(String[] command) {
		if (command[1].equals("=")) {
			// command 1
			createUnit(command);
		} else if (command[1].equals("attached")) {
			// command 2 & 3
			attachUnits(command);
			// } else if () {
			// //command 3
			// TODO
		} else if (command[0].equals("soldiers")) {
			// command 4
			soldiersDied(command);
		} else if (command[0].equals("show") && command.length == 2) {
			// command 5
			printUnit(command[1]);
		} else if (command[0].equals("show") && command.length == 3) {
			// command 6
			printSoldier(Integer.parseInt(command[2]));
		}

	}

	private void printUnit(String unitName) {
		String output = Unit.getUnitByName(unitName).toString();

		if (output.length() > 1) {
			// remove the last comma and space
			output = output.substring(0, output.length() - 2);
		}

		result += "[" + output + "]\n";
	}

	private void printSoldier(int id) {
		result += Soldier.getSoldierByName(id).toString() + "\n";
	}

	private void soldiersDied(String[] command) {
		String[] soldiers = Unit.getUnitByName(command[3]).toString()
				.split(", ");

		String[] range = command[1].split("\\..");

		String firstSoldierId = range[0];
		String lastSoldierId = range[1];

		int start = Arrays.asList(soldiers).indexOf(firstSoldierId);
		int end = Arrays.asList(soldiers).indexOf(lastSoldierId);

		for (int i = start; i <= end; i++) {
			Soldier.getSoldierByName(Integer.parseInt(soldiers[i])).die();
		}
	}

	private void attachUnits(String[] command) {
		Unit unit1 = Unit.getUnitByName(command[0]);
		Unit unit2 = Unit.getUnitByName(command[3]);

		if (command.length >= 5 && command[4].equals("after")) {
			unit2.attach(unit1, Integer.parseInt(command[6]));
		} else {
			unit2.attach(unit1);
		}
	}

	private void createUnit(String[] command) {
		if (command[2].equals("[]")) {
			// empty unit
			new Unit(command[0]);

		} else {
			ArrayList<Soldier> soldiers = new ArrayList<Soldier>();

			String unitsString = "";

			for (int i = 2; i < command.length; i++) {
				unitsString += command[i];
			}
			unitsString = unitsString.substring(1, unitsString.length() - 1);

			String[] units = unitsString.split(",");

			for (int i = 0; i < units.length; i++) {
				soldiers.add(new Soldier(Integer.parseInt(units[i])));
			}

			new Unit(command[0], soldiers);
		}

	}
}
