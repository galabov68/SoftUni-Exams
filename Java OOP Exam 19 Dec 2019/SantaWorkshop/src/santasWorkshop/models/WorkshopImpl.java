package santasWorkshop.models;

public class WorkshopImpl implements Workshop {

    private Present present;
    private Dwarf dwarf;

    @Override
    public void craft(Present present, Dwarf dwarf) {
        if (dwarf.canWork()) {
            for (Instrument instrument : dwarf.getInstruments()) {
                while (!instrument.isBroken() && dwarf.canWork() && !present.isDone()) {
                    dwarf.work();
                    instrument.use();
                    present.getCrafted();
                }

                if (!dwarf.canWork()) {
                    break;
                }

                if (present.isDone()) {
                    break;
                }
            }
        }
    }
}
