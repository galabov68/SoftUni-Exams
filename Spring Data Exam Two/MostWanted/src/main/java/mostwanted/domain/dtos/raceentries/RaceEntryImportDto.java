package mostwanted.domain.dtos.raceentries;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "race-entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportDto {
    @XmlAttribute(name = "has-finished")
    private boolean hasFinished;
    @XmlAttribute(name = "finish-time")
    private double finishTime;
    @XmlAttribute(name = "car-id")
    private int carId;
    @XmlElement
    private String racer;

    public RaceEntryImportDto() {
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getRacer() {
        return racer;
    }

    public void setRacer(String racer) {
        this.racer = racer;
    }
}
