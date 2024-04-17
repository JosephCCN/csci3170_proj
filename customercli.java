import java.sql.*;
import java.util.Scanner;

public class customercli {
    private Connection con;
    private Scanner sc;
    public customercli(Connection con, Scanner sc){
        this.con = con;
        this.sc = new Scanner(System.in);
    }
    public void startcli(){
        printcustomercli();
    }
    public void printcustomercli(){
        System.out.println("Here is customercli");
    }
}

