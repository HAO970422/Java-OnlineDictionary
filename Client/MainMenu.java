package javaHw2;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MainMenu extends JApplet
{
	private JButton jbtLog;
	private JButton jbtQuit;
	private JButton jbtReg;
	private JLabel status = new JLabel(" Welcome Visitor");
	
	private JTextField jtfWord = new JTextField(12);
	private JButton jbtSearch = new JButton("Search");
	private GetZan NumOfZan = new GetZan();
	
	private JCheckBox jcbBaidu;
	private JCheckBox jcbYoudao;
	private JCheckBox jcbKingsoft;
	
	private JButton ShareBaidu;
	private JButton ShareYoudao;
	private JButton ShareKingsoft;
	private JButton ZanBaidu;
	private JButton ZanYoudao;
	private JButton ZanKingsoft;
	private JTextArea NumOfZanBaidu;
	private JTextArea NumOfZanYoudao;
	private JTextArea NumOfZanKingsoft;
	private Approve zan = new Approve();
	private Share share = new Share();
	
	private JTextArea jtaBaidu = new JTextArea();
	private JTextArea jtaYoudao = new JTextArea();
	private JTextArea jtaKingsoft = new JTextArea();
	
	private boolean online =false;
	public userStatus stat = new userStatus();
	private LoginInterface loginSite ;
	private RegisterInterface registerSite = new RegisterInterface();
	private Logout logoutSite = new Logout();
	
	public void init()
	{
		InitMenu();
		jbtSearch.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(stat.getOnline() == true)
				{
					NumOfZan.init(jtfWord.getText().trim(), NumOfZanBaidu, NumOfZanYoudao, NumOfZanKingsoft, stat.getUsername());
				}
				
				//每次查询之前先清空文本区域
				jtaBaidu.setText("");
				jtaYoudao.setText("");
				jtaKingsoft.setText("");
				
				//判断单词是否含有非字母
				for (int i = 0; i < jtfWord.getText().trim().length(); i++)
				{
					if( !Character.isLetter(jtfWord.getText().trim().charAt(i)))
					{
						JOptionPane.showMessageDialog(null, "Invalid Word", " wrong ", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}
				
				if( !jcbBaidu.isSelected() && !jcbYoudao.isSelected() && !jcbKingsoft.isSelected())
				{
					showBaidu();
					showYoudao();
					showKingsoft();
				}
				else
				{
					if(jcbBaidu.isSelected())
					{
						showBaidu();
					}
					if(jcbYoudao.isSelected())
					{
						showYoudao();
					}
					if(jcbKingsoft.isSelected())
					{
						showKingsoft();
					}	
				}
			}
		});
		jtfWord.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
            	
                int code = e.getKeyCode();
                if(code==KeyEvent.VK_ENTER)
                {
                	if(stat.getOnline() == true)
    				{
    					NumOfZan.init(jtfWord.getText().trim(), NumOfZanBaidu, NumOfZanYoudao, NumOfZanKingsoft, stat.getUsername());
    				}
                	
                	jtaBaidu.setText("");
    				jtaYoudao.setText("");
    				jtaKingsoft.setText("");
    				
    				//判断单词是否含有非字母
    				for (int i = 0; i < jtfWord.getText().trim().length(); i++)
    				{
    					if( !Character.isLetter(jtfWord.getText().trim().charAt(i)))
    					{
    						JOptionPane.showMessageDialog(null, "Invalid Word", " wrong ", JOptionPane.INFORMATION_MESSAGE);
    						break;
    					}
    				}
    				
    				if( !jcbBaidu.isSelected() && !jcbYoudao.isSelected() && !jcbKingsoft.isSelected())
    				{
    					showBaidu();
    					showYoudao();
    					showKingsoft();
    				}
    				else
    				{
    					if(jcbBaidu.isSelected())
    					{
    						showBaidu();
    					}
    					if(jcbYoudao.isSelected())
    					{
    						showYoudao();
    					}
    					if(jcbKingsoft.isSelected())
    					{
    						showKingsoft();
    					}	
    				}
                    e.consume();
                }
            }
        });
		ZanBaidu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if( !jtfWord.getText().trim().isEmpty() || stat.getOnline() == true)
				{
					zan.add(jtfWord.getText().trim(), "baidu", stat.getUsername().trim());
				}
				else if(jtfWord.getText().trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null, " 单词或释义不存在 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(stat.getOnline()==false)
				{
					JOptionPane.showMessageDialog(null, " 用户未登录 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		ZanYoudao.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if( !jtfWord.getText().trim().isEmpty() && stat.getOnline() == true)
				{
					zan.add(jtfWord.getText().trim(), "youdao", stat.getUsername().trim());
				}
				else if(jtfWord.getText().trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null, " 单词或释义不存在 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(stat.getOnline()==false)
				{
					JOptionPane.showMessageDialog(null, " 用户未登录 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		ZanKingsoft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!jtfWord.getText().trim().isEmpty() && stat.getOnline()==true)
				{
					zan.add(jtfWord.getText().trim(), "jinshan", stat.getUsername().trim());
				}
				else if(jtfWord.getText().trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null, " 单词或释义不存在 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(stat.getOnline()==false)
				{
					JOptionPane.showMessageDialog(null, " 用户未登录 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		ShareBaidu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				//弹出一个界面，选择要分享的用户
				//此处需要传入的参数是jtfWord.getText().trim()， jtaBaidu
				if(!jtfWord.getText().trim().isEmpty() && !jtaBaidu.getText().trim().isEmpty() && stat.getOnline() == true)
				{
					share.init(jtfWord.getText().trim(), jtaBaidu.getText().trim(), stat.getUsername().trim());
				}
				else if(jtfWord.getText().trim().isEmpty() || jtaBaidu.getText().trim().isEmpty() )
				{
					JOptionPane.showMessageDialog(null, " 单词或释义不存在 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(stat.getOnline()==false)
				{
					JOptionPane.showMessageDialog(null, " 用户未登录 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				//share.setVisible(true);
			}
		});
		ShareYoudao.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if( !jtfWord.getText().trim().isEmpty() && !jtaYoudao.getText().trim().isEmpty() && stat.getOnline() == true)
				{
					share.init(jtfWord.getText().trim(), jtaYoudao.getText().trim(), stat.getUsername().trim());
					//share.setVisible(true);
				}
				else if(jtfWord.getText().trim().isEmpty() || jtaYoudao.getText().trim().isEmpty() )
				{
					JOptionPane.showMessageDialog(null, " 单词或释义不存在 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(stat.getOnline()==false)
				{
					JOptionPane.showMessageDialog(null, " 用户未登录 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				
				//share.setVisible(true);
			}
		});
		ShareKingsoft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if( !jtfWord.getText().trim().isEmpty() && !jtaKingsoft.getText().trim().isEmpty() && stat.getOnline()==true)
				{
					share.init(jtfWord.getText().trim(), jtaKingsoft.getText().trim(), stat.getUsername().trim());
				}
				else if(jtfWord.getText().trim().isEmpty() || jtaKingsoft.getText().trim().isEmpty() )
				{
					JOptionPane.showMessageDialog(null, " 单词或释义不存在 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(stat.getOnline() == false)
				{
					JOptionPane.showMessageDialog(null, " 用户未登录 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				
				//share.setVisible(true);
			}
		});
		
		jbtLog.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( stat.getOnline() == false)
				{
					loginSite=new LoginInterface(online,status,stat);
					loginSite.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, " 已有用户登录，请先退出 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		
		jbtReg.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				registerSite.setVisible(true);
			}
		});
		jbtQuit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if( stat.getOnline() == true)
				{
					logoutSite.init(status, stat);
				}
				else
				{
					JOptionPane.showMessageDialog(null, " 没有已经登录的用户 ", " wrong ", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		
		/*this.addWindowListener(new WindowAdapter() {
	          @Override
	          public void windowClosing(WindowEvent e)
	          {
	             System.exit(0);
	          }
	      });*/
	}
		
	public void InitMenu()
	{
		//Log Field
		jbtReg = new JButton("注册");
		jbtLog = new JButton("登录");
		jbtQuit = new JButton("退出");
		JPanel jplLog = new JPanel(new GridLayout(1,3,5,5));
		jplLog.add(jbtReg);
		jplLog.add(jbtLog);
		jplLog.add(jbtQuit);
		
		//Title Icon Field
		ImageIcon tI = new ImageIcon("../Icon/title.png");
		tI.setImage(tI.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)); //设置图片大小
		JLabel jlbTitleIcon = new JLabel("Dictionary", tI, SwingConstants.CENTER);
		
		//Merge Log and Title Icon
		JPanel jplTitle = new JPanel(new BorderLayout());
		jplTitle.add(jlbTitleIcon, BorderLayout.CENTER);
		jplTitle.add(jplLog, BorderLayout.EAST);
		jplTitle.add(status, BorderLayout.WEST);
		
		//Search Field
		JPanel jplSearch = new JPanel(new BorderLayout());
		jplSearch.add(new JLabel(" Enter Word Here"),  BorderLayout.WEST);
		jplSearch.add(jtfWord, BorderLayout.CENTER);
		jplSearch.add(jbtSearch, BorderLayout.EAST);
		
		//Merge Title and Search Field--------------p1
		JPanel p1 = new JPanel(new BorderLayout(5,10));
		p1.add(jplTitle, BorderLayout.NORTH);
		p1.add(jplSearch,BorderLayout.CENTER);
		
		//CheckBox
		/*ImageIcon baidu = new ImageIcon("../Icon/baidu.png");
		baidu.setImage(baidu.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT));
		ImageIcon youdao = new ImageIcon("../Icon/youdao.png");
		youdao.setImage(youdao.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon kingsoft = new ImageIcon("../Icon/kingsoft.png");
		kingsoft.setImage(youdao.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));*/
		jcbBaidu = new JCheckBox();
		jcbYoudao = new JCheckBox();
		jcbKingsoft = new JCheckBox();
		JPanel jplCheckBoxes = new JPanel(new GridLayout(3,1,5,10));
		jplCheckBoxes.add(jcbBaidu);
		jplCheckBoxes.add(jcbYoudao);
		jplCheckBoxes.add(jcbKingsoft);
		
		//Baidu-----------------p2
		ImageIcon share = new ImageIcon("../Icon/share.png");
		share.setImage(share.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ShareBaidu = new JButton(share);
		ImageIcon zan = new ImageIcon("../Icon/zan.png");
		zan.setImage(zan.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ZanBaidu = new JButton(zan);
		NumOfZanBaidu = new JTextArea(1,3);
		
		JPanel jplZanShareBaidu = new JPanel(new BorderLayout(0,5));
		jplZanShareBaidu.add(ShareBaidu, BorderLayout.NORTH);
		jplZanShareBaidu.add(ZanBaidu,BorderLayout.CENTER);
		jplZanShareBaidu.add(NumOfZanBaidu,BorderLayout.SOUTH);
		
		JPanel p2 = new JPanel(new BorderLayout());
		//p2.setBorder(new TitledBorder("BaiDu"));
		p2.setBorder(new TitledBorder("Bing"));
		p2.add(jplZanShareBaidu, BorderLayout.EAST);
		p2.add(new JScrollPane(jtaBaidu), BorderLayout.CENTER);
		jtaBaidu.setEditable(false);
		
		//Youdao----------------p3
		ShareYoudao = new JButton(share);
		ZanYoudao = new JButton(zan);
		NumOfZanYoudao = new JTextArea(1,3);
		
		JPanel jplZanShareYoudao = new JPanel(new BorderLayout(0,5));
		jplZanShareYoudao.add(ShareYoudao,BorderLayout.NORTH);
		jplZanShareYoudao.add(ZanYoudao,BorderLayout.CENTER);
		jplZanShareYoudao.add(NumOfZanYoudao,BorderLayout.SOUTH);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBorder(new TitledBorder("YouDao"));
		p3.add(new JScrollPane(jtaYoudao), BorderLayout.CENTER);
		p3.add(jplZanShareYoudao, BorderLayout.EAST);
		jtaYoudao.setEditable(false);
		
		//Kingsoft-----------------------p4
		ShareKingsoft = new JButton(share);
		ZanKingsoft = new JButton(zan);
		NumOfZanKingsoft = new JTextArea(1,3);
		
		JPanel jplZanShareKingsoft = new JPanel(new BorderLayout(0,5));
		jplZanShareKingsoft.add(ShareKingsoft,BorderLayout.NORTH);
		jplZanShareKingsoft.add(ZanKingsoft,BorderLayout.CENTER);
		jplZanShareKingsoft.add(NumOfZanKingsoft,BorderLayout.SOUTH);
		
		JPanel p4 = new JPanel(new BorderLayout());
		p4.setBorder(new TitledBorder("KingSoft"));
		p4.add(new JScrollPane(jtaKingsoft), BorderLayout.CENTER);
		p4.add(jplZanShareKingsoft, BorderLayout.EAST);
		jtaKingsoft.setEditable(false);
		
		//Merge baidu, youdao and  kingsoft
		JPanel jplArea = new JPanel(new GridLayout(3,1,5,5));
		jplArea.add(p2);
		jplArea.add(p3);
		jplArea.add(p4);
		
		//Merge CheckBox and pArea
		JPanel jpl = new JPanel(new BorderLayout());
		jpl.add(jplCheckBoxes, BorderLayout.WEST);
		jpl.add(jplArea, BorderLayout.CENTER);
		
		//finally merge p1 and jpl
		setLayout(new BorderLayout());
		add(p1,BorderLayout.NORTH);
		add(jpl,BorderLayout.CENTER);
		setSize(600, 500);
		
	}
	public void showBaidu()
	{
		BaiduView BD = new BaiduView(jtfWord.getText().trim(), jtaBaidu);
		BD.Search();
	}
	public void showYoudao()
	{
		YoudaoView YD = new YoudaoView(jtfWord.getText().trim(), jtaYoudao);
		YD.Search();
	}
	public void showKingsoft()
	{
		KingsoftView KF = new KingsoftView(jtfWord.getText().trim(), jtaKingsoft);
		KF.Search();
	}
}
