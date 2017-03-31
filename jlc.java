import java_cup.runtime.*;
import Javalette.*;
import Javalette.Absyn.*;
import java.io.*;

public class jlc {
    public static void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Usage: jlc <SourceFile>");
            System.exit(1);
        }

        Yylex l = null;
        try {
            l = new Yylex(new FileReader(args[0]));
            parser p = new parser(l);
            Javalette.Absyn.Program parse_tree = (Javalette.Absyn.Program)p.pProg();
            new TypeChecker().typecheck(parse_tree);
            new Interpreter().interpret(parse_tree);

        } catch (TypeException e) {
            System.out.println("TYPE ERROR");
            System.err.println(e.toString());
            System.exit(1);
        } catch (RuntimeException e) {
            //            System.out.println("RUNTIME ERROR");
            System.err.println(e.toString());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        } catch (Throwable e) {
            System.out.println("SYNTAX ERROR");
            System.out.println("At line " + String.valueOf(l.line_num())
                       + ", near \"" + l.buff() + "\" :");
            System.out.println("     " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

