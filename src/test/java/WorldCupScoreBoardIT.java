import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.example.maly.model.Game;
import org.example.maly.model.Team;
import org.example.maly.service.dao.GameRepository;
import org.example.maly.service.dao.TeamRepository;
import org.example.maly.service.driver.FootballScoreBoardDriver;
import org.example.maly.service.driver.ScoreBoardDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verification of the API
 */
public class WorldCupScoreBoardIT {

    private ScoreBoardDriver driver;

    private static TeamRepository teamRepository;
    private static GameRepository gameRepository;
    private static List<Game> gamesToStart;
    private static List<Game> expectedSortedSummary;

    @BeforeAll
    public static void setup(){
        teamRepository = TeamRepository.getInstance();
        gameRepository = GameRepository.getInstance();
        populateTeams();
        populateGames();
        populateExpectedSummary();
    }

    @Test
    public void end2endTest(){
        driver = new FootballScoreBoardDriver();
        testInvalidGames();
        testStartGames();
        testUpdateGames();

        List<Game> summary = driver.getSummary();
        //summary.forEach(g -> System.out.println(g.getScore()));
        //System.out.println("********************************");
        //expectedSortedSummary.forEach(g -> System.out.println(g.getScore()));

        testSummary(driver.getSummary(),expectedSortedSummary);
        finishGames();
    }

    private static void populateGames() {
        gamesToStart = new ArrayList<>();
        gamesToStart.add(buildGame("Mexico","Canada"));
        gamesToStart.add(buildGame("Spain","Brazil"));
        gamesToStart.add(buildGame("Germany","France"));
        gamesToStart.add(buildGame("Uruguay","Italy"));
        gamesToStart.add(buildGame("Argentina","Australia"));
    }

    private static void populateTeams() {
        teamRepository.addItem(new Team("Mexico"));
        teamRepository.addItem(new Team("Canada"));
        teamRepository.addItem(new Team("Spain"));
        teamRepository.addItem(new Team("Brazil"));
        teamRepository.addItem(new Team("Germany"));
        teamRepository.addItem(new Team("France"));
        teamRepository.addItem(new Team("Italy"));
        teamRepository.addItem(new Team("Uruguay"));
        teamRepository.addItem(new Team("Argentina"));
        teamRepository.addItem(new Team("Australia"));
    }

    private static void populateExpectedSummary(){
        expectedSortedSummary = new ArrayList<>();
        expectedSortedSummary.add(buildGame("Uruguay",6,"Italy",6));
        expectedSortedSummary.add(buildGame("Spain",10,"Brazil",2));
        expectedSortedSummary.add(buildGame("Mexico",0,"Canada",5));
        expectedSortedSummary.add(buildGame("Argentina",3,"Australia",1));
        expectedSortedSummary.add(buildGame("Germany",2,"France",2));
    }

    private void testInvalidGames() {
        Game invalidGameNoTeams = Game.getBuilder().build();
        Game invalidGameNoAwayTeam = Game.getBuilder().setHomeTeam(teamRepository.getTeamByName("Germany").get()).build();
        Game invalidSameTeam = buildGame("Germany","Germany");
        Assertions.assertAll(
            ()-> Assertions.assertThrows(RuntimeException.class,() -> driver.startGame(invalidGameNoTeams)),
            ()-> Assertions.assertThrows(RuntimeException.class,() -> driver.startGame(invalidGameNoAwayTeam)),
            ()-> Assertions.assertThrows(RuntimeException.class,() -> driver.startGame(invalidSameTeam))
        );
    }

    private void testStartGames(){
        gamesToStart.forEach(game -> {
            Assertions.assertDoesNotThrow(()->driver.startGame(game));
        });
        //should not start an already started game again
        Assertions.assertThrows(RuntimeException.class,()-> driver.startGame(gamesToStart.get(0)));

        //should not start a new game for a team which is already playing a game
        Game invalidGameItalyIsAlreadyPlaying = Game.getBuilder().setHomeTeam(teamRepository.getTeamByName("Italy").get()).setAwayTeam(new Team("Slovakia")).build();
        Assertions.assertThrows(RuntimeException.class,()-> driver.startGame(invalidGameItalyIsAlreadyPlaying));

        List<Game> summary = driver.getSummary();
        Collections.reverse(gamesToStart);
        testSummary(summary,gamesToStart);
    }


    private void testUpdateGames(){
        testUpdateGame("Mexico",0,5);
        testUpdateGame("Spain",10,2);
        testUpdateGame("Germany",2,2);
        testUpdateGame("Uruguay",6,6);
        testUpdateGame("Argentina",3,1);
    }


    private void testUpdateGame(String homeTeamName,int homeScore,int awayScore){
        //Searching the game based on the home team
        Game game = gameRepository.findGameForTeam(teamRepository.getTeamByName(homeTeamName).get()).orElse(null);
        driver.updateGame(game,homeScore,awayScore);
        Assertions.assertEquals(game.getHomeTeam().getScore(),homeScore,"Home score have not been updated.");
        Assertions.assertEquals(game.getAwayTeam().getScore(),awayScore,"Home score have not been updated.");
    }

    private void testSummary(List<Game> currentSummary,List<Game>expectedSummary){

        Assertions.assertTrue(currentSummary.size() == expectedSummary.size());
        for(int i=0; i<currentSummary.size();i++){
            Assertions.assertEquals(currentSummary.get(i),expectedSummary.get(i),String.format("Game with index %d does not match!",i));
        }
    }

    private void finishGames(){
        Game notStartedGame = buildGame("Italy","Germany");
        Assertions.assertThrows(RuntimeException.class,()-> driver.finishGame(notStartedGame));
        expectedSortedSummary.stream().map(game-> game.getHomeTeam().getTeam().getName()).forEach(team -> testFinishGame(team));
        Assertions.assertTrue(gameRepository.getSortedGames().isEmpty(),"Some of the games have not been finished");
    }

    private void testFinishGame(String teamName){
        Game game = gameRepository.findGameForTeam(teamRepository.getTeamByName(teamName).get()).orElse(null);
        Assertions.assertDoesNotThrow(()-> driver.finishGame(game));
    }

    private static Game buildGame(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        return Game.getBuilder()
            .setHomeTeam(teamRepository.getTeamByName(homeTeam).get())
            .setHomeScore(homeScore)
            .setAwayTeam(teamRepository.getTeamByName(awayTeam).get())
            .setAwayScore(awayScore)
            .build();
    }

    private static Game buildGame(String homeTeam,String awayTeam) {
        return Game.getBuilder()
            .setHomeTeam(teamRepository.getTeamByName(homeTeam).get())
            .setAwayTeam(teamRepository.getTeamByName(awayTeam).get())
            .build();
    }
}
