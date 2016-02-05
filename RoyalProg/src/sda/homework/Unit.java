package sda.homework;
import java.util.LinkedList;

public class Unit {
	private String name;
	public LinkedList<Soldier> soldiers;
	Unit atachedTo;
	LinkedList<Unit> atachedUnits;

	public Unit(String name) {
		setName(name);
		soldiers = new LinkedList<>();
		atachedUnits = new LinkedList<>();
	}

	public Unit(String name, String[] soldiers) {
		setName(name);
		this.soldiers = new LinkedList<>();
		for (String sName : soldiers) {
			Soldier soldier = new Soldier(sName, this);
			this.soldiers.add(soldier);
		}
		atachedUnits = new LinkedList<>();
	}

	public void attach(Unit unit, Soldier soldierAfterWhomToAdd) {
		if (unit.atachedTo != null) {
			unatach(unit);
		}
		this.atachedUnits.add(unit);
		int indexOfSoldier = this.soldiers.indexOf(soldierAfterWhomToAdd);
		this.soldiers.addAll(indexOfSoldier, unit.soldiers);
		for (Soldier soldier : unit.soldiers) {
			soldier.units.add(this);
		}
		if (this.atachedTo != null) {
			atachedTo.addSoldiers(unit.soldiers, soldierAfterWhomToAdd);
		}
		unit.atachedTo = this;

	}

	public void attach(Unit unit) {
		if (unit.atachedTo != null) {
			unatach(unit);
		}
		this.atachedUnits.add(unit);
		this.soldiers.addAll(unit.soldiers);
		for (Soldier soldier : unit.soldiers) {
			soldier.units.add(this);

		}
		if (this.atachedTo != null) {
			atachedTo.addSoldiers(unit.soldiers);
		}
		unit.atachedTo = this;
	}

	private void unatach(Unit unit) {
		unit.atachedTo.atachedUnits.remove(unit);
		unit.atachedTo.soldiers.removeAll(unit.soldiers);
		for (Soldier soldier : unit.soldiers) {
			soldier.units.remove(unit.atachedTo);
		}
		if (unit.atachedTo.atachedTo != null) {
			unit.atachedTo.removeSoldier(unit.soldiers);
		}

	}

	private void addSoldiers(LinkedList<Soldier> soldiers,
			Soldier soldierToWhomIndexToAdd) {
		int StartIndex = this.soldiers.indexOf(soldierToWhomIndexToAdd);
		this.soldiers.addAll(StartIndex, soldiers);
		if (this.atachedTo != null) {
			atachedTo.addSoldiers(soldiers, soldierToWhomIndexToAdd);
		}

	}

	private void addSoldiers(LinkedList<Soldier> soldiers) {
		this.soldiers.addAll(soldiers);
		if (this.atachedTo != null) {
			atachedTo.addSoldiers(soldiers);
		}
	}

	private void removeSoldier(LinkedList<Soldier> soldiers) {
		this.soldiers.removeAll(soldiers);
		for (Soldier soldier : soldiers) {
			soldier.units.remove(this.atachedTo);
		}
		if (this.atachedTo != null) {
			atachedTo.removeSoldier(soldiers);
		}
	}

	public String show() {
		StringBuilder result = new StringBuilder();
		int size = soldiers.size();
		result.append("[");
		for (Soldier soldier : soldiers) {

			result.append(soldier.getIdentifier());
			size--;
			if (size != 0) {
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void DeadSoldiers(String first, String last) {
		LinkedList<Soldier> deadMen = new LinkedList<>();
		boolean namerihPurviq = false;
		boolean namerihVtoriq = false;
		for (Soldier soldier : this.soldiers) {
			if (soldier.getIdentifier().equals(first)) {
				namerihPurviq = true;
			}
			if (namerihPurviq) {
				deadMen.add(soldier);

				if (soldier.getIdentifier().equals(last)) {
					namerihVtoriq = true;
				}

			}
			if (namerihVtoriq) {
				break;
			}
		}
		soldiers.removeAll(deadMen);
		removeDeadSoldiers(deadMen);
	}

	private void removeDeadSoldiers(LinkedList<Soldier> deadMen) {
		for (Unit atached : atachedUnits) {
			if (atached.soldiers.removeAll(deadMen)) {
				atached.removeDeadSoldiers(deadMen);
			}
		}
	}

}
