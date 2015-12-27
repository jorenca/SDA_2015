package FrontBookkeeper;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class FrontBookkeeper61853 implements IFrontBookkeeper {
	
	private class Unit {
		
		private String name;
		private int unitsConnected;
		private LinkedList<Integer> soldiers;
		private Unit currentUnitConnected;
		
		public Unit() {
			name = "";
			unitsConnected = 0;
			soldiers = new LinkedList<Integer>();
		}	
		
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
	
	private Map<String, Unit> units;
	private String Result;
	
	public FrontBookkeeper61853() {
		units = new HashMap<String,Unit>();
		Result = "";
	}
	
	@Override
	public String updateFront(String[] news) {
		
		for (int i = 0; i < news.length; i++) {
			String[] newsElements = news[i].split(" ");
			if (newsElements.length == 2 && newsElements[0].equals("show")) {
				Result += units.get(newsElements[1]).getSoldiers().toString() + "\n";
			} else if (newsElements.length == 3 && newsElements[0].equals("show")) {
					unitDisplay(Integer.parseInt(newsElements[2]));
			} else if (newsElements.length == 4 && newsElements[1].equals("attached") && newsElements[2].equals("to")) {
					unitAttachment(newsElements[0], newsElements[3]);
			} else if (newsElements.length == 7 && newsElements[1].equals("attached") && newsElements[2].equals("to")) {
					unitPositionalAttachment(Integer.parseInt(newsElements[6]), newsElements[0], newsElements[3]);
			} else if (newsElements.length == 6 && newsElements[0].equals("soldiers")) {
					String s1 = newsElements[1].replace("..", " ");
					String[] s2 = s1.split(" ");
					soldierDeath(newsElements[3], s2[0], s2[1]);
		  }
			String[] unitArray = news[i].replaceAll("\\s", "").split("=");
			if (unitArray.length == 2) {
				unitAssignment(unitArray);
			}	
		}
		return Result;
	}

	private void unitAssignment(String[] unitArray) {
	
		if (!Character.toString(unitArray[1].charAt(1)).equals("]")) {
			String[] soldiers = unitArray[1].substring(1, unitArray[1].length() - 1).split(",");
			LinkedList<Integer> soldierList = new LinkedList<Integer>();
			for (int i = 0; i < soldiers.length; i++) {
				soldierList.add(Integer.parseInt(soldiers[i]));
			}
			Unit unit = new Unit();
			unit.setSoldiers(soldierList);
			unit.setName(unitArray[0]);
			units.put(unitArray[0], unit);
		} else {
			Unit unit = new Unit();
			unit.setSoldiers(new LinkedList<Integer>());
			unit.setName(unitArray[0]);
			units.put(unitArray[0], unit);
		}
	}

	public void unitAttachment(String attachedUnit, String unit) {
		
		if (units.get(attachedUnit).getUnitsConnected() == 1) {
			units.get(attachedUnit).getCurrentUnitConnected().getSoldiers().removeAll(units.get(attachedUnit).getCurrentUnitConnected().getSoldiers());
			units.get(attachedUnit).setUnitsConnected(0);
		}
		for (int i = 0; i < units.get(attachedUnit).getSoldiers().size(); i++) {
			units.get(unit).getSoldiers().addLast(units.get(attachedUnit).getSoldiers().get(i));
		}
		units.get(attachedUnit).setUnitsConnected(1);
		units.get(attachedUnit).setCurrentUnit(units.get(unit));
	}
	
	public void unitPositionalAttachment(int id, String attachedUnit, String unit) {
		
		int size = units.get(unit).getSoldiers().size() - units.get(unit).getSoldiers().indexOf(id) - 1;
		for (int i = 0; i < size; i++) {
			units.get(unit).getSoldiers().removeLast();
		}
		int start = units.get(unit).getSoldiers().indexOf(id) + 1;
		for (int i = 0; i < units.get(attachedUnit).getSoldiers().size(); i++) {
			units.get(unit).getSoldiers().add(start, units.get(attachedUnit).getSoldiers().get(i));
			start++;
		}
		units.get(attachedUnit).setUnitsConnected(1);
		units.get(attachedUnit).setCurrentUnit(units.get(unit));
	}

	public void unitDisplay(int u) {
	
		LinkedList<String> unitDisplay = new LinkedList<String>();
		for (String key : units.keySet()) {
			if (units.get(key).getSoldiers().contains(u)) {
				unitDisplay.addLast(key);
			}
		}
		String result = unitDisplay.toString().replace('[', ' ').replace(']', ' ');
		Result += result.trim() + "\n";
	}

	public void soldierDeath(String name, String n1, String n2) {
		
		for (String key : units.keySet()) {
			Unit unit = units.get(key);
			LinkedList<Integer> u = unit.getSoldiers();
			for (int i = Integer.parseInt(n1); i <= Integer.parseInt(n2); i++) {
				int index = u.indexOf(i);
				if (index != -1) {
					u.remove(index);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		FrontBookkeeper61853 fbkeeper = new FrontBookkeeper61853();
		String[] news = {
			"regiment_Stoykov = [1, 2, 3]",
			"show regiment_Stoykov",
			"regiment_Chaushev = [13]",
			"show soldier 13",
			"division_Dimitroff = []",
			"regiment_Stoykov attached to division_Dimitroff",
			"regiment_Chaushev attached to division_Dimitroff",
			"show division_Dimitroff",
			"show soldier 13",
			"brigade_Ignatov = []",
			"regiment_Stoykov attached to brigade_Ignatov",
			"regiment_Chaushev attached to brigade_Ignatov after soldier 3",
			"show brigade_Ignatov", "show division_Dimitroff",
			"brigade_Ignatov attached to division_Dimitroff",
			"show division_Dimitroff",
			"soldiers 2..3 from division_Dimitroff died heroically",
			"show regiment_Stoykov", "show brigade_Ignatov",
			"show division_Dimitroff" 
		};
		//long start = System.currentTimeMillis();
		String out = fbkeeper.updateFront(news);
		System.out.print(out);
		//long end   = System.currentTimeMillis();
		//long total = end - start;
		//System.out.println(total / 1000.0);
	}
}
