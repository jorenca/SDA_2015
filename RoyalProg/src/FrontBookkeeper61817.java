package Main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrontBookkeeper61817 implements IFrontBookkeeper {

	public String updateFront(String[] news) {

		HashMap<String, SoldierCollection> objectsMap = new HashMap<String, SoldierCollection>();
		StringBuilder realOutput=new StringBuilder();
		for (int i = 0; i < news.length; i++) {

			// /gets =
			if (news[i].contains("=")) {
				String[] objects = news[i].split(" = ");
				objectsMap.put(objects[0], new SoldierCollection(objects[1]));
			}

			if (news[i].contains("show")) {
				String[] command = news[i].split(" ");
				// if shows 1 thing
				if (command.length == 2) {
					objectsMap.get(command[1]).showSoldiers(realOutput);
				} else {
					// if shows a sodlier
					Iterator iterator = objectsMap.keySet().iterator();
					StringBuilder makeOutput = new StringBuilder();
					while (iterator.hasNext()) {
						String key = iterator.next().toString();
						SoldierCollection value = objectsMap.get(key);
						if (value.getSoldiers().contains(
								Integer.parseInt(command[2]))) {
							makeOutput.append(key);
							makeOutput.append(", ");
						}
					}
					makeOutput.deleteCharAt(makeOutput.length() - 1);
					makeOutput.deleteCharAt(makeOutput.length() - 1);
					realOutput.append(makeOutput.toString());
					realOutput.append(System.getProperty("line.separator"));
				}
			}

			if (news[i].contains("attached to")) {
				String[] command = news[i].split(" ");
				SoldierCollection soldiersToAttach = objectsMap.get(command[0]);
				if (soldiersToAttach.isAttached()) {
					objectsMap.get(soldiersToAttach.getAttachedTo())
							.getSoldiers()
							.removeAll(soldiersToAttach.getSoldiers());
				}
				if (command.length == 4) {
					objectsMap.get(command[3]).attach(command[3],
							soldiersToAttach);
				} else {
					objectsMap.get(command[3]).attach(command[3],
							soldiersToAttach,
							Integer.parseInt(command[command.length - 1]));
				}

			}
			if (news[i].contains("died")) {
				String[] command = news[i].split(" ");
				String[] soldiers = command[1].split("\\.\\.");
				int first = Integer.parseInt(soldiers[0]);
				int second = Integer.parseInt(soldiers[1]);
				if (first != second) {
					for (int j = first; j <= second; j++) {
						objectsMap.get(command[3]).getSoldiers().remove(new Integer(j));
					}
				} else {
					objectsMap.get(command[3]).getSoldiers().remove(first);
				}
				removeDiedSoldiers(objectsMap,command[3],first,second);
			}

		}
		return realOutput.toString();
	}
	
	private void removeDiedSoldiers(HashMap<String, SoldierCollection> objectsMap,String unitName,int first, int second){
		Iterator iterator = objectsMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			SoldierCollection value = objectsMap.get(key);
			if (value.getAttachedTo().equals(unitName)) {
				if (first != second) {
					for (int j = first; j <= second; j++) {
						value.getSoldiers().remove(new Integer(j));
					}
				} else {
					value.getSoldiers().remove(first);
				}
				removeDiedSoldiers(objectsMap,key,first,second);
			}
		}
	}
}
