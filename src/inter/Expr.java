package inter;

import symbols.Type;
import lexer.Token;

/**
 * 基本表达式
 */
public class Expr extends Node{

	public Token op;//操作符
	public Type type;//类型

	/**
	 * 一般作为父类构造函数被子类继承使用
	 * @param tok 标识符
	 * @param p 标识符类型
	 */
	Expr(Token tok, Type p) { op = tok; type = p; }
	
	public Expr gen() { return this;}
	public Expr reduce() { return this;}
	
	public void jumping(int t, int f) {}
	
	public void emitjumps(String test, int t, int f){}
	public String toString() { return op.toString(); }
	
}
