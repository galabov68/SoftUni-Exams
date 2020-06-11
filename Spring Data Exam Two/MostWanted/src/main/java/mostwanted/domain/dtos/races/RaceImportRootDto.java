package mostwanted.domain.dtos.races;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportRootDto {
    @XmlElement(name = "race")
    private List<RaceImportDto> raceImportDto;

    public RaceImportRootDto() {
    }

    public List<RaceImportDto> getRaceImportDto() {
        return raceImportDto;
    }

    public void setRaceImportDto(List<RaceImportDto> raceImportDto) {
        this.raceImportDto = raceImportDto;
    }
}
