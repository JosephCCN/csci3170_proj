package inter;

import java.sql.*;
import java.util.Scanner;

public class Customer {
    private Statement stmt;

    public Customer(Statement s){
        this.stmt = s;
    }

    public void start(){
        System.out.println("Customer Interface");
    }
}

