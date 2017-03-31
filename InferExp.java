import Javalette.Absyn.*;

/**
 * Created by sctwyn on 3/31/17.
 */
public class InferExp implements Expr.Visitor<Type, Env> {

    public Type visit(Expr p, Env env) {
        if (p instanceof EVar) {
            return visit((EVar) p, env);
        } else if (p instanceof ELitInt) {
            return visit((ELitInt) p, env);
        } else if (p instanceof ELitDoub) {
            return visit((ELitDoub) p, env);
        } else if (p instanceof ELitTrue) {
            return visit((ELitTrue) p, env);
        } else if (p instanceof ELitFalse) {
            return visit((ELitFalse) p, env);
        } else if (p instanceof EApp) {
            return visit((EApp) p, env);
        } else if (p instanceof EString) {
            return visit((EString) p, env);
        } else if (p instanceof Neg) {
            return visit((Neg) p, env);
        } else if (p instanceof Not) {
            return visit((Not) p, env);
        } else if (p instanceof EMul) {
            return visit((EMul) p, env);
        } else if (p instanceof EAdd) {
            return visit((EAdd) p, env);
        } else if (p instanceof ERel) {
            return visit((ERel) p, env);
        } else if (p instanceof EAnd) {
            return visit((EAnd) p, env);
        } else if (p instanceof EOr) {
            return visit((EOr) p, env);
        }
        throw new TypeException("unknow expr type");
    }

    @Override
    public Type visit(EVar p, Env arg) {
        return null;
    }

    @Override
    public Type visit(ELitInt p, Env arg) {
        return null;
    }

    @Override
    public Type visit(ELitDoub p, Env arg) {
        return null;
    }

    @Override
    public Type visit(ELitTrue p, Env arg) {
        return null;
    }

    @Override
    public Type visit(ELitFalse p, Env arg) {
        return null;
    }

    @Override
    public Type visit(EApp p, Env arg) {
        return null;
    }

    @Override
    public Type visit(EString p, Env arg) {
        return null;
    }

    @Override
    public Type visit(Neg p, Env arg) {
        return null;
    }

    @Override
    public Type visit(Not p, Env arg) {
        return null;
    }

    @Override
    public Type visit(EMul p, Env arg) {
        return null;
    }

    @Override
    public Type visit(EAdd p, Env arg) {
        return null;
    }

    @Override
    public Type visit(ERel p, Env arg) {
        return null;
    }

    @Override
    public Type visit(EAnd p, Env arg) {
        return null;
    }

    @Override
    public Type visit(EOr p, Env arg) {
        return null;
    }
}
