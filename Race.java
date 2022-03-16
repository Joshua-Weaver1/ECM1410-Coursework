/**
 * We've created a package named cycling by using the
 * keyword 'package' which creates a new package to avoid
 * any name conflicts.
 */
package cycling;

import java.time.LocalDateTime;
/**
 * Here we have created a priavte class named RaceManagement
 * which contains attributes and set methods and get methods
 */
public class Race {
    private int raceID;
    private String raceName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;

    private void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    private void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setLength(double length) {
        this.length = length;
    }

    private void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    private void setType(StageType type) {
        this.type = type;
    }
}