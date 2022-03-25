package cycling;
import java.util.ArrayList;

public class Segment {
    private int segmentID;
    private int stageID;
    private Double location;
    private SegmentType type;
    private Double averageGradient;
    private Double length;

    public static ArrayList<Segment> listOfSegments = new ArrayList<>();


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

    public Segment() {
        segmentID = 0;
        stageID = 0;
        location = 0.0;
        averageGradient = 0.0;
        length = 0.0;
    }

    public Segment(int stageID, Double location, SegmentType type, Double averageGradient, Double length) {
        
        // Set Instance attributes
        this.stageID = stageID;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;

        if (listOfSegments.isEmpty()){
            this.segmentID = 1;
        }
        else{
            // Set ID 1 more than last rider
            this.segmentID = listOfSegments.get(listOfSegments.size() - 1).getSegmentID() + 1;
        }
    }

    public Segment(int stageID, Double location) {
        
        // Set Instance attributes
        this.stageID = stageID;
        this.location = location;
        this.type = SegmentType.SPRINT;

        if (listOfSegments.isEmpty()){
            this.segmentID = 1;
        }
        else{
            // Set ID 1 more than last rider
            this.segmentID = listOfSegments.get(listOfSegments.size() - 1).getSegmentID() + 1;
        }
    }
}