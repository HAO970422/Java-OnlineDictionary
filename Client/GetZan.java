package javaHw2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class GetZan
{
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	public GetZan()
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
	
	public void init(String word, JTextArea bd, JTextArea yd, JTextArea ks, String username)
	{
		Mysocket temp = new Mysocket("search", word, username);
		try
		{
			toServer.writeObject(temp);
			toServer.flush();
			
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
			
			if(answer.Order.equals("success"))
			{
				bd.setText("    " + answer.getkeyword(1));
				yd.setText("    " + answer.getkeyword(2));
				ks.setText("    " + answer.getkeyword(3));
			}
			else
			{
				JOptionPane.showMessageDialog(null, "获取赞失败 ", " good ", JOptionPane.INFORMATION_MESSAGE);
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
