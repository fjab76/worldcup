package fjab.worldcup;

import static fjab.worldcup.api.GroupResult.GAME_RESULTS;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.util.MatrixUtil;

public class CommonHelper {
	
	/**
	 * Checks that the matrix complies with the following rules:
	 * 1.all of the elements must be one of the values contained in GroupResults.GAME_RESULTS (-1,0,1)
	 * 2.the number of 0s must be even
	 * 3.the number of 1s must be equal to the number of -1s
	 * @param matrix Bidimensional array of Integers to be checked
	 * @return boolean True if the matrix complies with all the rules. False otherwise.
	 */
	boolean checkElements(Integer[][] matrix){

		//All elements must be one of these values: -1,0,1
		if(Stream.of(matrix).flatMap(x -> Stream.of(x)).filter(x -> (Arrays.binarySearch(GAME_RESULTS, x)==-1)).count()!=0){
			return false;
		}
		
		//The number of 0s must be even
		//The number of 1s must be equal to the number of -1s
		Map<Integer,List<Integer>> map = MatrixUtil.groupElements(matrix);		
		int num0s = map.get(GAME_RESULTS[1])!=null?map.get(GAME_RESULTS[1]).size():0;	
		int num1s = map.get(GAME_RESULTS[2])!=null?map.get(GAME_RESULTS[2]).size():0;
		int numMinus1s = map.get(GAME_RESULTS[0])!=null?map.get(GAME_RESULTS[0]).size():0;
		
		return (num0s/2*2==num0s && num1s==numMinus1s);
	}
	
	/**
	 * Checks that matrix dimensions are of the form m X n with n = m-1
	*  @param matrix Bidimensional array of Integers to be checked
	 * @return boolean True if the matrix passes the validation. False otherwise.
	 */
	boolean checkArrayDimensions(Integer[][] matrix){
		
		return Stream.of(matrix).filter(x -> x.length!=matrix.length-1).count()==0;
	}
	
	
	Integer[][] getTeamResults(int numTeams){
		
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(GAME_RESULTS);
		Generator<Integer> generator = Factory.createMultiCombinationGenerator(originalVector, numTeams-1);
		
		Integer[][] teamResults = new Integer[(int) generator.getNumberOfGeneratedObjects()][numTeams-1];		
		int j = 0;
		for(ICombinatoricsVector<Integer> comb : generator)
			teamResults[j++] = MatrixUtil.convertListToArray(comb.getVector());					
		
		return teamResults;
	}
	
	

}
