package inter;

public class Seq extends Stmt {

	   Stmt stmt1; Stmt stmt2;

	/**
	 *
	 * @param s1 第一个语段
	 * @param s2 后继语段
	 */
	public Seq(Stmt s1, Stmt s2) { stmt1 = s1; stmt2 = s2; }

	   public void gen(int b, int a) {}

	/**
	 * 语段展示
	 */
	public void display(){
		  if ( stmt1 == Stmt.Null ) stmt2.display();
		     else if ( stmt2 == Stmt.Null ) stmt1.display();
		     else {
		    	stmt1.display();
		        stmt2.display();
		     }
	   }
	}

