public class VBool extends Value<Boolean> {
    public VBool (boolean d) {value = d;}

    @Override
    public boolean equals(Object o) {
        return (o instanceof VBool) && this.value == ((VBool) o).value;
    }

    @Override
    public boolean gth(Object o) {
        return false;
    }

    @Override
    public boolean geq(Object o) {
        return false;
    }

    @Override
    public boolean lth(Object o) {
        return false;
    }

    @Override
    public boolean leq(Object o) {
        return false;
    }

    @Override
    public boolean neq(Object o) {
        return (o instanceof VBool) && this.value != ((VBool) o).value;
    }
}

