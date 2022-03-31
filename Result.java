package cycling;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A class responsible for design and management of Results.
 */
public class Result {
  //Instance variables
  private int resultId;
  private int riderId;
  private int stageId;
  private LocalTime[] checkpoints;

  //Static variables
  public static ArrayList<Result> listOfResults = new ArrayList<Result>();

  //Getters
  public int getResultId() {
    return this.resultId;
  }

  public int getRiderId() {
    return this.riderId;
  }

  public int getStageId() {
    return this.stageId;
  }

  public LocalTime[] getCheckpoints() {
    return this.checkpoints;
  }

  /**
  * A general constructor for a result object.
  */
  public Result() {
    this.riderId = 0;
    this.stageId = 0;
    this.resultId = 0;
  }

  /**
   * A constructor for a result object.
   * @param riderId An unique id for a rider.
   * @param stageId An unique id for a stage.
   * @param checkpoints An unique array containing the chronological times the rider the stage.
   */
  public Result(int riderId, int stageId, LocalTime[] checkpoints) {
    this.riderId = riderId;
    this.stageId = stageId;
    this.checkpoints = checkpoints;

    if (listOfResults.isEmpty()) {
      this.resultId = 1;
    } else {
      // Set ID 1 more than last result
      this.resultId = listOfResults.get(listOfResults.size() - 1).getResultId() + 1;
    }
  }
}