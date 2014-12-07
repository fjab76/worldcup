package fjab.worldcup.client;

import java.util.Set;

import fjab.worldcup.SolutionBySimulation;
import fjab.worldcup.api.GroupResult;

public class SolutionBySimulationClient {

	public static void main(String[] args) {
		
		if(args.length<2){
			throw new IllegalArgumentException("Must specify the number of teams and number of iterations");			
		}
		
		int numTeams = Integer.parseInt(args[0]);
		int numIterations= Integer.parseInt(args[1]);
		
		if(numTeams/2*2!=numTeams){
			throw new IllegalArgumentException("Number of teams must be an even number");
		}
		
		SolutionBySimulation simulationImpl = new SolutionBySimulation();
		simulationImpl.setNumIterations(numIterations);
		Set<GroupResult> combinations = simulationImpl.calculateGroupResults(numTeams);
		System.out.println("\n===============================================================");
		System.out.println("              Num iterations:"+numIterations);
		System.out.println("              Num combinations:"+combinations.size());
		System.out.println("===============================================================");
	}


}
