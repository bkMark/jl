import Javalette.Absyn.*;

/**
 * Created by sctwyn on 4/1/17.
 */
public class ProgramTCVisitor implements Prog.Visitor<Env, Env> {
    @Override
    public Env visit(Program prog, Env env) {

        // Scan all the function definition add them into env.sig
        for (TopDef x: prog.listtopdef_) {
            FnDef fd = (FnDef)x;
            if (env.sig.get(fd.ident_) != null)
                throw new TypeException("Function Define Error: " + fd.ident_ + " has already defined");
            env.updateFun(fd.ident_, new FnDef(fd.type_, fd.ident_, fd.listarg_, null));
        }

        // Iterate over all function definitions
        for (TopDef x: prog.listtopdef_) {
            x.accept(new FnDefTCVisitor(), env);
        }

        FnDef funType = env.sig.get("main");
        if (funType == null)
            throw new TypeException("Could not find the main function");
        if(!funType.type_.equals(new Int()))
            throw new TypeException("main function is not Int!");
        if(!funType.listarg_.isEmpty())
            throw new TypeException("main function cannot have any arguments!");
        return null;
    }
}
