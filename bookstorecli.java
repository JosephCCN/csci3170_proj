import java.sql.*;
import java.util.Scanner;

public class bookstorecli {
    private Connection con;
    private Scanner sc;
    public bookstorecli(Connection con, Scanner sc){
        this.con = con;
        this.sc = new Scanner(System.in);
    }
    public void startcli(){
        printbookstorecli();
    }
    public void printbookstorecli(){
        System.out.println("Here is bookstorecli");
    }
}
