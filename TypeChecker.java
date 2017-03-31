import Javalette.Absyn.*;

public class TypeChecker {
    CheckProg progChecker = new CheckProg();
    Env env = new Env();

    public void typecheck(Program p) {
        progChecker.visit(p, env);
    }
}
