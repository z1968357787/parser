package inter;

import symbols.Type;

public class Else extends Stmt {

   Expr expr; Stmt stmt1, stmt2;

    /**
     * if(bool)stmt else stmt
     * @param x bool
     * @param s1 if->stmt
     * @param s2 else->stmt
     */
   public Else(Expr x, Stmt s1, Stmt s2) {
      expr = x; stmt1 = s1; stmt2 = s2;
      if( expr.type != Type.Bool ) expr.error("boolean required in if");
   }
   public void gen(int b, int a) {}
   
   public void display(){
	   emit("stmt : else begin");
	   emit("if true");
	   stmt1.display();
	   emit("else");
	   stmt2.display();
	   emit("stmt : else end");
   }
}
