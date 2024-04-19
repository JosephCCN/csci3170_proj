package inter;

import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class SystemInterface{

    private Statement stmt;

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
            ISBN char(13),
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
            customer_id varchar(10),
            Primary Key (order_id)
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
            for(int i=0;i<7;i++) {
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

    private void insertData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the folder path");
        String path = scanner.nextLine();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for(int i=0;i<listOfFiles.length;i++) {
            if(listOfFiles[i].getName().equals("book.txt")) {
                insertBook(new File(path, "book.txt"));
            }
            else if(listOfFiles[i].getName().equals("book_author.txt")) insertAuthor(new File(path, "book_author.txt"));
        }
    }

    private void setSysDate() {

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
