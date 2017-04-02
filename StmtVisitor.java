import Javalette.Absyn.*;
import Javalette.Absyn.Void;

/**
 * Created by sctwyn on 4/2/17.
 */
public class StmtVisitor implements Stmt.Visitor<Env, Env> {
    
    @Override
    public Env visit(Empty stmt, Env env) {
        return env;
    }

    @Override
    public Env visit(BStmt stmt, Env env) {
        env.enterCtx();
        new BolckVisitor().visit((Block) stmt.blk_, env);
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(Decl stmt, Env env) {
        env.setCurrentVarType(stmt.type_);
        for(Item item: stmt.listitem_) {
            item.accept(new ItemVisitor(), env);
        }
        env.setCurrentVarType(null);
        return env;
    }

    @Override
    public Env visit(Ass stmt, Env env) {
        Type idType = env.lookupVar(stmt.ident_);
        checkType(idType, stmt.expr_.accept(new ExprVisitor(), env));
        return env;
    }

    @Override
    public Env visit(Incr stmt, Env env) {
        Type idType = env.lookupVar(stmt.ident_);
        if (! (idType instanceof Doub) &&
                ! (idType instanceof Int))
            throw new TypeException("Applying Decr operation on unsupported type");
        return env;
    }

    @Override
    public Env visit(Decr stmt, Env env) {
        Type idType = env.lookupVar(stmt.ident_);
        if (! (idType instanceof Doub) &&
                ! (idType instanceof Int))
            throw new TypeException("Applying Decr operation on unsupported type");
        return env;
    }

    @Override
    public Env visit(Ret stmt, Env env) {
        checkType(env.getReturnType(), stmt.expr_.accept(new ExprVisitor(), env));
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
        checkType(stmt.expr_.accept(new ExprVisitor(), env), new Bool());
        env.enterCtx();
        stmt.stmt_.accept(new StmtVisitor(), env);
        env.leaveCtx();
        return null;
    }

    @Override
    public Env visit(CondElse stmt, Env env) {
        checkType(stmt.expr_.accept(new ExprVisitor(), env), new Bool());
        env.enterCtx();
        stmt.stmt_1.accept(new StmtVisitor(), env);
        env.leaveCtx();
        env.enterCtx();
        stmt.stmt_2.accept(new StmtVisitor(), env);
        env.leaveCtx();
        return null;
    }

    @Override
    public Env visit(While stmt, Env env) {
        env.enterCtx();
        stmt.stmt_.accept(new StmtVisitor(), env);
        env.leaveCtx();
        return env;
    }

    @Override
    public Env visit(SExp stmt, Env env) {
        stmt.expr_.accept(new ExprVisitor(), env);
        return env;
    }
}
