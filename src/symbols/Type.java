package symbols;

import lexer.Tag;
import lexer.Word;

/*
 *关键字中的数据类型
 */
public class Type extends Word {
	public int width = 0;
	public Type(String s, int tag, int w) { super(s,tag); width=w; }//数组的Tag为index
	/*
	 *获取基本变量，分别为，类型名，识别类型，占用字节数
	 */
	public static final Type
		Int   = new Type( "int",   Tag.BASIC, 4 ),
	    Float = new Type( "float", Tag.BASIC, 8 ),
	    Char  = new Type( "char",  Tag.BASIC, 1 ),
	    Bool  = new Type( "bool",  Tag.BASIC, 1 );

	/**
	 *
	 * p判断是否为char或float或int
	 *
	 * @param p
	 * @return
	 */
	public static boolean numeric(Type p)	{

		if(p==Type.Char||p==Type.Int||p==Type.Float) return true;
		else return false;
	}

	/**
	 * 根据优先级判断数据类型，有float就返回float类型，没float但有int就返回int类型，都没有但有char就返回char类型
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static Type max(Type p1, Type p2){
		if(!numeric(p1)||!numeric(p2)) return null;
		else if(p1==Type.Float||p2==Type.Float) return Type.Float;//返回float
		else if(p1==Type.Int||p2==Type.Int) return Type.Int;//返回int
		else return Type.Char;//返回char
	}
	
}
