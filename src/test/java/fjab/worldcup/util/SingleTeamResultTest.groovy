package fjab.worldcup.util

import org.junit.Test;

class SingleTeamResultTest {

	@Test
	void numCombinationsIs6(){
		
		//given
		int numTeams = 3
		
		//when
		def combinations = SingleTeamResult.calculateSingleTeamResults(numTeams);
		
		//then
		assert 6==combinations.length;
	}
	
	@Test
	void numCombinationsIs10(){
		
		//given
		int numTeams = 4
		
		//when
		def combinations = SingleTeamResult.calculateSingleTeamResults(numTeams);
		
		//then
		assert 10==combinations.length;
	}
	
	@Test
	void numCombinationsIs21(){
		
		//given
		int numTeams = 6
		
		//when
		def combinations = SingleTeamResult.calculateSingleTeamResults(numTeams);
		
		//then
		assert 21==combinations.length;
	}
}
