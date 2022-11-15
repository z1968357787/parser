package inter;

import lexer.Token;

/*
 *与运算
 */
public class And extends Logical{
	/**
	 *
	 * @param tok
	 * @param x1
	 * @param x2
	 */
	public And(Token tok, Expr x1, Expr x2) { super(tok,x1,x2);}
	
	public void jumping(int t, int f){}

}
