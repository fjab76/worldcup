package fjab.worldcup;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fjab.worldcup.api.GroupResult;


public class SolutionByConstructionTest {
	
	private SolutionByConstruction impl;
	
	static final int[][] _4TEAM_GROUP_INDIVIDUAL_COMBINATIONS = {
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
	
	static final int[][] _3TEAM_GROUP_INDIVIDUAL_COMBINATIONS = {
		{-1,-1},
		{-1,0},
		{-1,1},		
		{0,0},
		{0,1},
		{1,1}
	};
	
	@Before
	public void setup(){
		impl = new SolutionByConstruction();
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
		GroupResult gr = new GroupResult(new Integer[][] {{0,0,0},{0,0,0},{0,0,0},{0,0,0}});
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
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}});
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
		GroupResult gr = new GroupResult(new Integer[][] {{1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}});
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
		GroupResult gr = new GroupResult(new Integer[][] {{1,1,1},{-1,1,1},{-1,-1,0},{-1,-1,0}});
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
		GroupResult gr = new GroupResult(new Integer[][] {{0,0,1},{-1,0,0},{0,0,0},{0,0,0}});
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
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,0,1},{0,0,0},{0,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	
	
	/**
	 * Create combination: {1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}
	 */
	@Test
	public void createCombination(){
		
		GroupResult gr = new GroupResult(new Integer[][] {{1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}});
		System.out.println("\n\n"+gr.toString());
		
	}
	
	@Test
	public void numberCombinationsIs40(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		
		//then
		Assert.assertEquals(40,setCombinations.size());
		
	}
	
	
	
	/**
	 * {-1,-1,-1},{-1,-1,1},{0,1,1},{0,1,1} is valid
	 */
	@Test
	public void combinationGroupAIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,-1},{-1,-1,1},{0,1,1},{0,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,-1},{-1,-1,1},{-1,1,1},{1,1,1} is valid
	 */
	@Test
	public void combinationGroupBIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,-1},{-1,-1,1},{-1,1,1},{1,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,0},{-1,-1,1},{-1,0,1},{1,1,1} is valid
	 */
	@Test
	public void combinationGroupCIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,-1,1},{-1,0,1},{1,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,0},{-1,-1,1},{-1,1,1},{0,1,1} is valid
	 */
	@Test
	public void combinationGroupDIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,-1,1},{-1,1,1},{0,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,-1},{-1,0,1},{-1,1,1},{0,1,1} is valid
	 */
	@Test
	public void combinationGroupEIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,-1},{-1,0,1},{-1,1,1},{0,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,0},{-1,-1,1},{-1,0,1},{1,1,1} is valid
	 */
	@Test
	public void combinationGroupFIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,-1,1},{-1,0,1},{1,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,0},{-1,0,1},{-1,0,1},{0,1,1} is valid
	 */
	@Test
	public void combinationGroupGIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,0,1},{-1,0,1},{0,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1} is valid
	 */
	@Test
	public void combinationGroupHIsValidResult(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);		
		
		//then
		GroupResult gr = new GroupResult(new Integer[][] {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	@Test
	public void entireGroupResultIsCompatible(){
		
		//given
		Integer[] groupResult = {5,1,3,2,4};
		Integer[] singleTeamResult = {1,5,2,3,4};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(groupResult, singleTeamResult);
		
		//then
		Assert.assertArrayEquals(new Integer[]{5,1,3,2,4}, resultingArray);		
	}
	
	@Test
	public void groupResultWithNotEnoughElements(){
		
		//given
		Integer[] groupResult = {1,3,2,4};
		Integer[] singleTeamResult = {1,2,3,4,7};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(groupResult, singleTeamResult);
		
		//then
		Assert.assertArrayEquals(null, resultingArray);		
	}
	
	
	
	
	@Test
	public void partialGroupResultIsCompatible(){
		
		//given
		Integer[] groupResult = {null,1,3,2,null};
		Integer[] singleTeamResult = {0,2,3,4,1};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(groupResult, singleTeamResult);
		
		//then
		Assert.assertArrayEquals(new Integer[]{1,3,2,0,4}, resultingArray);		
	}
	
	@Test
	public void groupResultIsNotCompatible(){
		
		//given
		Integer[] groupResult = {1,1,null};
		Integer[] singleTeamResult = {-1,-1,1};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(groupResult, singleTeamResult);
		
		//then
		Assert.assertArrayEquals(null, resultingArray);		
	}
}
