package inter;

import symbols.Type;
/*
 *for表达式识别
 */
public class For extends Stmt{
    Expr expr; Stmt stmt;Stmt assign1;Stmt assign2;//表达式

    public For() { expr = null; stmt = null;assign1=null;assign2=null;}

    /**
     * stmt -> for (assign;bool;assign) stmt
     * @param ass1
     * @param x
     * @param ass2
     * @param s
     */
    public void init(Stmt ass1,Expr x,Stmt ass2,Stmt s) {
        expr = x;  stmt = s; assign1=ass1; assign2=ass2;
        if( expr.type != Type.Bool ) expr.error("boolean required in while");
    }
    public void gen(int b, int a) {}

    public void display() {
        emit("stmt : for begin");
        emit("stmt : first branch:");
        assign1.display();
        emit("stmt : second branch:");
        assign2.display();
        emit("stmt : loop branch:");
        stmt.display();
        emit("stmt : for end");
    }
}
