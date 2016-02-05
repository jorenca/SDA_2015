package sda.homework.sol61797;
import java.util.ArrayList;

public class Soldier {

	public static ArrayList<Soldier> allSoldiers = new ArrayList<Soldier>();
	
	private int id;
	private Unit unit;
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Unit getUnit() {
		return unit;
	}

	public int getId() {
		return id;
	}
	
	public Soldier(int id) {
		this.id=id;
		allSoldiers.add(this);
	}
	
	public void die() {
		unit.getSoldiers().remove(this);
		unit = null;
		allSoldiers.remove(this);
	}
	
	public static Soldier getSoldierByName(int soldierId) {
		for (Soldier soldier : Soldier.allSoldiers) {
			if (soldier.getId() == soldierId) {
				return soldier;
			}
		}
		
		//this code shouldn't be reachable
		return new Soldier(0);
	}
	
	@Override public String toString() {
		return unit.unitsNames();
	}
}
