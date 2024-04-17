import java.sql.*;


public class main{
    public static void main(String args[]) {
        System.out.println("Hello");

        

        String host = "jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk";
        String username = "h017";
        String password = "cicsEvva";
        

        try{
            Connection con = DriverManager.getConnection(host, username, password);
            System.out.println("Connected");
            Statement stmt = con.createStatement();
            lobby cli = new lobby(con);
            cli.startmeun();
            /*ResultSet rs = stmt.executeQuery("select * from SPONSORS");
            while(rs.next()) {
                System.out.println(rs.getInt("SID") + " " + rs.getString("SPONSOR_NAME") + " " + rs.getFloat("MARKET_VALUE"));
            }*/
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}