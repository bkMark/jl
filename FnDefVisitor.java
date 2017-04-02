import Javalette.Absyn.Arg;
import Javalette.Absyn.FnDef;
import Javalette.Absyn.Type;

import java.util.TreeMap;

/**
 * Created by sctwyn on 4/1/17.
 */
public class FnDefVisitor implements Javalette.Absyn.TopDef.Visitor<Env, Env> {

    @Override
    public Env visit(FnDef p, Env env) {
        // save the return type and initial context
        env.setReturnType(p.type_);
        env.enterCtx();

        // add all function parameters to context
        for (Arg arg: p.listarg_) {
            arg.accept(new ArgumentVisitor(), env);
        }

        // check function statements
        p.blk_.accept(new BolckVisitor(), env);

        env.leaveCtx();
        env.setReturnType(null);

        return null;
    }
}
