import Javalette.Absyn.*;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by sctwyn on 4/2/17.
 */
public class ExprTCVisitor implements Javalette.Absyn.Expr.Visitor<Type, Env> {
    public Type visit(Expr exp, Env env) {
        if ( exp instanceof EVar) {
            return visit((EVar)exp, env);
        } else if ( exp instanceof ELitInt) {
            return visit((ELitInt) exp, env);
        } else if ( exp instanceof ELitDoub) {
            return visit((ELitDoub) exp, env);
        } else if ( exp instanceof ELitTrue) {
            return visit((ELitTrue) exp, env);
        } else if ( exp instanceof ELitFalse) {
            return visit((ELitFalse)exp, env);
        } else if ( exp instanceof EApp) {
            return visit((EApp)exp, env);
        } else if ( exp instanceof EString) {
            return visit((EString)exp, env);
        } else if ( exp instanceof Neg) {
            return visit((Neg)exp, env);
        } else if ( exp instanceof Not) {
            return visit((Not)exp, env);
        } else if ( exp instanceof EMul) {
            return visit((EMul)exp, env);
        } else if ( exp instanceof EAdd) {
            return visit((EAdd)exp, env);
        } else if ( exp instanceof ERel) {
            return visit((ERel)exp, env);
        } else if ( exp instanceof EAnd) {
            return visit((EAnd)exp, env);
        } else if ( exp instanceof EOr) {
            return visit((EOr)exp, env);
        }
        throw new TypeException("Unknow type of expression");
    }

    @Override
    public Type visit(EVar exp, Env env) {
        return env.lookupVar(exp.ident_) != null ? env.lookupVar(exp.ident_).getKey() : null;
    }

    @Override
    public Type visit(ELitInt exp, Env env) {
        return new Int();
    }

    @Override
    public Type visit(ELitDoub exp, Env env) {
        return new Doub();
    }

    @Override
    public Type visit(ELitTrue exp, Env env) {
        return new Bool();
    }

    @Override
    public Type visit(ELitFalse exp, Env env) {
        return new Bool();
    }

    private void checkType(Type t1, Type t2) {
        if(t1 != null && !t1.equals(t2)) {
            throw new TypeException("Expr: Expecting type " + t1 + " , got " + t2 + ".");
        }
    }

    @Override
    public Type visit(EApp exp, Env env) {
        FnDef funType = env.lookupFun(exp.ident_);
        if (funType == null)
            throw new TypeException("Refering undefined function" + exp.ident_);
        if (funType.listarg_.size() != exp.listexpr_.size())
            throw new TypeException("Unmatched arguments provide");
        int i = 0;
        for (Expr expr: exp.listexpr_) {
            Argument t = (Argument)funType.listarg_.get(i);
            checkType(t.type_, visit(expr, env));
            i++;
        }
        return funType.type_;
    }

    @Override
    public Type visit(EString exp, Env env) {
        return new Str();
    }

    @Override
    public Type visit(Neg exp, Env env) {
        Type expType = visit(exp.expr_, env);
        if (!(expType instanceof Int) && !(expType instanceof Doub))
            throw new TypeException("applying neg operator on none supported type");
        return expType;
    }

    @Override
    public Type visit(Not exp, Env env) {
        checkType(new Bool(), visit(exp.expr_, env));
        return new Bool();
    }

    @Override
    public Type visit(EMul exp, Env env) {
        Type exp1Type = visit(exp.expr_1, env);
        Type exp2Type = visit(exp.expr_2, env);
        if (exp.mulop_ instanceof Mod && !exp1Type.equals(new Int()))
            throw new TypeException("Applying Mod operation on unsupport type");

        checkType(exp1Type, exp2Type);
        if (!exp1Type.equals(new Int()) && !exp2Type.equals(new Doub()))
            throw new TypeException("Multiplying unsupport type");
        return exp1Type;
    }

    @Override
    public Type visit(EAdd exp, Env env) {
        Type exp1Type = visit(exp.expr_1, env);
        Type exp2Type = visit(exp.expr_2, env);
        checkType(exp1Type, exp2Type);
        if (!exp1Type.equals(new Int()) && ! exp2Type.equals(new Doub()))
            throw new TypeException("Adding unsupport type");
        return exp1Type;
    }

    @Override
    public Type visit(ERel exp, Env env) {
        Type exp1Type = visit(exp.expr_1, env);
        Type exp2Type = visit(exp.expr_2, env);
        checkType(exp1Type, exp2Type);
        if (!exp1Type.equals(new Int())
                && !exp2Type.equals(new Doub())
                && !exp1Type.equals(new Str())
                && !exp1Type.equals(new Bool()))
            throw new TypeException("Comparing unsupport type");
        return new Bool();
    }

    @Override
    public Type visit(EAnd exp, Env env) {
        Type exp1Type = visit(exp.expr_1, env);
        Type exp2Type = visit(exp.expr_2, env);
        checkType(exp1Type, exp2Type);
        if (!exp1Type.equals(new Bool()))
            throw new TypeException("Try to apply And operation unsupported type");
        return exp1Type;
    }

    @Override
    public Type visit(EOr exp, Env env) {
        Type exp1Type = visit(exp.expr_1, env);
        Type exp2Type = visit(exp.expr_2, env);
        checkType(exp1Type, exp2Type);
        if (!exp1Type.equals(new Bool()))
            throw new TypeException("Try to apply Or operation unsupported type");
        return exp1Type;
    }
}
