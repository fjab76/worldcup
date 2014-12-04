package fjab.worldcup.client;

import java.util.Set;

import fjab.worldcup.SolutionByConstruction;
import fjab.worldcup.api.GroupResult;
import fjab.worldcup.util.IntegerArrayImpl;

public class SolutionByConstructionClient {

	public static void main(String[] args) {
		
		if(args.length<1){
			throw new IllegalArgumentException("Must specify the number of teams");
		}
		
		int numTeams = Integer.parseInt(args[0]);
		
		SolutionByConstruction constructionImpl = new SolutionByConstruction();
		constructionImpl.setIntegerArray(new IntegerArrayImpl());
		int upperLimit = constructionImpl.calculateUpperLimitOfResults(numTeams);
		Set<GroupResult> combinations = constructionImpl.calculateGroupResults(numTeams);
		System.out.println("upper limit:"+upperLimit);
		System.out.println("num combinations:"+combinations.size());
	}


}
