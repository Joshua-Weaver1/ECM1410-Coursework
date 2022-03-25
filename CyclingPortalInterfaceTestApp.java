import cycling.CyclingPortal;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.Race;
import cycling.Team;
import cycling.Rider;
import cycling.StageType;
import cycling.SegmentType;
import java.time.LocalDateTime;
import cycling.Stage;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	private static final StageType FLAT = null;

	/**
	 * Test method.
	 * 
	 * @param args not used
	 * @throws InvalidNameException
	 * @throws IllegalNameException
	 * @throws IDNotRecognisedException
	 * @throws IllegalArgumentException
	 * @throws InvalidLengthException
	 * @throws InvalidStageTypeException
	 * @throws InvalidStageStateException
	 * @throws InvalidLocationException
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IllegalArgumentException, IDNotRecognisedException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		System.out.println("The system compiled and started the execution...");

		CyclingPortal portal = new CyclingPortal();

		System.out.println("Creating 5 teams...");
		portal.createTeam("Team1", "Hello!");
		portal.createTeam("Team2", "Hello!");
		portal.createTeam("Team3", "Hello!");
		portal.createTeam("Team4", "Hello!");
		portal.createTeam("Team5", "Hello!");

		System.out.println("Creating 7 riders...");
		portal.createRider(1, "John", 2000);
		portal.createRider(1, "Jones", 2005);
		portal.createRider(1, "Ben", 2006);
		portal.createRider(2, "Peter", 2001);
		portal.createRider(3, "Joshua", 2002);
		portal.createRider(4, "Frank", 2003);
		portal.createRider(5, "Dave", 2004);

		System.out.println("Printing the list of teams...");
		System.out.println(Team.listOfTeams);

		System.out.println("Printing the IDs of each individual team...");
		int[] a = portal.getTeams();
		for (int j:a) {
			System.out.println(j);
		}

		System.out.println("Printing the list of riders...");
		System.out.println(Rider.listOfRiders);

		System.out.println("Printing the IDs of each individual rider in team 1...");
		int[] b = portal.getTeamRiders(1);
		for (int k:b) {
			System.out.println(k);
		}

		System.out.println("Removing team 2 and printing new list of teams...");
		portal.removeTeam(2);
		System.out.println(Team.listOfTeams);

		System.out.println("Printing the IDs of each individual team with now team 2 being deleted...");
		int[] c = portal.getTeams();
		for (int j:c) {
			System.out.println(j);
		}


		System.out.println("Removing rider 1 and printing new list of riders...");
		portal.removeRider(1);
		System.out.println(Rider.listOfRiders);

		System.out.println("Printing the IDs of each individual rider in team 1 (should be 2 and 3)...");
		int[] d = portal.getTeamRiders(1);
		for (int k:d) {
			System.out.println(k);
		}

		System.out.println("Creating 6 new races and outputting the list of them...");
		portal.createRace("Race1", "Hello!");
		portal.createRace("Race2", "Hello!");
		portal.createRace("Race3", "Hello!");
		portal.createRace("Race4", "Hello!");
		portal.createRace("Race5", "Hello!");
		portal.createRace("Race6", "Hello!");
		System.out.println(Race.listOfRaces);

		System.out.println("Printing individual race IDs (should be 6)...");
		int[] e = portal.getRaceIds();
		for (int j:e) {
			System.out.println(j);
		}


		System.out.println("Removing race 2 and printing new list of races...");
		portal.removeRaceById(2);
		System.out.println(Race.listOfRaces);

		System.out.println("Creating 9 stages (3 in race 1 and 1 in each other race)...");
		portal.addStageToRace(1, "Stage1", "Hello1!", 12.0, LocalDateTime.now(), FLAT);
		portal.addStageToRace(1, "Stage2", "Hello2!", 7.0, LocalDateTime.now(), FLAT);
		portal.addStageToRace(1, "Stage3", "Hello3!", 6.0, LocalDateTime.now(), FLAT);
		portal.addStageToRace(3, "Stage1Race3", "Hello1!", 8.0, LocalDateTime.now(), FLAT);
		portal.addStageToRace(4, "Stage1Race4", "Hello1!", 9.0, LocalDateTime.now(), FLAT);
		portal.addStageToRace(5, "Stage1Race5", "Hello1!", 10.0, LocalDateTime.now(), FLAT);
		portal.addStageToRace(6, "Stage1Race6", "Hello1!", 11.0, LocalDateTime.now(), FLAT);
		System.out.println(Stage.listOfStages);

		System.out.println("Deleting Race 1 Stage 2...");
		portal.removeStageById(2);

		System.out.println("Getting stages from race 1 (should be 1 and 3)...");
		int[] f = portal.getRaceStages(1);
		for (int j:f) {
			System.out.println(j);
		}

		System.out.println("Getting number of stages in race 1");
		int g = portal.getNumberOfStages(1);
		System.out.println(g);

		portal.addStageToRace(1, "Stage2", "Hello2!", 7.0, LocalDateTime.now(), FLAT);

		System.out.println("Getting stages from race 1...");
		int[] h = portal.getRaceStages(1);
		for (int j:h) {
			System.out.println(j);
		}

		System.out.println("Getting number of stages in race 1");
		int i = portal.getNumberOfStages(1);
		System.out.println(i);

		System.out.println("Getting stages from race 6...");
		int[] k = portal.getRaceStages(6);
		for (int j:k) {
			System.out.println(j);
		}

		System.out.println("Getting Race Deatails for race 1");
		String l = portal.viewRaceDetails(1);
		System.out.println(l);

		System.out.println("Adding 3 catogarised climbs to stage 1...");
		portal.addCategorizedClimbToStage(1, 12.0, SegmentType.C1, 1.0, 3.0);
		portal.addCategorizedClimbToStage(1, 4.0, SegmentType.C1, 2.0, 3.0);
		portal.addCategorizedClimbToStage(1, 4.0, SegmentType.C1, 3.0, 3.0);

		System.out.println("Adding 3 intermediate sprints to stage 1...");
		portal.addIntermediateSprintToStage(1, 12.0);
		portal.addIntermediateSprintToStage(1, 4.0);
		portal.addIntermediateSprintToStage(1, 4.0);

		System.out.println("Getting segment IDs from stage 1");
		int[] m = portal.getStageSegments(1);
		for (int j:m) {
			System.out.println(j);
		}

		System.out.println("Deleting segment 2 from Stage 1");
		portal.removeSegment(2);

		System.out.println("Getting segment IDs from stage 1");
		int[] n = portal.getStageSegments(1);
		for (int j:n) {
			System.out.println(j);
		}

		//System.out.println("Getting stage length from stage 1 race 5");
		//double l = portal.getStageLength(77);
		//System.out.println(l);



		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

}
