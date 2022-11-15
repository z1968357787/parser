package inter;

import symbols.Type;
import lexer.Word;

/**
 * 标识符
 */
public class Id extends Expr {
	
	public int offset;

	/**
	 *
	 * @param id 标识符词法单元
	 * @param p 词法单元的类型
	 * @param b 占用内存大小
	 */
	public Id(Word id, Type p, int b) { super(id,p); offset=b; }//标识符，类型，大小
}
