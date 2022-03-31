package cycling;

import java.util.ArrayList;

/**
 * A class designed for design and management of segments.
 */
public class Segment {
  //Instance variables
  private int segmentID;
  private int stageID;
  private Double location;
  private SegmentType type;
  private Double averageGradient;
  private Double length;

  //Static variables
  public static ArrayList<Segment> listOfSegments = new ArrayList<>();

  //Getters
  public int getSegmentID() {
    return segmentID;
  }

  public int getStageID() {
    return stageID;
  }

  public Double getLocation() {
    return location;
  }

  public SegmentType getType() {
    return type;
  }

  public Double getAverageGradient() {
    return averageGradient;
  }

  public Double getLength() {
    return length;
  }

  /**
   * A default constructor for segment.
   */
  public Segment() {
    segmentID = 0;
    stageID = 0;
    location = 0.0;
    averageGradient = 0.0;
    length = 0.0;
  }


  /**
   * A constructor for a categorized climb segment.
   * @param stageID An unique id of a stage to add the instance of segment too.
   * @param location The kilometre location where the climb finishes within the stage.
   * @param type The category of the climb.
   * @param averageGradient The average gradient for the climb.
   * @param length The length of the climb in kilometre.
   */
  public Segment(int stageID, Double location, SegmentType type, Double averageGradient,
      Double length) {
        
    // Set Instance attributes
    this.stageID = stageID;
    this.location = location;
    this.type = type;
    this.averageGradient = averageGradient;
    this.length = length;

    if (listOfSegments.isEmpty()) {
      this.segmentID = 1;
    } else {
      // Set ID 1 more than last segment
      this.segmentID = listOfSegments.get(listOfSegments.size() - 1).getSegmentID() + 1;
    }
  }

  /**
   * A constructor for a intermediate sprint segment.
   * @param stageID An unique id of a stage to add the instance of segment too.
   * @param location The kilometre location where the sprint finishes within the stage.
   */
  public Segment(int stageID, Double location) {
        
    // Set instance attributes
    this.stageID = stageID;
    this.location = location;
    this.type = SegmentType.SPRINT;

    if (listOfSegments.isEmpty()) {
      this.segmentID = 1;
    } else {
      // Set ID 1 more than last segment
      this.segmentID = listOfSegments.get(listOfSegments.size() - 1).getSegmentID() + 1;
    }
  }
}