package fjab.worldcup.util;

import groovy.test.GroovyAssert

import org.junit.Test

public class IntegerArrayTest extends GroovyAssert {
	
	@Test	
	public void moveColumnFrom1To3(){
		
		//given
		int[][] matrix = [[0,0],[1,1],[2,2],[3,3],[4,4]];
		
		//when
		IntegerArray.moveElementFromTo(matrix, 1, 3);
		
		//then	
		assertArrayEquals([[0,0],[2,2],[3,3],[1,1],[4,4]] as int[][],matrix);
	}
	
	@Test	
	public void moveElementFrom1To3(){
		
		//given
		Integer[] matrix = [0,1,2,3,4];		
		
		//when
		IntegerArray.moveElementFromTo(matrix, 1, 3);
		
		//then		
		assertArrayEquals([0,2,3,1,4] as int[],matrix);
	}
	
	@Test
	public void arrayStartWith2(){
		
		//given
		Integer[] matrix = [0,1,2,4,3]
		
		//when
		IntegerArray.sortArrayStartingWithElement(matrix, 2)
		
		//then
		assertArrayEquals([2,0,1,3,4] as int[],matrix)
	}
	
	@Test
	public void arrayStartWith3(){
		
		//given
		Integer[] matrix = [1,0,2,3]
		
		//when
		IntegerArray.sortArrayStartingWithElement(matrix, 3)
		
		//then
		assertArrayEquals([3,0,1,2] as int[],matrix)
	}
	
	
}
