package fjab.worldcup;

import java.util.HashSet;
import java.util.Set;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.api.GroupResult;
import fjab.worldcup.api.GroupResultCalculator;
import fjab.worldcup.api.SingleTeamResult;
import fjab.worldcup.util.MatrixUtil;

public class BuilderImpl implements GroupResultCalculator {
	
	private SingleTeamResult singleTeamResult = SingleTeamResult.getInstance();

	@Override
	public Set<GroupResult> calculateGroupResults(int numTeams) {
		
		Set<GroupResult> results = new HashSet<>();
		
		Integer[][] singleTeamResults = singleTeamResult.getSingleTeamResults(numTeams);		
		
				
		//first team
		int firstTeam=0;
		for(int i=0; i<singleTeamResults.length; i++){

			Integer[][] groupResults = new Integer[numTeams][numTeams-1];
			groupResults[0] = singleTeamResults[i];
			for(int m=firstTeam+1; m<groupResults.length; m++)
				groupResults[m][firstTeam] = -groupResults[firstTeam][m-1];
			
			//second team
			int secondTeam=1;
			for(int j=0; j<singleTeamResults.length; j++){
				
				Integer[] array = MatrixUtil.matchArrays(groupResults[1], singleTeamResults[j]);
				if(array!=null){
					//groupResults[1][1] = array[1];
					//groupResults[1][2] = array[2];
					groupResults[1] = array; 
					
					for(int m=secondTeam+1; m<groupResults.length; m++)
						groupResults[m][secondTeam] = -groupResults[secondTeam][m-1];
					//groupResults[2][1] = -groupResults[1][1];
					//groupResults[3][1] = -groupResults[1][2];					
				
					//third team
					int thirdTeam=2;
					for(int k=0; k<singleTeamResults.length; k++){
						
						Integer[] array2 = MatrixUtil.matchArrays(groupResults[2], singleTeamResults[k]);
						if(array2!=null){
							//groupResults[2][2] = array2[2];
							groupResults[2] = array2;
							for(int m=thirdTeam+1; m<groupResults.length; m++)
								groupResults[m][thirdTeam] = -groupResults[thirdTeam][m-1];
							
							//groupResults[3][2] = -groupResults[2][2];					
												
							results.add(new GroupResult(groupResults));
							
							for(int m=thirdTeam; m<groupResults.length-1; m++){
								groupResults[thirdTeam][m] = null;
								
								groupResults[m+1][thirdTeam] = null;
							}
							
							//groupResults[2][2] = null;							
							//groupResults[3][2] = null;	
						}
					}
					
					for(int m=secondTeam; m<groupResults.length-1; m++){
						groupResults[secondTeam][m] = null;
						
						groupResults[m+1][secondTeam] = null;
					}
					//groupResults[1][1] = null;
					//groupResults[1][2] = null;
					
					//groupResults[2][1] = null;
					//groupResults[3][1] = null;
				}
			}
		}			
		
		return results;
	}

	private boolean isCompatibleResult(Integer[] integers, Integer[] integers2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int calculateUpperLimitOfResults(int numTeams) {
		
		//The number of combinations in a group when considering all the possible results for a single team
		//is a combination with repetition of the number of individual results taken in groups of as many
		//elements as the number of teams.
		//For instance, when numTeams=4 then the number of potential results for a team is 10 and the number of 
		//all the possible combinations is 715. This could be an upper limit.
		//
		//A more precise upper limit can be calculated by discarding those combinations that are not compliant
		//with this rules:
		//
		//1.the number of 0s must be even
		//2.the number of 1s must be equal to the number of -1s
		
		ICombinatoricsVector<Integer[]> originalVector = Factory.createVector(singleTeamResult.getSingleTeamResults(numTeams));
		Generator<Integer[]> generator = Factory.createMultiCombinationGenerator(originalVector, numTeams);
		
		int upperLimit = (int) generator.generateAllObjects().stream()
				  .map(ICombinatoricsVector::getVector)
				  .map(x -> MatrixUtil.convertListIntegersToArray(x))
				  .filter(x -> GroupResult.checkElements(x))
				  .count();
		
		return upperLimit;
	}
	
}
