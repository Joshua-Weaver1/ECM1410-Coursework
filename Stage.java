package cycling;

public class Stage {
    private int raceID;
    private int stageID;
    private SegmentType type;
    private Double averageGradient;
    private Double length;
    private double location;

    private void setRace(int raceID) {
        this.raceID = raceID;
    }

    private void setStageID(int stageID) {
        this.stageID = stageID;
    }

    private void setType(SegmentType type) {
        this.type = type;
    }

    private void setAverageGradient(Double averageGradient) {
        this.averageGradient = averageGradient;
    }

    private void setLength(Double length) {
        this.length = length;
    }

    private void setLocation(double location) {
        this.location = location;
    }
}
