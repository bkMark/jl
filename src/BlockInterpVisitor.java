import Javalette.Absyn.Block;
import Javalette.Absyn.Stmt;

/**
 * Created by scott on 2017-04-04.
 */
public class BlockInterpVisitor implements Javalette.Absyn.Blk.Visitor<Value, Env> {
    @Override
    public Value visit(Block p, Env env) {
        for (Stmt stmt: p.liststmt_) {
            stmt.accept(new StmtInterpVisitor(), env);
        }

        return new VVoid();
    }
}
