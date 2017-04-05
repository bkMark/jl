/**
 * Created by sctwyn on 4/2/17.
 */
public abstract class Value<T> {
    T value;
    public abstract boolean equals(Object o);
    public abstract boolean gth(Object o);
    public abstract boolean geq(Object o);
    public abstract boolean lth(Object o);
    public abstract boolean leq(Object o);
    public abstract boolean neq(Object o);
}

