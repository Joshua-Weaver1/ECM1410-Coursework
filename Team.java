package cycling;

// This imports a built-in java package which allows up to create an ArrayList with simple commands
import java.util.ArrayList;

public class Team {
    // These are all the Instance Attributes that will be required for the "Team" class
    private String teamName;
    private String teamDescription;
    private int teamID;

    /** 
    This line of code creates the ArrayList which has the name "listOfTeams" which 
    will hold all the teams which have registered for the races
    */
    public static ArrayList<Team> listOfTeams = new ArrayList<>();

    // This method retrieves the "teamName"
    public String getTeamName() {
        return this.teamName;
    }

    // This method retrieves the "teamDescription"
    public String getTeamDescription() {
        return this.teamDescription;
    }

    // This method retrieves the "teamID"
    public int getTeamID() {
        return this.teamID;
    }

    // This is the default constuctor for a team object
    public Team() {
        this.teamName = "Empty";
        this.teamDescription = "Empty";
        this.teamID = 0;
    }

    /**
     * This constructor creates an instance of the class "Team" and assigns it
     * a name, description and an ID.
     * @param teamName
     * @param teamDescription
     */
    public Team(String teamName, String teamDescription) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;

        if (listOfTeams.isEmpty()){
            this.teamID = 1;
        }
        else{
            /** 
             *This line of code gets the ArrayList named "listOfTeams" and allocates the new
             *team an ID based off the last used teamID by adding one to the ID.
             */ 
            this.teamID = listOfTeams.get(listOfTeams.size() - 1).getTeamID() + 1;
        }
    }
}