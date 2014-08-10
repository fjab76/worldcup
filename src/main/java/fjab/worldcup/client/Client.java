package fjab.worldcup.client;

import fjab.worldcup.BuilderImpl;
import fjab.worldcup.CommonHelper;
import fjab.worldcup.api.GroupResultCalculator;

public class Client {
	
	private static BuilderImpl builderImpl = new BuilderImpl();

	public static void main(String[] args) {
		
		if(args.length<1){
			throw new IllegalArgumentException("Must specify the number of teams");
		}
		
		int numTeams = Integer.parseInt(args[0]);
		builderImpl.setHelper(new CommonHelper());
		getUpperLimit(builderImpl,numTeams);

	}
	
	private static void getUpperLimit(GroupResultCalculator builderImpl, int numTeams){
		
		int upperLimit = builderImpl.calculateUpperLimitOfResults(numTeams);
		System.out.println("upper limit:"+upperLimit);
		
	}

}
