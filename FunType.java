import Javalette.Absyn.ListArg;
import Javalette.Absyn.Type;

/**
 * Created by sctwyn on 4/1/17.
 */
public class FunType {
    private final Type rtnType;
    private final ListArg args;
    public FunType (Type type, ListArg args) {
        rtnType = type;
        this.args = args;
    }

    public Type getRtnType() {
        return rtnType;
    }

    public ListArg getArgs() {
        return args;
    }
}
