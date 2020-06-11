package mostwanted.domain.dtos.raceentries;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportRootDto {
    @XmlElement(name = "race-entry")
    private List<RaceEntryImportDto> raceEntryImportDto;

    public RaceEntryImportRootDto() {
    }

    public List<RaceEntryImportDto> getRaceEntryImportDto() {
        return raceEntryImportDto;
    }

    public void setRaceEntryImportDto(List<RaceEntryImportDto> raceEntryImportDto) {
        this.raceEntryImportDto = raceEntryImportDto;
    }
}