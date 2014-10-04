package fjab.worldcup.util

import groovy.test.GroovyAssert

import org.junit.Before
import org.junit.Test

class IntegerArrayGImplTest extends GroovyAssert {

	private IntegerArrayGImpl integerArrayImpl;
	
	@Before
	public void setup(){
		integerArrayImpl = new IntegerArrayGImpl();
	}
	
	
	@Test
	public void arrayElementsAreInteger(){
		
		//given
		int[] array = [1,2,3]
		
		//when
		def newArray = integerArrayImpl.convertIntToIntegerArray(array)
		
		//then
		assert newArray.every({it instanceof Integer})
		
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
	
	@Test
	public void arrayIs123AfterRemovingNulls(){
		
		//given
		Integer[] array = [1,2,null,3,null]
		
		//when
		def newArray = integerArrayImpl.removeNullElements(array)
		
		//then
		assertArrayEquals([1,2,3] as Integer[],newArray)
		
	}
}
