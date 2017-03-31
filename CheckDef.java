import Javalette.Absyn.FnDef;
import Javalette.Absyn.TopDef;

/**
 * Created by sctwyn on 3/31/17.
 */
public class CheckDef implements TopDef.Visitor<Env, Env> {
    CheckStm stmChecker = new CheckStm();

    // FnDef.	   TopDef ::= Type Ident "(" [Arg] ")" Blk ;
    @Override
    public Env visit(FnDef p, Env env) {
        FunType funType = new FunType();
        funType.val = p.type_;
        return env;
    }
}
