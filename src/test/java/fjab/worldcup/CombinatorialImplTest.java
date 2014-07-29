package fjab.worldcup;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CombinatorialImplTest {
	
	private CombinatorialImpl impl;
	
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
	
	/**
	 * {0,0},{0,0},{0,0} is valid
	 */
	@Test	
	public void _3TeamGroups_allZeroCombinationIsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{0,0},{0,0},{0,0}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1},{-1,1},{1,1} is valid
	 */
	@Test	
	public void _3TeamGroups_combination1IsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			//System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,-1},{-1,1},{1,1}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,-1},{0,1},{0,1} is valid
	 */
	@Test	
	public void _3TeamGroups_combination2IsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			//System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,-1},{0,1},{0,1}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,0},{-1,0},{1,1} is valid
	 */
	@Test	
	public void _3TeamGroups_combination3IsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			//System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,0},{-1,0},{1,1}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,1},{-1,1},{-1,1} is valid
	 */
	@Test	
	public void _3TeamGroups_combination4IsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			//System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,1},{-1,1},{-1,1}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,0},{-1,1},{0,1} is valid
	 */
	@Test	
	public void _3TeamGroups_combination5IsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			//System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,0},{-1,1},{0,1}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	/**
	 * {-1,0},{0,0},{0,1} is valid
	 */
	@Test	
	public void _3TeamGroups_combination6IsValidResult(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		for(GroupResult combination : setCombinations){
			//System.out.println(combination);
		}
		
		//then
		GroupResult gr = new GroupResult(new int[][] {{-1,0},{-1,1},{0,1}},_3TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	@Test
	public void _3TeamGroups_numberCombinationsIs7(){
		
		//given
		int numTeamsPerGroup = 3;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		
		//then
		Assert.assertEquals(7,setCombinations.size());
		
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
		GroupResult gr = new GroupResult(new int[][] {{0,0,0},{0,0,0},{0,0,0},{0,0,0}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{1,1,1},{-1,1,1},{-1,-1,0},{-1,-1,0}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{0,0,1},{-1,0,0},{0,0,0},{0,0,0}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,0,1},{0,0,0},{0,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	
	
	/**
	 * Create combination: {1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}
	 */
	@Test
	public void createCombination(){
		
		GroupResult gr = new GroupResult(new int[][] {{1,1,1},{-1,0,1},{-1,-1,1},{-1,-1,0}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
	
	
	@Before
	public void setup(){
		impl = new CombinatorialImpl();
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,-1},{-1,-1,1},{0,1,1},{0,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,-1},{-1,-1,1},{-1,1,1},{1,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,-1,1},{-1,0,1},{1,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,-1,1},{-1,1,1},{0,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,-1},{-1,0,1},{-1,1,1},{0,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,-1,1},{-1,0,1},{1,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,0,1},{-1,0,1},{0,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
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
		GroupResult gr = new GroupResult(new int[][] {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}},_4TEAM_GROUP_INDIVIDUAL_COMBINATIONS);
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	

}
