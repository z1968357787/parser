package inter;

import lexer.Lexer;

public class Node {
	int lexline = 0;

	/**
	 * 获取当前行数
	 */
	Node() {lexline=Lexer.line;}

	/**
	 * 抛出异常
	 * @param s 异常信息
	 */
	void error(String s) { throw new Error("near line "+lexline+": "+s); }
	
	static int labels = 0;

	/**
	 * 新标签
	 * @return
	 */
	public int newlabel() { return ++labels;}
	
	public void emitlabel(int i) { System.out.print("L"+i+":"); }
	
	public void emit(String s) { System.out.println("\t"+s); }
}
