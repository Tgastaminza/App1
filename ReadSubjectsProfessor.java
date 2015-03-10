import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ReadSubjectsProfessor extends HttpServlet {
    Connection connection;
	
	String IDProfessor = request.getParameter("IDProfessor");
	
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:Clicker";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<!DOCTYPE HTML>");
        toClient.println("<html>");
        toClient.println("<head><title>Users</title></head>");
        toClient.println("<body>");
        toClient.println("<a href=\"index.html\">Home</A>");
                
        String sql = "Select Name FROM [Professor/Subject], Subjects WHERE [Professor/Subject].IDSubject=Subjects.IDSubject AND IDProfessor LIKE " + IDProfessor;
        System.out.println(sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {               
                toClient.println( result.getString("Name"));               
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
        
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}