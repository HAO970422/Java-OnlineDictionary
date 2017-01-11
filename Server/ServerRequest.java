package javaHw2;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;

public class ServerRequest implements Runnable{
	 Socket clientRequest;
	 ObjectInputStream inputFromClient ;
	 ObjectOutputStream outputToClient ;
	 private JTextArea jta;
	 private UserSystem us = new UserSystem();
	 private quality qu = new quality();
	 private Vector<String> sendTo;
	 private Vector<String> word;
	 private Vector<String> trans;
	 private Vector<String> sendfr;
	 public ServerRequest(Socket s,JTextArea temp,Vector<String>sendTo, Vector<String>word,Vector<String>Trans,Vector<String>sendFr){
		 this.clientRequest = s;
		 this.jta = temp;
		 this.sendTo = sendTo;
		 this.word = word;
		 this.trans= Trans;
		 this.sendfr = sendFr;
	 }
	 
	 public int check(String u){
		 for (int i =0;i<sendTo.size();i++){
			 if(sendTo.get(i).equals(u)){
				 return i;
			 }
		 }
		 return -1;
	 }
	 
	 public void run(){
		 Mysocket object=null;
//		 boolean done = false;
		 try { // 初始化输入、输出流  
	            inputFromClient = new ObjectInputStream(clientRequest.getInputStream());  
	            outputToClient = new ObjectOutputStream(clientRequest.getOutputStream());  
	          Mysocket ans;
	            while(true){
	            	object = (Mysocket)inputFromClient.readObject();
	            	String user=object.getkeyword(object.numofkeyword);
	            	int i = check(user);
	            	if(i!=-1){
	            		String[] lis= new String[3];
	            		lis[0]=word.get(i);
	            		lis[1]=trans.get(i);
	            		lis[2]=sendfr.get(i);
	            		Mysocket wordCard = new Mysocket("wordcard",lis,3);
	            		word.remove(i);
	            		sendTo.remove(i);
	            		trans.remove(i);
	            		sendfr.remove(i);
	            		outputToClient.writeObject(wordCard);
    	            	outputToClient.flush();
    	            	i = check(user);
	            	}
	            	String order= object.getOrder();
	            	switch(order){
	            	case "login":
	            				if(us.login(object.getkeyword(1),object.getkeyword(2))){
	            					jta.append(object.getkeyword(1)+" login success at " +new Date()+'\n');
	            					System.out.println("login success.");
	            					 ans = new Mysocket("success");
	            	            	outputToClient.writeObject(ans);
	            	            	outputToClient.flush();
	            				}
	            				else {
	            					System.out.println("login fail.");
	            					 ans = new Mysocket("fail");
	            					outputToClient.writeObject(ans);
	            	            	outputToClient.flush();
	            				}
	            				break;
	            	case "register":
	            			if(us.register(object.getkeyword(1),object.getkeyword(2))){
	            				System.out.println("regist success.");
	            				jta.append("New member: "+object.getkeyword(1)+" register success at " +new Date()+'\n');
	            				 ans = new Mysocket("success");
	            				outputToClient.writeObject(ans);
	            				outputToClient.flush();
	            			}
	            			else {
	            				System.out.println("regist fail.");
	            				 ans = new Mysocket("fail");
	            				outputToClient.writeObject(ans);
	            				outputToClient.flush();
	            			}break;
	            	case "zan":
	            		jta.append(object.getkeyword(2)+" get a good point of "+object.getkeyword(1)+'\n');
	            		qu.add(object.getkeyword(1), object.getkeyword(2));
	            		ans = new Mysocket("success");
	            		outputToClient.writeObject(ans);
        				outputToClient.flush();break;
	            	case "search":String zan[]=qu.search(object.getkeyword(1));
	            				ans = new Mysocket("success",zan,3);
	            				outputToClient.writeObject(ans);
	            				outputToClient.flush();break;
	            				
	            	case "logout": //temp = (user)object;
    				if(us.logout(object.getkeyword(1))){
    					jta.append(object.getkeyword(1)+" logout success at " +new Date()+'\n');
    					System.out.println("logout success.");
    					 ans = new Mysocket("success");
    	            	outputToClient.writeObject(ans);
    	            	outputToClient.flush();
    				}
    				else {
    					System.out.println("logout fail.");
    					 ans = new Mysocket("fail");
    					outputToClient.writeObject(ans);
    	            	outputToClient.flush();
    				}break;
	            	case "getlist":{
	            		jta.append("someone want to share\n");
	            		String []list = us.getUserlist();
	            		int n = list.length;
	            		 ans = new Mysocket("success",list,n);
	            		outputToClient.writeObject(ans);
		            	outputToClient.flush();
	            	}break;
	            	case "share":{
	            		String uSendTo = object.getkeyword(3);
	            		String uWord = object.getkeyword(1);
	            		String uTrans = object.getkeyword(2);
	            		String uSendFr = object.getkeyword(4);
	            		sendTo.add(uSendTo);
	            		word.add(uWord);
	            		trans.add(uTrans);
	            		sendfr.add(uSendFr);
	            		ans = new Mysocket("success");
	            		outputToClient.writeObject(ans);
		            	outputToClient.flush();
	            	}break;
	            	default:break;
	            	}
	            	/*Mysocket ans = new Mysocket("fin");
	            	outputToClient.writeObject(ans);
	            	outputToClient.flush();*/
	            }
	   
		 } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	     }  catch(ClassNotFoundException e){
	    	 System.out.println(e.getMessage());  
	     }
		/* try {  
	            clientRequest.close(); // 关闭套接字  
	        } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	     }*/ 
	 }
	 
}
