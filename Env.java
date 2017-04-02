import Javalette.Absyn.Type;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by sctwyn on 4/1/17.
 */
public class Env {
    public HashMap<String, FunType> sig;
    public LinkedList<HashMap<String, Type>> ctx;
    private Type returnType;
    private Type currentVarType;

    public Env() {
        this.sig = new HashMap<>();
        this.ctx = new LinkedList<>();
        this.returnType = null;
        this.currentVarType = null;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }


    public void enterCtx() {
        ctx.addFirst(new HashMap<String, Type>());
    }

    public void leaveCtx() {
        ctx.removeFirst();
    }

    public void updateFun(String id, FunType type) {

    }

    public void updateVar(String id, Type type) {
        Map<String, Type> currentCtx = ctx.getFirst();
        if (currentCtx.containsKey(id)) {
            throw new TypeException("Variable " + id + "is already defined in this scope");
        }
        currentCtx.put(id, type);
    }

    public Type lookupVar(String id) {
        for (Map<String, Type> cxt: ctx) {
            if (cxt.get(id) != null) {
                return cxt.get(id);
            }
        }
        return null;
    }

    public Type lookupVarInCurrentCtx (String id) {
        return ctx.getFirst().get(id);
    }

    public Type getCurrentVarType() {
        return currentVarType;
    }

    public void setCurrentVarType(Type type) {
        this.currentVarType = type;
    }

    public FunType lookupFun(String id, Env env) {
        return sig.get(id);
    }

}
