import java.sql.*;
import java.util.Scanner;

public class lobby {
    private Connection con;
    private Scanner sc;
    public lobby(Connection con){
        this.con = con;
        this.sc = new Scanner(System.in);
    }
    String year = "0000";
    String month = "00";
    String day = "00";
    public void startmeun(){
        printStartMenu();
        int choice = sc.nextInt();
        if(choice == 1){
            System.out.flush();
            systemcli c = new systemcli(con,sc);
            c.startcli();
        }
        else if(choice == 2){
            System.out.flush();
            customercli c = new customercli(con,sc);
            c.startcli();
        }
        else if(choice == 3){
            System.out.flush();
            bookstorecli c = new bookstorecli(con,sc);
            c.startcli();
        }
        else if(choice == 4){
            System.out.flush();
            System.out.println("The System Date is now: " + year + "-" + month + "-" + day);
            startmeun();
        }
        else if(choice == 5){
            System.out.println("quit the system now. see you later");
        }
        else{
            System.out.flush();
            System.out.print("[Error] Invalid choice, choose again.\n");
            startmeun();
        }
    }
    private void printStartMenu(){
        System.out.println("<This is the Book Ordering System.>");
        System.out.println("---------------------------------------------");
        System.out.println("1.  System interface.");
        System.out.println("2.  Customer interface.");
        System.out.println("3.  Bookstore interface.");
        System.out.println("4.  Show System Date");
        System.out.println("5.  Quit the system......");
        System.out.println("");
        System.out.print("Please enter your choice??..");
    }
}
