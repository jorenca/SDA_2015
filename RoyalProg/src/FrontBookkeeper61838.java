package fmi.sda.homework1;

import java.util.LinkedList;

public class FrontBookkeeper61838 implements IFrontBookkeeper {
	String result="";
	@Override
	public String updateFront(String[] news) {
		Army army=new Army();
		
		for(String currentNews:news){
			String[] newsFromFront=currentNews.split(" ");
			
			if(currentNews.contains("=")){
				String soldiers=currentNews.substring(newsFromFront[0].length()+4);
				String unitName = newsFromFront[0];
				LinkedList<String> soldiersList = new LinkedList<>();
				for(String soldier:soldiers.substring(0, soldiers.length()-1).split(", ")){
					soldiersList.add(soldier);
				}
				army.addUnit(unitName,soldiersList);
				
			}else if(currentNews.contains("attached")){
				if(newsFromFront.length==4){
					army.attachUnit(newsFromFront[0], newsFromFront[3]);
				}else{ //third command
				}
	
			}else if(currentNews.contains("died")){
				String[] killed = newsFromFront[1].replace("..", " ").split(" "); //доста грозно
				System.out.println(newsFromFront[3]);
					
				army.killSoldiers(Integer.parseInt(killed[0]), Integer.parseInt(killed[1]),newsFromFront[3]);
			}
			else if(currentNews.contains("show")){
				if(newsFromFront.length==2){
					String unit=newsFromFront[1];
					result+=army.showUnit(unit)+"\n";
				}else if(newsFromFront.length>2){ 
					result+=army.showSoldier(newsFromFront[2])+"\n";
				}
			}	
		}
		return result;
	}
}