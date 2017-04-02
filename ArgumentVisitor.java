import Javalette.Absyn.Argument;

/**
 * Created by sctwyn on 4/1/17.
 */
public class ArgumentVisitor implements Javalette.Absyn.Arg.Visitor<Env, Env> {
    @Override
    public Env visit(Argument arg, Env env) {
        env.updateVar(arg.ident_, arg.type_);
        return env;
    }
}
