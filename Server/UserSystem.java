package javaHw2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSystem {
	public boolean online;
	MYSQLCONNECT database ;
	
	public static void main (String[] args){
		UserSystem temp = new UserSystem();
		temp.login("andy", "andyh");
		temp.register("an", "123456");
		temp.login("an", "123456");
		temp.getUserlist();
		
	}
	public UserSystem(){
		online = false;
		database = new MYSQLCONNECT();
	}
	
	public boolean login(String username,String password){
		String sql = "select * from userlist where username = '"+username+"'";
		ResultSet result = database.query(sql);
		//database.printUserInfo(result);
		if(result==null){
			online = false;
		}else {
			String pwR;
			try {
				pwR=" ";
				while(result.next()){
					pwR=result.getString(2);
				}
				if(pwR.equals(password)){
						online = true;
						sql = "update userlist set online = 1 where username = '"+username+"'";
						database.executeSql(sql);
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return online;
	}
	
	public boolean register(String username,String password){
		String sql = "select * from userlist where username = '"+username+"'";
		ResultSet result = database.query(sql);
		try {
			
			if(!result.next()){
				sql = "insert into userlist values('"+username+"','"+password+"',0)";
				database.executeSql(sql);
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean logout(String username){
		String sql = "select * from userlist where username = '"+username+"'";
		ResultSet result = database.query(sql);
		try {
			if(result.next()){
				online = false;
				sql = "update userlist set online = 1 where username = '"+username+"'";
				database.executeSql(sql);
				return true;
			}
			else return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public String[] getUserlist(){
		String sql = "select * from userlist where online = 1";
		
		ResultSet result = database.query(sql);
		
		String[] list;
		try {
			result.last();
			int line = result.getRow();
			list= new String[line];
			result.beforeFirst();
			int i =0;
			while(result.next()){
				//System.out.println(result.getString(1));
				String u = result.getString(1);
				list[i]=u;
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
