package inter;

import symbols.Type;
import lexer.Tag;
import lexer.Word;
/*
 *数组访问
 */
public class Access extends Op{

	public Id array;//数组的标识符
	public Expr index;//index为数组的下标，访问坐标

	/**
	 *
	 * @param a 标识符
	 * @param i 代表偏移量的表达式
	 * @param p 数组的数据类型
	 */
	public Access(Id a, Expr i, Type p){
		super(new Word("[]",Tag.INDEX),p);
		array=a; index = i;
	}
	
	public Expr gen() { return new Access(array, index.reduce(),type); }
	
	public void jumping(int t, int f) {}
	
	public String toString() {
		return array.toString()+" [ "+index.toString()+" ]";
	}
}
