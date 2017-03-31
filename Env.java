import Javalette.Absyn.Type;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by sctwyn on 3/31/17.
 */
public class Env {
    public Env() {
        this.signature = new HashMap<String, FunType>();
        this.contexts = new LinkedList<HashMap<String, Type>>();
    }

    private HashMap<String, FunType> signature;
    private LinkedList<HashMap<String, Type>> contexts;

    public Type lookupVar(String id){
        for (HashMap<String, Type> ctx: this.contexts) {
            if (ctx.get(id) != null) {
                return ctx.get(id);
            }
        }
        return null;
    }

    public FunType lookupFun(String id) {
        return this.signature.get(id);
    }

    public void updateVar (String id, Type type) {
        this.contexts.getFirst().put(id, type);
    }

    public void updateFun(String id, FunType funType) {
        this.signature.put(id, funType);
    }

    public void enterCtx() {
        this.contexts.add(new HashMap<String, Type>());
    }

    public void leaveCtx() {
        this.contexts.removeFirst();
    }
}
