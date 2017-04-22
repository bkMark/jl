
/**
 * Created by sctwyn on 4/2/17.
 */
public class VDoub extends Value<Double> {
    public VDoub(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof VDoub && this.value.equals(((VDoub) o).value);
    }

    @Override
    public boolean gth(Object o) {
        return o instanceof VDoub && this.value > ((VDoub) o).value;
    }

    @Override
    public boolean geq(Object o) {
        return o instanceof VDoub && this.value >= ((VDoub) o).value;
    }

    @Override
    public boolean lth(Object o) {
        return o instanceof VDoub && this.value < ((VDoub) o).value;
    }

    @Override
    public boolean leq(Object o) {
        return o instanceof VDoub && this.value <= ((VDoub) o).value;
    }

    @Override
    public boolean neq(Object o) {
        return o instanceof VDoub && ! this.value.equals(((VDoub) o).value);
    }
}
