package fmi.sda.homework1;

import java.util.LinkedList;
import java.util.List;

public class Unit {
	private String name;
	private LinkedList<String> unit;
	
	public Unit(String name){
		this.name=name;
		unit=new LinkedList<>();
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> getSoldiers(){
		return unit;
	}
	
	public void addSoldier(String name){
		unit.add(name);
	}
	
	public void removeSoldier(String name){
		unit.remove(name);
	}
	
	public void showUnit(){
			System.out.print(unit.toString());
	}
}