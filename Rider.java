package cycling;

// This imports a built-in java package which allows up to create an ArrayList with simple commands
import java.util.ArrayList;

public class Rider {
    private String name;
    private int yearOfBirth;
    private int teamID;
    private int riderID;

    /** 
    This line of code creates the ArrayList which has the name "listOfTeams" which 
    will hold all the teams which have registered for the races
    */
    public static ArrayList<Rider> listOfRiders = new ArrayList<>();

    // Below are all the Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setRiderID(int riderID) {
        if (listOfRiders.contains(riderID))
            System.out.println("This ID is already taken by another rider!");
        else
            this.riderID = riderID;
    }

    // Below are the Getter methods
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

    // Constructor
    public Rider() {
        name = "Empty";
        yearOfBirth = 0;
        teamID = 0;
        riderID = 0;
    }

    public Rider(int teamID, int yearOfBirth, String name, ArrayList<Rider> listOfRiders) {

        // Set instance attributes
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;

        if (listOfRiders.isEmpty()){
            this.riderID = 1;
        }
        else{
            // Set ID 1 more than last rider
            this.riderID = listOfRiders.get(listOfRiders.size() - 1).getRiderID() + 1;
        }
    }
}