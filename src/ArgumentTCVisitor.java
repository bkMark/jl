import Javalette.Absyn.Argument;

/**
 * Created by sctwyn on 4/1/17.
 */
public class ArgumentTCVisitor implements Javalette.Absyn.Arg.Visitor<Env, Env> {
    @Override
    public Env visit(Argument arg, Env env) {
        env.newVar(arg.ident_, arg.type_, null);
        return env;
    }
}
