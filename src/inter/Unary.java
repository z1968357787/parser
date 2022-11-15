package inter;

import symbols.Type;
import lexer.Token;
/**
 *取相反数
 */
public class Unary extends Op {

   public Expr expr;

   /**
    *
    * @param tok 符号
    * @param x 表达式
    */
   public Unary(Token tok, Expr x) {    // handles minus, for ! see Not
      super(tok, null);  expr = x;
      type = Type.max(Type.Int, expr.type);//只能对int或float类型取相反数
      if (type == null ) error("type error");
   }

   public Expr gen() { return new Unary(op, expr.reduce()); }

   public String toString() { return op.toString()+" "+expr.toString(); }
}

