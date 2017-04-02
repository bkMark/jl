import Javalette.Absyn.Init;
import Javalette.Absyn.Item;
import Javalette.Absyn.NoInit;
import Javalette.Absyn.Type;

/**
 * Created by sctwyn on 4/2/17.
 */
public class ItemVisitor implements Item.Visitor<Type, Env> {
    @Override
    public Type visit(NoInit p, Env env) {
        if (null != env.lookupVarInCurrentCtx(p.ident_)) {
            new TypeException("Variable " + p.ident_ + " already defined in this scope");
        }
        env.updateVar(p.ident_, env.getCurrentVarType());
        return env.getCurrentVarType();
    }

    @Override
    public Type visit(Init p, Env env) {
        if (null != env.lookupVarInCurrentCtx(p.ident_)) {
            new TypeException("Variable " + p.ident_ + " already defined in this scope");
        }
        env.updateVar(p.ident_, env.getCurrentVarType());
        return env.getCurrentVarType();
    }
}
