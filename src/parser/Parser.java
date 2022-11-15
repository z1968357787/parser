package parser;

import inter.*;

import java.io.IOException;

import symbols.Array;
import symbols.Env;
import symbols.Type;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;
import lexer.Word;
import lexer.Num;

public class Parser {

	   private Lexer lex;    // lexical analyzer for this parser
	   private Token look;   // lookahead tagen
	   Env top = null;       // current or top symbol table
	   int used = 0;         // storage used for declarations

	/**
	 * 初始化词法分析器，并读取第一个词法单元
	 * @param l
	 * @throws IOException
	 */
	public Parser(Lexer l) throws IOException { lex = l; move(); }

	/**
	 * 负责从词法分析器中读取一个词法单元
	 * @throws IOException
	 */
	void move() throws IOException { look = lex.scan(); }

	/**
	 * 抛出程序语法错误信息
	 * @param s 错误信息
	 */
	void error(String s) { throw new Error("near line "+lex.line+": "+s); }

	/**
	 * 判断该词法单元是否合法，并读取下一个词法单元
	 * @param t 匹配字符
	 * @throws IOException
	 */
	void match(int t) throws IOException {
		  /*
		   *判断完这一个词法单元就读取下一个词法单元
		   */
		if( look.tag == t ) move();
		else error("syntax error");
	}

	/**
	 * 执行语法分析
	 * @throws IOException
	 */
	public void program() throws IOException {  // program -> block
		  // build the syntax tree
		   //构建抽象语法树
	      Stmt s = block();
	      // display the syntax tree
	      // only display the stmts, without expr
		   /*
		    *展示程序语句
		    */
	      s.display();
	   }

	/**
	 * 执行词法匹配
	 * @return
	 * @throws IOException
	 */
	Stmt block() throws IOException {  // block -> { decls stmts }
	      match('{');
		  Env savedEnv = top;//先保存原作用域中的标识符表
		  top = new Env(top);//初始化变量声明，不同作用域中的变量声明不一样
		   /*
		    *变量定义只能在左右操作语句之前
		    */
	      decls();//分析变量
		  Stmt s = stmts();//分析语句
	      match('}');
		  top = savedEnv;//然后再重新赋值给回top
	      return s;
	   }

	/**
	 * 判断变量声明
	 * @throws IOException
	 */
	void decls() throws IOException {
		   /*
		    *先判断是否是基本的变量类型
		    */
	      while( look.tag == Tag.BASIC ) {   // D -> type ID ;
	         Type p = type();//获取变量类型，如果是float[5][6]，这里返回的type就是[5][6]
			 Token tok = look; match(Tag.ID); match(';');//获取标识符
	         Id id = new Id((Word)tok, p, used);//如果是数组，就是标识符，index，used
	         top.put( tok, id );//将标识符放进标识符表汇总
	         used = used + p.width;//计算使用的内存大小
	      }
	   }

	/**
	 * type用于判断是数组类型还是普通类型
	 * @return
	 * @throws IOException
	 */
	Type type() throws IOException {

	      Type p = (Type)look;            // expect look.tag == Tag.BASIC 
	      match(Tag.BASIC);
	      if( look.tag != '[' ) return p; // T -> basic，基本类型
	      else return dims(p);            // return array type，数组类型
	   }

	/**
	 *
	 * @param p 数组变量类型
	 * @return
	 * @throws IOException
	 */
	Type dims(Type p) throws IOException {
		match('[');
		Token tok = look;//提前保存数字
		match(Tag.NUM);//数组维度大小
		match(']');
		if( look.tag == '[' ) p = dims(p);//递归获取多维数组大小
		return new Array(((Num)tok).value, p);//数组大小，数组类型，包含的类型为某一个维度的后继维度of以及该维度的长度size
	}

	/**
	 * 执行语句的判断
	 * @return
	 * @throws IOException
	 */
	Stmt stmts() throws IOException {
	      if ( look.tag == '}' ) return Stmt.Null;
	      else return new Seq(stmt(), stmts());//从一个表达式到下一个表达式
	}

	/**
	 * 执行单个语句的判断
	 * @return
	 * @throws IOException
	 */
	Stmt stmt() throws IOException {
	      Expr x;  Stmt s, s1, s2;
	      Stmt savedStmt;         // save enclosing loop for breaks，保存循环结点

	      switch( look.tag ) {

	      case ';':
	         move();//此处是一些多余的结束符号
	         return Stmt.Null;
	      case Tag.IF:
			  /**
			   * if语句判断
			   * if(bool)stmt
			   * if(bool)stmt else stmt
			   */
	         match(Tag.IF); match('('); x = bool(); match(')');
	         s1 = stmt();
	         if( look.tag != Tag.ELSE ) return new If(x, s1);
	         match(Tag.ELSE);
	         s2 = stmt();
	         return new Else(x, s1, s2);

	      case Tag.WHILE:
			  /**
			   * while语句判断
			   * while(bool)stmt
			   */
	         While whilenode = new While();//while是一个循环结点
	         savedStmt = Stmt.Enclosing; Stmt.Enclosing = whilenode;//记录循环结点
	         match(Tag.WHILE); match('('); x = bool(); match(')');
	         s1 = stmt();
			 /*
			  *把s1放进去递归
			  */
	         whilenode.init(x, s1);
	         Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing，返回结点，重新设置结点
	         return whilenode;

		  case Tag.FOR:
			  /**
			   * 这里为添加的for语句的识别
			   */
			  For fornode = new For();//for作为一个循环结点
			  Stmt ass1,ass2;
			  savedStmt = Stmt.Enclosing; Stmt.Enclosing = fornode;//记录循环结点
			  match(Tag.FOR);match('(');ass1=assignFor();match(';');x = bool();match(';');ass2=assignFor();match(')');
			  s1 = stmt();
			  /*
			   *把s1放进去递归
			   */
			  fornode.init(ass1,x,ass2,s1);
			  Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing 返回结点，重新设置结点
			  return fornode;

	      case Tag.DO:
			  /**
			   * do stmt while (bool);
			   * do while匹配
			   */
	         Do donode = new Do();
	         savedStmt = Stmt.Enclosing; Stmt.Enclosing = donode;//do作为循环节点
	         match(Tag.DO);
	         s1 = stmt();
	         match(Tag.WHILE); match('('); x = bool(); match(')'); match(';');
	         donode.init(s1, x);
	         Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing
	         return donode;

	      case Tag.BREAK:
			  /**
			   * break;
			   * 终止结点
			   */
	         match(Tag.BREAK); match(';');
	         return new Break();

	      case '{':
			  /**
			   * 新段落
			   */
	         return block();

	      default:
			  /**
			   *基础的assignment表达式
			   */
	         return assign();
	      }
	   }

	/**
	 * 赋值表达式
	 * @return 语句
	 * @throws IOException
	 */
	Stmt assign() throws IOException {
	      Stmt stmt;  Token t = look;
	      match(Tag.ID);//获取标识符
	      Id id = top.get(t);//判断该标识符是否被定义过
	      if( id == null ) error(t.toString() + " undeclared");

	      if( look.tag == '=' ) {       // S -> id = E ;
	         move();  stmt = new Set(id, bool());
	      }
	      else {                        // S -> L = E ;
	         Access x = offset(id);//数组赋值
	         match('=');  stmt = new SetElem(x, bool());
	      }
	      match(';');
	      return stmt;
	   }

	/**
	 * 作为for循环的assignment
	 * @return
	 * @throws IOException
	 */
	Stmt assignFor() throws IOException {
		Stmt stmt;  Token t = look;
		match(Tag.ID);//获取标识符
		Id id = top.get(t);//判断该标识符是否被定义过
		if( id == null ) error(t.toString() + " undeclared");

		if( look.tag == '=' ) {       // S -> id = E ;
			move();  stmt = new Set(id, bool());
		}
		else {                        // S -> L = E ;
			Access x = offset(id);//数组赋值
			match('=');  stmt = new SetElem(x, bool());
		}//无需匹配最后一个分号
		return stmt;
	}

	/**
	 * 包含了优先级处理的思想
	 * 先判断是否有&&运算，再做||运算
	 * @return
	 * @throws IOException
	 */
	Expr bool() throws IOException {
	      Expr x = join();//先判断是否是&运算
	      while( look.tag == Tag.OR ) {
	         Token tok = look;  move();  x = new Or(tok, x, join());//类型，前置表达式，后置表达式
	      }
	      return x;
	   }

	/**
	 * 先判断是否有==活!=运算
	 * 再做&&运算
	 * @return
	 * @throws IOException
	 */
	Expr join() throws IOException {
	      Expr x = equality();//再判断是否是==或!=运算
	      while( look.tag == Tag.AND ) {
	         Token tok = look;  move();  x = new And(tok, x, equality());
	      }
	      return x;
	   }

	/**
	 * 先判断是否有<或>运算，再做==或!=运算
	 * @return
	 * @throws IOException
	 */
	Expr equality() throws IOException {
	      Expr x = rel();//再判断是否是不等于运算
	      while( look.tag == Tag.EQ || look.tag == Tag.NE ) {
	         Token tok = look;  move();  x = new Rel(tok, x, rel());
	      }
	      return x;
	   }

	/**
	 * 先判断是否有基本四则运算，再做逻辑运算
	 * @return
	 * @throws IOException
	 */
	Expr rel() throws IOException {
	      Expr x = expr();//再判断是否是基本的算数表达式
	      switch( look.tag ) {
	      case '<': case Tag.LE: case Tag.GE: case '>':
	         Token tok = look;  move();  return new Rel(tok, x, expr());
	      default:
	         return x;
	      }
	   }

	/**
	 * 先* / 再 + -
	 * @return
	 * @throws IOException
	 */
	Expr expr() throws IOException {
	      Expr x = term();//判断是否是*或/
	      while( look.tag == '+' || look.tag == '-' ) {
	         Token tok = look;  move();  x = new Arith(tok, x, term());
	      }
	      return x;
	   }

	/**
	 * 先判断是否有单目运算符!或-，取非或取反
	 * @return
	 * @throws IOException
	 */
	Expr term() throws IOException {
	      Expr x = unary();
	      while(look.tag == '*' || look.tag == '/' ) {
	         Token tok = look;  move();   x = new Arith(tok, x, unary());
	      }
	      return x;
	   }

	/**
	 * 最终判断，常量或变量
	 * @return
	 * @throws IOException
	 */
	Expr unary() throws IOException {
		   /*
		    * 单目运算符，取负数
		    */
	      if( look.tag == '-' ) {
	         move();  return new Unary(Word.minus, unary());
	      }
		  /*
		   *取非
		   */
	      else if( look.tag == '!' ) {
	         Token tok = look;  move();  return new Not(tok, unary());
	      }
		  /*
		   *变量
		   */
	      else return factor();
	   }

	/**
	 * 常量或变量
	 * @return
	 * @throws IOException
	 */
	Expr factor() throws IOException {
	      Expr x = null;
	      switch( look.tag ) {
	      case '(':
	         move(); x = bool(); match(')');
	         return x;
	      case Tag.NUM:
	         x = new Constant(look, Type.Int);    move(); return x;
	      case Tag.REAL:
	         x = new Constant(look, Type.Float);  move(); return x;
	      case Tag.TRUE:
	         x = Constant.True;                   move(); return x;
	      case Tag.FALSE:
	         x = Constant.False;                  move(); return x;
	      default:
	         error("syntax error");
	         return x;
	      case Tag.ID:
			  //变量赋值
	         String s = look.toString();
	         Id id = top.get(look);
	         if( id == null ) error(look.toString() + " undeclared");
	         move();
	         if( look.tag != '[' ) return id;//取普通变量值
	         else return offset(id);//取数组值
	      }
	   }

	/**
	 * 访问数组的某个元素
	 * @param a 数组的标识符
	 * @return
	 * @throws IOException
	 */
	Access offset(Id a) throws IOException {   // I -> [E] | [E] I
	      Expr i; Expr w; Expr t1, t2; Expr loc;  // inherit id

	      Type type = a.type;//先判断标识符的数据类型
	      match('['); i = bool(); match(']');     // first index, I -> [ E ]，获取数组的下标，即坐标
	      type = ((Array)type).of;//获取数组的维度，类型内嵌套类型，相当于判断第几维度
	      w = new Constant(type.width);//递归获取长度
	      t1 = new Arith(new Token('*'), i, w);//获取偏移量*数据类型长度
	      loc = t1;//第一层偏移量
	      while( look.tag == '[' ) {      // multi-dimensional I -> [ E ] I
	         match('['); i = bool(); match(']');//i为访问下标
	         type = ((Array)type).of;
	         w = new Constant(type.width);
	         t1 = new Arith(new Token('*'), i, w);//获取第下一层偏移量，i*w
	         t2 = new Arith(new Token('+'), loc, t1);//加上一层的总偏移量
	         loc = t2;//赋值
	      }
	      return new Access(a, loc, type);//数组访问类型，a为数组的标识符，loc为访问地址，type为数组的数据类型
	}
}
