import Javalette.Absyn.Init;
import Javalette.Absyn.Item;
import Javalette.Absyn.NoInit;
import Javalette.Absyn.Type;
import javafx.util.Pair;

/**
 * Created by sctwyn on 4/2/17.
 */
public class ItemTCVisitor implements Item.Visitor<Type, Env> {
    public Type visit(Item p, Env env) {
        if (p instanceof NoInit) {
            return visit((NoInit)p, env);
        } if (p instanceof Init) {
            return visit((Init)p, env);
        }
        throw new TypeException("Unknown instance type of Item");
    }

    @Override
    public Type visit(NoInit p, Env env) {
        if (null != env.lookupVarInCurrentCtx(p.ident_)) {
            throw new TypeException("Variable " + p.ident_ + " already defined in this scope");
        }
        env.newVar(p.ident_, env.getCurrentVarType(), null);
        return env.getCurrentVarType();
    }

    @Override
    public Type visit(Init p, Env env) {
        if (null != env.lookupVarInCurrentCtx(p.ident_)) {
            throw new TypeException("Variable " + p.ident_ + " already defined in this scope");
        }
        Type type = new ExprTCVisitor().visit(p.expr_, env);
        if (! env.getCurrentVarType().getClass().equals(type.getClass())) {
            throw new TypeException("variable assign mismatch type value");
        }
        env.newVar(p.ident_, env.getCurrentVarType(), null);
        return env.getCurrentVarType();
    }
}
