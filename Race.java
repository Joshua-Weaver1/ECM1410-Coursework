package cycling;
import java.util.ArrayList;
/**
 * Here we have created a priavte class named RaceManagement
 * which contains attributes and set methods and get methods
 */
public class Race {
    // Instance variables
    private int raceID;
    private String raceName;
    private String description;

    // Static variable
    public static ArrayList<Race> listOfRaces = new ArrayList<>();

    public int getRaceID() {
        return this.raceID;
    }

    public String getRaceName() {
        return this.raceName;
    }

    public String getDescription() {
        return this.description;
    }

    public Race() {
        this.raceID = 0;
        this.raceName = "Empty";
        this.description = "Empty";
    }

    public Race(String raceName, String description) {
        this.raceName = raceName;
        this.description = description;

        if (listOfRaces.isEmpty()){
            this.raceID = 1;
        }
        else{
            /** 
             *This line of code gets the ArrayList named "listOfRaces" and allocates the new
             *race an ID based off the last used raceID by adding one to the ID.
             */ 
            this.raceID = listOfRaces.get(listOfRaces.size() - 1).getRaceID() + 1;
        }
    }
}