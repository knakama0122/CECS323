import java.sql.*;
import java.util.Scanner;

public class JDBC {
    static String USER = "root";
    static String PASS = "toor";
    static String DBNAME = "test";
    static final String WRITING_DISPLAY_FORMAT ="%-55s%-55s%-10s%-20s\n";
    static final String PUBLISHER_DISPLAY_FORMAT = "%-55s%-55s%-25s%-40s\n";
    static final String BOOK_DISPLAY_FORMAT = "%-55s%-55s%-50s%-5s%-5s\n";
    static final String BOOK_DATA_FORMAT = "%-55s%-55s%-55s%-15s%-15s%-55s%-55s%-55s%-55s%-15s%-55s\n";  
    static final String PUBLISHER_DATA_FORMAT = "%-55s%-55s%-55s%-55s%-55s%-55s%-16s%-16s%-55s%-15s%-55s\n";  
    static final String GROUP_DATA_FORMAT = "%-55s%-55s%-15s%-55s%-55s%-55s%-15s%-16s%-55s%-55s%-55s\n";
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
            menu(conn);
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
        String y = "1";
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);
        while(x != 0) {
            printMenu();
            y = input.nextLine();
            try{
            x = Integer.parseInt(y);
            }catch (Exception e)
            {
                System.out.println("Invalid value. \n Please try again");
                x = -1;
            }
            switch(x){
                case 1:
                    displayBook(conn);
                    break;
                case 2:
                    displayPublisher(conn);
                break;
                case 3:
                    displayGroup(conn);
                    break;
                case 4:
                    bookData(conn);
                    break;
                case 5:
                    publisherData(conn);
                    break;
                case 6:
                    groupData(conn);
                    break;
                case 7:
                    insertBook(conn);
                    break;
                case 8:
                    removeBook(conn);
                    break;
                case 9:
                    replacePublisher(conn, insertPublisher(conn));
                    break;
                case 0:
                break;
                default: 
                    System.out.println("Invalid value");
                    break;
            }
            if(x != 0)
                x = 1;
        }
        if(rs != null)
            rs.close();
        if(rs != null)
            stmt.close();
        input.close();
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
                int year = rs.getInt("yearFormed");
                String subject = rs.getString("subject");
                //Display values
                System.out.printf(WRITING_DISPLAY_FORMAT,
                dispNull(name), dispNull(writer), dispNull(Integer.toString(year)), dispNull(subject));
            }
            System.out.println();
            //Clean up
            rs.close();
            stmt.close();
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
        sql = "SELECT groupname, booktitle, publishername, yearpublished, numberpages FROM book";
        rs = stmt.executeQuery(sql);
        //STEP 5: Extract data from result set
        System.out.printf(BOOK_DISPLAY_FORMAT, "Group Name","Book Title", "Publisher Name", "Year Published", "# of Pages");
        while (rs.next()) {
            //Retrieve by column name
            String group = rs.getString("groupname");
            String title = rs.getString("booktitle");
            String publisher = rs.getString("publishername");
            int year = rs.getInt("yearpublished");
            String pages = rs.getString("numberpages");
            //Display values
            System.out.printf(BOOK_DISPLAY_FORMAT,
                dispNull(group), dispNull(title), dispNull(publisher), dispNull(Integer.toString(year)), dispNull(pages));
            }
        if(rs != null)
            rs.close();
        if(stmt != null)
            stmt.close();
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
        String sql = "INSERT INTO Book (groupName, bookTitle, publisherName, yearPublished, numberPages) values (?,?,?,?,?)";
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
            if (st != null) 
                st.close();              
        }
    }

    public static String insertPublisher(Connection conn) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        Statement stmt = null;
        int count = 0;
        int count2 = 0;
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
        String sql2= "SELECT COUNT(*) AS TOTAL FROM Publisher";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql2);
            while (rs.next())
                count = rs.getInt(1);
            st = conn.prepareStatement(sql);
            st.setString(1, newPName);
            st.setString(2, addr);
            st.setString(3, phone);
            st.setString(4, email);
            st.execute();
            while (rs.next())
                count2 = rs.getInt(1);
            rs = stmt.executeQuery(sql2);
            if(count != count2)
                count = -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
      	} finally {
            if (st != null) 
                st.close();
            if (stmt != null)
                stmt.close();
            if(rs != null)
                rs.close();
            if(count == -1){
                System.out.println("New Publisher Added Successfully added!");
                return newPName;
            }
        }
        return null;
    }

    public static void replacePublisher(Connection conn, String newPName) throws SQLException {
    	if(newPName == null)
            return;
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
        }
        if(stmt != null) {
            stmt.close();
        }
    }

    public static void removeBook(Connection conn) throws SQLException {
        boolean bookExists = false;
        boolean wgExists = false;
        PreparedStatement st = null;
        String book;
        String writer;
        Statement stmt = null;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of book you would like to delete from the database: ");
        book = in.nextLine();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT bookTitle FROM Book";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String removeBook = rs.getString("bookTitle");
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
                String gsql = "SELECT groupName FROM Book WHERE groupName = ?";
                PreparedStatement stmt2 = conn.prepareStatement(gsql);
                stmt2.setString(1, writer);
                rs = stmt2.executeQuery();
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
                    String rsql = "DELETE FROM Book WHERE bookTitle = ? AND groupName = ?";
                    st = conn.prepareStatement(rsql);
                    st.setString(1, book);
                    st.setString(2, writer);
                    st.execute();
                    System.out.println( "\nYour book has been deleted from the database");
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
        if(stmt != null)
            stmt.close();
        if(st != null)
            st.close();
        }
       // in.close();
    }
    
    public static void bookData(Connection conn) throws SQLException
    {
        Scanner in = new Scanner(System.in);
        PreparedStatement stmt = null;
        System.out.println("Enter a book title to display: ");
        String title = in.nextLine();
        String sql = "SELECT * FROM (Book NATURAL JOIN WritingGroup) INNER JOIN Publisher using (publisherName) where bookTitle = ?";
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,title);
            rs = stmt.executeQuery();
            System.out.printf(BOOK_DATA_FORMAT, "Group Name","Publisher Name","Title of Book","Year Published","Number of Pages","Address","Phone Number", "Email Address","Head Writer","Year Formed","Subject");
            while(rs.next())
            {
                String gname = rs.getString("groupName");
                String pname = rs.getString("publisherName");
                title = rs.getString("booktitle");
                int yearp = rs.getInt("yearPublished");
                int pages = rs.getInt("numberPages");
                String addr = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                String hw = rs.getString("headWriter");
                int yearf = rs.getInt("yearFormed");
                String subject = rs.getString("Subject");

                System.out.printf(BOOK_DATA_FORMAT, dispNull(gname),dispNull(pname),
                        dispNull(title),dispNull(Integer.toString(yearp)),
                        dispNull(Integer.toString(pages)), dispNull(addr),
                        dispNull(phone),dispNull(email), dispNull(hw), dispNull(Integer.toString(yearf)), dispNull(subject));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {}
        if(stmt != null) {
            stmt.close();
        }
    }
    
    public static void publisherData(Connection conn) throws SQLException
    {
        Scanner in = new Scanner(System.in);
        PreparedStatement stmt = null;
        System.out.println("Enter the publisher name to display: ");
        String pname = in.nextLine();
        String sql = "SELECT * FROM Publisher INNER JOIN (Book NATURAL JOIN WritingGroup) using (publisherName) where publisherName = ?";
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,pname);
            rs = stmt.executeQuery();
            System.out.printf(PUBLISHER_DATA_FORMAT, "Publisher Name","Address","Phone Number", "Email Address","Group Name","Title of Book","Year Published","Number of Pages","Head Writer","Year Formed","Subject");
            while(rs.next())
            {
                pname = rs.getString("publisherName");
                String addr = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                String gname = rs.getString("groupName");
                String title = rs.getString("booktitle");
                int yearp = rs.getInt("yearPublished");
                int pages = rs.getInt("numberPages");
                String hw = rs.getString("headWriter");
                int yearf = rs.getInt("yearFormed");
                String subject = rs.getString("Subject");

                System.out.printf(PUBLISHER_DATA_FORMAT, dispNull(pname),dispNull(addr),
                        dispNull(phone),dispNull(email),dispNull(gname),dispNull(title),
                        dispNull(Integer.toString(yearp)),dispNull(Integer.toString(pages)),
                        dispNull(hw),dispNull(Integer.toString(yearf)), dispNull(subject));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {}
        if(stmt != null) {
            stmt.close();
        }
    }
    
    public static void groupData(Connection conn) throws SQLException
    {
        Scanner in = new Scanner(System.in);
        PreparedStatement stmt = null;
        System.out.println("Enter the Writing Group name to display: ");
        String gname = in.nextLine();
        String sql = "SELECT * FROM WritingGroup INNER JOIN (Book NATURAL JOIN Publisher) using(groupName) where groupName = ?";
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,gname);
            rs = stmt.executeQuery();
            System.out.printf(GROUP_DATA_FORMAT, "Group Name","Head Writer","Year Formed","Subject","Publisher Name","Title of Book","Year Published","Number of Pages","Address","Phone Number", "Email Address");
            while(rs.next())
            {
                gname = rs.getString("groupName");
                String hw = rs.getString("headWriter");
                int yearf = rs.getInt("yearFormed");
                String subject = rs.getString("Subject");
                String pname = rs.getString("publisherName");
                String title = rs.getString("booktitle");
                int yearp = rs.getInt("yearPublished");
                int pages = rs.getInt("numberPages");
                String addr = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                System.out.printf(PUBLISHER_DATA_FORMAT, dispNull(gname),
                        dispNull(hw),dispNull(Integer.toString(yearf)),dispNull(subject),
                        dispNull(pname),dispNull(title),dispNull(Integer.toString(yearp)),
                        dispNull(Integer.toString(pages)),dispNull(addr),dispNull(phone), dispNull(email));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {}
        if(stmt != null) {
            stmt.close();
        }
    }
    
    public static void printMenu() {
        System.out.println("(1) Display Books");
        System.out.println("(2) Display Publishers");
        System.out.println("(3) Display Writing Groups");
        System.out.println("(4) List all data for a book");
        System.out.println("(5) List all data for a publisher");
        System.out.println("(6) List all data for a writing group");
        System.out.println("(7) Insert Book");
        System.out.println("(8) Remove Book");
        System.out.println("(9) Change publisher");
        System.out.println("(0) Exit");
        System.out.println("Enter a value: ");
    }
}
