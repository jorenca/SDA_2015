package sda.homework;

import java.util.LinkedList;

public class Soldier {

	private String identifier;
	public LinkedList<Unit> units;

	public Soldier() {
	}

	public Soldier(String identifier, Unit unit) {
		setIdentifier(identifier);
		units = new LinkedList<>();
		this.units.add(unit);
	}

	public String show() {
		StringBuilder result = new StringBuilder();
		int size = units.size();
		for (Unit unit : this.units) {
			result.append(unit.getName());
			size--;
			if (size != 0) {
				result.append(", ");
			}
		}
		return result.toString();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
