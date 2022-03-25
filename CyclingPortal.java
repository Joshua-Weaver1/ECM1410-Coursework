package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * CyclingPortal is a compiling, functioning implementor
 * of the CyclingPortalInterface interface.
 */
public class CyclingPortal implements CyclingPortalInterface {

  @Override
  public int[] getRaceIds() {
    int[] raceIDs = new int[Race.listOfRaces.size()];
    if (Race.listOfRaces.size()>=1) {
        ;
    }
    for (int i = 0;i < Race.listOfRaces.size(); i++) {
				raceIDs[i] = Race.listOfRaces.get(i).getRaceID();
			}
		return raceIDs;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		
		boolean validRaceName = true;
		boolean legalRaceName = true;

		for(int i = 0;i < Race.listOfRaces.size(); i++) {
			if (Race.listOfRaces.get(i).getRaceName() == name) {
				legalRaceName = false;
			}
			else if((name == null) || (name == "") || (name.length() > 30) || (name.contains(" "))) {
				validRaceName = false;
			}
        }

		if(validRaceName == false) {
			throw new InvalidNameException();
		}
		else if(legalRaceName == false) {
			throw new IllegalNameException();
		}
		else {
			Race.listOfRaces.add(new Race(name, description));
			return Race.listOfRaces.get(Race.listOfRaces.size() - 1).getRaceID();
		}
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		
		boolean invalidID = true;
		int raceIDForDetails = 0;
		String raceNameForDetails = "Empty";
		String raceDescriptionForDetails = "Empty";
		int numOfStages = 0;
		double totalLength = 0.0;


		for(int i = 0;i < Race.listOfRaces.size();i++) {
			if (Race.listOfRaces.get(i).getRaceID() == raceId) {
				invalidID = false;
				raceIDForDetails = Race.listOfRaces.get(i).getRaceID();
				raceNameForDetails = Race.listOfRaces.get(i).getRaceName();
				raceDescriptionForDetails = Race.listOfRaces.get(i).getDescription();
			}
		}
		
		for(int j = 0;j < Stage.listOfStages.size(); j++) {
			if (Stage.listOfStages.get(j).getRaceID() == raceId) {

				numOfStages++;
				totalLength += Stage.listOfStages.get(j).getLength();
			}
		}

		if(invalidID == true) {
			throw new IDNotRecognisedException("This ID does not match to any race in the system");
		}

		String returnInfo = "Race ID: " + raceIDForDetails + " Race Name: " + raceNameForDetails + " Race Description: " +
			raceDescriptionForDetails + " Number of Stages: " + numOfStages + " Total Length of Stages: " + totalLength;
		return returnInfo;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		
		boolean invalidID = true;

		for(int i = 0;i < Race.listOfRaces.size(); i++) {
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
		if (Stage.listOfStages.size()>=1) {
            ;
        }
			for (int i = 0;i < Stage.listOfStages.size(); i++) {
				if (Stage.listOfStages.get(i).getRaceID() == raceId) {
					raceStageIDs[numOfStages] = Stage.listOfStages.get(i).getStageID(); numOfStages++;
				}
			}


		return raceStageIDs.length;

	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
				
				boolean validID = false;
				boolean validLength = true;
				boolean illegalName = true;
				boolean invalidName = true;
		
				for(int i = 0;i < Stage.listOfStages.size(); i++) {
					if (Stage.listOfStages.get(i).getStageName() == stageName) {
						illegalName = false;
					}
					else if((stageName == null) || (stageName == "") || (stageName.length() > 30) || (stageName.contains(" "))) {
						invalidName = false;
					}
				}

				for(int i = 0;i < Race.listOfRaces.size(); i++) {
					if (Race.listOfRaces.get(i).getRaceID() == raceId) {
						validID = true;
					}
				}
		
				if (validID == false) {
					throw new IDNotRecognisedException("The ID does not match to any race in the system.");
				}
				if(length < 5.0) {
					validLength = false;
				}
				if(invalidName == false) {
					throw new InvalidNameException("The new name cannot be null, empty, or more than 30.");
				}
				if(illegalName == false) {
					throw new IllegalNameException("The name already exists in the platform.");
				}
				if(validLength == false) {
					throw new InvalidLengthException("The length must be above 5km!");
				}
				else {
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
		if (Stage.listOfStages.size()>=1) {
            ;
        }
			for (int i = 0;i < Stage.listOfStages.size(); i++) {
				if (Stage.listOfStages.get(i).getRaceID() == raceId) {
					raceStageIDs[numOfStages] = Stage.listOfStages.get(i).getStageID(); numOfStages++;
				}
			}


		return raceStageIDs;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		
		double lengthOfStage = 0.0;
		boolean invalidID = true;

		for(int i = 0;i < Stage.listOfStages.size();i++) {
			if (Stage.listOfStages.get(i).getStageID() == stageId) {
                ;
            }
				invalidID = false;
				lengthOfStage = Stage.listOfStages.get(i).getLength();
		}

		if(invalidID == true) {
			throw new IDNotRecognisedException("This ID does not match to any stage in the system");
		}

		return lengthOfStage;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		
		boolean invalidID = true;

		for(int i = 0;i < Stage.listOfStages.size(); i++) {
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
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		
				boolean invalidID = true;
				boolean invalidStageState = true;
				boolean invalidStageType = true;
				int indexOfStage = 0;
				Double lengthOfStage = 0.0;
				StageState stateOfStage = StageState.WAITING_FOR_RESULTS;

				for(int i = 0;i < Stage.listOfStages.size(); i++) {
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
					throw new InvalidLocationException("This location is unavailable as it is larger that the stage length");
				}

				if (invalidStageType == true) {
					throw new InvalidStageTypeException("Can't have a Categorized Climb in a Time Trial!");
				}

				if (invalidStageState == true) {
					throw new InvalidStageStateException("Attempting to perform an action within a stage that is incompatible with its current state");
				}

				else {
					Segment.listOfSegments.add(new Segment(stageId, location, type, averageGradient, length));
					return Segment.listOfSegments.get(Segment.listOfSegments.size() - 1).getSegmentID();
				}
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		
				boolean invalidID = true;
				boolean invalidStageState = true;
				boolean invalidStageType = true;
				int indexOfStage = 0;
				Double lengthOfStage = 0.0;
				StageState stateOfStage = StageState.WAITING_FOR_RESULTS;

				for(int i = 0;i < Stage.listOfStages.size(); i++) {
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
					throw new InvalidLocationException("This location is unavailable as it is larger that the stage length");
				}

				if (invalidStageType == true) {
					throw new InvalidStageTypeException("Can't have a Categorized Climb in a Time Trial!");
				}

				if (invalidStageState == true) {
					throw new InvalidStageStateException("Attempting to perform an action within a stage that is incompatible with its current state");
				}

				else {
					Segment.listOfSegments.add(new Segment(stageId, location));
					return Segment.listOfSegments.get(Segment.listOfSegments.size() - 1).getSegmentID();
				}
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		
		boolean invalidID = true;
		boolean invalidStageState = false;

		for(int i = 0;i < Segment.listOfSegments.size(); i++) {
			if (Segment.listOfSegments.get(i).getSegmentID() == segmentId) {
				invalidID = false;
				Segment.listOfSegments.remove(i);
			}
		}

		if (invalidStageState == true) {
			throw new InvalidStageStateException("Attempting to perform an action within a stage that is incompatible with its current state");
		}

		if (invalidID == true) {
			throw new IDNotRecognisedException("The ID does not match to any stage in the system.");
		}

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

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
		if (Segment.listOfSegments.size()>=1) {
            ;
        }
			for (int i = 0;i < Segment.listOfSegments.size(); i++) {
				if (Segment.listOfSegments.get(i).getStageID() == stageId) {
					listOfSegmentIds[numOfSegments] = Segment.listOfSegments.get(i).getSegmentID(); numOfSegments++;
				}
			}


		return listOfSegmentIds;
	}

	// Creates a team with name and description.
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {

		boolean validTeamName = true;
		boolean legalTeamName = true;

		for(int i = 0;i < Team.listOfTeams.size(); i++) {
			if (Team.listOfTeams.get(i).getTeamName() == name) {
				legalTeamName = false;
			}
			else if((name == null) || (name == "") || (name.length() > 30) || (name.contains(" "))) {
				validTeamName = false;
			}
        }

		if(validTeamName == false) {
			throw new InvalidNameException();
		}
		else if(legalTeamName == false) {
			throw new IllegalNameException();
		}
		else {
			Team.listOfTeams.add(new Team(name, description));
			return Team.listOfTeams.get(Team.listOfTeams.size() - 1).getTeamID();
		}
		
	}


	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		
		boolean invalidID = true;

		for(int i = 0;i < Team.listOfTeams.size(); i++) {
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
		if (Team.listOfTeams.size()>=1) {
            ;
        }
			for (int i = 0;i < Team.listOfTeams.size(); i++) {
				teamIDs[i] = Team.listOfTeams.get(i).getTeamID();
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
		if (Rider.listOfRiders.size()>=1) {
            ;
        }
			for (int i = 0;i < Rider.listOfRiders.size(); i++) {
				if (Rider.listOfRiders.get(i).getTeamID() == teamId) {
					teamRiderIDs[numOfRiders] = Rider.listOfRiders.get(i).getRiderID(); numOfRiders++;
				}
			}


		return teamRiderIDs;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {

				boolean invalidID = true;
				boolean legalArgument = true;
				
				for(int i = 0;i < Team.listOfTeams.size(); i++) {
					if (Team.listOfTeams.get(i).getTeamID() == teamID) {
						invalidID = false;
					}
					else if((name == null) || (yearOfBirth < 1900)) {
						legalArgument = false;
					}
				}

				if(invalidID == true) {
					throw new IDNotRecognisedException("The ID does not match to any team in the system.");
				}
				else if(legalArgument == false) {
					throw new IllegalArgumentException();
				}
				else {
					Rider.listOfRiders.add(new Rider(teamID, yearOfBirth, name, Rider.listOfRiders));
					return Rider.listOfRiders.get(Rider.listOfRiders.size() - 1).getRiderID();
				}

	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		
		boolean invalidID = true;
		
		for(int i = 0;i < Rider.listOfRiders.size(); i++) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		
		boolean validName = false;

		for(int i = 0;i < Race.listOfRaces.size(); i++) {
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
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
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
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
}
