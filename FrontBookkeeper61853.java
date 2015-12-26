package FrontBookkeeper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FrontBookkeeper61853 implements IFrontBookkeeper {
	
	private Map<String, Unit> units = new HashMap<String, Unit>();
	private String Result = "";
	
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
	
}
