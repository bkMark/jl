import Javalette.Absyn.*;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interpreter {
    Env env = new Env();

    public void interpret(Program p) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        // Logger.log(Level.OFF, "Starting Interpret");
        env.setInput(new Scanner(System.in));
        p.accept(new ProgramInterpVisitor(), env);
        env.getInput().close();
        env.setInput(null);
//        System.out.println("");
    }

}
