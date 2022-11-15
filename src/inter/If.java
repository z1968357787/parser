package inter;

import symbols.Type;

public class If extends Stmt {

   Expr expr; Stmt stmt;

    /**
     * 纯if语句，无else
     * if(bool)stmt
     * @param x
     * @param s
     */
   public If(Expr x, Stmt s) {
      expr = x;  stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in if");
   }

   public void gen(int b, int a) {}
   
   public void display(){
	   emit("stmt : if begin");
	   //expr
	   stmt.display();
	   emit("stmt : if end");
   }
}
