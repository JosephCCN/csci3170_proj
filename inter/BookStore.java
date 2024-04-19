package inter;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class BookStore {
    private Statement stmt;

    public BookStore(Statement s){
        this.stmt = s;
    }
    public void printBScli(){
        System.out.println("<This is the bookstore interface.>");
        System.out.println("1. Order Update.");
        System.out.println("2. Order Query.");
        System.out.println("3. N most Popular Book Query.");
        System.out.println("4. Back to main meun.");
        System.out.println("");
        System.out.print("What is your choice??..");
    }
    public void orderupdate(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input the order ID: ");
        boolean found = false;
        String order_id = scan.nextLine();
        String select_sql = 
        """
            SELECT orders.order_id,orders.shipping_status, sum(ordering.quantity) as num
            FROM orders, ordering
            WHERE orders.order_id=ordering.order_id
            group by orders.shipping_status,orders.order_id
            order by orders.order_id asc    
        """;
        try{
            ResultSet rs = stmt.executeQuery(select_sql);
            String select_shipstatus = "none";
            int num = 0;
            while(rs.next() && !found){
                String select_id = rs.getString("order_id");
                if(Objects.equals(select_id, order_id)){
                    select_shipstatus = rs.getString("shipping_status");
                    num = rs.getInt("num");
                    found = true;
                }
            }
            if(found){
                System.out.println("the Shipping status of " + order_id + " is " + select_shipstatus + " and " + num + " books ordered");
                System.out.print("Are you sure to update the shipping status? (Yes=Y) ");
                if(Objects.equals(select_shipstatus, "Y")){
                    System.out.print("\n");
                    System.out.println("The status has already been 'Yes'. You cannot update it ");
                }
                else{
                    String option = scan.nextLine();
                    if(Objects.equals(option, "Y")){
                        String select_sql1 = "update orders set orders.shipping_status = 'Y' where orders.order_id = '"+order_id+"' ";
                        try{
                            stmt.executeUpdate(select_sql1);
                            System.out.println("Updated shiping status");
                        }
                        catch(Exception e) {
                            System.out.println(e);
                        }
                    }
                    else{
                        System.out.println("[Error] Invalid choice, back to bookstore interface");
                        return;
                    }
                }
            }
            else{
                System.out.println("Cannot find the order id. Back to bookstore interface");
                return;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }   
    }
    public void orderquery(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input the Month for Order Query (e.g.2003-06): ");
        String raw_input_date = scan.nextLine();
        String segment[] = raw_input_date.split("-");
        String select_sql2 = """
            select order_id, customer_id, o_date , charge
            from orders
            order by order_id asc        
        """;
        int record_num = 1;
        int total_charge = 0;
        try{
            ResultSet rs1 = stmt.executeQuery(select_sql2);
            while(rs1.next()){
                String find_date = rs1.getString("o_date");
                String segment1[] = find_date.split("-");
                String segment2[] = find_date.split(" ");
                if(Objects.equals(segment[0], segment1[0]) && Objects.equals(segment[1], segment1[1])){
                    System.out.print("\n");
                    System.out.println("Record : " + record_num);
                    record_num += 1;
                    String od_id = rs1.getString("order_id");
                    String cust_id = rs1.getString("customer_id");
                    int charge = rs1.getInt("charge");
                    System.out.println("order_id : " + od_id);
                    System.out.println("customer_id : " + cust_id);
                    System.out.println("date : " + segment2[0]);
                    System.out.println("charge : " + charge);
                    total_charge += charge;
                    System.out.print("\n");
                }
            }
            System.out.print("\n");
            System.out.println("Total charges of the month is " + total_charge);
            System.out.println("Back to bookstore interface");
            return;
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void n_popular(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input the N popular books number: ");
        String save_num_popular = scan.nextLine();
        int num_popular;
        int count = 0;
        int mark = 0;
        boolean max_print = false;
        try{
            num_popular = Integer.valueOf(save_num_popular);
        }
        catch(NumberFormatException e){
            System.out.println("[Error] Invalid input, back to bookstore interface");
            return;
        }
        String select_sql3 = """
            select sum(o.quantity) as num, o.ISBN, b.title
            from ordering o, book b
            where o.ISBN = b.ISBN
            group by o.ISBN, b.title
            order by o.ISBN asc, num desc 
            """;
        System.out.println("ISBN            Title           copies");
        try{
            ResultSet rs2 = stmt.executeQuery(select_sql3);
            while(rs2.next() && !max_print){
                String ISBN = rs2.getString("ISBN");
                String title = rs2.getString("title");
                int num = rs2.getInt("num");
                System.out.println(ISBN + "   " + title + "    " + num);
                count++;
                if(count == num_popular){
                    mark = num;
                }
                if(mark != num && count >= num_popular){
                    max_print = true;
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void start(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printBScli();
            int choice = scanner.nextInt();
            if(choice == 1){
                orderupdate();
            }
            else if(choice == 2){
                orderquery();
            }
            else if(choice == 3){
                n_popular();
            }
            else if(choice == 4){
                return;
            }
            else{
                System.out.print("[Error] Invalid choice, choose again.\n");
            }
        }

    }
}
