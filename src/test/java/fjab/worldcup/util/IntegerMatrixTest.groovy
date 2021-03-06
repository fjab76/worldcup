package fjab.worldcup.util

import org.junit.Test

class IntegerMatrixTest  {

	//=========================================================
	//=============== TESTING groupElements ===================
	//=========================================================
	
	@Test
	void thereIs3_1s(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,0],[4,3,2]];
		
		//when
		def groups = IntegerMatrix.groupElements(matrix);
		
		//then
		assert 3==groups[1].size();		
	}
	
	@Test
	void thereIs5DifferentElements(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,0],[4,3,2]];
		
		//when
		def groups = IntegerMatrix.groupElements(matrix);
		
		//then
		assert 5==groups.size();		
	}
	
	
	
	//=========================================================================
	//=============== TESTING indexOfFirstColumnWithElement ===================
	//=========================================================================
	
	@Test
	void firstIndexOf0Is2(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,0],[4,3,2]];
		
		//when
		def index = IntegerMatrix.indexOfFirstColumnWithElement(matrix,0);
		
		//then
		assert 2==index;		
	}
	
	@Test
	void firstIndexOf5IsMinus1(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,0],[4,3,2]];
		
		//when
		def index = IntegerMatrix.indexOfFirstColumnWithElement(matrix,5);
		
		//then
		assert -1==index;		
	}
	
	//=========================================================================
	//=============== TESTING findLeastFrequentElement ========================
	//=========================================================================
	
	@Test
	void lestFrequentElementIs0NonStrict(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,0],[4,3,2]];
		
		//when
		def element = IntegerMatrix.findLeastFrequentElement(matrix,false);
		
		//then
		assert 0==element;
	
	}
	
	@Test
	void lestFrequentElementIsNull(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,0],[4,3,2]];
		
		//when
		def element = IntegerMatrix.findLeastFrequentElement(matrix,true);
		
		//then
		assert null==element;
	
	}
	
	@Test
	void lestFrequentElementIs0Strict(){
		
		//given
		Integer[][] matrix = [[1,2,3],[2,2,3],[1,1,1],[0,3,2]];
		
		//when
		def element = IntegerMatrix.findLeastFrequentElement(matrix,true);
		
		//then
		assert 0==element;
	
	}
	
	@Test
	void combinations(){
		println GroovyCollections.combinations(["a","b","c"],["1"])
	}

}
