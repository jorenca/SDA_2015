package sda.author;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsTransformer {
	String[] news;
	StringBuilder updateFront = new StringBuilder();
	ManageUnits manager = new ManageUnits();
	public NewsTransformer(String[] news){
		this.news = news;
	}
	
	public void sendCommands(){

		Pattern findCreateUnits = Pattern.compile("([a-zA-Z]*_*[a-zA-Z]*\\d*)\\s=\\s\\[(.*)\\]");
		Pattern findAttachUnit = Pattern.compile("([a-zA-Z]*_*[a-zA-Z]*\\d*)\\b attached to \\b([a-zA-Z]*_*[a-zA-Z]*\\d*)");
		Pattern findAttachUnitAfter = Pattern.compile("([a-zA-Z]*_*[a-zA-Z]*\\d*)\\b attached to \\b([a-zA-Z]*_*[a-zA-Z]*\\d*)\\b after soldier \\b(\\d*)");	
		Pattern findSoldiersDied = Pattern.compile("\\bsoldiers between \\b(\\d*)\\b and \\b(\\d*)\\b from \\b([a-zA-Z]*_*[a-zA-Z]*\\d*)\\b died heroically\\b");
		Pattern findSoldiersDied2 = Pattern.compile("\\bsoldiers \\b(\\d*)\\b..\\b(\\d*)\\b from \\b([a-zA-Z]*_*[a-zA-Z]*\\d*)\\b died heroically\\b");
		Pattern findShow = Pattern.compile("\\bshow \\b([a-zA-Z]*_*[a-zA-Z]*\\d*)");
		Pattern findShowSoldier= Pattern.compile("\\bshow soldier \\b(\\d*)");
		Pattern[] differentPatterns = {findCreateUnits, findAttachUnit, findAttachUnitAfter, findSoldiersDied, findSoldiersDied2, findShow, findShowSoldier};

		boolean matcherFindsPattern = false;
		int pattern_found = 0;
		

		for(int i = 0; i < news.length; i++){
			String newsI = news[i];
			Matcher matcher = null;
			for(int j = 0; j < differentPatterns.length; j++){
				matcher = differentPatterns[j].matcher(newsI);
				matcherFindsPattern = matcher.matches();
				if (matcherFindsPattern){ 
					pattern_found = j;
					break;	
				}
			}			 
			
			switch(pattern_found){
			case 0:
				manager.createUnits(matcher.group(1), matcher.group(2));
				break;
			case 1:
				manager.attachUnit(matcher.group(1), matcher.group(2));
				break;
			case 2:
				manager.attachUnitAfter(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)));
				break;
			case 3:
				manager.soldiersDied(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3));
				break;	
			case 4:
				manager.soldiersDied(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3));
				break;
			case 5:
				updateFront.append(manager.show(matcher.group(1)));
				break;	
			case 6:
				updateFront.append(manager.show(Integer.parseInt(matcher.group(1))));
				break;	
			}
		}		
	}
	
	public void output(){
		System.out.println(updateFront);
	}
	
	public String getUpdateFront(){
		return updateFront.toString();
	}
}
