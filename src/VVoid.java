/**
 * Created by sctwyn on 4/2/17.
 */
public class VVoid extends Value<Void> {

    @Override
    public boolean equals(Object o) {
        return o instanceof VVoid;
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
        return !(o instanceof VVoid);
    }
}
