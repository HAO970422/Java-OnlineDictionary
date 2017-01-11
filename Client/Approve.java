package javaHw2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
//
//	点赞功能
//  Created by 张昊 on 16/12/1.
//  Copyright © 2016年 张昊. All rights reserved.
//

public class Approve 
{
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	public Approve()
	{
		
		try
		{
			//socket = new Socket("localhost",8000);
			socket = new Socket("114.212.131.93",8000);
			toServer= new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString() + '\n');
		}	
	}
	
	public void add(String word, String website, String username)
	{
		//JOptionPane.showMessageDialog(null, "点赞失败 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
		String []list = { word, website,username};
		Mysocket temp = new Mysocket("zan",list, 3);
		//System.out.println(word + website);
		try
		{
			//System.out.println("send");
			toServer.writeObject(temp);
			toServer.flush();
			//System.out.println("return");
			Mysocket answer = (Mysocket)fromServer.readObject();
			
			//接收单词卡or应该接收的反馈消息
			while( !answer.getOrder().equals("success") && !answer.getOrder().equals("fail") )
			{
				String shareWord = answer.getkeyword(1);
				String shareTranslate = answer.getkeyword(2);
				String shareSender = new String("来自 ") + answer.getkeyword(3) + new String(" 的分享：\n");
				JOptionPane.showMessageDialog(null, new String(shareSender + shareWord + "\n" + shareTranslate), " share ", JOptionPane.INFORMATION_MESSAGE);
				answer = (Mysocket)fromServer.readObject();
			}
			//System.out.println(answer.Order);
			if(answer.Order.equals("fail"))
			{
				JOptionPane.showMessageDialog(null, "点赞失败 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "点赞成功 ", " good ", JOptionPane.INFORMATION_MESSAGE);
				
				 //getType()为success 点赞数目,需要加一个参数来显示点赞数目
				 // 从服务器返回一个数字（String类型）,代表当前的点赞数目
				 
			}
		}
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (ClassNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
