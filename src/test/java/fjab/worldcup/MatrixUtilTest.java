package fjab.worldcup;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class MatrixUtilTest {
	
	@Test	
	public void moveColumnFrom1To3(){
		
		//given
		int[][] matrix = {{0,0},{1,1},{2,2},{3,3},{4,4}};
		
		//when
		MatrixUtil.moveElementFromTo(matrix, 1, 3);
		
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
		MatrixUtil.moveElementFromTo(matrix, 1, 3);
		
		//then		
		Assert.assertEquals(0,matrix[0].intValue());
		Assert.assertEquals(2,matrix[1].intValue());
		Assert.assertEquals(3,matrix[2].intValue());
		Assert.assertEquals(1,matrix[3].intValue());
		Assert.assertEquals(4,matrix[4].intValue());
	}
	
	
}
