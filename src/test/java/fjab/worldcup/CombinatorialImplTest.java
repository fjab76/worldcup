package fjab.worldcup;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;


public class CombinatorialImplTest {
	
	private CombinatorialImpl impl;
	
	static final int[][] INDIVIDUAL_TEAM_COMBINATIONS = {
		{-1,-1,-1},
		{-1,-1,0},
		{-1,-1,1},
		{-1,0,0},
		{-1,0,1},
		{-1,1,1},
		{0,0,0},
		{0,0,1},
		{0,1,1},
		{1,1,1}
	};
	
	@Before
	public void setup(){
		impl = new CombinatorialImpl();
	}
	
	@Test
	public void numberCombinationsIs36(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		
		//then
		Assert.assertEquals(36,setCombinations.size());
		
	}
	
	/**
	 * {0,0,0},{0,0,0},{0,0,0},{0,0,0} is valid
	 */
	@Test	
	public void allZeroCombinationIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{0,0,0},{0,0,0},{0,0,0},{0,0,0}},INDIVIDUAL_TEAM_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {1,1,1},{-1,0,0},{-1,0,1},{-1,-1,0} is valid
	 */
	@Test
	public void combination1IsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}},INDIVIDUAL_TEAM_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * FAILS
	 * {1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0} is valid
	 */
	@Test
	public void combination2IsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}},INDIVIDUAL_TEAM_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {1,1,1},{-1,1,1},{-1,-1,0},{-1,-1,0} is valid
	 */
	@Test
	public void combination3IsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{1,1,1},{-1,1,1},{-1,-1,0},{-1,-1,0}},INDIVIDUAL_TEAM_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {0,0,1},{-1,0,0},{0,0,0},{0,0,0} is valid
	 */
	@Test
	public void combination4IsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{0,0,1},{-1,0,0},{0,0,0},{0,0,0}},INDIVIDUAL_TEAM_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,0},{-1,0,1},{0,0,0},{0,1,1} is valid
	 */
	@Test
	public void combination5IsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,0,1},{0,0,0},{0,1,1}},INDIVIDUAL_TEAM_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * Create combination: {1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}
	 */
	@Test
	public void createCombination(){
		
		GroupResult gr = new GroupResult(new int[][] {{1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}},INDIVIDUAL_TEAM_COMBINATIONS);
		System.out.println("\n\n"+gr.toString());
		
	}
	

}
