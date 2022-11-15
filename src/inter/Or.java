package inter;

import lexer.Token;

/**
 * 或运算
 */
public class Or extends Logical {

   /**
    *
    * @param tok ||操作符
    * @param x1 第一个操作数
    * @param x2 第二个操作数
    */
   public Or(Token tok, Expr x1, Expr x2) { super(tok, x1, x2); }

   public void jumping(int t, int f) {}
}
