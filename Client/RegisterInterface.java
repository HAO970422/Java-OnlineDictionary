package javaHw2;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RegisterInterface extends JDialog{

	private JLabel label1 = new JLabel("UserName");
	private JLabel label2 = new JLabel("Password");
	private JTextField name = new JTextField(8);
	private JTextField pw = new JTextField(8);
	
	private JButton jbtOk = new JButton("Register");
	private JButton jbtCancel = new JButton("Cancel");
	
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	public RegisterInterface ()
	{
		this(null,true);
	}
	
	public RegisterInterface(Frame parent,boolean modal)
	{
		super(parent,modal);

		JPanel jpButtons = new JPanel();
		jpButtons.add(jbtOk);
		jpButtons.add(jbtCancel);
		
		JPanel jplSite = new JPanel();
		jplSite.setLayout(new GridLayout(2,2,5,5));
		jplSite.add(label1);
		jplSite.add(name);
		jplSite.add(label2);
		jplSite.add(pw);
		
		setTitle("REG");
		add(jpButtons,BorderLayout.SOUTH);
		add(jplSite,BorderLayout.CENTER);
		pack();
		
		try
		{
			//socket = new Socket("localhost",8000);
			socket = new Socket("114.212.131.93",8000);
			toServer= new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());	
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString()+'\n');
		}
		
		jbtOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				String username = name.getText();
				String pass = pw.getText();
				 while(username.isEmpty() || pass.isEmpty())
				 {
					 JOptionPane.showMessageDialog(null, " Error：用户名或密码为空 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
					 return;
					  //username = name.getText();
					  //pass = pw.getText();
				 }
				Mysocket temp = new Mysocket("register",username,pass);
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
					//System.out.println(answer.Order);
					if(answer.Order.equals("fail"))
					{
						JOptionPane.showMessageDialog(null, "注册失败 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "注册成功 ", " good ", JOptionPane.INFORMATION_MESSAGE);
						
					}
				} 
				catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);		
			}				
		});
		pw.addKeyListener(new KeyAdapter()
		{
			 public void keyPressed(KeyEvent e)
			 {
				 int code = e.getKeyCode();
	             if(code==KeyEvent.VK_ENTER)
	             {
	            	 String username = name.getText();
					 String pass = pw.getText();
					 while(username.isEmpty() || pass.isEmpty())
					 {
						 JOptionPane.showMessageDialog(null, " Error：用户名或密码为空 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
						 return;
						//username = name.getText();
						//pass = pw.getText();
					 }
					
					Mysocket temp = new Mysocket("register",username,pass);
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
						//System.out.println(answer.Order);
						if(answer.Order.equals("fail"))
						{
							JOptionPane.showMessageDialog(null, "注册失败 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "注册成功 ", " good ", JOptionPane.INFORMATION_MESSAGE);
							
						}
					} 
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false); 
	             }
			}
		});
		jbtCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				name.setText(null);
				pw.setText(null);
				setVisible(false);
			}				
		});
	}
}
