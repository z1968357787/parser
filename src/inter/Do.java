package inter;

import symbols.Type;

public class Do extends Stmt {

   Expr expr; Stmt stmt;

    /**
     * do循环识别
     */
   public Do() { expr = null; stmt = null; }

    /**
     *
     * @param s 循环内语句
     * @param x 循环判断语句bool
     */
   public void init(Stmt s, Expr x) {
      expr = x; stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in do");
   }

   public void gen(int b, int a) {}

    /**
     * 展示
     */
   public void display(){
	  emit("stmt : do begin");
	  stmt.display();
	  //expr.jumping(b,0);
	  emit("stmt : do end");
   }
}