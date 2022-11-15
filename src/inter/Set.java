package inter;

import symbols.Type;

/**
 * 集合
 */
public class Set extends Stmt {

   public Id id; public Expr expr;

   /**
    *
    * @param i 标识符
    * @param x 表达式
    */
   public Set(Id i, Expr x) {
      id = i; expr = x;//判断类型是否错误
      if ( check(id.type, expr.type) == null ) error("type error");
   }

   /**
    * 判断等号两边的数据类型(要么都不是bool类型，要么都是bool类型)
    * @param p1 标识符的类型
    * @param p2 表达式的结果类型
    * @return
    */
   public Type check(Type p1, Type p2) {
      if ( Type.numeric(p1) && Type.numeric(p2) ) return p2;
      else if ( p1 == Type.Bool && p2 == Type.Bool ) return p2;
      else return null;
   }

   public void gen(int b, int a) {}
   
   public void display(){
	   emit(" assignment ");
   }
}