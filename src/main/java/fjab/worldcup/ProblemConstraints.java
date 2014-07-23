package fjab.worldcup;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the parameters that define the problem
 *
 */
final class ProblemConstraints {

	final static int NUMBER_OF_TEAMS_PER_GROUP = 4;
	final static int NUMBER_OF_GAMES_PER_TEAM = NUMBER_OF_TEAMS_PER_GROUP-1;
	
	public enum GameOutcomeForATeam {
		WIN,DRAW,LOSS
	}
	final static int NUMBER_OF_GAME_OUTCOMES_FOR_A_TEAM = GameOutcomeForATeam.values().length;
	
	//Since each team play 3 games, the potential combinations of results for a team are:
	//WIN-WIN-WIN, WIN-WIN-DRAW, WIN-WIN-LOSS, WIN-DRAW-DRAW, etc.
	//More precisely, these are combinations with repetition of 3 elements so in total there are 10 different
	//combinations
	enum FreeResultCombination{
		WWW (GameOutcomeForATeam.WIN,GameOutcomeForATeam.WIN,GameOutcomeForATeam.WIN),
		DDD (GameOutcomeForATeam.DRAW,GameOutcomeForATeam.DRAW,GameOutcomeForATeam.DRAW),
		LLL (GameOutcomeForATeam.LOSS,GameOutcomeForATeam.LOSS,GameOutcomeForATeam.LOSS),
		WWD (GameOutcomeForATeam.WIN,GameOutcomeForATeam.WIN,GameOutcomeForATeam.DRAW),
		WWL (GameOutcomeForATeam.WIN,GameOutcomeForATeam.WIN,GameOutcomeForATeam.LOSS),
		DDW (GameOutcomeForATeam.DRAW,GameOutcomeForATeam.DRAW,GameOutcomeForATeam.WIN),
		DDL (GameOutcomeForATeam.DRAW,GameOutcomeForATeam.DRAW,GameOutcomeForATeam.LOSS),
		LLW (GameOutcomeForATeam.LOSS,GameOutcomeForATeam.LOSS,GameOutcomeForATeam.WIN),
		LLD (GameOutcomeForATeam.LOSS,GameOutcomeForATeam.LOSS,GameOutcomeForATeam.DRAW),
		WDL (GameOutcomeForATeam.WIN,GameOutcomeForATeam.DRAW,GameOutcomeForATeam.LOSS);
		
		private GameOutcomeForATeam result1;
		private GameOutcomeForATeam result2;
		private GameOutcomeForATeam result3;
		
		FreeResultCombination(GameOutcomeForATeam result1,GameOutcomeForATeam result2,GameOutcomeForATeam result3){
			this.result1=result1;
			this.result2=result2;
			this.result3=result3;
		}

		public GameOutcomeForATeam getResult1() {
			return result1;
		}

		public GameOutcomeForATeam getResult2() {
			return result2;
		}

		public GameOutcomeForATeam getResult3() {
			return result3;
		}
		
		
	}
	final static int NUM_FREE_RESULT_COMBINATION = FreeResultCombination.values().length;
	
	//These are the possible combinations for a team in state "free". When a team is taken as part of a group,
	//its status influence the status of other teams. For instance, for a team to have status WWW, each of the
	//other teams in the group must have an L-status (L-status is an status with at least one L)
	final static Map<GameOutcomeForATeam,GameOutcomeForATeam> relatedOutcomes = new HashMap<>();
	static{
		relatedOutcomes.put(GameOutcomeForATeam.WIN, GameOutcomeForATeam.LOSS);
		relatedOutcomes.put(GameOutcomeForATeam.DRAW, GameOutcomeForATeam.DRAW);
		relatedOutcomes.put(GameOutcomeForATeam.LOSS, GameOutcomeForATeam.WIN);
	}	
	
	/*
	 * Since each team play 3 games, the potential combinations of results for a team are:
	 * WIN-WIN-WIN, WIN-WIN-DRAW, WIN-WIN-LOSS, WIN-DRAW-DRAW, etc.
	 * More precisely, these are combinations with repetition of 3 elements taken in groups of 3 so in total 
	 * there are 10 different combinations.
	 * 
	 * As the order of the elements of a combination does not matter, it is convenient to represent all the possible
	 * permutations of the elements of a combination with a "normal form", namely, the values sorted in ascending order.
	 * To do so, we can represent the results WIN,DRAW and LOSS with numeric values: 1,0 and -1, respectively. Therefore,
	 * given the combination {0,-1,0}, the normalised version is {-1,0,0} 
	 * 
	 * 
	 * The 10 normalised forms listed in this constant are sorted in ascending order so this order can be used as a reference
	 * to normalise the combinations of results of the different teams in a group. For instance, given a group of 4 teams,
	 * a possible combination of results is: {{1,1,1,},{-1,0,0},{-1,0,1},{-1,-1,0}}. The combination may be normalised using
	 * the order set in this constant to obtain: {{-1,-1,0},{-1,0,0},{-1,0,1},{1,1,1}}
	 * 
	 * Note: the combinations listed in this constant are individual combinations in the sense that all of them are potential
	 * combinations of results for an individual team without taking into consideration the results obtained by other teams.
	 * However, when a team is not free but part of a group, its results depend on the results of the other teams. For
	 * instance, a combination such as {{1,1,1},{1,1,1},{1,1,1},{1,1,1}} would not be possible since no all teams can
	 * win. On the contrary, a value 1 for a team must induce a value -1 for another and a value 0 for a team must induce
	 * a value 0 for another. As a consequence, such combinations cannot be reduced to a normal form.
	*/
	static final int[][] INDIVIDUAL_TEAM_COMBINATIONS = {
			{-1,-1,-1},
			{-1,-1,0},
			{-1,-1,1},
			{-1,0,0},
			{-1,0,1},
			{-1,1,1},
			{0,0,0},
			{0,0,1},
			{0,1,1},
			{1,1,1}
	};
	

}
