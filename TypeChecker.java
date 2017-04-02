import Javalette.Absyn.*;

public class TypeChecker {
    Env env = new Env();

    public void typecheck(Program p) {
        p.accept(new ProgramVisitor(), env);
    }


}
