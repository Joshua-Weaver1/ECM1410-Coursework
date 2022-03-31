package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class designed for design and management of Stages.
 */
public class Stage {
  //Instance variables
  private int raceID;
  private int stageID;
  private String stageName;
  private String description;
  private Double length;
  private LocalDateTime startTime;
  private StageType type;
  private StageState stageState;


  //Static variables
  public static ArrayList<Stage> listOfStages = new ArrayList<>();

  //Getters
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

  //Setters
  public void setStageState(StageState stageState) {
    this.stageState = stageState;
  }

  /**
   * A default constructor for a stage.
   */
  public Stage() {
    raceID = 0;
    stageID = 0;
    stageName = "Empty";
    description = "Empty";
    length = 0.0;
    stageState = StageState.PREPARATION_STATE;
  }

  /**
   * A constructor for a stage.
   * @param raceID An unique id of a race to add the instance of a stage too.
   * @param stageName Name of the stage.
   * @param description Description of the stage.
   * @param length Stage length in kilometres.
   * @param type StageType of the stage.
   */
  public Stage(int raceID, String stageName, String description, Double length, StageType type) {

    this.raceID = raceID;
    this.stageName = stageName;
    this.description = description;
    this.length = length;
    this.type = type;
    this.stageState = StageState.PREPARATION_STATE;

    if (listOfStages.isEmpty()) {
      this.stageID = 1;
    } else {
      // Set ID 1 more than last stage
      this.stageID = listOfStages.get(listOfStages.size() - 1).getStageID() + 1;
    }
  }
}
