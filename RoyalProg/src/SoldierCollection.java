package Main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoldierCollection {
	
	private ArrayList<Integer> soldiers;
	private boolean isAttached;
	private String attachedTo;

	public boolean isAttached() {
		return isAttached;
	}

	public void setAttached(boolean isAttached) {
		this.isAttached = isAttached;
	}

	public String getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(String attachedTo) {
		this.attachedTo = attachedTo;
	}

	public SoldierCollection(String strContent){
		soldiers=new ArrayList<Integer>();
		isAttached=false;
		attachedTo="";
		strToArr(strContent);
	}
	
	private void strToArr(String strContent){
		String REGEX = "-?\\d+";			
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(strContent);
		while (m.find()) {
			soldiers.add(Integer.parseInt(m.group()));
		}
	}
	
	public ArrayList<Integer> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(ArrayList<Integer> soldiers) {
		this.soldiers = soldiers;
	}
	
	public void attach(String thisUnit,SoldierCollection secondSquad){
		soldiers.addAll(secondSquad.getSoldiers());
		secondSquad.setAttached(true);
		secondSquad.setAttachedTo(thisUnit);
	}
	public void attach(String thisUnit,SoldierCollection secondSquad,int postion){
		soldiers.addAll(postion,secondSquad.getSoldiers());
		secondSquad.setAttached(true);
		secondSquad.setAttachedTo(thisUnit);
	}
	public void showSoldiers(StringBuilder realOutput){
		StringBuilder makeOutput=new StringBuilder();
		if(!soldiers.isEmpty()){
		makeOutput.append('[');
		for(Integer currentSoldier:soldiers){
			makeOutput.append(currentSoldier);
			makeOutput.append(", ");
		}
		makeOutput.deleteCharAt(makeOutput.length()-1);
		makeOutput.setCharAt(makeOutput.length()-1, ']');
		}else{
			makeOutput.append("[]");
		}
		realOutput.append(makeOutput.toString());
		realOutput.append(System.getProperty("line.separator"));
	}
	
}
