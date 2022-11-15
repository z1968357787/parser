package inter;

import symbols.Type;
import lexer.Token;
/**
 *处理逻辑运算
 */
public class Logical extends Expr{
	public Expr expr1, expr2;

	/**
	 *
	 * @param tok 逻辑运算符
	 * @param x1 第一个操作数
	 * @param x2 第二个操作数
	 */
	Logical(Token tok, Expr x1, Expr x2){
		super(tok,null);
		expr1 = x1; expr2 = x2;
		type = check(expr1.type, expr2.type);
		if(type==null) error("type error");
	}

	/**
	 * 判断两个操作数是否都是bool类型
	 * @param p1 第一个操作数的数据类型
	 * @param p2 第二个操作数的数据类型
	 * @return
	 */
	public Type check(Type p1, Type p2){
		if(p1==Type.Bool&&p2==Type.Bool) return Type.Bool;
		else return null;
	}

	/**
	 *
	 * @return
	 */
	public Expr gen(){
		int f = newlabel(); int a = newlabel();
		Temp temp = new Temp(type);
		this.jumping(0,f);
		emit(temp.toString()+" = true");
		emit("goto L"+a);
		emitlabel(f);emit(temp.toString()+" = false");
		emitlabel(a);
		return temp;
	}
}
