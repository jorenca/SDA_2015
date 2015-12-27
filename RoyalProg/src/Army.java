package fmi.sda.homework1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Army {
	private Map<String, LinkedList<String>> army; //key - unit name, value - soldiers in unit
	private Map<String, LinkedList<String>> soldiers; //key - soldier id, value - units
	private Map<String,String> attached; // if the unit (key) is attached to other unit already the value is the unit it is attached to, else it's "OK"

	public Army(){
		army=new HashMap<>();
		soldiers=new HashMap<>();
		attached=new HashMap<>();
	}
	
	public void addUnit(String unitName,LinkedList<String> unit){
		if(!army.containsKey(unitName)){
			army.put(unitName,unit);
			attached.put(unitName, "ok");
		}else{
			army.get(unitName).addAll(unit);
		}
		for(String soldier:unit){
			if(!soldiers.containsKey(soldier)){
				LinkedList<String> units = new LinkedList<>();
				units.add(unitName);
				soldiers.put(soldier, units);
			}
			else{
				soldiers.get(soldier).add(unitName);
			}
		}
	}
	
	public String showSoldier(String soldier){
		String unitsPartOf = soldiers.get(soldier).toString();
		return unitsPartOf.substring(1,unitsPartOf.length()-1);
	}
	
	// fckn UGLY
	public String showUnit(String unit){
		//return army.get(unit).toString(); it's working but....
		String result="";
		if(army.get(unit).size()>1){
			for(String soldiers:army.get(unit)){
				if(!soldiers.equals("")){
					result+=soldiers+", ";
				}
			}
			result=result.substring(0,result.length()-2);
		}
		return "["+result+"]";
	}
	
	public void attachUnit(String unit1, String unit2){
		if(attached.get(unit1).equals("ok")){
			addUnit(unit2,army.get(unit1));
			attached.put(unit1, unit2);
		}else{
			String alreadyAttachedTo=attached.get(unit1);
			army.get(alreadyAttachedTo).removeAll(army.get(unit1));
			addUnit(unit2,army.get(unit1));
		}
	}
	
	public void killSoldiers(int x, int y,String unitName){
		for(int i=x;i<=y;i++){
			String soldier = new Integer(i).toString();
			if(soldiers.containsKey(soldier)){
				soldiers.remove(soldier);
				for(String unit:army.keySet()){
					army.get(unit).remove(soldier);
				}
			}else{
				continue;
			}
		}
	}
}