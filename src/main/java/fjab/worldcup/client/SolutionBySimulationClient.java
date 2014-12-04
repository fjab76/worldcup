package fjab.worldcup.client;

import java.util.Set;

import fjab.worldcup.SolutionBySimulation;
import fjab.worldcup.api.GroupResult;

public class SolutionBySimulationClient {

	public static void main(String[] args) {
		
		if(args.length<1){
			throw new IllegalArgumentException("Must specify the number of teams");
		}
		
		int numTeams = Integer.parseInt(args[0]);
		
		SolutionBySimulation simulationImpl = new SolutionBySimulation();
		Set<GroupResult> combinations = simulationImpl.calculateGroupResults(numTeams);
		System.out.println("===============================================================");
		System.out.println("              Num combinations:"+combinations.size());
		System.out.println("===============================================================");
	}


}
