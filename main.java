import java.sql.*;
import java.util.Scanner;
import java.io.*;

class SystemInterface{

    static Statement stmt;

    public SystemInterface(Statement s) {
        stmt = s;
    }

    public static void print_interface() {
        System.out.println("<This is the system interface.>");
        System.out.println("1. Create Table.");
        System.out.println("2. Delete Table.");
        System.out.println("3. Insert Data.");
        System.out.println("4. Set System Date.");
        System.out.println("5. Back to main menu.\n");
        System.out.print("Please enter your choice??..");
    }


    public static void create_table() {
        String book = """
        CREATE TABLE book (
            ISBN char(13),
            title varchar(100),
            unit_price integer,
            no_of_copies integer,
            Primary Key (ISBN)
        )
        """;
        String author = """
        CREATE TABLE author (
            author_name varchar(50),
            Primary Key (author_name)
        )
        """;
        String Book_author = """
        CREATE TABLE Book_author (
            author_name varchar(50),
            ISBN char(13),
            FOREIGN KEY(author_name) REFERENCES author(author_name) on delete CASCADE,
            FOREIGN KEY(ISBN) REFERENCES book(ISBN) on delete CASCADE
        )
        """;
        String customer = """
        CREATE TABLE customer (
            customer_id varchar(10),
            name varchar(50),
            shopping_addres varchar(200),
            credit_card_no char(19),
            Primary Key (customer_id)
        )
        """;
        String orders = """
        CREATE TABLE orders (
            order_id char(8),
            o_date DATE,
            shipping_status char(1),
            charge Integer,
            Primary Key (order_id)
        )
        """;
        String have = """
        CREATE TABLE have (
            order_id char(8),
            customer_id varchar(10),
            FOREIGN KEY(customer_id) REFERENCES customer(customer_id) on delete CASCADE,
            FOREIGN KEY(order_id) REFERENCES orders(order_id) on delete CASCADE
        )
        """;
        String ordering = 
        """
        CREATE TABLE ordering (
            order_id char(8),
            ISBN char(13),
            quantity Integer,
            FOREIGN KEY(order_id) REFERENCES orders(order_id) on delete CASCADE,
            FOREIGN KEY(ISBN) REFERENCES book(ISBN) on delete CASCADE
        )
        """;
        try {
            stmt.executeQuery(book);
            stmt.executeQuery(author);
            stmt.executeQuery(orders);
            stmt.executeQuery(customer);

            stmt.executeQuery(Book_author);
            stmt.executeQuery(ordering);
            stmt.executeQuery(have);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void delete_table() {
        String[] tables = {"have", "ordering", "Book_author", "customer", "orders", "author", "book"};
        try{
            for(int i=0;i<7;i++) {
                String query = String.join(" ", "drop table", tables[i]);
                stmt.executeQuery(query);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void insert_data() {

    }

    public static void set_sys_date() {

    }

    public static void run() {
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            print_interface();
            choice = scan.nextInt();
            if(choice == 1) {
                create_table();
            }
            else if(choice == 2) {
                delete_table();
            }
            else if(choice == 3) {
                insert_data();
            }
            else if(choice == 4) {
                set_sys_date();
            }
        } while(choice != 5);
    }
}

class main{

    public static void main(String args[]) {
        System.out.println("Hello");


        String host = "jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk";
        String username = "h017";
        String password = "cicsEvva";
        

        try{
            Connection con = DriverManager.getConnection(host, username, password);
            System.out.println("Connected");
            Statement stmt = con.createStatement();
            SystemInterface sys_inf = new SystemInterface(stmt);
            sys_inf.run();
            // ResultSet rs = stmt.executeQuery("select * from SPONSORS");
            // while(rs.next()) {
            //     System.out.println(rs.getInt("SID") + " " + rs.getString("SPONSOR_NAME") + " " + rs.getFloat("MARKET_VALUE"));
            // }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}