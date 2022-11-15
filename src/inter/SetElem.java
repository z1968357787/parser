package inter;

import symbols.Array;
import symbols.Type;

public class SetElem extends Stmt {

   public Id array;//数组的标识符
   public Expr index;//数组的下标
   public Expr expr;//表达式

   /*
    *Access是数组访问地址，Expr是右侧表达式
    */
   public SetElem(Access x, Expr y) {
      array = x.array; index = x.index; expr = y;
      if ( check(x.type, expr.type) == null ) error("type error");
   }
   /*
    *判断数组赋值是否正确
    */
   public Type check(Type p1, Type p2) {
      if ( p1 instanceof Array || p2 instanceof Array ) return null;//数组之间不能直接复制
      else if ( p1 == p2 ) return p2;//判断类型是否相等
      else if ( Type.numeric(p1) && Type.numeric(p2) ) return p2;//类型不同按右边变量类型为主
      else return null;
   }

   public void gen(int b, int a) {}
   /*
    *assignment
    */
   public void display(){
	   emit(" assignment ");
   }
}
