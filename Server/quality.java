package javaHw2;


import java.sql.ResultSet;
import java.sql.SQLException;

public class quality {
	MYSQLCONNECT database ;
	
	public static void main (String[] args){
		quality temp = new quality();
		temp.add("apple", "baidu");
		temp.add("apple", "jinshan");
		temp.add("beer", "jinshan");
		temp.search("apple");
		temp.search("net");
		temp.search("good");
	}
	public quality(){
		//online = false;
		database = new MYSQLCONNECT();
	}
	
	public boolean add(String word,String kind){
		String sql = "select * from wordlist where word = '"+word+"'";
		ResultSet result = database.query(sql);
		try {
			if(result.next()){
				sql = "update wordlist set "+kind+" = "+kind+"+1 where word = '"+word+"'";
				database.executeSql(sql);
			}else{
				sql = "insert into wordlist(word,baidu,youdao,jinshan)values('"+word+"',0,0,0)";
				database.executeSql(sql);
				sql = "update wordlist set "+kind+" = "+kind+"+1 where word = '"+word+"'";
				database.executeSql(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public String[] search(String word){
		String sql = "select * from wordlist where word = '"+word+"'";
		ResultSet result = database.query(sql);
		String []zan=new String[3];
		try{
			if(result.next()){
				int baidu = result.getInt("baidu");
				int youdao = result.getInt("youdao");
				int jinshan = result.getInt("jinshan");
				
				zan[0]=Integer.toString(baidu);
				zan[1]=Integer.toString(youdao);
				zan[2]=Integer.toString(jinshan);
				//return zan;
				System.out.println(baidu);
				System.out.println(youdao);
				System.out.println(jinshan);
			}else{
				sql = "insert into wordlist(word,baidu,youdao,jinshan)values('"+word+"',0,0,0)";
				database.executeSql(sql);
				int baidu =0;
				int youdao = 0;
				int jinshan = 0;
				zan[0]="0";
				zan[1]="0";
				zan[2]="0";
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	return 0;
		return zan;
	}
}
