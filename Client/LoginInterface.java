package javaHw2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.*;
public class LoginInterface extends JDialog
{
		private JLabel label1 = new JLabel("UserName");
		private JLabel label2 = new JLabel("Password");
		private JTextField name = new JTextField(8);
		private JTextField pw = new JTextField(8);
		
		private JButton jbtOk = new JButton("Login");
		private JButton jbtCancel = new JButton("Cancel");
		
		private Socket socket;
		private ObjectOutputStream toServer;
		private ObjectInputStream fromServer;
		
		private boolean online;
		private JLabel userm;
		private userStatus stat;

		public LoginInterface (boolean online,JLabel userm,userStatus temp){
			this(null,true,online,userm,temp);
		}
		
		public LoginInterface(Frame parent,boolean modal,boolean online,JLabel userm,userStatus temp)
		{
			super(parent,modal);
			
			//socket= s;
			this.online = online;
			this.userm = userm;
			stat=temp;
			
			JPanel jpButtons = new JPanel();
			jpButtons.add(jbtOk);
			jpButtons.add(jbtCancel);
			
			JPanel LoginSite = new JPanel();
			LoginSite.setLayout(new GridLayout(2,2,5,5));
			LoginSite.add(label1);
			LoginSite.add(name);
			LoginSite.add(label2);
			LoginSite.add(pw);
			
			setTitle("Login");
			add(jpButtons,BorderLayout.SOUTH);
			add(LoginSite,BorderLayout.CENTER);
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
			
			jbtOk.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String username = name.getText();
					String pass = pw.getText();
						
					 if(username.isEmpty() || pass.isEmpty())
					 {
						JOptionPane.showMessageDialog(null, " Error：用户名或密码为空 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
						return;
						//username = name.getText();
						//pass = pw.getText();
					 }
					
					Mysocket temp = new Mysocket("login",username,pass);
					try 
					{
						
						toServer.writeObject(temp);
						toServer.flush();
						
				//		fromServer = new ObjectInputStream(socket.getInputStream());
						Mysocket answer = (Mysocket) fromServer.readObject();
						
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
							userm.setText("Welcome,"+username);
							stat.setUsername(username);
							stat.serUserpw(pass);
							stat.turnOnline();
							setVisible(false);
						}
						else if(answer.getOrder().equals("fail"))
						{
							JOptionPane.showMessageDialog(null,
	                                "登入失败 ",
	                                " wrong ",
	                                JOptionPane.INFORMATION_MESSAGE);
							setVisible(false);
						}
						
					} 
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
						
					
					//setVisible(false);
				}				
			});
			pw.addKeyListener(new KeyAdapter()
			{
				 public void keyPressed(KeyEvent e)
				 {
					// TODO Auto-generated method stub
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
							 
							Mysocket temp = new Mysocket("login",username,pass);
							try 
							{
								
								toServer.writeObject(temp);
								toServer.flush();
								
						//		fromServer = new ObjectInputStream(socket.getInputStream());
								Mysocket answer = (Mysocket) fromServer.readObject();
								
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
									userm.setText("Welcome,"+username);
									stat.setUsername(username);
									stat.serUserpw(pass);
									stat.turnOnline();
									setVisible(false);
								}
								else if(answer.getOrder().equals("fail"))
								{
									JOptionPane.showMessageDialog(null,
			                                "登入失败 ",
			                                " wrong ",
			                                JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
								}
								
							} 
							catch (IOException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ClassNotFoundException e1) 
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}	
		             }
						
				 }
			});
			jbtCancel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					setVisible(false);
				}				
			});

		}
}
