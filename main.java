import java.sql.*;
import java.util.Scanner;
import java.io.*;
import inter.*;

class Lobby {
    private Statement stmt;

    public Lobby(Statement s){
        this.stmt = s;
    }

    private void printStartMenu(){
        System.out.println("<This is the Book Ordering System.>");
        System.out.println("---------------------------------------------");
        System.out.println("1.  System interface.");
        System.out.println("2.  Customer interface.");
        System.out.println("3.  Bookstore interface.");
        System.out.println("4.  Show System Date");
        System.out.println("5.  Quit the system......\n");
        System.out.print("Please enter your choice??..");
    }

    public void start(){
        SystemInterface system = new SystemInterface(stmt);
        Customer customer = new Customer(stmt);
        BookStore bookStore = new BookStore(stmt);

        Scanner scanner = new Scanner(System.in);
        while(true) {
            printStartMenu();
            int choice = scanner.nextInt();
            if(choice == 1){
                system.start();
            }
            else if(choice == 2){
                customer.start();
            }
            else if(choice == 3){
                bookStore.start();
            }
            else if(choice == 4){
                System.out.println("The System Date is now: " + system.getDate());
                continue;
            }
            else if(choice == 5){
                System.out.println("Quit the system now. See you later");
                break;
            }
            else{
                System.out.print("[Error] Invalid choice, choose again.\n");
            }
        }
        
    }
}


class main{

    public static void main(String args[]) {
        System.out.println("Hello");


        String host = "jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk";
        String username = "h072";
        String password = "danfiavI";
        

        try{
            Connection con = DriverManager.getConnection(host, username, password);
            System.out.println("Connected");
            Statement stmt = con.createStatement();
            Lobby lobby = new Lobby(stmt);
            lobby.start();
            /*ResultSet rs = stmt.executeQuery("select * from SPONSORS");
            while(rs.next()) {
                System.out.println(rs.getInt("SID") + " " + rs.getString("SPONSOR_NAME") + " " + rs.getFloat("MARKET_VALUE"));
            }*/
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}