package main;

import java.io.IOException;

import parser.Parser;
import lexer.Lexer;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Lexer lex = new Lexer();//词法分析器
		Parser parser = new Parser(lex);//语法分析器
		parser.program();//执行语法分析
		System.out.print("\n");
	}

}
