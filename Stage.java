package cycling;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {
    private int raceID;
    private int stageID;
    private String stageName;
    private String description;
    private Double length;
    private LocalDateTime startTime;
    private StageType type;
    private StageState stageState;

    /** 
    This line of code creates the ArrayList which has the name "listOfTeams" which 
    will hold all the teams which have registered for the races
    */
    public static ArrayList<Stage> listOfStages = new ArrayList<>();

    public int getRaceID() {
        return this.raceID;
    }

    public int getStageID() {
        return this.stageID;
    }

    public String getStageName() {
        return this.stageName;
    }

    public String getdescription() {
        return this.description;
    }

    public Double getLength() {
        return this.length;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public StageType getType() {
        return this.type;
    }

    public StageState getStageState() {
        return this.stageState;
    }

    public Stage() {
        raceID = 0;
        stageID = 0;
        stageName = "Empty";
        description = "Empty";
        length = 0.0;
        stageState = StageState.PREPARATION_STATE;
    }

    public Stage(int raceID, String stageName, String description, Double length, StageType type) {

        // Set Instance attributes
        this.raceID = raceID;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.type = type;
        this.stageState = StageState.PREPARATION_STATE;

        if (listOfStages.isEmpty()){
            this.stageID = 1;
        }
        else{
            // Set ID 1 more than last rider
            this.stageID = listOfStages.get(listOfStages.size() - 1).getStageID() + 1;
        }
    }
}
