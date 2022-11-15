package inter;

import symbols.Type;
import lexer.Num;
import lexer.Token;
import lexer.Word;

public class Constant extends Expr {
	/**
	 *
	 * @param tok 常量数值
	 * @param p 常量类型(非bool)
	 */
	public Constant(Token tok, Type p) { super(tok, p); }

	/**
	 * @param i bool类型常量
	 */
	public Constant(int i) { super(new Num(i), Type.Int); }

	/**
	 * bool类型常量
	 */
	public static final Constant
	      True  = new Constant(Word.True,  Type.Bool),
	      False = new Constant(Word.False, Type.Bool);

	   public void jumping(int t, int f) {}
}

