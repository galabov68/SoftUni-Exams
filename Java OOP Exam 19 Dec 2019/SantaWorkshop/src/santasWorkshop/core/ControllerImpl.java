package santasWorkshop.core;

import santasWorkshop.models.*;
import santasWorkshop.repositories.DwarfRepository;
import santasWorkshop.repositories.PresentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static santasWorkshop.common.ConstantMessages.*;
import static santasWorkshop.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private DwarfRepository dwarfRepository;
    private PresentRepository presentRepository;
    private Workshop workshop;

    public ControllerImpl() {
        dwarfRepository = new DwarfRepository();
        presentRepository = new PresentRepository();
        workshop = new WorkshopImpl();
    }

    @Override
    public String addDwarf(String type, String dwarfName) {
        Dwarf dwarf;
        if (type.equals("Happy")) {
            dwarf = new Happy(dwarfName);
        } else if (type.equals("Sleepy")) {
            dwarf = new Sleepy(dwarfName);
        } else {
            throw new IllegalArgumentException(DWARF_TYPE_DOESNT_EXIST);
        }

        dwarfRepository.add(dwarf);
        return String.format(ADDED_DWARF, type, dwarfName);
    }

    @Override
    public String addInstrumentToDwarf(String dwarfName, int power) {
        Dwarf dwarf = dwarfRepository.findByName(dwarfName);
        if (dwarf == null) {
            throw new IllegalArgumentException(DWARF_DOESNT_EXIST);
        }

        Instrument instrument = new InstrumentImpl(power);
        dwarf.addInstrument(instrument);
        return String.format(SUCCESSFULLY_ADDED_INSTRUMENT_TO_DWARF, power, dwarfName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        presentRepository.add(present);
        return String.format(SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        Present present = presentRepository.findByName(presentName);

        List<Dwarf> readyDwarfs = dwarfRepository.getModels().stream()
                .filter(dwarf -> dwarf.getEnergy() > 50)
                .collect(Collectors.toList());

        if (readyDwarfs.isEmpty()) {
            throw new IllegalArgumentException(NO_DWARF_READY);
        }

        for (Dwarf readyDwarf : readyDwarfs) {
            workshop.craft(present, readyDwarf);

            if (present.isDone()) {
                break;
            }
        }

        int brokenInstruments = 0;

        for (Dwarf readyDwarf : readyDwarfs) {
            brokenInstruments += readyDwarf.getInstruments().stream()
                    .filter(Instrument::isBroken)
                    .count();
        }

        String doneOrNotDone;

        if (present.isDone()) {
            doneOrNotDone = "done";
        } else {
            doneOrNotDone = "not done";
        }

        return String.format(PRESENT_DONE, presentName, doneOrNotDone) + String.format(COUNT_BROKEN_INSTRUMENTS, brokenInstruments);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        long donePresents = presentRepository.getModels().stream()
                .filter(Present::isDone)
                .count();
        sb.append(String.format("%d presents are done!%n", donePresents));
        sb.append(String.format("Dwarf info:%n"));

        dwarfRepository.getModels().forEach(dwarf -> {
            sb.append(String.format("Name: %s%n", dwarf.getName()));
            sb.append(String.format("Energy: %d%n", dwarf.getEnergy()));
            long notBrokenInstruments = dwarf.getInstruments().stream().
                    filter(instrument -> !instrument.isBroken())
                    .count();
            sb.append(String.format("Instruments %d not broken left%n", notBrokenInstruments));
        });

        return String.valueOf(sb);
    }
}
