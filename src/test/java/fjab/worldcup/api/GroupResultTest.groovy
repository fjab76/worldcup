package fjab.worldcup.api

import groovy.test.GroovyAssert

import org.junit.Test

class GroupResultTest extends GroovyAssert{
	
	private final Map<Integer,Integer[]> decisionMatrix1 = [1: [1,1,3] as Integer[], 0 : [0,1,0] as Integer[]]
	private final Map<Integer,Integer[]> decisionMatrix2 = [1: [1,1,1] as Integer[], 0 : [0,1,2] as Integer[], (-1) : [2,1,0] as Integer[]]
	private final Map<Integer,Integer[]> decisionMatrix3 = [(-1): [1,2,1] as Integer[]]
	
	private final Integer[][] combination1 = [[-1,-1,0],[1,0,1],[1,0,1],[0,-1,-1]];
	
	//@Ignore("not ready yet")
	@Test
	public void elementDistributionOfComb1IsOk(){
		
		assert GroupResult.checkElementsDistribution(combination1)
	}
	
	@Test
	public void decisionMatrixIs1(){

		def decisionMatrix = GroupResult.calculateDecisionMatrix([[-1,-1,0],[-1,-1,1],[-1,0,1],[1,1,1]] as Integer[][])
		
		assert decisionMatrix.containsKey(1)
		assert decisionMatrix.containsKey(0)
		
		assertArrayEquals decisionMatrix1[1],decisionMatrix[1]
		assertArrayEquals decisionMatrix1[0],decisionMatrix[0]
	}
	
	@Test
	public void inMatrixDecision1MatchingTeamIs3(){
		
		def index = GroupResult.findTeamWithMatchingResult(decisionMatrix1,1,3)
		
		assert 3==index
	}
	
	@Test
	public void inMatrixDecision2MatchingTeamIs1(){
		
		def index = GroupResult.findTeamWithMatchingResult(decisionMatrix2,1,3)
		
		assert 1==index
	}
	
	@Test
	public void inMatrixDecision3MatchingTeamIs2(){
		
		def index = GroupResult.findTeamWithMatchingResult(decisionMatrix3,-1,3)
		
		assert 2==index
	}

}
