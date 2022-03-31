package cycling;

import java.util.ArrayList;

/**
 * A class designed for design and management of Riders.
 */
public class Rider {
  //Instance attributes
  private String name;
  private int yearOfBirth;
  private int teamID;
  private int riderID;

  //Static attributes
  public static ArrayList<Rider> listOfRiders = new ArrayList<>();

  //Setters
  public void setName(String name) {
    this.name = name;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public void setTeamID(int teamID) {
    this.teamID = teamID;
  }

  //Getters
  public String getName() {
    return this.name;
  }

  public int getYearOfBirth() {
    return this.yearOfBirth;
  }

  public int getTeamID() {
    return this.teamID;
  }

  public int getRiderID() {
    return this.riderID;
  }

  /**
   * A general constructor for a rider object.
   */
  public Rider() {
    name = "Empty";
    yearOfBirth = 0;
    teamID = 0;
    riderID = 0;
  }

  /**
   * A constructor for an instance of a rider object.
   * @param teamID An unique identifier for a team.
   * @param yearOfBirth The year of birth for the instance of the rider.
   * @param name The name of the instance of the rider.
   * @param listOfRiders The static array containing all the instances of rider.
   */
  public Rider(int teamID, int yearOfBirth, String name, ArrayList<Rider> listOfRiders) {
    this.teamID = teamID;
    this.name = name;
    this.yearOfBirth = yearOfBirth;

    if (listOfRiders.isEmpty()) {
      this.riderID = 1;
    } else {
      // Set ID 1 more than last rider
      this.riderID = listOfRiders.get(listOfRiders.size() - 1).getRiderID() + 1;
    }
  }
}