import Javalette.Absyn.*;
import Javalette.Absyn.Void;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by sctwyn on 4/1/17.
 */
public class Env {
    public HashMap<String, FnDef> sig;
    public LinkedList<HashMap<String, Pair<Type, Value>>> ctx;
    private Type returnType;
    private Type currentVarType;
    private Scanner input;

    public Value get_retVal() {
        return _retVal;
    }

    public void set_retVal(Value _retVal) {
        this._retVal = _retVal;
    }

    private Value _retVal;

    public Env() {
        this.sig = new HashMap<>();
        this.ctx = new LinkedList<>();
        this.returnType = null;
        this.currentVarType = null;
        this._retVal = null;

        ListArg printIntArgs = new ListArg();
        printIntArgs.add(new Argument(new Int(), "x"));
        sig.put("printInt", new FnDef(new Javalette.Absyn.Void(), "printInt", printIntArgs, null));

        ListArg printDoubleArgs = new ListArg();
        printDoubleArgs.add(new Argument(new Doub(), "x"));
        sig.put("printDouble", new FnDef(new Void(), "printDouble", printDoubleArgs, null));

        ListArg printStringArgs = new ListArg();
        printStringArgs.add(new Argument(new Str(), "x"));
        sig.put("printString", new FnDef(new Void(), "printString", printStringArgs, null));

        // TODO:
        //env.sig.put("printElem");

        sig.put("readInt", new FnDef(new Int(), "readInt", new ListArg(), null));
        sig.put("readDouble", new FnDef(new Doub(), "readInt", new ListArg(), null));

    }

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }


    public void enterCtx() {
        ctx.addFirst(new HashMap<String, Pair<Type, Value>>());
    }

    public void leaveCtx() {
        ctx.removeFirst();
    }

    public void updateFun(String id, FnDef fnDef) {
        sig.put(id, fnDef);
    }

    public void newVar(String id, Type type, Value value) {
        Map<String, Pair<Type, Value>> currentCtx = ctx.getFirst();
        currentCtx.put(id, new Pair<>(type, value));
    }

    public void updateVar(String id, Type type, Value value) {
        // Lookup Var, then update the value in the right ctx
        for (Map<String, Pair<Type, Value>> context : ctx) {
            if (context.get(id) != null) {
                context.put(id, new Pair<>(type, value));
                break;
            }
        }
    }

    public Pair<Type, Value> lookupVar(String id) {
        for (Map<String, Pair<Type, Value>> cxt : ctx) {
            if (cxt.get(id) != null) {
                return cxt.get(id);
            }
        }
        return null;
    }

    public Pair<Type, Value> lookupVarInCurrentCtx(String id) {
        return ctx.getFirst().get(id);
    }

    public Type getCurrentVarType() {
        return currentVarType;
    }

    public void setCurrentVarType(Type type) {
        this.currentVarType = type;
    }

    public FnDef lookupFun(String id) {
        return this.sig.get(id);
    }

}
