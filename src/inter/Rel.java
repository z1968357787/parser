package inter;

import symbols.Array;
import symbols.Type;
import lexer.Token;

/**
 * 小于或大于
 */
public class Rel extends Logical {

   /**
    *
    * @param tok <或>
    * @param x1 第一个操作数
    * @param x2 第二个操作数
    */
   public Rel(Token tok, Expr x1, Expr x2) { super(tok, x1, x2); }

   /**
    * Type instanceof Array 表示判断 父类Type的实例化对象是否是Array类
    * @param p1 第一个操作数的数据类型
    * @param p2 第二个操作数的数据类型
    * @return
    */
   public Type check(Type p1, Type p2) {
      if ( p1 instanceof Array || p2 instanceof Array ) return null;//不能两个数组直接比较
      else if( p1 == p2 ) return Type.Bool;//两个变量类型一致才能比较
      else return null;
   }

   public void jumping(int t, int f) {}
}
