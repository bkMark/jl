import Javalette.Absyn.*;
import sun.awt.SunHints;

/**
 * Created by scott on 2017-04-04.
 */
public class StmtInterpVisitor implements Javalette.Absyn.Stmt.Visitor<Value, Env> {
    public Value visit(Stmt stmt, Env env) {
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

        throw new InterpreterException("Unkown instance type of Statement");
    }

    @Override
    public Value visit(Empty stmt, Env env) {
        return new VVoid();
    }

    @Override
    public Value visit(BStmt stmt, Env env) {
        env.enterCtx();
        for (Stmt stmt1: ((Block) stmt.blk_).liststmt_) {
            new StmtInterpVisitor().visit(stmt1, env);
            if (env.get_retVal() != null) {
                env.leaveCtx();
                return new VVoid();
            }
        }
        env.leaveCtx();
        return new VVoid();
    }

    @Override
    public Value visit(Decl stmt, Env env) {
        env.setCurrentVarType(stmt.type_);
        for (Item item: stmt.listitem_) {
            new ItemInterpVisitor().visit(item, env);
        }
        env.setCurrentVarType(null);

        String id = stmt.listitem_.getLast() instanceof Init ? ((Init)stmt.listitem_.getLast()).ident_ : ((NoInit)stmt.listitem_.getLast()).ident_;
        return env.lookupVar(id).getValue();
    }

    @Override
    public Value visit(Ass stmt, Env env) {
        Type type = env.lookupVar(stmt.ident_).getKey();
        Value value = new ExprInterpVisitor().visit(stmt.expr_, env);
        env.updateVar(stmt.ident_, type, value);
        return value;
    }

    @Override
    public Value visit(Incr stmt, Env env) {
        Type type = env.lookupVar(stmt.ident_).getKey();
        Value value = env.lookupVar(stmt.ident_).getValue();
        if (value instanceof VInt) {
            env.updateVar(stmt.ident_, type, new VInt(((VInt) value).value + 1));
        } else if (value instanceof VDoub) {
            env.updateVar(stmt.ident_, type, new VDoub(((VDoub) value).value + 1));
        }
        return value;
    }

    @Override
    public Value visit(Decr stmt, Env env) {
        Type type = env.lookupVar(stmt.ident_).getKey();
        Value value = env.lookupVar(stmt.ident_).getValue();
        if (value instanceof VInt) {
            env.updateVar(stmt.ident_, type, new VInt(((VInt) value).value - 1));
        } else if (value instanceof VDoub) {
            env.updateVar(stmt.ident_, type, new VDoub(((VDoub) value).value - 1));
        }
        return value;

    }

    @Override
    public Value visit(Ret stmt, Env env) {
        Value val = new ExprInterpVisitor().visit(stmt.expr_, env);
        env.set_retVal(val);
        return val;
    }

    @Override
    public Value visit(VRet stmt, Env env) {
        return new VVoid();
    }

    @Override
    public Value visit(Cond stmt, Env env) {
        Value value = new ExprInterpVisitor().visit(stmt.expr_, env);
        if (value.equals(new VBool(true))) {
            return new StmtInterpVisitor().visit(stmt.stmt_, env);
        }

        return new VVoid();
    }

    @Override
    public Value visit(CondElse stmt, Env env) {
        Value value = new ExprInterpVisitor().visit(stmt.expr_, env);
        if (value.equals(new VBool(true))) {
            return new StmtInterpVisitor().visit(stmt.stmt_1, env);
        } else {
            return new StmtInterpVisitor().visit(stmt.stmt_2, env);
        }
    }

    @Override
    public Value visit(While stmt, Env env) {
        while (new ExprInterpVisitor().visit(stmt.expr_, env).equals(new VBool(true))) {
            env.enterCtx();
            new StmtInterpVisitor().visit(stmt.stmt_, env);
            if (env.get_retVal() != null) {
                env.leaveCtx();
                break;
            }
            env.leaveCtx();
        }

        return new VVoid();
    }


    @Override
    public Value visit(SExp stmt, Env env) {
        return new ExprInterpVisitor().visit(stmt.expr_, env);
    }
}
