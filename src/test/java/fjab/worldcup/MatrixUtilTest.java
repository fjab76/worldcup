package fjab.worldcup;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import fjab.worldcup.util.IntegerMatrix;


public class MatrixUtilTest {
	
	@Test	
	public void moveColumnFrom1To3(){
		
		//given
		int[][] matrix = {{0,0},{1,1},{2,2},{3,3},{4,4}};
		
		//when
		IntegerMatrix.moveElementFromTo(matrix, 1, 3);
		
		//then		
		Assert.assertTrue(Arrays.equals(new int[]{0,0},matrix[0]));
		Assert.assertTrue(Arrays.equals(new int[]{2,2},matrix[1]));
		Assert.assertTrue(Arrays.equals(new int[]{3,3},matrix[2]));
		Assert.assertTrue(Arrays.equals(new int[]{1,1},matrix[3]));
		Assert.assertTrue(Arrays.equals(new int[]{4,4},matrix[4]));
	}
	
	@Test	
	public void moveElementFrom1To3(){
		
		//given
		Integer[] matrix = {0,1,2,3,4};		
		
		//when
		IntegerMatrix.moveElementFromTo(matrix, 1, 3);
		
		//then		
		Assert.assertEquals(0,matrix[0].intValue());
		Assert.assertEquals(2,matrix[1].intValue());
		Assert.assertEquals(3,matrix[2].intValue());
		Assert.assertEquals(1,matrix[3].intValue());
		Assert.assertEquals(4,matrix[4].intValue());
	}
	
	@Test
	public void matchFullArrays(){
		
		//given
		Integer[] reference = {5,1,3,2,4};
		Integer[] arrayToBeMatched = {1,5,2,3,4};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(reference, arrayToBeMatched);
		
		//then
		Assert.assertArrayEquals(new Integer[]{5,1,3,2,4}, resultingArray);		
	}
	
	@Test
	public void matchArraysWithDifferentDimensions(){
		
		//given
		Integer[] reference = {1,3,2,4};
		Integer[] arrayToBeMatched = {1,2,3,4,7};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(reference, arrayToBeMatched);
		
		//then
		Assert.assertArrayEquals(null, resultingArray);		
	}
	
	public void mostBalancedColumnIsSecond(){
		
		//given
		Integer[][] matrix = {{-1,-1,1},{-1,0,1},{-1,0,1},{0,0,1}};
		
		//when
		int column = MatrixUtil.findMostBalancedColumn(matrix);
		
		//then
		Assert.assertEquals(1, column);
	}
	
	
	@Test
	public void matchPartialArray(){
		
		//given
		Integer[] reference = {null,1,3,2,null};
		Integer[] arrayToBeMatched = {0,2,3,4,1};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(reference, arrayToBeMatched);
		
		//then
		Assert.assertArrayEquals(new Integer[]{1,3,2,0,4}, resultingArray);		
	}
	
	@Test
	public void matchedArrayIsNull(){
		
		//given
		Integer[] reference = {1,1,null};
		Integer[] arrayToBeMatched = {-1,-1,1};
		
		//when
		Integer[] resultingArray = new SolutionByConstruction().checkCompatibility(reference, arrayToBeMatched);
		
		//then
		Assert.assertArrayEquals(null, resultingArray);		
	}
}
