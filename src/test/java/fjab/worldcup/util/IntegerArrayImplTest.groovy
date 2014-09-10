package fjab.worldcup.util;

import groovy.test.GroovyAssert

import org.junit.Before
import org.junit.Test


class IntegerArrayImplTest extends GroovyAssert {
	
	private IntegerArrayImpl integerArrayImpl;
	
	@Before
	public void setup(){
		integerArrayImpl = new IntegerArrayImpl();
	}
	
	@Test
	public void mostFrequentNumberOfRepetitionsIs3(){
		
		//given
		Integer[] array = [1,2,3,2,4,3,2]
		
		//when
		def repetitions = integerArrayImpl.getMostFrequentNumberOfRepetitions(array)
		
		//then
		assert 3==repetitions
	}
	
	@Test
	public void arrayElementsAreInteger(){
		
		//given
		int[] array = [1,2,3]
		
		//when
		def newArray = integerArrayImpl.convertIntToIntegerArray(array)
		
		//then
		assert newArray.findAll({it instanceof Integer}).size()==array.size()
		
	}
	
	@Test
	public void arrayElementsAreInteger2(){
		
		//given
		List<Integer> list = [1,2,3]
		
		//when
		def newArray = integerArrayImpl.convertListToArray(list)
		
		//then
		assert newArray.findAll({it instanceof Integer}).size()==list.size()
		
	}
	
	@Test
	public void arrayIs12345(){
		
		//given
		Integer[] array1 = [1,2,3]
		Integer[] array2 = [4,5]
		
		//when
		def newArray = integerArrayImpl.concatArrays(array1, array2)
		
		//then
		assertArrayEquals([1,2,3,4,5] as Integer[],newArray)
		
	}
	
	
	
	
	
	//=======================================
	
	@Test	
	public void moveColumnFrom1To3(){
		
		//given
		int[][] matrix = [[0,0],[1,1],[2,2],[3,3],[4,4]];
		
		//when
		IntegerArrayImpl.moveElementFromTo(matrix, 1, 3);
		
		//then	
		assertArrayEquals([[0,0],[2,2],[3,3],[1,1],[4,4]] as int[][],matrix);
	}
	
	@Test	
	public void moveElementFrom1To3(){
		
		//given
		Integer[] matrix = [0,1,2,3,4];		
		
		//when
		IntegerArrayImpl.moveElementFromTo(matrix, 1, 3);
		
		//then		
		assertArrayEquals([0,2,3,1,4] as int[],matrix);
	}
	
	@Test
	public void arrayStartWith2(){
		
		//given
		Integer[] matrix = [0,1,2,4,3]
		
		//when
		IntegerArrayImpl.sortArrayStartingWithElement(matrix, 2)
		
		//then
		assertArrayEquals([2,0,1,3,4] as int[],matrix)
	}
	
	@Test
	public void arrayStartWith3(){
		
		//given
		Integer[] matrix = [1,0,2,3]
		
		//when
		IntegerArrayImpl.sortArrayStartingWithElement(matrix, 3)
		
		//then
		assertArrayEquals([3,0,1,2] as int[],matrix)
	}
	
	
}
