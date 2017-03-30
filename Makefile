# Makefile for jlc in java1.5

JAVAC = javac
JAVAC_FLAGS = -sourcepath .

JAVA = java

# Name of generated .cup file for bnfc 2.8.1
CUPFILE = Javalette/Javalette.cup

.PHONY: bnfc jlc clean distclean vclean

all: bnfc jlc

jlc:
	${JAVAC} ${JAVAC_FLAGS} jlc.java
	chmod a+x jlc

bnfc:
	bnfc -java1.5 Javalette.cf
	${JAVA} ${JAVA_FLAGS} JLex.Main Javalette/Yylex
	${JAVA} ${JAVA_FLAGS} java_cup.Main -nopositions -expect 100 $(CUPFILE)
	mv sym.java parser.java Javalette

clean:
	 -rm -f Javalette/Absyn/*.class Javalette/*.class
	 -rm -f .dvi Javalette.aux Javalette.log Javalette.ps  *.class

distclean: vclean

vclean: clean
	 -rm -f Javalette/Absyn/*.java
	 -rmdir Javalette/Absyn/
	 -rm -f Javalette.tex Javalette.dvi Javalette.aux Javalette.log Javalette.ps
	 -rm -f Javalette/Yylex $(CUPFILE) Javalette/Yylex.java Javalette/VisitSkel.java Javalette/ComposVisitor.java Javalette/AbstractVisitor.java Javalette/FoldVisitor.java Javalette/AllVisitor.java Javalette/PrettyPrinter.java Javalette/Skeleton.java Javalette/Test.java Javalette/sym.java Javalette/parser.java Javalette/*.class
	 -rmdir -p Javalette/

# EOF
