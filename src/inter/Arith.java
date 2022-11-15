package inter;

import symbols.Type;
import lexer.Token;
/*
 *算术运算
 */
public class Arith extends Op {

	   public Expr expr1, expr2;

	/**
	 * 基本四则运算
	 * @param tok 运算符
	 * @param x1 第一个操作数
	 * @param x2 第二个操作数
	 */
	public Arith(Token tok, Expr x1, Expr x2)  {
	      super(tok, null); expr1 = x1; expr2 = x2;
	      type = Type.max(expr1.type, expr2.type);//返回结果的数据类型
	      if (type == null ) error("type error");
	}

	   public Expr gen() {
	      return new Arith(op, expr1.reduce(), expr2.reduce());
	   }

	   public String toString() {
	      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
	   }
	}
