package cycling;

import java.util.ArrayList;

/**
 * A class designed for design and management of Teams.
 */
public class Team {
  //Instance variables
  private String teamName;
  private String teamDescription;
  private int teamID;

  //Static variables
  public static ArrayList<Team> listOfTeams = new ArrayList<>();

  //Getters
  public String getTeamName() {
    return this.teamName;
  }

  public String getTeamDescription() {
    return this.teamDescription;
  }

  public int getTeamID() {
    return this.teamID;
  }

  /**
   * A default constructor for an instance of a team.
   */
  public Team() {
    this.teamName = "Empty";
    this.teamDescription = "Empty";
    this.teamID = 0;
  }

  /**
  * This constructor creates an instance of the class "Team" and assigns it
  * a name, description and an ID.
  * @param teamName The name of the instance of the team.
  * @param teamDescription The description of the instance of the team.
  */
  public Team(String teamName, String teamDescription) {
    this.teamName = teamName;
    this.teamDescription = teamDescription;

    if (listOfTeams.isEmpty()) {
      this.teamID = 1;
    } else {
      //Sets ID based off previous team 
      this.teamID = listOfTeams.get(listOfTeams.size() - 1).getTeamID() + 1;
    }
  }
}