Skip to content
This repository  
Search
Pull requests
Issues
Gist
 @NataliaIgnatova
 Unwatch 1
  Star 0
 Fork 0 NataliaIgnatova/new
 Code  Issues 0  Pull requests 0  Wiki  Pulse  Graphs  Settings
Branch: master Find file Copy pathnew/FrontBookkepper61767.java
6cbb0d8  4 days ago
@NataliaIgnatova NataliaIgnatova nn
1 contributor
RawBlameHistory     215 lines (195 sloc)  5.88 KB
import java.lang.String;
import java.util.Scanner;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
public class FrontBookkepper implements IFrontBookkepper61767{
	static LinkedList<String> unitsWhereSoldierIs = new LinkedList<>();
	 public String updateFront(String[] news){
		 
		for (int i = 0;i<news.length;i++){
			function(news[i]);
			
		}
		return null;
		
	}
	 private static void function(String func){
		 if (func.contains("=")){
			 assign(func);
		 }
		 if (func.contains("after")){
			 attachAfter(func);
		 }
		 if (func.contains("attached")){
			 attach(func);
		 }
		 if (func.contains("died")){
			 died(func);
		 }
		 if(func.contains("show soldier")){
			 showSoldier(func);
		 }
		 else if(func.contains("show")){
			 show(func);
		 }
	 }
	


	private static void show(String func) {
		
		String[] otherFunc=func.split(" ");
		if (unitsWhereSoldierIs.get(otherFunc[1].getElements().size()<2)){
			System.out.println("[]");
		}
		else {
			System.out.println(unitsWhereSoldierIs.get(otherFunc[1].getElements(.toString()));
		}
		
	}
	private static String showSoldier(String func) {
	LinkedList<String> unitsWhereSoldierIs = new LinkedList<>();
	String[] otherFunc=func.split(" ");
	for (Map.Entry<String, Unit> entry :   unitsWhereSoldierIs.entrySet()){
		String key= entry.getKey();
		Unit value= entry.getValue();
		if(value.getElements().contains(Integer.parseInt(otherFunc[2]))){
			unitsWhereSoldierIs.add(key);
		}
	}
	System.out.println(unitsWhereSoldierIs.toString());
	return unitsWhereSoldierIs.toString();
		
		
	}
	private static void died(String func) {
		LinkedList<String> unitsWhereSoldierIs = new LinkedList<>();
		String[] otherFunc =func.split(" ");
		String[] soldiers = otherFunc[1].split("\\..");
		int first = Integer.parseInt(soldiers[0]);
		int last = Integer.parseInt(soldiers[1]);
		String unit = otherFunc[3];
		if(!unitsWhereSoldierIs.get(unit).getContain().isEmpty());
		int length=unitsWhereSoldierIs.get(unit).getContain().size();
		for(int i;i<length;++i){
			removeSoldiers(first,last,unitsWhereSoldierIs.get(unit).getContain().get(i));
		}
		
		
	}
	private static void attach(String func) {
		String[] otherFunc= func.split("attached to");
		if(unitsWhereSoldierIs.containsKey(otherFunc[0])&&((!(unitsWhereSoldierIs.get(otherFunc[1]).getElements().size()<1)))
			&&!(unitsWhereSoldierIs.get(otherFunc[1]).getUnit().isEmpty())||unitsWhereSoldierIs.get(otherFunc[1].getElements().size()<1){
			unitsWhereSoldierIs.get(otherFunc[0]).isContain(otherFunc[1]);
			unitsWhereSoldierIs.get(otherFunc[1]).getContain().addAll(unitsWhereSoldierIs.get(otherFunc[0]).getContain());
		}
		;
	}
	private static void attachAfter(String func) {
		String[] otherFunc = func.split(" ");
		unitsWhereSoldierIs.get(otherFunc[3]).addAfter(Integer.parseInt(otherFunc[6]), unitsWhereSoldierIs.get(otherFunc[0]).getElements());
		 	unitsWhereSoldierIs.get(otherFunc[0]).isContain(otherFunc[3]);
		 	unitsWhereSoldierIs.get(otherFunc[3]).getContain().addAll(unitsWhereSoldierIs.get(otherFunc[0]).getContain());
	}
	private static void assign(String func) {
		String[] otherFunc = func.split(" ");
		if(otherFunc[1].length()<3) {
			unitsWhereSoldierIs.put(otherFunc[0],new Unit(otherFunc[0]));
		}
		else {
			LinkedList<Integer> elements = new LinkedList<>();
			elements.addAll(addElement(otherFunc[1]));
			unitsWhereSoldierIs.put(otherFunc[0],new Unit(otherFunc[0],elements));
		}
		
	}
	private static LinkedList<Integer> addElement(String func) {
		String[] item=func.replaceAll("\\[","").replace("\\]", "").split(",");
		LinkedList<Integer> elements= new LinkedList<>();
		for(int i =0;i<item.length;++i){
			try{
				elements.add(Integer.parseInt(item[i]));
			}
			catch(NumberFormatException exception){
				
			};
		}
		return elements;
	}
	private static void removeSoldier(int first, int last, int Unit){
		for(int i=first; i<=last; ++i){
			Object soldier=(Integer) i;
			if(unitsWhereSoldierIs.get(Unit).getElements().contains(soldier)){
				unitsWhereSoldierIs.get(Unit).getElements().remove(soldier);
			}
		}
	}
	public class Unit {
		private String name;
		private LinkedList<Integer> elements;
		private LinkedList<String> contain;
		private LinkedList<Unit> unit=new LinkedList<>();
		private LinkedList<Unit> unitIn=new LinkedList<Unit>();
	public Unit(String name,LinkedList <Integer> elements){
		this.elements=new LinkedList<>();
		setElements(elements);
		setName(name);
		itContain();
	}
	public Unit(String name){
	this.elements=new LinkedList<>();
	setElements(elements);
	setName(name);
	itContain();
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public LinkedList<Integer> getElements(){
		return elements;
	}
	public void setElements(LinkedList<Integer> elements){
		this.elements=elements;
		
	}
	public void addLast(LinkedList<Integer> items){
		if(this.elements.peek()==0){
			this.elements.poll();
			int size=elements.size();
			this.elements.addAll(size,items);
		}
	}
	public void addAfter(int index,LinkedList<Integer> items){
		if(this.elements.peek()==0){
			this.elements.poll();
			this.elements.addAll(index,items);
		}
	}
	private void itContain(){
		contain=new LinkedList<>();
		contain.add(this.name);
	}
	public void isContaint(String func){
		contain.add(func);
	}
	public String toString(){
		System.out.println(this.name);
		System.out.println(this.elements.toString());
		return " ";
	}
	public LinkedList<String> getContain(){
		return contain;
	}
	public LinkedList<Unit> getUnit(){
		return unit;
	}
	public void setUnit(LinkedList<Unit> unit){
		this.unit=unit;
	}
	public void addUnit(Unit unit){
		this.unit.add(unit);
		this.elements.addAll(unit.elements);
		unit.addedIn(this);
	}
	public void addedIn(Unit unit){
		this.unitIn.add(unit);
		if(this.unitIn.size()>1){
			this.unitIn.peek().elements.removeAll(this.elements);
			this.unitIn.poll();
		}
	}
	}


	public static void main(String[] args) {
		
	}

}
Status API Training Shop Blog About Pricing
© 2016 GitHub, Inc. Terms Privacy Security Contact Help
