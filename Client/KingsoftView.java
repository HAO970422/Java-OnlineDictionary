package javaHw2;
//
//	金山词霸查询
//  Created by 张昊 on 16/12/1.
//  Copyright © 2016年 张昊. All rights reserved.
//
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import java.io.IOException;
import java.net.*;

public class KingsoftView 
{
	private String word = new String();
	private JTextArea jta = new JTextArea();
	public KingsoftView()
	{
	}
	
	public KingsoftView(String w, JTextArea j)
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
			url = new URL(new String("http://www.iciba.com/") + word);
			input = new java.util.Scanner(url.openStream());
			jta.setText("");
			while(input.hasNext())
			{
				content += input.nextLine();
			}
			
			Pattern pattern = Pattern.compile("<ul class=\"base-list switch_part\" class=\"\">                                                    <li class=\"clearfix\">(.+?)</ul>");
			Matcher matcher = pattern.matcher(content);
			if(matcher.find())
			{
				String group = matcher.group(1).replaceAll(" ", "").replaceAll("</span><span>", "").replaceAll("</span><p><span>", "").replaceAll("<spanclass=\"prop\">", "").replaceAll("</span></p></li>", "").replaceAll("<liclass=\"clearfix\">", "\n");
				jta.append(group);
				
			}
			else
			{
				jta.append("抱歉，金山词库中没有收录与“" + word + "”有关的结果!");
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
