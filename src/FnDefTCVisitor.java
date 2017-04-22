import Javalette.Absyn.Arg;
import Javalette.Absyn.Argument;
import Javalette.Absyn.FnDef;
import Javalette.Absyn.Stmt;

/**
 * Created by sctwyn on 4/1/17.
 */
public class FnDefTCVisitor implements Javalette.Absyn.TopDef.Visitor<Env, Env> {

    @Override
    public Env visit(FnDef p, Env env) {
        // save the return type and start new context
        env.setReturnType(p.type_);
        env.enterCtx();

        // add all function parameters to context
        for (Arg arg: p.listarg_) {
            new ArgumentTCVisitor().visit((Argument)arg, env);
        }

        // check function
        for (Stmt stmt: p.liststmt_) {
            new StmtTCVisitor().visit(stmt, env);
        }

        env.leaveCtx();
        env.setReturnType(null);

        return null;
    }
}
