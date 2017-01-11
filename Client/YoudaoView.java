package javaHw2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import java.io.IOException;
import java.net.*;

public class YoudaoView 
{
	private String word = new String();
	private JTextArea jta = new JTextArea();
	public YoudaoView()
	{
	}
	
	public YoudaoView(String w, JTextArea j)
	{
		this.word = w;
		this.jta = j;
	}
	
	public void Search()
	{
		Scanner input = null;
		URL url = null;
		String content = new String();
		try
		{
			url = new URL(new String("http://dict.youdao.com/w/") + word + new String("/#keyfrom=dict2.top"));
			input = new java.util.Scanner(url.openStream());
			jta.setText("");
			while(input.hasNext())
			{
				content += input.nextLine();
				//jta.append(input.nextLine() + '\n');
			}
			//jta.append(content);
			Pattern pattern = Pattern.compile("</div>            </h2>             <div class=\"trans-container\">(.+?)</div>    </div>");
			Matcher matcher = pattern.matcher(content);
			if(matcher.find())
			{
				String group = matcher.group(1).replaceAll(" ", "");
				//jta.append(group);
				Pattern p = Pattern.compile("<li>(.+?)</li>");
				Matcher m = p.matcher(matcher.group(1));
				while(m.find())
				{
					jta.append(m.group(1) + '\n');
					//m.find();
				}
			}
		}
		catch(MalformedURLException ex)
		{
			jta.setText("URL not found.");
		}
		catch(IOException e)
		{
			jta.setText(e.getMessage());
		}
		finally
		{
			if(input != null)
				input.close();
		}
	}
}
