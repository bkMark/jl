import Javalette.Absyn.*;
import Javalette.Absyn.Void;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by scott on 2017-04-04.
 */
public class ExprInterpVisitor implements Javalette.Absyn.Expr.Visitor<Value, Env> {
    public Value visit(Expr expr, Env env) {
        if (expr instanceof EVar) {
            return visit((EVar) expr, env);
        } else if (expr instanceof ELitInt) {
            return visit((ELitInt) expr, env);
        } else if (expr instanceof ELitDoub) {
            return visit((ELitDoub) expr, env);
        } else if (expr instanceof ELitTrue) {
            return visit((ELitTrue) expr, env);
        } else if (expr instanceof ELitFalse) {
            return visit((ELitFalse) expr, env);
        } else if (expr instanceof EApp) {
            return visit((EApp) expr, env);
        } else if (expr instanceof EString) {
            return visit((EString) expr, env);
        } else if (expr instanceof Neg) {
            return visit((Neg) expr, env);
        } else if (expr instanceof Not) {
            return visit((Not) expr, env);
        } else if (expr instanceof EMul) {
            return visit((EMul) expr, env);
        } else if (expr instanceof EAdd) {
            return visit((EAdd) expr, env);
        } else if (expr instanceof ERel) {
            return visit((ERel) expr, env);
        } else if (expr instanceof EAnd) {
            return visit((EAnd) expr, env);
        } else if (expr instanceof EOr) {
            return visit((EOr) expr, env);
        }

        throw new InterpreterException("Unknow type if expr");
    }

    @Override
    public Value visit(EVar expr, Env env) {
        return env.lookupVar(expr.ident_).getValue();
    }

    @Override
    public Value visit(ELitInt expr, Env env) {
        return new VInt(expr.integer_);
    }

    @Override
    public Value visit(ELitDoub expr, Env env) {
        return new VDoub(expr.double_);
    }

    @Override
    public Value visit(ELitTrue expr, Env env) {
        return new VBool(true);
    }

    @Override
    public Value visit(ELitFalse expr, Env env) {
        return new VBool(false);
    }

    @Override
    public Value visit(EApp expr, Env env) {
        // before call the application, lets evaluate its arguments
        List<Value> values = new LinkedList<>();
        for (Expr iexpr : expr.listexpr_) {
            values.add(visit(iexpr, env));
        }

        if (expr.ident_.equals("printInt")) {
            System.out.println(values.get(0).value);
        } else if (expr.ident_.equals("printDouble")) {
            System.out.println(values.get(0).value);
        } else if (expr.ident_.equals("printString")) {
            System.out.println(values.get(0).value);
        } else if (expr.ident_.equals("readInt")) {
            return new VInt(env.getInput().nextInt());
        } else if (expr.ident_.equals("readDouble")) {
            return new VDoub(env.getInput().nextDouble());
        } else {
            //get function record from env
            String fId = expr.ident_;
            FnDef fun = env.lookupFun(fId);

            //build new environment for function execution
            //add the evaluated parameters to the function's execution environment
            //using the function declaration names from symbol table
            env.enterCtx();
            int i = 0;
            for(Arg arg : fun.listarg_){
                Argument argument = (Argument) arg;
                env.newVar(argument.ident_, argument.type_, values.get(i));
                i++;
            }

            for(Stmt s : fun.liststmt_){
                s.accept(new StmtInterpVisitor(), env);
                if(env.get_retVal()!=null)
                    break;
            }

            if (env.get_retVal() == null && !(fun.type_ instanceof Void))
                throw new TypeException("None void function needs to have a return value");

            env.leaveCtx();

            Value _retVal = env.get_retVal();
            env.set_retVal(null);
            return _retVal;
        }


        return null;
    }

    @Override
    public Value visit(EString expr, Env env) {
        return new VStr(expr.string_);
    }

    @Override
    public Value visit(Neg expr, Env env) {
        Value v = visit(expr.expr_, env);

        // Type Checked, there is only 2 type valid here
        if (v instanceof VInt) {
            return new VInt(-((VInt) v).value);
        } else {
            return new VDoub(-((VDoub) v).value);
        }
    }

    @Override
    public Value visit(Not expr, Env env) {
        VBool v = (VBool)visit(expr.expr_, env);
        return new VBool(!v.value);
    }

    @Override
    public Value visit(EMul expr, Env env) {
        Value v1 = visit(expr.expr_1, env);
        Value v2 = visit(expr.expr_2, env);

        // Type Checked, there is only 2 type valid here
        if (v1 instanceof VInt) {
            if (expr.mulop_ instanceof Times) {
                return new VInt(((VInt) v1).value * ((VInt) v2).value);
            } else if (expr.mulop_ instanceof Div) {
                return new VInt(((VInt) v1).value / ((VInt) v2).value);
            } else {
                return new VInt(((VInt) v1).value % ((VInt) v2).value);
            }
        } else {
            // no mod operation here, we have type checked
            if (expr.mulop_ instanceof Times) {
                return new VDoub(((VDoub) v1).value * ((VDoub) v2).value);
            } else {
                return new VDoub(((VDoub) v1).value / ((VDoub) v2).value);
            }
        }
    }

    @Override
    public Value visit(EAdd expr, Env env) {
        Value v1 = visit(expr.expr_1, env);
        Value v2 = visit(expr.expr_2, env);

        // Type Checked, there is only 2 type valid here
        if (v1 instanceof VInt) {
            if (expr.addop_ instanceof Plus)
                return new VInt(((VInt) v1).value + ((VInt) v2).value);
            else
                return new VInt(((VInt) v1).value - ((VInt) v2).value);
        } else {
            if (expr.addop_ instanceof Plus)
                return new VDoub(((VDoub) v1).value + ((VDoub) v2).value);
            else
                return new VDoub(((VDoub) v1).value - ((VDoub) v2).value);
        }
    }

    @Override
    public Value visit(ERel expr, Env env) {
        Value v1 = visit(expr.expr_1, env);
        Value v2 = visit(expr.expr_2, env);

        if (expr.relop_ instanceof LTH) {
            return new VBool(v1.lth(v2));
        } else if (expr.relop_ instanceof LE) {
            return new VBool(v1.leq(v2));
        } else if (expr.relop_ instanceof GTH) {
            return new VBool(v1.gth(v2));
        } else if (expr.relop_ instanceof GE) {
            return new VBool(v1.geq(v2));
        } else if (expr.relop_ instanceof EQU) {
            return new VBool(v1.equals(v2));
        } else { // ne
            return new VBool(v1.neq(v2));
        }
    }

    @Override
    public Value visit(EAnd expr, Env env) {
        Value v1 = visit(expr.expr_1, env);
        if (v1.equals(new VBool(false))) {
            return v1;
        } else {
            return visit(expr.expr_2, env);
        }
    }

    @Override
    public Value visit(EOr expr, Env env) {
        Value v1 = visit(expr.expr_1, env);
        if (v1.equals(new VBool(true))) {
            return v1;
        } else {
            return visit(expr.expr_2, env);
        }

    }
}
