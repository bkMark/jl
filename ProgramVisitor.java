import Javalette.Absyn.Void;
import Javalette.Absyn.*;

/**
 * Created by sctwyn on 4/1/17.
 */
public class ProgramVisitor implements Prog.Visitor<Env, Env> {
    @Override
    public Env visit(Program prog, Env env) {
        ListArg printIntArgs = new ListArg();
        printIntArgs.add(new Argument(new Int(), "x"));
        env.sig.put("printInt", new FunType(new Void(), printIntArgs));

        ListArg printDoubleArgs = new ListArg();
        printDoubleArgs.add(new Argument(new Doub(), "x"));
        env.sig.put("printDouble", new FunType(new Void(), printDoubleArgs));

        ListArg printBoolArgs = new ListArg();
        printBoolArgs.add(new Argument(new Bool(), "x"));
        env.sig.put("printBool", new FunType(new Void(), printBoolArgs));

        ListArg printStringArgs = new ListArg();
        printStringArgs.add(new Argument(new Str(), "x"));
        env.sig.put("printString", new FunType(new Void(), printStringArgs));

        // TODO:
        //env.sig.put("printElem");

        env.sig.put("readInt", new FunType(new Int(), new ListArg()));
        env.sig.put("readDouble", new FunType(new Doub(), new ListArg()));

        // Scan all the function definition add them into env.sig
        for (TopDef x: prog.listtopdef_) {
            FnDef fd = (FnDef)x;
            if (env.sig.get(fd.ident_) != null)
                throw new TypeException("Function Define Error: " + fd.ident_ + " has already defined");
            env.sig.put(fd.ident_, new FunType(fd.type_, fd.listarg_));
        }

        // Iterate over all function definitions
        for (TopDef x: prog.listtopdef_) {
            x.accept(new FnDefVisitor(), env);
        }

        FunType funType = env.sig.get("main");
        if (funType == null)
            throw new TypeException("Could not find the main function");
        if(!funType.getRtnType().equals(new Int()))
            throw new TypeException("main function is not Int!");
        if(!funType.getArgs().isEmpty())
            throw new TypeException("main function cannot have any arguments!");
        return null;
    }
}
