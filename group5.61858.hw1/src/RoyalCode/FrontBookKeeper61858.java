package RoyalCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.management.openmbean.InvalidKeyException;

public class FrontBookKeeper61858 implements IFrontBookKeeper {

	private List<String> soldiers; // single soldiers
	private List<String> units; // units
	private List<String> unknown; // structures which are not identified
	private HashMap<String, List<String>> attachments; //
	private HashMap<String, List<Integer>> archive; // archive of the Bookkeeper

	public FrontBookKeeper61858() {
		soldiers = new ArrayList<>();
		units = new ArrayList<>();
		unknown = new ArrayList<>();
		archive = new HashMap<String, List<Integer>>();
		attachments = new HashMap<String, List<String>>();
	}

	public HashMap<String, List<Integer>> getArchive() {
		return archive;
	}

	public Boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public String updateFront(String[] news) {
		StringBuilder result = new StringBuilder();
		for (String newString : news) {
			if (newString.contains(" = ")) {
				StringTokenizer splitted = new StringTokenizer(newString, ",[] =");
				String name = null;
				List<Integer> indexes = new ArrayList<>();
				while (splitted.hasMoreTokens()) {
					String num = splitted.nextToken();
					if (isNumber(num)) {
						indexes.add(Integer.parseInt(num));
					} else {
						name = num;
					}
				}
				if (indexes.size() == 1) {
					soldiers.add(name);
				} else if (indexes.size() != 0) {
					units.add(name);
				} else {
					unknown.add(name);
				}
				archive.put(name, indexes);
			} else if (newString.contains(" attached to ") && !newString.contains(" after ")) {
				newString = newString.replace(" attached to ", " ");
				StringTokenizer splitted = new StringTokenizer(newString, ",[] =");
				String unit1 = splitted.nextToken();
				String unit2 = splitted.nextToken();
				/*
				 * if ((!(soldiers.contains(unit2) && (soldiers.contains(unit1)
				 * || unknown.contains(unit1))) || !(units.contains(unit2) &&
				 * (units.contains(unit1) || unknown.contains(unit1)))) ||
				 * (!(soldiers.contains(unit1) && (soldiers.contains(unit2) ||
				 * unknown.contains(unit2))) || !(units.contains(unit1) &&
				 * (units.contains(unit2) || unknown.contains(unit2))))) { throw
				 * new InvalidKeyException("Invalid operation!"); }
				 */
				if (archive.keySet().contains(unit1) && archive.keySet().contains(unit2)) {
					archive.get(unit2).addAll(archive.get(unit1));
				} else {
					throw new InvalidKeyException("Invalid key!");
				}
				if (attachments.containsKey(unit2)) {
					attachments.get(unit2).add(unit1);
				} else {
					List<String> attached = new ArrayList<String>();
					attached.add(unit1);
					attachments.put(unit2, attached);
				}
				for (String string : attachments.keySet()) {
					if (!unit2.equals(string) && attachments.get(string).contains(unit1)) {
						archive.get(string).removeAll(archive.get(unit1));
						attachments.get(string).remove(unit1);
					}
				}
			} else if (newString.contains(" attached to ") && newString.contains(" after ")) {
				newString = newString.replace(" attached to ", " ");
				newString = newString.replace(" after soldier ", " ");
				StringTokenizer splitted = new StringTokenizer(newString, ",[] =");
				String unit1 = splitted.nextToken();
				String unit2 = splitted.nextToken();
				String id = splitted.nextToken();
				if (archive.keySet().contains(unit1) && archive.keySet().contains(unit2) && isNumber(id)) {
					archive.get(unit2).addAll(archive.get(unit2).indexOf(Integer.parseInt(id)) + 1, archive.get(unit1));
				} else {
					throw new InvalidKeyException("Invalid key!");
				}
				if (attachments.containsKey(unit2)) {
					attachments.get(unit2).add(unit1);
				} else {
					List<String> attached = new ArrayList<String>();
					attached.add(unit1);
					attachments.put(unit2, attached);
				}
				for (String string : attachments.keySet()) {
					if (!unit2.equals(string) && attachments.get(string).contains(unit1)) {
						archive.get(string).removeAll(archive.get(unit1));
						attachments.get(string).remove(unit1);
					}
				}
			} else if (newString.contains(" died ")) {
				newString = newString.replace("died heroically", " ");
				newString = newString.replace("soldiers", " ");
				newString = newString.replace("from", " ");
				String[] splitted = newString.split(" ");
				int start = 0, end = 0;
				for (String string : splitted) {
					if (string.contains(".")) {
						start = Integer.parseInt(string.substring(0, string.indexOf('.')));
						end = Integer.parseInt(string.substring(string.lastIndexOf('.') + 1, string.length()));
					} else if (archive.keySet().contains(string)) {
						for (int i = start; i <= end; i++) {
							if (archive.get(string).contains(i)) {
								archive.get(string).remove((Object) i);
							}
						}
					} else {
					}
				}
				for (String string : archive.keySet()) {
					for (int i = start; i <= end; i++) {
						if (archive.get(string).contains(i)) {
							archive.get(string).remove((Object) i);
						}
					}
				}
			} else if (newString.contains("show ") && !newString.contains("show soldier")) {
				newString = newString.replace("show ", "");
				result.append(archive.get(newString).toString()+"\n");
			} else if (newString.contains("show soldier ")) {
				newString = newString.replace("show soldier ", "");
				if (isNumber(newString)) {
					int id = Integer.parseInt(newString);
					for (String string : archive.keySet()) {
						if (archive.get(string).contains(id)) {
							result.append(string+",");
						}
					}
					result.append("\n");
				}
			} else {
				throw new InvalidKeyException("Invalid order!");
			}
		}
		return result.toString();
	}
}
