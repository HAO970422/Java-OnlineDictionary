package javaHw2;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Share extends JDialog
{
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	private JScrollPane userlist = new JScrollPane();
	private JList <String> jltUser = new JList <String>();
	private JButton jbtShare = new JButton("share");
	private JButton jbtCancel = new JButton("cancel");
	private String shareUser = new String();
	
	private String word = new String();
	private String explain = new String();
	private String username = new String();
	
	public Share()
	{
		try
		{
			//socket = new Socket("localhost",8000);
			socket = new Socket("114.212.131.93",8000);
			this.toServer= new ObjectOutputStream(socket.getOutputStream());
			this.fromServer = new ObjectInputStream(socket.getInputStream());
			
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString() + '\n');
		}
		
	}
	
	public void init(String w, String e, String username)
	{
		word = w;
		explain = e;
		this.username = username;
		
		Mysocket temp = new Mysocket("getlist", username);
		try {
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
			
			
			if(answer.getOrder().equals("success"))
			{
				
				//jltUser = new JList<String>(answer.getallkeyword());
				jltUser.setListData(answer.getallkeyword());
				userlist = new JScrollPane(jltUser);
				//setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, " 分享失败！（用户列表获取失败） ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
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
		
		JPanel jplOption = new JPanel();
		jplOption.add(jbtShare);
		jplOption.add(jbtCancel);
		
		userlist = new JScrollPane(jltUser);
		setLayout(new BorderLayout());
		add(userlist, BorderLayout.CENTER);
		add(jplOption, BorderLayout.SOUTH);
		pack();
		setVisible(true);
		
		jltUser.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				shareUser = jltUser.getSelectedValue();
			}	
		});
		//JOptionPane.showMessageDialog(null, "分享失败 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
		jbtShare.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				//也可以默认为空的话就群发分享
				if(shareUser.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "请选择要分享的用户！", " wrong ", JOptionPane.INFORMATION_MESSAGE);
					//return;
				}
				//更改为三个参数，包括单词，释义，分享用户，type
				String[] list = {word, explain, shareUser,username};
				Mysocket temp = new Mysocket("share",list, 4);
				
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
					
					if(answer.getOrder().equals("fail"))
					{
						JOptionPane.showMessageDialog(null, " 分享失败，请重试 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, " 分享成功 ", " Great ", JOptionPane.INFORMATION_MESSAGE);
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
				finally
				{
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
				setVisible(false);
			}				
		});
	}
}
