package fjab.worldcup;

import java.util.stream.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * This implementation is based on the use of combinatorial methods. The results of a game are represented by GAME_RESULT:
 * loss (-1), draw (0) and win (1).
 * For the discussion of this implementation, let's assume that numTeamsPerGroup=4.
 * 
 * Since each team play 3 games (numTeamsPerGroup-1), the potential combinations of results for a team are:
 * {1,1,1},{1,1,0},{1,1,-1},{1,0,0} and so on.
 * More precisely, these are combinations with repetition of 3 elements taken in groups of 3 so in total 
 * there are 10 different combinations.
 * 
 * However, no all the individual combinations are possible when a team is added to a group as these combinations must be in 
 * agreement with other teams' combinations.
 * For instance, a combination such as {{1,1,1},{1,1,1},{1,1,1},{1,1,1}} would not be possible since no all teams can
 * win. On the contrary, a value 1 for a team must induce a value -1 for another and a value 0 for a team must induce
 * a value 0 for another.
 * 
 * 
 * Description of the implementation given by this class:
 * 1.obtain all the possible combinations of results within a group by combining all the possible combinations of 
 * an individual team (10) in groups of 4 (number of teams per group). From a mathematical point of view, these are
 * combinations with repetition of 10 elements taken in groups of 4 (=715)
 * 2.filter combinations to get those that overall have an even number of 0s and the number of 1s equals
 * the number of -1s. This is a necessary condition for a combination to be valid 
 * 3.check each combination to see if the distribution of results among teams is valid. Since the
 * success of the algorithm to check the distribution of results depends on the order in which
 * the results of each team are given, the algorithm is applied as many times as the number of 
 * teams (each time the order of the teams is rotated to have a different team at the beginning)
 *
 */
public class CombinatorialImpl implements Group {
	
	/**
	 * Array representing the results of a game, namely: loss (-1), draw (0) and win (1)
	 */
	private static final Integer[] GAME_RESULTS = {-1,0,1};
	
	@Override
	public Set<GroupResult> calculateGroupResults(final int numTeamsPerGroup) {
						
		//If numTeamsPerGroup=4 then the number of combinations of the results of a team is 10:
		//{-1,-1,-1},{-1,-1,0},{-1,-1,1} and so on
		final int[][] individualResultCombinations = getIndividualResultCombinations(numTeamsPerGroup);		
		
		//The number of combinations in a group when considering all the possible individual combinations of results
		//is a combination with repetition of individualResultCombinations elements taken in groups of numTeamsPerGroup.
		//For instance, when numTeamsPerGroup=4 then individualResultCombinations=10 and the number of all the possible
		//combinations is 715
		//All the invalid combinations must be taken away from the total number of possible combinations.
		
		ICombinatoricsVector<int[]> originalVector = Factory.createVector(individualResultCombinations);
		Generator<int[]> generator = Factory.createMultiCombinationGenerator(originalVector, numTeamsPerGroup);
		
		Set<GroupResult> groupResultCombinationsFiltered = generator.generateAllObjects().stream()
				  .map(ICombinatoricsVector::getVector)
				  .map(x -> convertToArray(x,numTeamsPerGroup))
				  .filter(this::filter2)
				  .filter(x -> this.isValidCombination(x,numTeamsPerGroup))
				  .map(x -> new GroupResult(x,individualResultCombinations))
				  .collect(Collectors.toSet());
		
		if(true) return groupResultCombinationsFiltered;

		
		Set<GroupResult> groupResultCombinations = generator.generateAllObjects().stream()
		  .map(ICombinatoricsVector::getVector)
		  .map(x -> convertToArray(x,numTeamsPerGroup))
		  .filter(x -> this.isValidCombination(x,numTeamsPerGroup))	  
		  .map(x -> new GroupResult(x,individualResultCombinations))
		  .collect(Collectors.toSet());
		
		
		return groupResultCombinations;
	}
	
	private boolean filter1(int[][] combination){
		
		int[][] temp = new int[combination.length][];
		for(int j=0; j<combination.length; j++){
			temp[j] = Arrays.copyOf(combination[j], combination[j].length);
		}
		
		int nullValue = 2;
		for(int j=0; j<temp.length-1; j++){
			for(int result=-1; result<=1; result++){
				final int r = result;
				int count = (int) IntStream.of(temp[j]).filter(x -> x==r).count();
				if(count>0){
					int counter = 0;
					for(int k=j+1; k<temp.length; k++){
						for(int m=0; m<temp[k].length; m++){
							if(result==-temp[k][m]){
								counter++;
								temp[k][m] = nullValue;
								break;
							}
						}
						if(counter==count) break;
					}
					if(counter!=count) return false;
				}
			}
		}
		return true;
	}
	
	private boolean filter2(int[][] combination){
		
		int counter1s=0, counter0s=0, counter_1s=0;
		for(int j=0; j<combination.length; j++){
			for(int k=0; k<combination[j].length; k++){
				if(combination[j][k]==1) counter1s++; 
				else if(combination[j][k]==0) counter0s++; 
				else if(combination[j][k]==-1) counter_1s++;
			}
		}
		return (counter0s/2*2==counter0s && counter1s==counter_1s);
		
	}
	
	private boolean isValidCombination(int[][] combination, int numTeamsPerGroup){
		return isValidCombination(combination, numTeamsPerGroup, 1);
	}
	
	/**
	 * Checks if the combination of results in a group passed as an argument is a valid combination. It is a valid combination if for
	 * every win (1) found in a team there is a loss (-1) in another and viceversa. Likewise, for every draw (0) in a team
	 * there must be a draw (0) in another.
	 * @param combination 2-dimensional array representing the results in a group
	 * @return boolean True if the given combination is valid. Otherwise, false.
	 */
	private boolean isValidCombination(int[][] originalCombination, int numTeamsPerGroup, int round) {				
		
		//Copying original array into other object
		int[][] combination = new int[originalCombination.length][];
		for(int j=0; j<combination.length; j++){
			combination[j] = Arrays.copyOf(originalCombination[j], originalCombination[j].length);
		}
		
		//Checking preconditions		
		assert (int) Stream.of(combination).flatMapToInt(x -> IntStream.of(x)).filter(x -> (x!=0 && x!=1 && x!=-1)).count()==0 : "values of the array must be -1,0 or 1";
		assert Stream.of(combination).filter(x -> x.length!=combination.length-1).count()==0 : "number of team results must be equal to number of teams minus 1";	
		
		
		//Counter of linked results for each team used for convenience in order to ensure that a linked result only 
		//can be part of one pair
		int[] counter = new int[combination.length];		
		
		//Looping over teams
		for(int i=0; i<combination.length-1; i++){
			
			//Counter of teams available to look for pairs (Two teams cannot have more than one pair of linked results)
			int numTeamsToSearchForLinkedElement = combination.length-1;
			
			//Looping over results of i-th team
			for(int j=counter[i]; j<combination[i].length; j++){
				
				final int searchedValue = -combination[i][j];
				//Looping over remaining teams to count number of occurrences of possible partners in each team 
				int[] numberOfOccurrences = new int[combination.length];
				for(int k=i+1; k<=numTeamsToSearchForLinkedElement; k++){
					numberOfOccurrences[k] = (int) Arrays.stream(Arrays.copyOfRange(combination[k],counter[k],combination[k].length))
												   .filter(y -> y==searchedValue)
												   .count();
				}

				int max = Arrays.stream(numberOfOccurrences).max().orElse(0);
				
				//As soon as a result in one team fails to find its partner in another team, we can conclude that the combination
				//is not valid
				if(max==0){
					if(round<=numTeamsPerGroup){
						putTeamAtTheEnd(originalCombination, 0);
						return isValidCombination(originalCombination, numTeamsPerGroup, ++round);
					}
					else
						return false;
				}
								
				int partnerTeam = IntStream.range(0, numberOfOccurrences.length)
						                   .filter(x -> numberOfOccurrences[x]==max)
						                   .findFirst().getAsInt();
																		
					
				//Looping over results of k-th team to find all possible linked results
				for(int m=counter[partnerTeam]; m<combination[partnerTeam].length; m++){											
					
					if(combination[partnerTeam][m]==-combination[i][j]){
						counter[partnerTeam]++;
						//A linked result only can be part of one pair. By moving it to the top, it is ensured that
						//is not considered for any other pair
						putLinkedResultAtTheBeginning(combination[partnerTeam],m);
						
						if(partnerTeam<numTeamsToSearchForLinkedElement){
							//Two teams cannot have more than one pair of linked results. By moving the selected team to the end,
							//it is ensured that said team is not considered for any other pair with the i-th team
							putTeamAtTheEnd(combination,counter,partnerTeam);
						}
						numTeamsToSearchForLinkedElement--;
						break;
					}
				}										
			}
		}
		return true;
	}

	

	private void putTeamAtTheEnd(int[][] combination, int[] counter, int k) {

		for(int j=k; j<combination.length-1; j++){
			//swapping j and j+1 values
			int[] jplus1 = combination[j+1];
			combination[j+1] = combination[j];
			combination[j] = jplus1;
			
			int jplus1_2 = counter[j+1];
			counter[j+1] = counter[j];
			counter[j] = jplus1_2;
		}
		
	}
	
	private void putTeamAtTheEnd(int[][] combination, int k) {

		for(int j=k; j<combination.length-1; j++){
			//swapping j and j+1 values
			int[] jplus1 = combination[j+1];
			combination[j+1] = combination[j];
			combination[j] = jplus1;
		}
		
	}

	private void putLinkedResultAtTheBeginning(int[] array, int m) {
		
		int linkedElement = array[m];
		for(int j=m; j>0; j--){
			array[j] = array[j-1];
		}
		array[0] = linkedElement;		
	}
	
	private int[][] getIndividualResultCombinations(int numTeamsPerGroup){
					
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(GAME_RESULTS);
		Generator<Integer> generator = Factory.createMultiCombinationGenerator(originalVector, numTeamsPerGroup-1);
		
		int[][] resultCombinations = new int[(int) generator.getNumberOfGeneratedObjects()][numTeamsPerGroup-1];		
		int combinationNumber = 0;
		for(ICombinatoricsVector<Integer> comb : generator){
			resultCombinations[combinationNumber++] = comb.getVector().stream().mapToInt(Integer::intValue).toArray();
		}
		
		return resultCombinations;
	}
	
	private int[][] convertToArray(List<int[]> list, int numTeamsPerGroup){
		
		int[][] combination = new int[numTeamsPerGroup][numTeamsPerGroup-1];
		
		for(int j=0; j<list.size(); j++){
			combination[j] = IntStream.of(list.get(j)).toArray();
		}
		
		return combination;
	}

}
 