import Javalette.Absyn.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by scott on 2017-04-05.
 */
public class ItemInterpVisitor implements Item.Visitor<Value, Env> {
    public Value visit(Item p, Env env) {
        if (p instanceof NoInit) {
            return visit((NoInit)p, env);
        } if (p instanceof Init) {
            return visit((Init)p, env);
        }
        throw new TypeException("Unknown instance type of Item");
    }

    @Override
    public Value visit(NoInit p, Env env) {
        if (null != env.lookupVarInCurrentCtx(p.ident_)) {
            new TypeException("Variable " + p.ident_ + " already defined in this scope");
        }
        // FIXME: no init
        Value val = null;
        if (env.getCurrentVarType() instanceof Javalette.Absyn.Void) {
            val = new VVoid();
        } else if (env.getCurrentVarType() instanceof Int) {
            val = new VInt(0);
        } else if (env.getCurrentVarType() instanceof Doub) {
            val = new VDoub(new Double(0));
        } else if (env.getCurrentVarType() instanceof Str) {
            val = new VStr("");
        } else if (env.getCurrentVarType() instanceof Bool) {
            val = new VBool(false);
        }


        env.newVar(p.ident_, env.getCurrentVarType(), val);
        return val;
    }

    @Override
    public Value visit(Init p, Env env) {
        if (null != env.lookupVarInCurrentCtx(p.ident_)) {
            new TypeException("Variable " + p.ident_ + " already defined in this scope");
        }
        Value value = new ExprInterpVisitor().visit(p.expr_, env);
        env.newVar(p.ident_, env.getCurrentVarType(), value);
        return value;
    }
}
