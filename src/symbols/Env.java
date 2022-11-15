package symbols;

import inter.Id;

import java.util.Hashtable;

import lexer.Token;

public class Env {
	/*
	 *保存已经定义的标识符，以及相应的类型和大小
	 */
	private Hashtable table;
	protected Env prev;

	/**
	 * 一个是本作用域中声明的变量，prev是作用域外声明的变量
	 * @param n
	 */
	public Env(Env n) { table = new Hashtable(); prev=n;}

	/**
	 * 添加到变量声明表中
	 * @param w
	 * @param i
	 */
	public void put(Token w, Id i) {
		table.put(w, i);
	}

	/**
	 *
	 * @param w 词法单元
	 * @return 该词法单元对应的变量声明
	 */
	public Id get(Token w){
		/**
		 *以链表的方式遍历每一个后序标识符表，找出该变量是否被声明
		 */
		for(Env e=this; e!=null; e=e.prev){
			Id found = (Id)(e.table.get(w));
			if(found!=null) return found;
		}
		return null;
	}
}
