import Javalette.Absyn.Blk;
import Javalette.Absyn.Block;
import Javalette.Absyn.Stmt;

/**
 * Created by sctwyn on 4/1/17.
 */
public class BolckTCVisitor implements Blk.Visitor<Env, Env> {
    @Override
    public Env visit(Block p, Env env) {
        for (Stmt stmt: p.liststmt_) {
            stmt.accept(new StmtTCVisitor(), env);
        }
        return env;
    }
}
