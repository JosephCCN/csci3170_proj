package inter;

import java.sql.*;
import java.util.Scanner;

public class Customer {
    private Statement stmt;

    public Customer(Statement s){
        this.stmt = s;
    }

    public void start(){
        Scanner scan = new Scanner(System.in);
        int choice;
        while(true) {
            print_interface();
            choice = scan.nextInt();
            if(choice == 1) {
                book_search();
            }
            else if(choice == 2) {
                order_creation();
            }
            else if(choice == 3) {
                order_altering();
            }
            else if(choice == 4) {
                order_query();
            }
            else if(choice == 5) {
                break;
            }
            else{
                System.out.print("[Error] Invalid choice, choose again.\n");
            }
        };
    }

    public void print_interface() {
        System.out.println("<This is the customer interface.>");
        System.out.println("---------------------------------");
        System.out.println("1. Book Search.");
        System.out.println("2. Order Creation.");
        System.out.println("3. Order Altering.");
        System.out.println("4. Order Query.");
        System.out.println("5. Back to main menu.\n");
        System.out.print("Please enter your choice??..");
    }

    public void book_search() {
        Scanner scan = new Scanner(System.in);
        int choice;
        while(true) {
            print_book_search_interface();
            choice = scan.nextInt();
            if(choice == 1) {
                ISBN();
            }
            else if(choice == 2) {
                Book_Title();
            }
            else if(choice == 3) {
                Author_Name();
            }
            else if(choice == 4) {
                break;
            }
            else{
                System.out.print("[Error] Invalid choice, choose again.\n");
            }
        };
    }

    public void print_book_search_interface() {
        System.out.println("What do you want to search??");
        System.out.println("1 ISBN");
        System.out.println("2 Book Title");
        System.out.println("3 Author Name");
        System.out.println("4 Exit");
        System.out.print("Your choice?...");
    }

    public void ISBN() {
        System.out.print("Input the ISBN: ");
        Scanner scan = new Scanner(System.in);
        String isbn;
        isbn = scan.nextLine();
        System.out.print("\n");
        String sql = """
                select * 
                from book
                """;
        try{
            ResultSet rs = stmt.executeQuery(sql);
            int count = 1;
            while(rs.next()) {
                System.out.println("Record: " + count + "\nBook Title: " + rs.getString("title") + "\nISBN: " + rs.getString("ISBN") + "\nUnit Price: " + rs.getInt("unit_price") + "\nNo Of Available: " + rs.getInt("no_of_copies") + "\nAuthors:\n");
                count = count + 1;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void Book_Title() {
        System.out.print("Input the Book Title: ");
        Scanner scan = new Scanner(System.in);
        String book_title;
        book_title = scan.nextLine();
    }

    public void Author_Name() {
        System.out.print("Input the Author Name: ");
        Scanner scan = new Scanner(System.in);
        String author_name;
        author_name = scan.nextLine();
    }

    public void order_creation() {
        System.out.print("Please enter your customerID??");
        Scanner scan = new Scanner(System.in);
        String customer_id;
        customer_id = scan.nextLine();
        System.out.println(">> What book do you want to order??");
        System.out.println(">> Input ISBN and then the quantity.");
        System.out.println(">> You can press \"L\" to see the ordered list, or \"F\" to finish ordering.");
        while(true){
            System.out.print("Please enter the book's ISBN: ");
            String isbn;
            isbn = scan.nextLine();
            if(isbn.equals("L")){
                System.out.println("ISBN          Number:");
            }
            else if(isbn.equals("F")){
                System.out.println("Your order has been created.");
                break;
            }
            else{
                System.out.print("Please enter the quantity of the order: ");
                String quantity_i;
                int quantity;
                quantity_i = scan.nextLine();
                quantity = Integer.valueOf(quantity_i);
            }
        }
    }

    public void order_altering() {
        System.out.print("Please enter the orderID that you want to change: ");
        Scanner scan = new Scanner(System.in);
        String order_id;
        order_id = scan.nextLine();
        System.out.println("Which book you want to alter (input book no.):");
        String book_no_i;
        int book_no;
        book_no_i = scan.nextLine();
        book_no = Integer.valueOf(book_no_i);
        System.out.println("input add or remove");
        String action;
        action = scan.nextLine();
        System.out.print("input the number: ");
        int action_no;
        action_no = scan.nextInt();
        System.out.println("Update is ok!");
        System.out.println("Update done!!");
        System.out.println("Updated charge");
    }

    public void order_query() {
        System.out.print("Please input your customerID: ");
        Scanner scan = new Scanner(System.in);
        String customer_id;
        customer_id = scan.nextLine();
        System.out.print("Please input the year: ");
        int year;
        year = scan.nextInt();
        System.out.println("order query");
    }
}
