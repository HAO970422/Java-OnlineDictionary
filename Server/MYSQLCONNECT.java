package javaHw2;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.mysql.jdbc.Statement;  


public class MYSQLCONNECT {
	private Statement statement;
	public static void main (String[] args){
		MYSQLCONNECT temp =new MYSQLCONNECT();
		ResultSet result = temp.query("select * from userlist where username = 'andy'");
		temp.printUserInfo(result);
	}
	public MYSQLCONNECT(){
		try{   
		    //加载MySql的驱动类   
		    Class.forName("com.mysql.jdbc.Driver") ;   
		    }catch(ClassNotFoundException e){   
		    System.out.println("找不到驱动程序类 ，加载驱动失败！");   
		    e.printStackTrace() ;   
		    }   
		System.out.println("Driver connected");
		String url ="jdbc:mysql://localhost:3306/JAVAHWUser";
		String name="root";
		String pw = "andy19950923";
		
		 try{   
			 Connection con = DriverManager.getConnection(url , name , pw ) ;
			 statement = (Statement) con.createStatement();
			 }catch(SQLException se){   
			 System.out.println("数据库连接失败！");   
			 se.printStackTrace() ;   
			 }
		 System.out.println("Database connected");
	}
	
	public ResultSet query(String sql) {
        ResultSet result = null;
        try{
            result = statement.executeQuery(sql);
        } catch (SQLException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
	public void executeSql(String sql) {
        try{
            statement.execute(sql);
        } catch (SQLException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	 public void printUserInfo(ResultSet result) {
	        try
	        {
	            while(result.next()) {
	                System.out.println("userNname:" + result.getString(1) 
	                        + ", password:" + result.getString(2));
	            }
	        } catch (SQLException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}
