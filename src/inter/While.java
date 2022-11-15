package inter;

import symbols.Type;
/*
 *while循环
 */
public class While extends Stmt {

   Expr expr; Stmt stmt;

   public While() { expr = null; stmt = null; }

    /**
     *
     * @param x bool表达式
     * @param s 循环语段
     */
   public void init(Expr x, Stmt s) {
      expr = x;  stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in while");
   }
   public void gen(int b, int a) {}
   
   public void display() {
	   emit("stmt : while begin");
	   stmt.display();
	   emit("stmt : while end");
   }
}
