package mostwanted.domain.dtos.races;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryImportRootDto {
    @XmlElement(name = "entry")
    private List<EntryImportDto> entryImportDtos;

    public EntryImportRootDto() {
    }

    public List<EntryImportDto> getEntryImportDtos() {
        return entryImportDtos;
    }

    public void setEntryImportDtos(List<EntryImportDto> entryImportDtos) {
        this.entryImportDtos = entryImportDtos;
    }
}
