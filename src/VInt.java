import java.util.Objects;

/**
 * Created by sctwyn on 4/2/17.
 */
public class VInt extends Value<Integer> {
    public VInt(Integer value){ this.value = value; }

    @Override
    public boolean equals(Object o) {
        return o instanceof VInt && this.value.equals(((VInt) o).value);
    }

    @Override
    public boolean gth(Object o) {
        return o instanceof VInt && this.value > ((VInt) o).value;
    }

    @Override
    public boolean geq(Object o) {
        return o instanceof VInt && this.value >= ((VInt) o).value;
    }

    @Override
    public boolean lth(Object o) {
        return o instanceof VInt && this.value < ((VInt) o).value;
    }

    @Override
    public boolean leq(Object o) {
        return o instanceof VInt && this.value <= ((VInt) o).value;
    }

    @Override
    public boolean neq(Object o) {
        return o instanceof VInt && ! this.value.equals(((VInt) o).value);
    }
}
