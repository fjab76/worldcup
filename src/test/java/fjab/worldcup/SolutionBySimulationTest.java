package fjab.worldcup;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fjab.worldcup.api.GroupResult;


public class SolutionBySimulationTest {
	
	private SolutionBySimulation impl;
	
	
	@Before
	public void setup(){
		impl = new SolutionBySimulation();
		//impl.setNumIterations(100);
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
	public void groupResultWithNotEnoughElements(){
		
		//given
		Integer[] groupResult = {1,3,2,4};
		Integer[] singleTeamResult = {1,2,3,4,7};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(groupResult, singleTeamResult);
		
		//then
		Assert.assertArrayEquals(null, resultingArray);		
	}
	

}
