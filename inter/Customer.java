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
        do {
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
        } while(choice != 5);
    }

    public static void print_interface() {
        System.out.println("<This is the customer interface.>");
        System.out.println("1. Book Search.");
        System.out.println("2. Order Creation.");
        System.out.println("3. Order Altering.");
        System.out.println("4. Order Query.");
        System.out.println("5. Back to main menu.\n");
        System.out.print("Please enter your choice??..");
    }

    public static void book_search() {
        /*
        public static void print_book_search_interface() {
            System.out.println("What do you want to search??");
            System.out.println("1 ISBN");
            System.out.println("2 Book Title");
            System.out.println("3 Author Name");
            System.out.println("4 Exit");
            System.out.print("Your choice?...");
        }

        public static void ISBN() {
            System.out.print("Input the ISBN: ");
            Scanner scan = new Scanner(System.in);
            string isbn;
            isbn =  scan.nextLine();
        }

        public static void Book_Title() {
            System.out.print("Input the Book Title: ");
            Scanner scan = new Scanner(System.in);
            string book_title;
            book_title =  scan.nextLine();
        }

        public static void Author_Name() {
            System.out.print("Input the Author Name: ");
            Scanner scan = new Scanner(System.in);
            string author_name;
            author_name =  scan.nextLine();
        }

        Scanner scan = new Scanner(System.in);
        int choice;
        do {
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
        } while(choice != 4);
        System.out.flush();
        CustomerInterface cus_inf = new CustomerInterface(stmt);
        cus_inf.run();
        */
    }

    public static void order_creation() {
       
    }

    public static void order_altering() {

    }

    public static void order_query() {

    }
}

