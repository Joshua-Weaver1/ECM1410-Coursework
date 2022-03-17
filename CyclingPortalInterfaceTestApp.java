import cycling.CyclingPortal;
import cycling.BadMiniCyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.Team;
import cycling.Rider;

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

	/**
	 * Test method.
	 * 
	 * @param args not used
	 * @throws InvalidNameException
	 * @throws IllegalNameException
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException {
		System.out.println("The system compiled and started the execution...");

		CyclingPortal portal = new CyclingPortal();

		
		Team team1 = new Team("Team1", "Hello there!");
		Team team2 = new Team("Team2", "Hello there!");

		
		System.out.println(team1.getTeamName());
		System.out.println(team1.getTeamDescription());
		System.out.println(team1.getTeamID());
		System.out.println(team2.getTeamName());
		System.out.println(team2.getTeamDescription());
		System.out.println(team2.getTeamID());

		Rider rider1 = new Rider(1, 2002, "Dave");
		Rider rider2 = new Rider(2, 2003, "Jim");

		System.out.println(rider1.getName());
		System.out.println(rider1.getYearOfBirth());
		System.out.println(rider1.getTeamID());
		System.out.println(rider1.getRiderID());
		System.out.println(rider2.getName());
		System.out.println(rider2.getYearOfBirth());
		System.out.println(rider2.getTeamID());
		System.out.println(rider2.getRiderID());




		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

}
