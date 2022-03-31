package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * CyclingPortal is a compiling, functioning implementor
 * of the CyclingPortalInterface interface.
 */
public class CyclingPortal implements CyclingPortalInterface {

  /**
   * This method will take riderId and stageId and return the elapsed time
   * between the starting and finishing time of that rider in that stage.
   * @param riderId This is the unique id of the rider.
   * @param stageId This is the unique id of the stage.
   * @return
   */
  public LocalTime calculateElapsedTime(int riderId, int stageId) {

    long timeDiff = 0;

    LocalTime elapsedTime = LocalTime.of(0, 0, 0, 0);

    //For all results
    for (int k = 0;k < Result.listOfResults.size(); k++) {
      for (int i = 0;i < Stage.listOfStages.size(); i++) {
        if (Stage.listOfStages.get(i).getStageID() == stageId) {
          for (int j = 0;j < Rider.listOfRiders.size(); j++) {
            if (Rider.listOfRiders.get(j).getRiderID() == riderId) {
              LocalTime[] resultsArray = Result.listOfResults.get(k).getCheckpoints();
              LocalTime finalValue = resultsArray[resultsArray.length - 1];
              LocalTime firstValue = resultsArray[0];

              timeDiff = firstValue.until(finalValue, ChronoUnit.NANOS);

              long hours = TimeUnit.NANOSECONDS.toHours(timeDiff);
              long minutes = TimeUnit.NANOSECONDS.toMinutes(timeDiff) % 60;
              long seconds = TimeUnit.NANOSECONDS.toSeconds(timeDiff) % 60;
              long nanoseconds = TimeUnit.NANOSECONDS.toNanos(timeDiff) % 60;

              int hoursResult = Math.toIntExact(hours);
              int minutesResult = Math.toIntExact(minutes);
              int secondsResult = Math.toIntExact(seconds);
              int nanosecondsResult = Math.toIntExact(nanoseconds);

              elapsedTime = LocalTime.of(hoursResult, minutesResult, secondsResult,
               nanosecondsResult);
            }
          }
        }
      }
    }
    return elapsedTime;

  }

  @Override
  public int[] getRaceIds() {
    int[] raceIDs = new int[Race.listOfRaces.size()];

    assert raceIDs != null;

    if (Race.listOfRaces.size() >= 1) {
      for (int i = 0;i < Race.listOfRaces.size(); i++) {
        raceIDs[i] = Race.listOfRaces.get(i).getRaceID();
      }
    }
    return raceIDs;
  }

  @Override
  public int createRace(String name, String description) 
      throws IllegalNameException, InvalidNameException {

    boolean validRaceName = true;
    boolean legalRaceName = true;

    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceName() == name) {
        legalRaceName = false;

      } else if ((name == null) || (name == "") || (name.length() > 30) || (name.contains(" "))) {
        validRaceName = false;
      }
    }

    if (validRaceName == false) {
      throw new InvalidNameException();
    
    } else if (legalRaceName == false) {
      throw new IllegalNameException();

    } else {
      Race.listOfRaces.add(new Race(name, description));
      return Race.listOfRaces.get(Race.listOfRaces.size() - 1).getRaceID();
    }
  }

  @Override
  public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

    boolean invalidID = true;
    int raceIdForDetails = 0;
    String raceNameForDetails = "Empty";
    String raceDescriptionForDetails = "Empty";
    int numOfStages = 0;
    double totalLength = 0.0;


    for (int i = 0;i < Race.listOfRaces.size();i++) {
      if (Race.listOfRaces.get(i).getRaceID() == raceId) {
        invalidID = false;
        raceIdForDetails = Race.listOfRaces.get(i).getRaceID();
        raceNameForDetails = Race.listOfRaces.get(i).getRaceName();
        raceDescriptionForDetails = Race.listOfRaces.get(i).getDescription();
      }
    }

    for (int j = 0;j < Stage.listOfStages.size(); j++) {
      if (Stage.listOfStages.get(j).getRaceID() == raceId) {

        numOfStages++;
        totalLength += Stage.listOfStages.get(j).getLength();
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("This ID does not match to any race in the system");
    }

    String returnInfo = "Race ID: " + raceIdForDetails + " Race Name: " + raceNameForDetails 
        + " Race Description: " + raceDescriptionForDetails + " Number of Stages: " 
        + numOfStages + " Total Length of Stages: " + totalLength;
    return returnInfo;
  }

  @Override
  public void removeRaceById(int raceId) throws IDNotRecognisedException {

    boolean invalidID = true;

    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceID() == raceId) {
        for (int j = 0; j < Stage.listOfStages.size(); j++) {
          if (Stage.listOfStages.get(j).getRaceID() == raceId) {
            int stageId = Stage.listOfStages.get(j).getStageID();
            for (int k = 0; k < Segment.listOfSegments.size(); k++) {
              if (Segment.listOfSegments.get(k).getStageID() == stageId) {
                Segment.listOfSegments.remove(k);
              }
            }
          }
        }
      }
    }

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getRaceID() == raceId) {
        invalidID = false;
        Stage.listOfStages.remove(i);
      }
    }

    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceID() == raceId) {
        invalidID = false;
        Race.listOfRaces.remove(i);
      }
    }

    

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any race in the system.");
    }

  }

  @Override
  public int getNumberOfStages(int raceId) throws IDNotRecognisedException {

    boolean invalidID = true;
    int numOfStages = 0;
    int sizeOfArray = 0;

    for (int i = 0; i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getRaceID() == raceId) {
        sizeOfArray++;
        invalidID = false;
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any race in the system.");
    }

    int[] raceStageIDs = new int[sizeOfArray];
    if (Stage.listOfStages.size() >= 1) {
      for (int i = 0;i < Stage.listOfStages.size(); i++) {
        if (Stage.listOfStages.get(i).getRaceID() == raceId) {
          raceStageIDs[numOfStages] = Stage.listOfStages.get(i).getStageID();
          numOfStages++;
        }
      }
    }
    return raceStageIDs.length;
  }

  @Override
  public int addStageToRace(int raceId, String stageName, String description, double length,
      LocalDateTime startTime, StageType type)
      throws IDNotRecognisedException, IllegalNameException, InvalidNameException,
      InvalidLengthException {

    boolean validID = false;
    boolean validLength = true;
    boolean illegalName = true;
    boolean invalidName = true;

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageName() == stageName) {
        illegalName = false;
      } else if ((stageName == null) || (stageName == "") || (stageName.length() > 30)
          || (stageName.contains(" "))) {
        invalidName = false;
      }
    }

    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceID() == raceId) {
        validID = true;
      }
    }

    if (validID == false) {
      throw new IDNotRecognisedException("The ID does not match to any race in the system.");
    }
    if (length < 5.0) {
      validLength = false;
    }
    if (invalidName == false) {
      throw new InvalidNameException("The new name cannot be null, empty, or more than 30.");
    }
    if (illegalName == false) {
      throw new IllegalNameException("The name already exists in the platform.");
    }
    if (validLength == false) {
      throw new InvalidLengthException("The length must be above 5km!");
    } else {
      Stage.listOfStages.add(new Stage(raceId, stageName, description, length, type));
      return Stage.listOfStages.get(Stage.listOfStages.size() - 1).getStageID();
    }
  }

  @Override
  public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

    boolean invalidID = true;
    int numOfStages = 0;
    int sizeOfArray = 0;

    for (int i = 0; i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getRaceID() == raceId) {
        sizeOfArray++;
        invalidID = false;
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any race in the system.");
    }

    int[] raceStageIDs = new int[sizeOfArray];

    assert raceStageIDs != null;

    if (Stage.listOfStages.size() >= 1) {
      for (int i = 0;i < Stage.listOfStages.size(); i++) {
        if (Stage.listOfStages.get(i).getRaceID() == raceId) {
          raceStageIDs[numOfStages] = Stage.listOfStages.get(i).getStageID();
          numOfStages++;
        }
      }
    }
    return raceStageIDs;
  }

  @Override
  public double getStageLength(int stageId) throws IDNotRecognisedException {

    double lengthOfStage = 0.0;
    boolean invalidID = true;

    for (int i = 0;i < Stage.listOfStages.size();i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidID = false;
        lengthOfStage = Stage.listOfStages.get(i).getLength();
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("This ID does not match to any stage in the system");
    }

    return lengthOfStage;
  }

  @Override
  public void removeStageById(int stageId) throws IDNotRecognisedException {

    boolean invalidID = true;

    for (int i = 0;i < Segment.listOfSegments.size(); i++) {
      if (Segment.listOfSegments.get(i).getStageID() == stageId) {
        invalidID = false;
        Segment.listOfSegments.remove(i);
      }
    }

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidID = false;
        Stage.listOfStages.remove(i);
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any stage in the system.");
    }

  }

  @Override
  public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type,
      Double averageGradient, Double length) throws IDNotRecognisedException,
      InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

    boolean invalidID = true;
    boolean invalidStageState = true;
    boolean invalidStageType = true;
    int indexOfStage = 0;
    Double lengthOfStage = 0.0;
    StageState stateOfStage = StageState.WAITING_FOR_RESULTS;

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidID = false;
        indexOfStage = i;
        lengthOfStage = Stage.listOfStages.get(i).getLength();
        stateOfStage = Stage.listOfStages.get(i).getStageState();
      }
    }

    if (Stage.listOfStages.get(indexOfStage).getType() != StageType.TT) {
      invalidStageType = false;
    }
    if (stateOfStage == StageState.PREPARATION_STATE) {
      invalidStageState = false;
    }
    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any stage in the system.");
    }
    if (lengthOfStage < location) {
      throw new InvalidLocationException("This location is unavailable as it is larger than the"
    + " stage length");
    }

    if (invalidStageType == true) {
      throw new InvalidStageTypeException("Can't have a Categorized Climb in a Time Trial!");
    }

    if (invalidStageState == true) {
      throw new InvalidStageStateException("Attempting to perform an action within a stage that is"
    + " incompatible with its current state");
    } else {
      Segment.listOfSegments.add(new Segment(stageId, location, type, averageGradient, length));
      return Segment.listOfSegments.get(Segment.listOfSegments.size() - 1).getSegmentID();
    }
  }

  @Override
  public int addIntermediateSprintToStage(int stageId, double location)
      throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
      InvalidStageTypeException {

    boolean invalidID = true;
    boolean invalidStageState = true;
    boolean invalidStageType = true;
    int indexOfStage = 0;
    Double lengthOfStage = 0.0;
    StageState stateOfStage = StageState.WAITING_FOR_RESULTS;

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidID = false;
        indexOfStage = i;
        lengthOfStage = Stage.listOfStages.get(i).getLength();
        stateOfStage = Stage.listOfStages.get(i).getStageState();
      }
    }

    if (Stage.listOfStages.get(indexOfStage).getType() != StageType.TT) {
      invalidStageType = false;
    }

    if (stateOfStage == StageState.PREPARATION_STATE) {
      invalidStageState = false;
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any stage in the system.");
    }

    if (lengthOfStage < location) {
      throw new InvalidLocationException("This location is unavailable as it is larger that the"
      + " stage length");
    }

    if (invalidStageType == true) {
      throw new InvalidStageTypeException("Can't have a Categorized Climb in a Time Trial!");
    }

    if (invalidStageState == true) {
      throw new InvalidStageStateException("Attempting to perform an action within a stage that is"
      + " incompatible with its current state");
    } else {
      Segment.listOfSegments.add(new Segment(stageId, location));
      return Segment.listOfSegments.get(Segment.listOfSegments.size() - 1).getSegmentID();
    }
  }

  @Override
  public void removeSegment(int segmentId) throws IDNotRecognisedException,
      InvalidStageStateException {

    boolean invalidID = true;
    boolean invalidStageState = false;

    for (int i = 0;i < Segment.listOfSegments.size(); i++) {
      if (Segment.listOfSegments.get(i).getSegmentID() == segmentId) {
        invalidID = false;
        Segment.listOfSegments.remove(i);
      }
    }

    if (invalidStageState == true) {
      throw new InvalidStageStateException("Attempting to perform an action within a stage that is"
      + " incompatible with its current state");
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any stage in the system.");
    }

  }

  @Override
  public void concludeStagePreparation(int stageId) throws IDNotRecognisedException,
      InvalidStageStateException {

    boolean invalidId = true;
    int indexOfCorrectStage = 0;

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidId = false;
        indexOfCorrectStage = i;
      }
    }

    if (invalidId == true) {
      throw new IDNotRecognisedException("The ID does not match to any stage in the system.");
    }

    if (Stage.listOfStages.get(indexOfCorrectStage).getStageState() 
        == StageState.WAITING_FOR_RESULTS) {
      throw new InvalidStageStateException("Attempting to perform an action within a stage that is"
        + " incompatible with its current state");
    }

    Stage.listOfStages.get(indexOfCorrectStage).setStageState(StageState.WAITING_FOR_RESULTS);
  }

  @Override
  public int[] getStageSegments(int stageId) throws IDNotRecognisedException {

    boolean invalidID = true;
    int numOfSegments = 0;
    int sizeOfArray = 0;

    for (int i = 0; i < Segment.listOfSegments.size(); i++) {
      if (Segment.listOfSegments.get(i).getStageID() == stageId) {
        sizeOfArray++;
        invalidID = false;
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any race in the system.");
    }

    int[] listOfSegmentIds = new int[sizeOfArray];

    assert listOfSegmentIds != null;

    if (Segment.listOfSegments.size() >= 1) {
      for (int i = 0;i < Segment.listOfSegments.size(); i++) {
        if (Segment.listOfSegments.get(i).getStageID() == stageId) {
          listOfSegmentIds[numOfSegments] = Segment.listOfSegments.get(i).getSegmentID();
          numOfSegments++;
        }
      }
    }
    return listOfSegmentIds;
  }

  // Creates a team with name and description.
  @Override
  public int createTeam(String name, String description) throws IllegalNameException,
      InvalidNameException {

    boolean validTeamName = true;
    boolean legalTeamName = true;

    for (int i = 0;i < Team.listOfTeams.size(); i++) {
      if (Team.listOfTeams.get(i).getTeamName() == name) {
        legalTeamName = false;
      } else if ((name == null) || (name == "") || (name.length() > 30) || (name.contains(" "))) {
        validTeamName = false;
      }
    }

    if (validTeamName == false) {
      throw new InvalidNameException();
    } else if (legalTeamName == false) {
      throw new IllegalNameException();
    } else {
      Team.listOfTeams.add(new Team(name, description));
      return Team.listOfTeams.get(Team.listOfTeams.size() - 1).getTeamID();
    }
  }


  @Override
  public void removeTeam(int teamId) throws IDNotRecognisedException {

    boolean invalidID = true;

    for (int i = 0;i < Rider.listOfRiders.size(); i++) {
      if (Rider.listOfRiders.get(i).getTeamID() == teamId) {
        Rider.listOfRiders.remove(i);
      }
    }

    for (int i = 0;i < Team.listOfTeams.size(); i++) {
      if (Team.listOfTeams.get(i).getTeamID() == teamId) {
        invalidID = false;
        Team.listOfTeams.remove(i);
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any team in the system.");
    }
  }

  @Override
  public int[] getTeams() {
    int[] teamIDs = new int[Team.listOfTeams.size()];

    assert teamIDs != null;

    if (Team.listOfTeams.size() >= 1) {
      for (int i = 0;i < Team.listOfTeams.size(); i++) {
        teamIDs[i] = Team.listOfTeams.get(i).getTeamID();
      }
    }
    return teamIDs;
  }

  @Override
  public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {

    boolean invalidID = true;
    int numOfRiders = 0;
    int sizeOfArray = 0;

    for (int i = 0; i < Rider.listOfRiders.size(); i++) {
      if (Rider.listOfRiders.get(i).getTeamID() == teamId) {
        sizeOfArray++;
        invalidID = false;
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any team in the system.");
    }

    int[] teamRiderIDs = new int[sizeOfArray];
    if (Rider.listOfRiders.size() >= 1) {
      for (int i = 0;i < Rider.listOfRiders.size(); i++) {
        if (Rider.listOfRiders.get(i).getTeamID() == teamId) {
          teamRiderIDs[numOfRiders] = Rider.listOfRiders.get(i).getRiderID();
          numOfRiders++;
        }
      }
    }
    return teamRiderIDs;
  }

  @Override
  public int createRider(int teamID, String name, int yearOfBirth)
      throws IDNotRecognisedException, IllegalArgumentException {

    boolean invalidID = true;
    boolean legalArgument = true;

    for (int i = 0;i < Team.listOfTeams.size(); i++) {
      if (Team.listOfTeams.get(i).getTeamID() == teamID) {
        invalidID = false;
      } else if ((name == null) || (yearOfBirth < 1900)) {
        legalArgument = false;
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any team in the system.");
    } else if (legalArgument == false) {
      throw new IllegalArgumentException();
    } else {
      Rider.listOfRiders.add(new Rider(teamID, yearOfBirth, name, Rider.listOfRiders));
      return Rider.listOfRiders.get(Rider.listOfRiders.size() - 1).getRiderID();
    }
  }

  @Override
  public void removeRider(int riderId) throws IDNotRecognisedException {

    boolean invalidID = true;

    for (int i = 0;i < Result.listOfResults.size(); i++) {
      if (Result.listOfResults.get(i).getRiderId() == riderId) {
        invalidID = false;
        Rider.listOfRiders.remove(i);
      }
    }

    for (int i = 0;i < Rider.listOfRiders.size(); i++) {
      if (Rider.listOfRiders.get(i).getRiderID() == riderId) {
        invalidID = false;
        Rider.listOfRiders.remove(i);
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("The ID does not match to any rider in the system.");
    }
  }

  @Override
  public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
      throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
      InvalidStageStateException {

    boolean invalidId = true;
    boolean duplicatedResult = false;
    boolean invalidStageState = true;
    int numberOfSegmentsInStage = 0;

    for (int i = 0;i < Rider.listOfRiders.size(); i++) {
      if (Rider.listOfRiders.get(i).getRiderID() == riderId) {
        invalidId = false;
      }
    }

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidId = false;
      }
    }

    for (int i = 0;i < Result.listOfResults.size(); i++) {
      if (Result.listOfResults.get(i).getStageId() == stageId) {
        if (Result.listOfResults.get(i).getRiderId() == riderId) {
          duplicatedResult = true;
        }
      }
    }

    for (int i = 0;i < Segment.listOfSegments.size(); i++) {
      if (Segment.listOfSegments.get(i).getStageID() == stageId) {
        numberOfSegmentsInStage++;
      }
    }

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageState() == StageState.WAITING_FOR_RESULTS) {
        invalidStageState = false;
      }
    }

    if (invalidId == true) {
      throw new IDNotRecognisedException("The ID does not match to any rider or stage"
       + " in the system.");
    }

    if (duplicatedResult == true) {
      throw new DuplicatedResultException("This rider already had a result in this stage");
    }

    if (checkpoints.length != numberOfSegmentsInStage + 2) {
      throw new InvalidCheckpointsException("The length of checkpoints is not equal to n + 2");
    }

    if (invalidStageState == true) {
      throw new InvalidStageStateException("Attempting to perform an action within a stage that is"
       + " incompatible with its current state");
    } else {
      Result.listOfResults.add(new Result(riderId, stageId, checkpoints));
    }
  }

  @Override
  public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws
      IDNotRecognisedException {

    boolean invalidId = true;
    boolean checkpointsNotNull = true;

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidId = false;
      }
    }

    for (int j = 0;j < Rider.listOfRiders.size(); j++) {
      if (Rider.listOfRiders.get(j).getRiderID() == riderId) {
        invalidId = false;
      }
    }

    for (int k = 0;k < Result.listOfResults.size(); k++) {
      if (Result.listOfResults.get(k).getCheckpoints() == null) {
        for (int i = 0;i < Stage.listOfStages.size(); i++) {
          if (Stage.listOfStages.get(i).getStageID() == stageId) {
            for (int j = 0;j < Rider.listOfRiders.size(); j++) {
              if (Rider.listOfRiders.get(j).getRiderID() == riderId) {
                checkpointsNotNull = false;
              }
            }
          }
        }
      }
    }

    int sizeOfArray = 0;

    for (int i = 0;i < Result.listOfResults.size(); i++) {
      if (Result.listOfResults.get(i).getStageId() == stageId) {
        for (int j = 0;j < Result.listOfResults.size(); j++) {
          if (Result.listOfResults.get(j).getRiderId() == riderId) {
            sizeOfArray = Result.listOfResults.get(i).getCheckpoints().length;
          }
        }
      }
    }

    LocalTime[] resultsArray = new LocalTime[sizeOfArray + 1];

    for (int i = 0;i < Result.listOfResults.size(); i++) {
      if (Result.listOfResults.get(i).getStageId() == stageId) {
        for (int j = 0;j < Result.listOfResults.size(); j++) {
          if (Result.listOfResults.get(j).getRiderId() == riderId) {
            resultsArray = Result.listOfResults.get(i).getCheckpoints();
          }
        }
      }
    }

    resultsArray[resultsArray.length - 1] = calculateElapsedTime(riderId, stageId);

    if (invalidId == true) {
      throw new IDNotRecognisedException("The ID does not match to any rider or stage in the"
       + " system.");
    }

    if (checkpointsNotNull == false) {
      return new LocalTime[0];
    } 

    return resultsArray;
  }

  @Override
  public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws
      IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

    boolean invalidId = true;

    for (int i = 0;i < Rider.listOfRiders.size(); i++) {
      if (Rider.listOfRiders.get(i).getRiderID() == riderId) {
        invalidId = false;
      }
    }

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidId = false;
      }
    }

    if (invalidId == true) {
      throw new IDNotRecognisedException("The ID does not match to any rider or stage in"
       + " the system.");
    } else {
      for (int i = 0;i < Result.listOfResults.size(); i++) {
        if (Result.listOfResults.get(i).getStageId() == stageId) {
          if (Result.listOfResults.get(i).getRiderId() == riderId) {
            Result.listOfResults.remove(i);
          }
        }
      }
    }
  }

  @Override
  public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {

    boolean invalidId = true;
    int sizeOfArray = 0;

    for (int i = 0;i < Stage.listOfStages.size(); i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidId = false;
      }
    }

    if (invalidId == true) {
      throw new IDNotRecognisedException("The ID does not match to any rider or stage in"
       + " the system.");
    }

    for (int i = 0; i < Result.listOfResults.size(); i++) {
      if (Result.listOfResults.get(i).getStageId() == stageId) {
        sizeOfArray++;
      }
    }

    int count = 0;
    int[] ridersRankInStage = new int[sizeOfArray];
    LocalTime[] ridersElapsedTimes = new LocalTime[sizeOfArray];

    for (int i = 0; i < Result.listOfResults.size(); i++) {
      if (Result.listOfResults.get(i).getStageId() == stageId) {
        ridersRankInStage[count] = Result.listOfResults.get(i).getRiderId();
        ridersElapsedTimes[count] = calculateElapsedTime(Result.listOfResults.get(i)
        .getRiderId(), stageId);
        count++;
      }
    }

    int n = ridersElapsedTimes.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (ridersElapsedTimes[j].compareTo(ridersElapsedTimes[j + 1]) > 0) {
          LocalTime timeSwap = ridersElapsedTimes[j];
          ridersElapsedTimes[j] = ridersElapsedTimes[j + 1];
          ridersElapsedTimes[j + 1] = timeSwap;

          int rankSwap = ridersRankInStage[j];
          ridersRankInStage[j] = ridersRankInStage[j + 1];
          ridersRankInStage[j + 1] = rankSwap;

        }
      }
    }
    return ridersRankInStage;
  }

  @Override
  public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws
      IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {

    boolean invalidID = true;
    int[] rankedRiderIds = getRidersRankInStage(stageId);
    int[] listOfAssignedPoints = new int[rankedRiderIds.length];
    int[] flatStagePoints = new int[]{50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
    int[] mediumMountainStagePoints = new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4,
      3, 2};
    int[] highMountainOrTtStagePoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3,
      2, 1};

    for (int i = 0;i < Stage.listOfStages.size();i++) {
      if (Stage.listOfStages.get(i).getStageID() == stageId) {
        invalidID = false;
      }
    }

    if (invalidID == true) {
      throw new IDNotRecognisedException("This ID does not match to any stage in the system");
    }


    for (int i = 0; i < Stage.listOfStages.size();i++) {
      if (Stage.listOfStages.get(i).getType() == StageType.FLAT) {
        for (int j = 0; j < rankedRiderIds.length;j++) {
          switch (j) {
            case 0:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 1:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 2:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 3:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 4:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 5:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 6:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 7:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 8:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 9:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 10:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 11:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 12:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 13:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            case 14:
              listOfAssignedPoints[j] = flatStagePoints[j];
              break;
            default:
              listOfAssignedPoints[j] = 0;
              break;
          }
        }
      } else if (Stage.listOfStages.get(i).getType() == StageType.MEDIUM_MOUNTAIN) {
        for (int j = 0; j < rankedRiderIds.length;j++) {
          switch (j) {
            case 0:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 1:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 2:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 3:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 4:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 5:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 6:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 7:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 8:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 9:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 10:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 11:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 12:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 13:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            case 14:
              listOfAssignedPoints[j] = mediumMountainStagePoints[j];
              break;
            default:
              listOfAssignedPoints[j] = 0;
              break;
          }
        }
      } else if ((Stage.listOfStages.get(i).getType() == StageType.HIGH_MOUNTAIN) 
          || (Stage.listOfStages.get(i).getType() == StageType.TT)) {
        for (int j = 0; j < rankedRiderIds.length;j++) {
          switch (j) {
            case 0:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 1:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 2:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 3:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 4:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 5:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 6:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 7:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 8:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 9:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 10:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 11:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 12:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 13:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            case 14:
              listOfAssignedPoints[j] = highMountainOrTtStagePoints[j];
              break;
            default:
              listOfAssignedPoints[j] = 0;
              break;
          }
        }
      }
    }
    return listOfAssignedPoints;
  }

  @Override
  public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void eraseCyclingPortal() {

    Team.listOfTeams.clear();
    Rider.listOfRiders.clear();
    Race.listOfRaces.clear();
    Stage.listOfStages.clear();
    Segment.listOfSegments.clear();
    Result.listOfResults.clear();
  }

  @Override
  public void saveCyclingPortal(String filename) throws IOException {
    try {
      ObjectOutputStream oosOut = new ObjectOutputStream(
          new FileOutputStream(filename));
      oosOut.writeObject(Team.listOfTeams);
      oosOut.writeObject(Rider.listOfRiders);
      oosOut.writeObject(Race.listOfRaces);
      oosOut.writeObject(Stage.listOfStages);
      oosOut.writeObject(Segment.listOfSegments);
      oosOut.writeObject(Result.listOfResults);
      oosOut.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
    try {
      ObjectInputStream oosIn = new ObjectInputStream(new FileInputStream(
          filename));
      Team.listOfTeams = (ArrayList<Team>)oosIn.readObject();
      Rider.listOfRiders = (ArrayList<Rider>)oosIn.readObject();
      Race.listOfRaces = (ArrayList<Race>)oosIn.readObject();
      Stage.listOfStages = (ArrayList<Stage>)oosIn.readObject();
      Segment.listOfSegments = (ArrayList<Segment>)oosIn.readObject();
      Result.listOfResults = (ArrayList<Result>)oosIn.readObject();
      oosIn.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void removeRaceByName(String name) throws NameNotRecognisedException {

    boolean validName = false;

    int raceId;
    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceName() == name) {
        raceId = Race.listOfRaces.get(i).getRaceID();
        for (int j = 0; j < Stage.listOfStages.size(); j++) {
          if (Stage.listOfStages.get(j).getRaceID() == raceId) {
            int stageId = Stage.listOfStages.get(j).getStageID();
            for (int k = 0; k < Segment.listOfSegments.size(); k++) {
              if (Segment.listOfSegments.get(k).getStageID() == stageId) {
                Segment.listOfSegments.remove(k);
              }
            }
          }
        }
      }
    }

    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceName() == name) {
        raceId = Race.listOfRaces.get(i).getRaceID();
        for (int j = 0; j < Stage.listOfStages.size(); j++) {
          if (Stage.listOfStages.get(j).getRaceID() == raceId) {
            Stage.listOfStages.remove(j);
          }
        }
      }
    }

    for (int i = 0;i < Race.listOfRaces.size(); i++) {
      if (Race.listOfRaces.get(i).getRaceName() == name) {
        validName = false;
        Race.listOfRaces.remove(i);
      }
    }

    if (validName == true) {
      throw new NameNotRecognisedException("The name does not match to any race in the system.");
    }
  }

  @Override
  public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws
      IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getRidersMountainPointClassificationRank(int raceId) throws
      IDNotRecognisedException {
    // TODO Auto-generated method stub
    return null;
  }
}
