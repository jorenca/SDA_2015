package sda.homework;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import sda.grading.IFrontBookkeeper;

public class FrontBookkeeper61802 implements IFrontBookkeeper {
	HashMap<String, Unit61802> units;
	HashMap<String, Soldier61802> soldiers;

	public FrontBookkeeper61802() {
		units = new HashMap<>();
		soldiers = new HashMap<>();
	}

	@Override
	public String updateFront(String[] news) {
		StringBuilder output = new StringBuilder();
		units = new HashMap<>();
		soldiers = new HashMap<>();

		for (String currentNews : news) {
			String[] words = currentNews.split(" ");

			if (words[1].equals("=")) {
				String unitName = words[0];
				LinkedList<Soldier61802> soldiersInUnit = new LinkedList<>();
				if (words.length > 3) {
					for (int i = 2; i < words.length; i++) {
						Soldier61802 curSoldier;
						String soldierId;
						if (i == 2) {
							soldierId = words[i].substring(1,
									words[i].length() - 1);
							curSoldier = new Soldier61802(soldierId, unitName);
						} else {
							soldierId = words[i].substring(0,
									words[i].length() - 1);
							curSoldier = new Soldier61802(soldierId, unitName);
						}
						soldiersInUnit.add(curSoldier);
						soldiers.put(soldierId, curSoldier);
					}
					units.put(unitName, new Unit61802(unitName, soldiersInUnit));
				} else {
					int lenght = words[2].length();
					if (lenght > 2) {
						String soldierId = words[2].substring(1, lenght - 1);
						Soldier61802 curSoldier = new Soldier61802(soldierId,
								unitName);
						soldiersInUnit.add(curSoldier);
						soldiers.put(soldierId, curSoldier);
						units.put(unitName, new Unit61802(unitName,
								soldiersInUnit));
					} else {
						units.put(unitName, new Unit61802(unitName,
								soldiersInUnit));
					}
				}
			} else if (words[0].equals("show")) {
				if (words.length == 2) {
					String unitName = words[1];
					LinkedList<Soldier61802> soldiersInUnit = units.get(
							unitName).GetSoldiers();
					if (soldiersInUnit != null) {
						output.append("[");
						int remainingSoldiers = soldiersInUnit.size();
						for (Soldier61802 curSoldier : soldiersInUnit) {
							output.append(curSoldier.GetId());
							if (--remainingSoldiers != 0) {
								output.append(", ");
							}
						}
						output.append("]");
						output.append(System.lineSeparator());
					}
				} else {
					Soldier61802 soldierToShow = soldiers.get(words[2]);
					if (soldierToShow != null) {
						LinkedList<String> unitsAttachedTo = soldierToShow
								.getUnitsPartOf();
						int remainingUnits = unitsAttachedTo.size();
						if (remainingUnits > 0) {
							for (String unitName : unitsAttachedTo) {
								output.append(unitName);
								if (--remainingUnits != 0) {
									output.append(", ");
								}
							}
							output.append(System.lineSeparator());
						}
					}
				}
			} else if (words[2].equals("to")) {
				Unit61802 lowerUnit = units.get(words[0]);
				Unit61802 upperUnit = units.get(words[3]);
				if (words.length == 4) {
					upperUnit.attachUnitAtTheEnd(lowerUnit);
				} else {
					upperUnit.attachUnitAfterSoldier(lowerUnit,
							soldiers.get(words[6]));
				}
			} else if (words[2].equals("from")) {
				String[] soldierRange = words[1].split("[.]+", 0);
				String startSoldier = soldierRange[0];
				String endSoldier = soldierRange[1];
				LinkedList<Soldier61802> deadSoldiers = units.get(words[3])
						.removeDiedSoldiers(startSoldier, endSoldier);
				for (Soldier61802 deadSoldier : deadSoldiers) {
					soldiers.remove(deadSoldier.GetId());
				}
			}
		}
		return output.toString();
	}

}

class Unit61802 {
	private String name;
	private LinkedList<Soldier61802> soldiers;
	private LinkedList<Unit61802> lowerUnits;
	private Unit61802 upperUnit;

	public Unit61802(String name) {
		this(name, new LinkedList<Soldier61802>());
	}

	public Unit61802(String name, LinkedList<Soldier61802> soldiers) {
		this.name = name;
		this.soldiers = soldiers;
		lowerUnits = new LinkedList<>();
		upperUnit = null;
	}

	public LinkedList<Soldier61802> GetSoldiers() {
		return soldiers;
	}

	public void attachUnitAtTheEnd(Unit61802 unit) {
		lowerUnits.add(unit);
		if (unit.upperUnit != null) {
			unit.upperUnit.removeLowerUnit(unit);
		}
		unit.upperUnit = this;

		attachmentPlaceFinderIteration(unit.soldiers, Boolean.TRUE);
	}

	public void attachUnitAfterSoldier(Unit61802 unit, Soldier61802 soldier) {
		int indexToInsert = 0;
		for (Unit61802 curUnit : lowerUnits) {
			indexToInsert++;
			if (soldier.equals(curUnit.soldiers.getLast())) {
				break;
			}
		}
		lowerUnits.add(indexToInsert, unit);
		if (unit.upperUnit != null) {
			unit.upperUnit.removeLowerUnit(unit);
		}
		if (unit.upperUnit != null) {
			unit.upperUnit.removeLowerUnit(unit);
		}
		unit.upperUnit = this;

		addSoldiersAfterSoldierIteration(unit.soldiers, soldier);
	}

	private void attachmentPlaceFinderIteration(
			LinkedList<Soldier61802> soldiersToAdd, boolean attachAtTheEnd) {
		Unit61802 curUnit = this;
		boolean hasUpper;
		do {
			boolean hasSoldiers = !curUnit.soldiers.isEmpty();
			hasUpper = curUnit.upperUnit != null;
			Soldier61802 afterSoldier = null;
			if (hasSoldiers) {
				afterSoldier = curUnit.soldiers.getLast();
			}

			if (attachAtTheEnd) {
				curUnit.addSoldiersAtTheEnd(soldiersToAdd);
			} else {
				curUnit.addSoldiersAtTheFront(soldiersToAdd);
			}

			if (hasUpper) {
				if (hasSoldiers) {
					curUnit.upperUnit.addSoldiersAfterSoldierIteration(
							soldiersToAdd, afterSoldier);
					break;
				} else if (curUnit.upperUnit.soldiers.isEmpty()
						|| curUnit.equals(curUnit.upperUnit.lowerUnits
								.getLast())) {
					attachAtTheEnd = true;
				} else if (curUnit.equals(curUnit.upperUnit.lowerUnits
						.getFirst())) {
					attachAtTheEnd = false;
				} else {
					int index = curUnit.upperUnit.lowerUnits.indexOf(curUnit);
					afterSoldier = null;
					for (int i = index - 1; i >= 0; i--) {
						Unit61802 unit = curUnit.upperUnit.lowerUnits.get(i);
						if (!unit.soldiers.isEmpty()) {
							afterSoldier = unit.soldiers.getLast();
							break;
						}
					}
					if (afterSoldier == null) {
						attachAtTheEnd = false;
					} else {
						curUnit.upperUnit.addSoldiersAfterSoldierIteration(
								soldiersToAdd, afterSoldier);
						break;
					}
				}
				curUnit = curUnit.upperUnit;
			}

		} while (hasUpper);
	}

	private void addSoldiersAtTheEnd(LinkedList<Soldier61802> soldiers) {
		for (Soldier61802 soldier : soldiers) {
			soldier.addUnit(name);
			this.soldiers.add(soldier);
		}
	}

	private void addSoldiersAtTheFront(LinkedList<Soldier61802> soldiers) {
		Stack<Soldier61802> soldiersOrder = new Stack<>();
		for (Soldier61802 soldier : soldiers) {
			soldier.addUnit(name);
			soldiersOrder.push(soldier);
		}
		while (!soldiersOrder.isEmpty()) {
			this.soldiers.addFirst(soldiersOrder.pop());
		}
	}

	private void addSoldiersAfterSoldierIteration(
			LinkedList<Soldier61802> soldiers, Soldier61802 afterSoldier) {
		Unit61802 curUnit = this;
		do {
			LinkedList<Soldier61802> newSoldiersArrangement = new LinkedList<>();
			boolean addedNewSoldiers = false;
			for (Soldier61802 soldier : curUnit.soldiers) {
				newSoldiersArrangement.add(soldier);
				if (!addedNewSoldiers) {
					if (soldier.equals(afterSoldier)) {
						for (Soldier61802 newSoldier : soldiers) {
							newSoldier.addUnit(name);
							newSoldiersArrangement.add(newSoldier);
						}
						addedNewSoldiers = true;
					}
				}
			}
			curUnit.soldiers = newSoldiersArrangement;
			curUnit = curUnit.upperUnit;
		} while (curUnit != null);
	}

	public void removeLowerUnit(Unit61802 unit) {
		lowerUnits.remove(unit);

		unit.upperUnit = null;

		removeSoldiers(unit.soldiers);
	}

	private void removeSoldiers(LinkedList<Soldier61802> soldiers) {
		for (Soldier61802 soldier : soldiers) {
			soldier.removeUnit(name);
			this.soldiers.remove(soldier);
		}
		if (upperUnit != null) {
			upperUnit.removeSoldiers(soldiers);
		}
	}

	public LinkedList<Soldier61802> removeDiedSoldiers(String startSoldier,
			String endSoldier) {
		LinkedList<Soldier61802> diedSoldiers = new LinkedList<>();
		boolean afterStart = false;
		boolean afterEnd = false;
		for (Soldier61802 soldier : soldiers) {
			String curId = soldier.GetId();
			if (startSoldier.equals(curId)) {
				afterStart = true;
			}
			if (afterStart) {
				diedSoldiers.add(soldier);
				if (endSoldier.equals(curId)) {
					afterEnd = true;
				}
			}
			if (afterEnd) {
				break;
			}
		}

		this.soldiers.removeAll(diedSoldiers);
		removeDiedSoldiersInLowerUnits(diedSoldiers);

		return diedSoldiers;
	}

	private void removeDiedSoldiersInLowerUnits(
			LinkedList<Soldier61802> diedSoldiers) {
		for (Unit61802 lowerUnit : lowerUnits) {
			if (lowerUnit.soldiers.removeAll(diedSoldiers)) {
				lowerUnit.removeDiedSoldiersInLowerUnits(diedSoldiers);
			}
		}
	}
}

class Soldier61802 {
	private String id;
	private LinkedList<String> unitsPartOf;

	public Soldier61802(String id, String initialUnit) {
		this.id = id;
		unitsPartOf = new LinkedList<>();
		unitsPartOf.add(initialUnit);
	}

	public String GetId() {
		return id;
	}

	public LinkedList<String> getUnitsPartOf() {
		return unitsPartOf;
	}

	public void addUnit(String unitName) {
		unitsPartOf.add(unitName);
	}

	public void removeUnit(String unitName) {
		unitsPartOf.remove(unitName);
	}
}
