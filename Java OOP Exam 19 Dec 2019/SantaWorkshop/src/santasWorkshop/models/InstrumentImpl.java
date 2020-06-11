package santasWorkshop.models;

import static santasWorkshop.common.ExceptionMessages.*;

public class InstrumentImpl implements Instrument {

    private int power;

    public InstrumentImpl(int power) {
        this.setPower(power);
    }

    @Override
    public int getPower() {
        return this.power;
    }

    private void setPower(int power) {
        if (power < 0) {
            throw new IllegalArgumentException(INSTRUMENT_POWER_LESS_THAN_ZERO);
        }

        this.power = power;
    }

    @Override
    public void use() {
        this.setPower(Math.max(this.power - 10, 0));
    }

    @Override
    public boolean isBroken() {
        return this.power == 0;
    }
}
