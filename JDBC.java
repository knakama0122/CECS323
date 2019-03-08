import java.sql.*;
import java.util.Scanner;

public class JDBC {
    static String USER = "root";
    static String PASS = "toor";
    static String DBNAME = "test";
    static final String WRITING_DISPLAY_FORMAT ="%-55s%-55s%-30s%-20s\n";
    static final String PUBLISHER_DISPLAY_FORMAT = "%-55s%-55s%-25s%-40s\n";
    static final String BOOK_DISPLAY_FORMAT = "%-55s%-55s%-50s%-4s%-4s\n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";

    /**
     * Takes the input string and outputs "N/A" if the string is empty or null.
     * @param input The string to be mapped.
     * @return  Either the input string or "N/A" as appropriate.
     */
    public static String dispNull (String input) {
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }

    public static void main(String[] args) {
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupname, booktitle, publishername, yearpublished, numberpages FROM book";
            ResultSet rs = stmt.executeQuery(sql);

            //Extract data from result set
            System.out.printf(BOOK_DISPLAY_FORMAT, "Group Name","Book Title", "Publisher Name", "Year Published", "# of Pages");
            while (rs.next()) {
                //Retrieve by column name
                String group = rs.getString("groupname");
                String title = rs.getString("booktitle");
                String publisher = rs.getString("publishername");
                String year = rs.getString("yearpublished");
                String pages = rs.getString("numberpages");

                //Display values
                System.out.printf(BOOK_DISPLAY_FORMAT,
                        dispNull(group), dispNull(title), dispNull(publisher), dispNull(year), dispNull(pages));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
