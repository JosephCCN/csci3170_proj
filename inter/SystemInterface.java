package inter;

import java.sql.*;
import java.util.Scanner;

public class SystemInterface{

    private Statement stmt;

    public SystemInterface(Statement s) {
        stmt = s;
    }

    public void printInterface() {
        System.out.println("<This is the system interface.>");
        System.out.println("1. Create Table.");
        System.out.println("2. Delete Table.");
        System.out.println("3. Insert Data.");
        System.out.println("4. Set System Date.");
        System.out.println("5. Back to main menu.\n");
        System.out.print("Please enter your choice??..");
    }


    public void createTable() {
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

    public void deleteTable() {
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

    public void insertData() {

    }

    public void setSysDate() {

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