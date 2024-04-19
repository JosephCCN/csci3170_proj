package inter;

import java.sql.*;
import java.util.*;
import java.io.*;

public class SystemInterface{

    private Statement stmt;
    private String year = "0000";
    private String month = "00";
    private String day = "00";

    public SystemInterface(Statement s) {
        stmt = s;
    }

    private void printInterface() {
        System.out.println("<This is the system interface.>");
        System.out.println("-------------------------------");
        System.out.println("1. Create Table.");
        System.out.println("2. Delete Table.");
        System.out.println("3. Insert Data.");
        System.out.println("4. Set System Date.");
        System.out.println("5. Back to main menu.\n");
        System.out.print("Please enter your choice??..");
    }


    private void createTable() {
        String book = """
        CREATE TABLE book (
            ISBN char(13),
            title varchar(100),
            unit_price integer,
            no_of_copies integer,
            Primary Key (ISBN)
        )
        """;
        String Book_author = """
        CREATE TABLE Book_author (
            author_name varchar(50),
            ISBN char(13)
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
            customer_id varchar(10),
            Primary Key (order_id)
        )
        """;
        String ordering = 
        """
        CREATE TABLE ordering (
            order_id char(8),
            ISBN char(13),
            quantity Integer
        )
        """;
        try {
            stmt.executeQuery(book);
            stmt.executeQuery(orders);
            stmt.executeQuery(customer);

            stmt.executeQuery(Book_author);
            stmt.executeQuery(ordering);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private void deleteTable() {
        String[] tables = {"ordering", "Book_author", "customer", "orders", "book"};
        try{
            for(int i=0;i<tables.length;i++) {
                String query = String.join(" ", "drop table", tables[i]);
                stmt.executeQuery(query);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private void insertBook(File file) {
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parm = line.split("\\|", 0);
                
                String isbn = parm[0];
                String title = parm[1];
                String price = parm[2];
                String copies = parm[3];

                String query = String.format("insert into book values ('%s', '%s', %s, %s)", isbn, title, price, copies);

                this.stmt.executeQuery(query);
            }
            scanner.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void insertAuthor(File file) {
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parm = line.split("\\|", 0);
                
                String isbn = parm[0];
                String name = parm[1];

                String query = String.format("insert into Book_author values ('%s', '%s')", name, isbn);
                this.stmt.executeQuery(query);
            }
            scanner.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void insertCustomer(File file) {
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parm = line.split("\\|", 0);
                
                String cid = parm[0];
                String name = parm[1];
                String addr = parm[2];
                String card_no = parm[3];

                String query = String.format("insert into customer values ('%s', '%s', '%s', '%s')", cid, name, addr, card_no);
                this.stmt.executeQuery(query);
            }
            scanner.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void insertOrders(File file) {
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parm = line.split("\\|", 0);
                
                String oid = parm[0];
                String date = parm[1];
                String status = parm[2];
                String charge = parm[3];
                String cid = parm[4];

                String query = String.format("insert into orders values ('%s', to_date('%s', 'YYYY-MM-DD'), '%s', %s, '%s')", oid, date, status, charge, cid);
                this.stmt.executeQuery(query);
            }
            scanner.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void insertOrdering(File file) {
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parm = line.split("\\|", 0);
                
                String oid = parm[0];
                String isbn = parm[1];
                String quantity = parm[2];

                String query = String.format("insert into ordering values ('%s', '%s', %s)", oid, isbn, quantity);
                this.stmt.executeQuery(query);
            }
            scanner.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the folder path");
        String path = scanner.nextLine();
        System.out.print("Processing...");
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for(int i=0;i<listOfFiles.length;i++) {
            if(listOfFiles[i].getName().equals("book.txt")) insertBook(new File(path, "book.txt"));
            else if(listOfFiles[i].getName().equals("book_author.txt")) insertAuthor(new File(path, "book_author.txt"));
            else if(listOfFiles[i].getName().equals("customer.txt")) insertCustomer(new File(path, "customer.txt"));
            else if(listOfFiles[i].getName().equals("orders.txt")) insertOrders(new File(path, "orders.txt"));
            else if(listOfFiles[i].getName().equals("ordering.txt")) insertOrdering(new File(path, "ordering.txt"));
        }
        System.out.println("Data is loaded!");
    }

    private void setSysDate() {
        try{
            Scanner scanner = new Scanner(System.in);

            ResultSet rs = this.stmt.executeQuery("select max(o_date) from orders");
            rs.next();
            String date = rs.getString(1);
            date = date.substring(0, 9);

            String newDate;
            boolean pass = true;

            do {
                pass = true;
                System.out.print("Please Input the date (YYYYMMDD): ");
                newDate = scanner.nextLine();
                boolean isNumeric = true;
                for(int i=0;i<newDate.length() && isNumeric;i++) {
                    if(!('0' <= newDate.charAt(i) && newDate.charAt(i) <= '9')) isNumeric = false;
                }
                if(newDate.length() != 8) {
                    System.out.println("Date should in (YYYYMMDD), please input again!");
                    pass = false;
                }
                else if(!isNumeric) {
                    System.out.println("Date should in all digits, please input again!");
                    pass = false;
                }
            }while(!pass);
            
            String year = newDate.substring(0, 4);
            String month = newDate.substring(4, 6);
            String day = newDate.substring(6, 8);

            this.year = year;
            this.month = month;
            this.day = day;

            System.out.println("Latest date in orders: " + date);
            System.out.println("Today is " + this.getDate());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        return year + "-" + month + "-" + day;
    }

    public void start() {
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            printInterface();
            choice = scan.nextInt();
            if(choice == 1) {
                createTable();
            }
            else if(choice == 2) {
                deleteTable();
            }
            else if(choice == 3) {
                insertData();
            }
            else if(choice == 4) {
                setSysDate();
            }
        } while(choice != 5);
    }
}
