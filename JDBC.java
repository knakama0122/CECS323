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
    }
    public static void menu(Connection conn) throws SQLException {
        int x = 1;
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        Scanner in = new Scanner(System.in);
        while(x != 0) {
            printMenu();
            x = in.nextInt();
            switch(x){
                case 1:
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    sql = "SELECT groupname, booktitle, publishername, yearpublished, numberpages FROM book";
                    rs = stmt.executeQuery(sql);
                    System.out.printf(BOOK_DISPLAY_FORMAT, "Group Name","Book Title", "Publisher Name", "Year Published", "# of Pages");
                    while (rs.next()) {
                        //Retrieve by column name
                        String group = rs.getString("groupname");
                        String title = rs.getString("");
                        String publisher = rs.getString("publishername");
                        String year = rs.getString("yearpublished");
                        String pages = rs.getString("numberpages");
                        //Display values
                        System.out.printf(BOOK_DISPLAY_FORMAT,
                            dispNull(group), dispNull(title), dispNull(publisher), dispNull(year), dispNull(pages));
                    }
                break;
                case 2:
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    sql = "SELECT PublisherName, PublisherAddress, PublisherPhone, PublisherEmail FROM Publisher";
                    rs = stmt.executeQuery(sql);
                    System.out.printf(PUBLISHER_DISPLAY_FORMAT, "Publisher Name","Address", "Phone Number", "Email Address");
                    while (rs.next()) {
                        //Retrieve by column name
                        String pname = rs.getString("PublisherName");
                        String paddr = rs.getString("PublisherAddress");
                        String pphone = rs.getString("PublisherPhone");
                        String pemail = rs.getString("PublisherEmail");
                        //Display values
                        System.out.printf(PUBLISHER_DISPLAY_FORMAT,
                            dispNull(pname), dispNull(paddr), dispNull(pphone), dispNull(pemail));
                    }
                break;
                case 3:
                    displayBook(conn);
                    break;
                case 4:
                    insertBook(conn);
                    break;
                case 5:
                    replacePublisher(conn, insertPublisher(conn));
                    break;
                case 0:
                break;
            }
        }
        rs.close();
        stmt.close();
    }

    public static void displayGroup(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroup";
            ResultSet rs = stmt.executeQuery(sql);
            //Extract data from result set
            System.out.println();
            System.out.printf(WRITING_DISPLAY_FORMAT, "Group name", "Head Writer", "Year Formed", "Subject\n");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("groupName");
                String writer = rs.getString("headWriter");
                String year = rs.getString("yearFormed");
                String subject = rs.getString("subject");
                //Display values
                System.out.printf(WRITING_DISPLAY_FORMAT,
                dispNull(name), dispNull(writer), dispNull(year), dispNull(subject));
            }
            System.out.println();
            //Clean up
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
        }
    }

    public static void displayPublisher(Connection conn) {
        try{
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM Publisher";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf(PUBLISHER_DISPLAY_FORMAT, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email\n");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("publisherName");
                String address = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                //Display values
                System.out.printf(PUBLISHER_DISPLAY_FORMAT,
                dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
            }
            System.out.println();
            //Clean up
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
        }
    }

    public static void displayBook(Connection conn) throws SQLException {
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        //STEP 4: Execute a query
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        sql = "SELECT GroupName, HeadWriter, YearFormed, Subject FROM WritingGroup";
        rs = stmt.executeQuery(sql);
        //STEP 5: Extract data from result set
        System.out.printf(WRITING_DISPLAY_FORMAT, "Group Name","Head Writer", "Year Formed", "Subject");
        while (rs.next()) {
            //Retrieve by column name
            String gname = rs.getString("GroupName");
            String hwriter = rs.getString("HeadWriter");
            String year = rs.getString("YearFormed");
            String subject = rs.getString("Subject");
            //Display values
            System.out.printf(WRITING_DISPLAY_FORMAT,
            dispNull(gname), dispNull(hwriter), dispNull(year), dispNull(subject));
        }
    }

    public static void insertBook(Connection conn) throws SQLException {
        PreparedStatement st = null;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a group name: ");
        String gname = in.nextLine();
        System.out.println("Enter a book title: ");
        String title = in.nextLine();
        System.out.println("Enter a publisher name: ");
        String pname = in.nextLine();
        System.out.println("Enter the year published: ");
        String year = in.nextLine();
        System.out.println("Enter the number of pages: ");
        String pages = in.nextLine();
        String sql = "INSERT INTO Book (groupName, , publisherName, yearPublished, numberPages) values (?,?,?,?,?)";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, gname);
            st.setString(2, title);
            st.setString(3, pname);
            st.setString(4, year);
            st.setString(5, pages);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
      	} finally {
              if (st != null) {
                  st.close();
              }
        }
    }

    public static String insertPublisher(Connection conn) throws SQLException {
        PreparedStatement st = null;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a publisher name: ");
        String newPName = in.nextLine();
        System.out.println("Enter a publisher address: ");
        String addr = in.nextLine();
        System.out.println("Enter a publisher phone: ");
        String phone = in.nextLine();
        System.out.println("Enter the publisher email: ");
        String email = in.nextLine();
        String sql = "INSERT INTO Publisher (PublisherName, PublisherAddress, publisherPhone, PublisherEmail) values (?,?,?,?)";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, newPName);
            st.setString(2, addr);
            st.setString(3, phone);
            st.setString(4, email);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
      	} finally {
              if (st != null) {
                  st.close();
              }
        }
        in.close();
        return newPName;
    }

    public static void replacePublisher(Connection conn, String newPName) throws SQLException {
    	Scanner in = new Scanner(System.in);
        PreparedStatement stmt = null;
        System.out.println("Enter the publisher name to update: ");
        String pname = in.nextLine();
        String sql2 = "UPDATE Book SET publisherName = ? WHERE publisherName = ?";

        try {
            stmt = conn.prepareStatement(sql2);
            stmt.setString(1, newPName);
            stmt.setString(2, pname);
            stmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
        	in.close();
        }
        if(stmt != null) {
            stmt.close();
        }
    }

    public static void removeBook(Connection conn) throws SQLException {
        boolean bookExists = false;
        boolean wgExists = false;
        String book;
        String writer;
        Statement stmt = null;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of book you would like to delete from the database: ");
        book = in.nextLine();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT book FROM Books";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String removeBook = rs.getString("");
                if (removeBook.equals (book)){
                    bookExists = true;
                }
            }
            if (bookExists == false){
                 System.out.println("The book you want to remove is not in the database.\n");
            }
            else {
                System.out.println("What is the writing group of your book?");
                writer = in.nextLine();
                stmt = conn.createStatement();
                String gsql;
                gsql = "SELECT groupName FROM Books WHERE groupName = '" + writer +"'";
                rs = stmt.executeQuery(gsql);
                while (rs.next()){
                    String removeWriter = rs.getString("groupName");
                    if (removeWriter.equals(writer)){
                        wgExists = true;
                    }
                }
                if (wgExists == false){
                    System.out.println("The writing group you chose does not exist.\n");
                }
                else {
                    stmt = conn.createStatement();
                    String rsql = "DELETE FROM Books WHERE bookTitle = '" + book + "' AND groupName = '" + writer + "'";
                    stmt.executeUpdate(rsql);
                    System.out.println( "\nYour book has been deleted from the database");
                    System.out.println("\nUpdated table: \n");
                    displayBook(conn);
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
        	in.close();
            stmt.close();
        }
    }

    public static void printMenu() {
        System.out.println("(1) Display Books");
        System.out.println("(2) Display Publishers");
        System.out.println("(3) Display Writing Groups");
        System.out.println("(0) Exit");
        System.out.println("Enter a value: ");
    }
}
