package symbols;

import lexer.Tag;

public class Array extends Type{
	public Type of;//类型，保存着后续维度，比如a[5][6],这里of=[6]，然后递归到[5][6],然后p.width=6*4(假设是int类型)，层层递归
	public int size = 1;//数组长度

	/**
	 * 数量*类型长度，最底层的是数组的数据类型，of=int，float，char，bool
	 * @param sz 该维度的长度
	 * @param p 后继维度
	 */
	public Array(int sz, Type p){
		super("[]", Tag.INDEX, sz*p.width); size = sz; of = p;
	}
	public String toString() { return "["+size+"]"+of.toString(); }
}
