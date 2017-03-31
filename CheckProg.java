import Javalette.Absyn.FnDef;
import Javalette.Absyn.Prog;
import Javalette.Absyn.Program;
import Javalette.Absyn.TopDef;

/**
 * Created by sctwyn on 3/31/17.
 */
public class CheckProg implements Prog.Visitor<Env, Env> {
    CheckDef defChecker = new CheckDef();

    @Override
    public Env visit(Program program, Env env) {
//        program.
        for (TopDef topDef : program.listtopdef_) {
            defChecker.visit((FnDef) topDef, env);
        }
        return env;
    }
}
