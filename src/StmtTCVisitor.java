import Javalette.Absyn.*;
import Javalette.Absyn.Void;
import javafx.util.Pair;

/**
 * Created by sctwyn on 4/2/17.
 */
public class StmtTCVisitor implements Stmt.Visitor<Env, Env> {
    public Env visit(Stmt stmt, Env env) {
        if (stmt instanceof Empty) {
            return visit((Empty) stmt, env);
        } else if (stmt instanceof BStmt) {
            return visit((BStmt) stmt, env);
        } else if (stmt instanceof Decl) {
            return visit((Decl) stmt, env);
        } else if (stmt instanceof Ass) {
            return visit((Ass) stmt, env);
        } else if (stmt instanceof Incr) {
            return visit((Incr) stmt, env);
        } else if (stmt instanceof Decr) {
            return visit((Decr) stmt, env);
        } else if (stmt instanceof Ret) {
            return visit((Ret) stmt, env);
        } else if (stmt instanceof VRet) {
            return visit((VRet) stmt, env);
        } else if (stmt instanceof Cond) {
            return visit((Cond) stmt, env);
        } else if (stmt instanceof CondElse) {
            return visit((CondElse) stmt, env);
        } else if (stmt instanceof While) {
            return visit((While) stmt, env);
        } else if (stmt instanceof SExp) {
            return visit((SExp) stmt, env);
        }

        throw new TypeException("Unkown instance type of Statement");
    }

    @Override
    public Env visit(Empty stmt, Env env) {
        return env;
    }

    @Override
    public Env visit(BStmt stmt, Env env) {
        env.enterCtx();
        new BolckTCVisitor().visit((Block) stmt.blk_, env);
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(Decl stmt, Env env) {
        env.setCurrentVarType(stmt.type_);
        for(Item item: stmt.listitem_) {
            new ItemTCVisitor().visit(item, env);
        }
        env.setCurrentVarType(null);
        return env;
    }

    @Override
    public Env visit(Ass stmt, Env env) {
        Pair<Type, Value> idType = env.lookupVar(stmt.ident_);
        if (idType == null)
            throw new TypeException("Undefined variable " + stmt.ident_);
        checkType(idType.getKey(), new ExprTCVisitor().visit(stmt.expr_, env));
        return env;
    }

    @Override
    public Env visit(Incr stmt, Env env) {
        Pair<Type, Value> idType = env.lookupVar(stmt.ident_);
        if (idType == null)
            throw new TypeException("Undefined variable " + stmt.ident_);
        if (! (idType.getKey() instanceof Doub) &&
                ! (idType.getKey() instanceof Int))
            throw new TypeException("Applying Decr operation on unsupported type");
        return env;
    }

    @Override
    public Env visit(Decr stmt, Env env) {
        Pair<Type, Value> idType = env.lookupVar(stmt.ident_);
        if (idType == null)
            throw new TypeException("Undefined variable " + stmt.ident_);
        if (! (idType.getKey() instanceof Doub) &&
                ! (idType.getKey() instanceof Int))
            throw new TypeException("Applying Decr operation on unsupported type");
        return env;
    }

    @Override
    public Env visit(Ret stmt, Env env) {
        checkType(env.getReturnType(), stmt.expr_.accept(new ExprTCVisitor(), env));
        return env;
    }

    private void checkType(Type t1, Type t2) {
        if(!t1.equals(t2)) {
            throw new TypeException("Stmt: Expecting type " + t1 + " , got " + t2 + ".");
        }
    }

    @Override
    public Env visit(VRet stmt, Env env) {
        checkType(env.getReturnType(), new Void());
        return env;
    }

    @Override
    public Env visit(Cond stmt, Env env) {
        checkType(stmt.expr_.accept(new ExprTCVisitor(), env), new Bool());
        env.enterCtx();
        stmt.stmt_.accept(new StmtTCVisitor(), env);
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(CondElse stmt, Env env) {
        checkType(stmt.expr_.accept(new ExprTCVisitor(), env), new Bool());
        env.enterCtx();
        stmt.stmt_1.accept(new StmtTCVisitor(), env);
        env.leaveCtx();
        env.enterCtx();
        stmt.stmt_2.accept(new StmtTCVisitor(), env);
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(While stmt, Env env) {
        env.enterCtx();
        stmt.stmt_.accept(new StmtTCVisitor(), env);
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(SExp stmt, Env env) {
        stmt.expr_.accept(new ExprTCVisitor(), env);
        return env;
    }
}
