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
        String sql = "select b.ISBN, b.title, b.unit_price, b.no_of_copies, ba.author_name from book b, book_author ba where b.ISBN = ba.ISBN and b.ISBN = '"+isbn+"' order by ba.author_name asc ";
        try{
            ResultSet rs = stmt.executeQuery(sql);
            int count = 1;
            String result_b_t;
            String result_ISBN;
            int result_price;
            int result_no;
            String result_author;
            while(rs.next()) {
                result_author = rs.getString("author_name");
                if(count == 1){
                    System.out.println("Record " + count);
                    result_ISBN = rs.getString("ISBN");
                    result_b_t = rs.getString("title");
                    result_price = rs.getInt("unit_price");
                    result_no = rs.getInt("no_of_copies"); 
                    System.out.println("ISBN: " + result_ISBN);
                    System.out.println("Book Title:" + result_b_t);
                    System.out.println("Unit Price:" + result_price);
                    System.out.println("No of Available:" + result_no);
                    System.out.println("Authors:\n" + count + " :" + result_author);
                }
                else{
                    System.out.println(count + " :" + result_author);
                }
                count = count + 1;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("\nSearch end. You cannot have any action on searching result.\nBack to book search interface\n");
    }

    public void Book_Title() {
        System.out.print("Input the Book Title: ");
        Scanner scan = new Scanner(System.in);
        String book_title;
        book_title = scan.nextLine();
        System.out.println("\n");
        String sql1 = "select b.ISBN, b.title, b.unit_price, b.no_of_copies, ba.author_name from book b, book_author ba where b.ISBN = ba.ISBN and b.title like '"+book_title+"' order by b.ISBN, ba.author_name asc";
        try{
            ResultSet rs = stmt.executeQuery(sql1);
            int record_count = 0;
            int author_count = 1;
            String result_b_t;
            String result_ISBN;
            int result_price;
            int result_no;
            String result_author;
            String current_ISBN = "";
            while(rs.next()){
                result_author = rs.getString("author_name");
                result_ISBN = rs.getString("ISBN");
                result_b_t = rs.getString("title");
                result_price = rs.getInt("unit_price");
                result_no = rs.getInt("no_of_copies");
                if(!current_ISBN.equals(result_ISBN)){
                    record_count++;
                    if(record_count > 1){
                        System.out.print("\n");
                    }
                    author_count = 1;
                    current_ISBN = String.valueOf(result_ISBN);
                    System.out.println("Record " + record_count);
                    System.out.println("ISBN: " + result_ISBN);
                    System.out.println("Book Title:" + result_b_t);
                    System.out.println("Unit Price:" + result_price);
                    System.out.println("No of Available:" + result_no);
                    System.out.println("Authors:\n" + author_count + " :" + result_author);
                    author_count++;
                }
                else{
                    System.out.println(author_count + " :" + result_author);
                    author_count++;
                }
            }
            System.out.println("Search end. You cannot have any action on searching result.\nBack to book search interface\n");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void Author_Name() {
        System.out.print("Input the Author Name: ");
        Scanner scan = new Scanner(System.in);
        String author_name;
        author_name = scan.nextLine();
        System.out.println("\n");
        String sql1 = "select b.ISBN, b.title, b.unit_price, b.no_of_copies, ba.author_name from book b, book_author ba where b.ISBN = ba.ISBN and b.ISBN in (Select b.ISBN from book b, book_author ba where b.ISBN = ba.ISBN and ba.author_name like '"+author_name+"') order by b.ISBN, ba.author_name asc";
        try{
            ResultSet rs = stmt.executeQuery(sql1);
            int record_count = 0;
            int author_count = 1;
            String result_b_t;
            String result_ISBN;
            int result_price;
            int result_no;
            String result_author;
            String current_ISBN = "";
            while(rs.next()){
                result_author = rs.getString("author_name");
                result_ISBN = rs.getString("ISBN");
                result_b_t = rs.getString("title");
                result_price = rs.getInt("unit_price");
                result_no = rs.getInt("no_of_copies");
                if(!current_ISBN.equals(result_ISBN)){
                    record_count++;
                    if(record_count > 1){
                        System.out.print("\n");
                    }
                    author_count = 1;
                    current_ISBN = String.valueOf(result_ISBN);
                    System.out.println("Record " + record_count);
                    System.out.println("ISBN: " + result_ISBN);
                    System.out.println("Book Title:" + result_b_t);
                    System.out.println("Unit Price:" + result_price);
                    System.out.println("No of Available:" + result_no);
                    System.out.println("Authors:\n" + author_count + " :" + result_author);
                    author_count++;
                }
                else{
                    System.out.println(author_count + " :" + result_author);
                    author_count++;
                }
            }
            System.out.println("Search end. You cannot have any action on searching result.\nBack to book search interface\n");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void order_creation() {
        SystemInterface system = new SystemInterface(stmt);
        String date = system.getDate();
        int total_book = 0;
        int charge = 0;
        int copies = 0;
        String new_order_id = "";
        String sql_order_id = """
                select max(order_id)
                from ordering
                """;
        try{
            ResultSet rs = stmt.executeQuery(sql_order_id);
            while(rs.next()){
                new_order_id = rs.getString("max(order_id)");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        int order_id_i = Integer.valueOf(new_order_id);
        order_id_i = order_id_i + 1;
        String new_order_id_i = String.valueOf(order_id_i);
        new_order_id = "000000" + new_order_id_i;
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
                String sql_list = "select ISBN, quantity from ordering where order_id = '" + new_order_id + "'";
                try{
                    ResultSet rs = stmt.executeQuery(sql_list);
                    while(rs.next()){
                        System.out.print(rs.getString("ISBN"));
                        System.out.print("   ");
                        System.out.println(rs.getInt("quantity"));
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            }
            else if(isbn.equals("F")){
                String sql_total_book = "select sum(quantity) from ordering where order_id = '" + new_order_id + "'";
                try{
                    ResultSet rs = stmt.executeQuery(sql_total_book);
                    while(rs.next()){
                        total_book = rs.getInt("sum(quantity)");
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
                if(total_book > 0){
                    String sql_book_price = "select * from book b, ordering o where b.ISBN = o.ISBN and o.order_id = '" + new_order_id + "'";
                    try{
                        ResultSet rs = stmt.executeQuery(sql_book_price);
                        while(rs.next()){
                            int unit_price = 0;
                            int quantity = 0;
                            unit_price = rs.getInt("unit_price");
                            quantity = rs.getInt("quantity");
                            charge = charge + unit_price * quantity;
                        }
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                    charge = charge + total_book*10 + 10;
                }
                else{
                    charge = 0;
                }
                System.out.println("Your order has been created.");
                String sql_orders = "insert into orders(order_id, o_date, shipping_status, charge, customer_id) values('" + new_order_id + "', to_date('" + date + "', 'YYYY-MM-DD'), 'N', " + charge + ", '" + customer_id + "')";
                try {
                    stmt.executeQuery(sql_orders);
                } 
                catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }
            else{
                System.out.print("Please enter the quantity of the order: ");
                String quantity_i;
                int quantity;
                quantity_i = scan.nextLine();
                quantity = Integer.valueOf(quantity_i); 
                String sql_copies = "select * from book where ISBN = '" + isbn + "'";
                try{
                    ResultSet rs = stmt.executeQuery(sql_copies);
                    while(rs.next()){
                        copies = rs.getInt("no_of_copies");
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
                if(quantity > copies){
                    System.out.println("There are not enough book copies available.");
                }
                else{
                    String sql_ordering = "insert into ordering(order_id, ISBN, quantity) values('" + new_order_id + "', '" + isbn + "', " + quantity + ")";
                    try {
                        stmt.executeQuery(sql_ordering);
                    } 
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }

    public void order_altering() {
        int book_no_c = 1;
        int quantity = 0;
        int new_quantity = 0;
        int copies = 0;
        int new_charge = 0;
        String isbn = "";
        System.out.print("Please enter the orderID that you want to change: ");
        Scanner scan = new Scanner(System.in);
        String order_id;
        order_id = scan.nextLine();
        String sql_order = "select * from orders os, ordering oi where os.order_id = oi.order_id and os.order_id = '" + order_id + "'";
        try{
            ResultSet rs = stmt.executeQuery(sql_order);
            while(rs.next()){
                quantity = rs.getInt("quantity");
                isbn = rs.getString("ISBN");
                if(book_no_c == 1){
                    System.out.println("orderID: " + order_id + " shipping: " + rs.getString("shipping_status") + " charge: " + rs.getInt("charge") + " customerID: " + rs.getString("customer_id"));
                }
                System.out.println("book no: " + book_no_c + " ISBN: " + rs.getString("ISBN") + " quantity: " + rs.getInt("quantity"));
                book_no_c = book_no_c + 1;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("Which book you want to alter (input book no.):");
        String book_no_i;
        int book_no;
        book_no_i = scan.nextLine();
        book_no = Integer.valueOf(book_no_i);
        System.out.println("input add or remove");
        String action;
        action = scan.nextLine();
        if(action.equals("add")){
            String sql_copies = "select * from book where ISBN = '" + isbn + "'";
            try{
                ResultSet rs = stmt.executeQuery(sql_copies);
                while(rs.next()){
                    copies = rs.getInt("no_of_copies");
                }
            }
            catch(Exception e) {
                System.out.println(e);
            }
            do{
                System.out.print("input the number: ");
                int action_no;
                action_no = scan.nextInt();
                new_quantity = quantity + action_no;
                if(new_quantity > copies){
                    System.out.println("There are not enough book copies available.");
                    System.out.println("Please input again.");
                }
            } while(new_quantity > copies);
            String sql_update_ordering = "update ordering set quantity = " + new_quantity + " where order_id = '" + order_id + "'";
            try {
                stmt.executeQuery(sql_update_ordering);
            } 
            catch (Exception e) {
                System.out.println(e);
            }
            String sql_book_price = "select * from book b, ordering o where b.ISBN = o.ISBN and o.order_id = '" + order_id + "'";
            try{
                ResultSet rs = stmt.executeQuery(sql_book_price);
                while(rs.next()){
                    int unit_price = 0;
                    int quant = 0;
                    unit_price = rs.getInt("unit_price");
                    quant = rs.getInt("quantity");
                    new_charge = new_charge + unit_price * quant;
                }
            }
            catch(Exception e) {
                System.out.println(e);
            }
            new_charge = new_charge + new_quantity * 10 + 10;
            String sql_update_orders = "update orders set charge = " + new_charge + " where order_id = '" + order_id + "'";
            try {
                stmt.executeQuery(sql_update_orders);
            } 
            catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Update is ok!");
            System.out.println("Update done!!");
            System.out.println("Updated charge");
            int new_book_no_c = 1;
            String sql_new_order = "select * from orders os, ordering oi where os.order_id = oi.order_id and os.order_id = '" + order_id + "'";
            try {
                ResultSet rs = stmt.executeQuery(sql_new_order);
                while(rs.next()){
                    if(new_book_no_c == 1){
                        System.out.println("orderID: " + order_id + " shipping: " + rs.getString("shipping_status") + " charge: " + rs.getInt("charge") + " customerID: " + rs.getString("customer_id"));
                    }
                    System.out.println("book no: " + new_book_no_c + " ISBN: " + rs.getString("ISBN") + " quantity: " + rs.getInt("quantity"));
                    new_book_no_c = new_book_no_c + 1;
                }
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        else if(action.equals("remove")){
            System.out.print("input the number: ");
            int action_no;
            action_no = scan.nextInt();
            new_quantity = quantity - action_no;
            String sql_update_ordering = "update ordering set quantity = " + new_quantity + " where order_id = '" + order_id + "'";
            try {
                stmt.executeQuery(sql_update_ordering);
            } 
            catch (Exception e) {
                System.out.println(e);
            }
            if(new_quantity == 0){
                new_charge = 0;
            }
            else{
                String sql_book_price = "select * from book b, ordering o where b.ISBN = o.ISBN and o.order_id = '" + order_id + "'";
                try{
                    ResultSet rs = stmt.executeQuery(sql_book_price);
                    while(rs.next()){
                        int unit_price = 0;
                        int quant = 0;
                        unit_price = rs.getInt("unit_price");
                        quant = rs.getInt("quantity");
                        new_charge = new_charge + unit_price * quant;
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
                new_charge = new_charge + new_quantity * 10 + 10;
            }
            String sql_update_orders = "update orders set charge = " + new_charge + " where order_id = '" + order_id + "'";
            try {
                stmt.executeQuery(sql_update_orders);
            } 
            catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Update is ok!");
            System.out.println("Update done!!");
            System.out.println("Updated charge");
            int new_book_no_c = 1;
            String sql_new_order = "select * from orders os, ordering oi where os.order_id = oi.order_id and os.order_id = '" + order_id + "'";
            try{
                ResultSet rs = stmt.executeQuery(sql_new_order);
                while(rs.next()){
                    if(new_book_no_c == 1){
                        System.out.println("orderID: " + order_id + " shipping: " + rs.getString("shipping_status") + " charge: " + rs.getInt("charge") + " customerID: " + rs.getString("customer_id"));
                    }
                    System.out.println("book no: " + new_book_no_c + " ISBN: " + rs.getString("ISBN") + " quantity: " + rs.getInt("quantity"));
                    new_book_no_c = new_book_no_c + 1;
                }
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        System.out.print("\n");
    }

    public void order_query() {
        System.out.print("Please input your customerID: ");
        Scanner scan = new Scanner(System.in);
        String customer_id;
        customer_id = scan.nextLine();
        System.out.print("Please input the year: ");
        int year;
        year = scan.nextInt();
        String sql = """
                select * 
                from orders
                """;
        try{
            ResultSet rs = stmt.executeQuery(sql);
            int count = 1;
            while(rs.next()) {
                String date[] = rs.getString("o_date").split("-");
                int date_year;
                date_year = Integer.valueOf(date[0]);
                if(customer_id.equals(rs.getString("customer_id")) && date_year == year){
                    System.out.print("\n");
                    System.out.println("Record: " + count + "\nordereID: " + rs.getString("order_id") + "\norderdate: " + rs.getDate("o_date") + "\ncharge: " + rs.getInt("charge") + "\nshipping status: " + rs.getString("shipping_status"));
                    count = count + 1;
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        System.out.print("\n");
    }
}