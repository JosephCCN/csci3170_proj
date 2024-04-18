package inter;

import java.sql.*;
import java.util.Scanner;

public class BookStore {
    private Statement stmt;

    public BookStore(Statement s){
        this.stmt = s;
    }

    public void start(){
        System.out.println("Book Store Interface");
    }
}
