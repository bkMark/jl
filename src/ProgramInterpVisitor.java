import Javalette.Absyn.FnDef;
import Javalette.Absyn.Program;
import Javalette.Absyn.Stmt;
import Javalette.Absyn.TopDef;

/**
 * Created by sctwyn on 4/2/17.
 */
public class ProgramInterpVisitor implements Javalette.Absyn.Prog.Visitor<Env, Env> {
    @Override
    public Env visit(Program prog, Env env) {
        for (TopDef x: prog.listtopdef_) {
            FnDef fd = (FnDef)x;
            // no need to check re-def, weve already Type Checked
            env.updateFun(fd.ident_, new FnDef(fd.type_, fd.ident_, fd.listarg_, fd.liststmt_));
        }

        FnDef mainFun = env.lookupFun("main");
        env.enterCtx();
        for (Stmt stmt: mainFun.liststmt_) {
            new StmtInterpVisitor().visit(stmt, env);
            if (env.get_retVal() != null) {
                env.leaveCtx();
                return env;
            }
        }

        if (env.get_retVal() == null)
            throw new TypeException("main function needs to have a return value");

        env.leaveCtx();
//        System.out.println("");
        return env;
    }
}
