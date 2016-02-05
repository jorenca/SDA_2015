package sda.author;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class ManageUnits{
	Hashtable<String, ArrayList<Integer>> unitsAndSoldiers = new Hashtable<String, ArrayList<Integer>>();
	Hashtable<String, String> unitAttachedTo = new Hashtable<String, String>();
	Hashtable<String, ArrayList<String>> unitsAttachedToTheKey = new Hashtable<String, ArrayList<String>>();
	NewsTransformer NT;
	
	public ManageUnits(){
	
	}
	
	public void createUnits(String unitName, String soldiers){
		//array with strings for names
		String[] stringArray = soldiers.split("\\s*,\\s*");
		String emptyString = ""; 
		//array with integers for names
		int length = stringArray.length;
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		
		if(!soldiers.equals(emptyString)){
			for(int i = 0; i < length; i++){
				intArray.add(Integer.parseInt(stringArray[i]));
			}
		}
	
		unitsAndSoldiers.put(unitName, intArray);	
	} 
	
	public void attachUnit(String unitName1, String unitName2){
		if(unitAttachedTo.get(unitName1) != unitName2){
			deleteOldSuperior(unitName1);
			unitsAndSoldiers.get(unitName2).addAll(unitsAndSoldiers.get(unitName1));
			
			unitAttachedTo.put(unitName1, unitName2);
			makeUnitInferior(unitName1, unitName2);
			linkedHierchy(unitName1, unitName2);
		}
	}
	
	//puts the soldiers of unitName1 into unitName2 after a specific index(=after)
	public void attachUnitAfter(String unitName1, String unitName2, int after){
		int indexToAttachAfter = unitsAndSoldiers.get(unitName2).indexOf(after) + 1;
		int unitSize = unitsAndSoldiers.get(unitName2).size();
		int middle = (unitSize % 2 == 0) ? unitSize / 2 :  unitSize / 2 + 1;
	
		if (indexToAttachAfter != middle){
			deleteOldSuperior(unitName1);
			
			ArrayList<Integer> toBeAttached = new ArrayList<Integer>();
			toBeAttached = unitsAndSoldiers.get(unitName1);
			
			for(int i = 0; i < toBeAttached.size(); i++){
				unitsAndSoldiers.get(unitName2).add(indexToAttachAfter + i, toBeAttached.get(i));
			}
		
		
		unitAttachedTo.put(unitName1, unitName2);
		makeUnitInferior(unitName1, unitName2);
		linkedHierchy(unitName1, unitName2);
		}
	}
	
	private void linkedHierchy(String unitName1, String unitName2){
		if(unitAttachedTo.containsKey(unitName2)){
			unitsAndSoldiers.get(unitAttachedTo.get(unitName2)).addAll(unitsAndSoldiers.get(unitName1));
		}
	}
	
	private void deleteOldSuperior(String unitName1){
		if(unitAttachedTo.containsKey(unitName1)){
			deleteAfterSecondAttachment(unitName1);
			unitsAttachedToTheKey.get(unitAttachedTo.get(unitName1)).remove(unitName1);
		}
	}
	
	private void makeUnitInferior(String unitName1, String unitName2){
		if(unitsAttachedToTheKey.containsKey(unitName2)){
			unitsAttachedToTheKey.get(unitName2).add(unitName1);
		}
		else {
			ArrayList<String> arr = new ArrayList<String>();
			arr.add(unitName1);
			unitsAttachedToTheKey.put(unitName2, arr);
		}
	}
	
	private void deleteAfterSecondAttachment(String unitName1){
		ArrayList<Integer> toBeDeleted = new ArrayList<Integer>();
		toBeDeleted = unitsAndSoldiers.get(unitName1);
		for (int i = 0; i < toBeDeleted.size(); i++){
			unitsAndSoldiers.get(unitAttachedTo.get(unitName1)).remove(toBeDeleted.get(i));
		}
	}
	
	public void soldiersDied(int from, int to, String unitName){
		for(int i = from; i <= to; i++){
			unitsAndSoldiers.get(unitName).remove(new Integer(i));
			ArrayList<String> subordinates = new ArrayList<String>();
			subordinates = unitsAttachedToTheKey.get(unitName);
			if(unitsAttachedToTheKey.containsKey(unitName)){
				for(int j = 0; j < subordinates.size(); j++){
					String name = subordinates.get(j);
					unitsAndSoldiers.get(name).remove(new Integer(i));
					soldiersDied(from, to, subordinates.get(j));
				}
			}
		}
	}
	
	public String show(String unitName){
		String returns;
		returns = "[";
		Iterator<Integer> it = unitsAndSoldiers.get(unitName).iterator();
		while(it.hasNext()){
			returns = returns + it.next();
			if(it.hasNext()){
				returns = returns + ", ";
			}
		}
		returns = returns + "]\n";
		
		return returns;	
	}
	
	 public String show(int soldierNumber){
		StringBuilder returns = new StringBuilder();
		
		Enumeration<String> enumKey = unitsAndSoldiers.keys();
		boolean flag = true;
		while(enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    ArrayList<Integer> value = unitsAndSoldiers.get(key);
		    
		    if(value.contains(soldierNumber) && flag){
				returns.append(key);
				flag = false;
			} else if(value.contains(soldierNumber)) {
				returns.append(", " + key);
			}
		}
		
		returns.append("\n");
		return returns.toString();
	}
}