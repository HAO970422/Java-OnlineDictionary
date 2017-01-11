package javaHw2;
//
//一个socket包，继承SocketType类
//Created by 张昊 on 16/12/1.
//Copyright © 2016年 张昊. All rights reserved.
//

import java.io.Serializable;

@SuppressWarnings("serial")
public class Mysocket implements Serializable{
	String Order;
	String keyword[];
	int numofkeyword;
	
	public String getOrder(){
		return Order;
	}
	
	Mysocket(String order){
		this.Order = order;
		numofkeyword = 0;
	}
	
	Mysocket(String order,String word){
		this.Order=order;
		numofkeyword=1;
		keyword= new String[1];
		keyword[0]=word;
	}
	Mysocket(String order,String username,String pw){
		this.Order=order;
		numofkeyword= 2;
		keyword= new String[2];
		keyword[0]=username;
		keyword[1]=pw;
	}
	Mysocket(String order,String[] list,int n){
		this.Order=order;
		numofkeyword = n;
		keyword = list;
	}
	String getkeyword(int n){
		return keyword[n-1];
	}
	String[] getallkeyword(){
		return keyword;
	}
}
