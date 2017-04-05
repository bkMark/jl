
/**
 * Created by sctwyn on 4/2/17.
 */
public class VStr extends Value<String> {
    public VStr (String val) { value = val; }

    @Override
    public boolean equals(Object o) {
        return (o instanceof VStr) && this.value.equals(((VStr) o).value);
    }

    @Override
    public boolean gth(Object o) {
        return (o instanceof VStr) && this.value.compareTo(((VStr) o).value) > 0;
    }

    @Override
    public boolean geq(Object o) {
        return (o instanceof VStr) && this.value.compareTo(((VStr) o).value) >= 0;
    }

    @Override
    public boolean lth(Object o) {
        return (o instanceof VStr) && this.value.compareTo(((VStr) o).value) < 0;
    }

    @Override
    public boolean leq(Object o) {
        return (o instanceof VStr) && this.value.compareTo(((VStr) o).value) <= 0;
    }

    @Override
    public boolean neq(Object o) {
        return (o instanceof VStr) && !this.value.equals(((VStr) o).value);
    }
}
