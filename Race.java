package cycling;

import java.util.ArrayList;

/**
 * A public class named Race which contains attributes and relevant methods
 * for handling and creating races.
 */
public class Race {
  // Instance variables
  private int raceID;
  private String raceName;
  private String description;

  // Static variables
  public static ArrayList<Race> listOfRaces = new ArrayList<>();

  //Getters
  public int getRaceID() {
    return this.raceID;
  }

  public String getRaceName() {
    return this.raceName;
  }

  public String getDescription() {
    return this.description;
  }

  /**
   * A generic constructor for a race object.
   */
  public Race() {
    this.raceID = 0;
    this.raceName = "Empty";
    this.description = "Empty";
  }

  /**
   * A constructor for a race object.
   * @param raceName Defines the name for a race object.
   * @param description Defines the description for a race object.
   */
  public Race(String raceName, String description) {
    this.raceName = raceName;
    this.description = description;

    if (listOfRaces.isEmpty()) {
      this.raceID = 1;
    } else {
      // Set ID 1 more than last race
      this.raceID = listOfRaces.get(listOfRaces.size() - 1).getRaceID() + 1;
    }
  }
}