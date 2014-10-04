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
	void arrayElementsAreInteger(){
		
		//given
		int[] array = [1,2,3]
		
		//when
		def newArray = integerArrayImpl.convertIntToIntegerArray(array)
		
		//then
		assert newArray.every({it instanceof Integer})
	}
	
	@Test
	void arrayIs12345(){
		
		//given
		Integer[] array1 = [1,2,3]
		Integer[] array2 = [4,5]
		
		//when
		def newArray = integerArrayImpl.concatArrays(array1, array2)
		
		//then
		assertArrayEquals([1,2,3,4,5] as Integer[],newArray)
		
	}
	
	
	@Test
	void arrayIs123AfterRemovingNulls(){
		
		//given
		Integer[] array = [1,2,null,3,null]
		
		//when
		def newArray = integerArrayImpl.removeNullElements(array)
		
		//then
		assertArrayEquals([1,2,3] as Integer[],newArray)
		
	}
	
	
	//=======================================
	
	
	
	@Test
	 void arrayStartWith2(){
		
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
