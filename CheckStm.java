import Javalette.Absyn.*;

/**
 * Created by sctwyn on 3/31/17.
 */
public class CheckStm implements Stmt.Visitor<Env, Env> {
    InferExp inferExp = new InferExp();

    public Env visit(Stmt p, Env env) {
        if (p instanceof Empty) {
            return visit((Empty)p, env);
        } else if (p instanceof BStmt) {
            return visit((BStmt)p, env);
        } else if (p instanceof Decl) {
            return visit((Decl)p, env);
        } else if (p instanceof Ass) {
            return visit((Ass)p, env);
        } else if (p instanceof Incr) {
            return visit((Incr)p, env);
        } else if (p instanceof Decr) {
            return visit((Decr)p, env);
        } else if (p instanceof Ret) {
            return visit((Ret)p, env);
        } else if (p instanceof VRet) {
            return visit((VRet)p, env);
        } else if (p instanceof Cond) {
            return visit((Cond)p, env);
        } else if (p instanceof CondElse) {
            return visit((CondElse)p, env);
        } else if (p instanceof While) {
            return visit((While)p, env);
        } else if (p instanceof SExp) {
            return visit((SExp)p, env);
        }

        throw new TypeException("Unexpected instance of stmt");
    }

    @Override
    public Env visit(Empty p, Env env) {
        return env;
    }


    @Override
    public Env visit(BStmt p, Env env) {
        env.enterCtx();
        // TODO: visit blk
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(Decl p, Env env) {
        for (Item id: p.listitem_) {
            env.updateVar(id.toString(), p.type_);
        }
        return env;
    }

    @Override
    public Env visit(Ass p, Env env) {
        if (!env.lookupVar(p.ident_).equals(inferExp.visit(p.expr_, env))) {
            throw new TypeException("Assign two different types.");
        }
        return env;
    }

    @Override
    public Env visit(Incr p, Env env) {
        if (! env.lookupVar(p.ident_).equals(new Int()) ||
                ! env.lookupVar(p.ident_).equals(new Doub())) {
            throw new TypeException("Increasing none numberic type");
        }
        return env;
    }

    @Override
    public Env visit(Decr p, Env env) {
        return env;
    }

    @Override
    public Env visit(Ret p, Env env) {
        Type ret = inferExp.visit(p.expr_, env);
        // TODO: check return type of the function
        return env;
    }

    @Override
    public Env visit(VRet p, Env env) {
        // TODO: check return type of the function
        return env;
    }

    @Override
    public Env visit(Cond p, Env env) {
        inferExp.visit(p.expr_, env);
        return env;
    }

    @Override
    public Env visit(CondElse p, Env env) {
        return null;
    }

    @Override
    public Env visit(While p, Env env) {
        return null;
    }

    @Override
    public Env visit(SExp p, Env env) {
        inferExp.visit(p.expr_, env);
        return env;
    }


}
