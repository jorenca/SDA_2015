package FrontBookkeeper;

import java.util.LinkedList;

public class Unit {
	
	private String name;
	private int unitsConnected = 0;
	private LinkedList<Integer> soldiers;
	private Unit currentUnitConnected;
			
	public String getName() {
		return name;
	}	
	public void setName(String name) {
		this.name = name;
	}	
	public int getUnitsConnected() {
		return unitsConnected;
	}	
	public void setUnitsConnected(int n) {
		unitsConnected = n;
	}	
	public void setSoldiers(LinkedList<Integer> soldiers) {
		this.soldiers = soldiers;
	}	
	public LinkedList<Integer> getSoldiers() {
		return soldiers;
	}	
	public void setCurrentUnit(Unit unit) {
		currentUnitConnected = unit;
	}	
	public Unit getCurrentUnitConnected() {
		return currentUnitConnected;
	}
	
}
