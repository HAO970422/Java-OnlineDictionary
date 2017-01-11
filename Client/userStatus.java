package javaHw2;

public class userStatus {
	
	private String username;
	private String password;
	private boolean online;
	
	public userStatus(){
		username="";
		password = "";
		online = false;
	}
	public userStatus(String name,String password){
		username=name;
		this.password=password;
		online =false;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void serUserpw(String pw){
		password=pw;
	}
	public boolean getOnline()
	{
		return online;
	}
	public void turnDownLine()
	{
		online = false;
	}
	public void turnOnline(){
		online = true;
	}
}
