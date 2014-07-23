package fjab.worldcup;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;


public class CombinatorialImplTest {
	
	private CombinatorialImpl impl;
	
	@Before
	public void setup(){
		impl = new CombinatorialImpl();
	}
	
	
	@Test
	public void isNotNormalForm(){
		
		//given
		int[][] combination = {{1,1,1},{1,1,1},{1,1,1},{1,1,1}};
		
		//when
		boolean isNormalForm = impl.normaliseCombination(combination);
		
		//then
		Assert.assertFalse(isNormalForm);
		
	}
	
	@Test
	public void isNormalForm(){
		
		//given
		int[][] combination = {{1,1,1},{-1,1,1},{-1,-1,1},{-1,-1,-1}};
		
		//when
		impl.normaliseCombination(combination);
		
		//then
		//combination = {{-1,-1,-1},{-1,-1,1},{-1,1,1},{1,1,1}}
		Assert.assertEquals(-1, combination[0][0]);
		Assert.assertEquals(-1, combination[0][1]);
		Assert.assertEquals(-1, combination[0][2]);
		
		Assert.assertEquals(-1, combination[1][0]);
		Assert.assertEquals(-1, combination[1][1]);
		Assert.assertEquals(1, combination[1][2]);
		
		Assert.assertEquals(-1, combination[2][0]);
		Assert.assertEquals(1, combination[2][1]);
		Assert.assertEquals(1, combination[2][2]);
		
		Assert.assertEquals(1, combination[3][0]);
		Assert.assertEquals(1, combination[3][1]);
		Assert.assertEquals(1, combination[3][2]);
		
	}
	
	@Test
	public void isNotNormalFormWithZeros(){
		
		//given
		int[][] combination = {{1,1,1},{0,0,0},{1,0,1},{1,-1,1}};
		
		//when
		boolean isNormalForm = impl.normaliseCombination(combination);
		
		//then
		Assert.assertFalse(isNormalForm);
		
	}
	
	@Test
	public void isNormalFormWithZeros(){
		
		//given
		int[][] combination = {{1,1,1},{-1,0,0},{-1,0,1},{-1,-1,0}};
		
		//when
		impl.normaliseCombination(combination);
		
		//then
		//combination = {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}}
		Assert.assertEquals(-1, combination[0][0]);
		Assert.assertEquals(-1, combination[0][1]);
		Assert.assertEquals(0, combination[0][2]);
		
		Assert.assertEquals(-1, combination[1][0]);
		Assert.assertEquals(0, combination[1][1]);
		Assert.assertEquals(0, combination[1][2]);
		
		Assert.assertEquals(-1, combination[2][0]);
		Assert.assertEquals(0, combination[2][1]);
		Assert.assertEquals(1, combination[2][2]);
		
		Assert.assertEquals(1, combination[3][0]);
		Assert.assertEquals(1, combination[3][1]);
		Assert.assertEquals(1, combination[3][2]);
		
	}
	
	@Test
	public void numberCombinationsIs28(){
		
		//given
		int numTeamsPerGroup = 4;
		
		//when
		Set<GroupResult> setCombinations = impl.calculateGroupResults(numTeamsPerGroup);
		
		//then
		Assert.assertEquals(28,setCombinations.size());
		
	}
	
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
		GroupResult gr = new GroupResult(new int[][] {{0,0,0},{0,0,0},{0,0,0},{0,0,0}});
		Assert.assertTrue(setCombinations.contains(gr));
		
	}
	
	@Test
	public void getIndividualResultCombinations(){
		
		//given
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(new Integer[]{-1,0,1});
		
		
		//when
		Generator<Integer> generator = Factory.createMultiCombinationGenerator(originalVector, 3);
		for(ICombinatoricsVector<Integer> comb : generator){
			System.out.println(comb);
		}
		
		
		
	}

}
