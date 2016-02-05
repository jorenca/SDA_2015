package sda.homework.sol61797;
import java.util.ArrayList;

public class Unit {

	private String name;
	private Unit attachedTo;
	private ArrayList<Unit> attachedUnits;
	private ArrayList<Soldier> soldiers;
	
	public static ArrayList<Unit> allUnits = new ArrayList<Unit>();
	
	public String getName() {
		return name;
	}

	public ArrayList<Soldier> getSoldiers() {
		return soldiers;
	}

	public Unit getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(Unit attachedTo) {
		this.attachedTo = attachedTo;
	}

	public ArrayList<Unit> getAttachedUnits() {
		return attachedUnits;
	}
	
	public Unit(String name, ArrayList<Soldier> soldiers) {
		this(name);
		
		this.soldiers = soldiers;
		for (Soldier soldier : soldiers) {
			soldier.setUnit(this);
		}
	}
	
	public Unit(String name) {
		this.name = name;
		
		soldiers 	  = new ArrayList<Soldier>();
		attachedUnits = new ArrayList<Unit>();
		
		allUnits.add(this);
	}
	
	public void attach(Unit unit) {
		if (unit.attachedTo != null) {
			unit.attachedTo.attachedUnits.remove(unit);
		}
		
		unit.attachedTo = this;
		attachedUnits.add(unit);
	}
	
	public void attach(Unit unit, int soldierId) {
		if (unit.attachedTo != null) {
			unit.attachedTo.attachedUnits.remove(unit);
		}
		
		unit.attachedTo = this;
		
		int index = attachedUnits.indexOf(Soldier.getSoldierByName(soldierId).getUnit());
		attachedUnits.add(index + 1, unit);;
	}
	
	public String unitsNames() {
		String unitNames = new String(name);
		
		if (attachedTo != null) {
			unitNames += ", " + attachedTo.unitsNames();
		}
		
		return unitNames;
	}
	
	public static Unit getUnitByName(String unitName) {
		for (Unit unit : allUnits) {
			if (unit.getName().equals(unitName)) {
				return unit;
			}
		}
		
		//it should never get to heres
		return new Unit("Error");
	}
	
	@Override public String toString() {
		String output = "";
		
		for (Soldier soldier : soldiers) {
			output += soldier.getId() + ", ";
		}
		
		//attached units
		if (attachedUnits != null) {
			for (Unit unit : attachedUnits) {
				output += unit.toString();
			}
		}
		
		return output;
	}
	
}
