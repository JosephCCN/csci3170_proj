import java.sql.*;
import java.util.Scanner;

public class systemcli {
    private Connection con;
    private Scanner sc;
    public systemcli(Connection con, Scanner sc){
        this.con = con;
        this.sc = new Scanner(System.in);
    }
    public void startcli(){
        printsystemcli();
    }
    public void printsystemcli(){
        System.out.println("Here is systemcli");
    }
}
