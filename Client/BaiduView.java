package javaHw2;
//
//	百度词典查询
//  Created by 张昊 on 16/12/1.
//  Copyright © 2016年 张昊. All rights reserved.
//
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import java.io.IOException;
import java.net.*;

public class BaiduView 
{
	private String word = new String();
	private JTextArea jta = new JTextArea();
	
	public BaiduView()
	{
	}
	
	public BaiduView(String w, JTextArea j)
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
			url = new URL(new String("http://cn.bing.com/dict/search?q=") + word + new String("&go=搜索&qs=n&form=Z9LH5&sp=-1&pq=") +word);
			input = new java.util.Scanner(url.openStream());
			jta.setText("");
			while(input.hasNext())
			{
				content +=input.nextLine();
			}
			
			Pattern pattern = Pattern.compile("/><meta name=\"description\" content=\"必应词典为您提供"+ word +"的释义，(.+?)\" /><meta");
			Matcher matcher = pattern.matcher(content);
			if(matcher.find())
			{
				/*String group = matcher.group(1).replaceAll(" ","");
				
				Pattern p = Pattern.compile("<strong>(.+?)</span>");
				Matcher m = p.matcher(group);
				while(m.find())
				{
					jta.append(m.group(1).replaceAll("</strong><span>", "") + '\n');
				}*/
				String group = matcher.group(1);
				String[] target1 = group.split("，");
				jta.append(target1[0] + ", ");
				jta.append(target1[1] + '\n');
				//int index = group.lastIndexOf("，");
				String[] target2 = target1[2].split("； ");
				for( String i : target2)
					jta.append(i + '\n');
			}
			else
			{
				jta.append("抱歉，百度词典中没有收录与“" + word + "”有关的结果!");
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
	/*
	public void Search()
	{
		Scanner input = null;
		URL url = null;
		String content = new String();
		try
		{
			//http://cn.bing.com/dict/search?q=find&go=搜索&qs=n&form=Z9LH5&sp=-1&pq=find&sc=8-4&sk=&cvid=41A5EED16E094090B456A903F95799D3
			url = new URL(new String("http://dict.baidu.com/s?wd=") + word + new String("&device=pc&from=home&q=") + word);
			input = new java.util.Scanner(url.openStream());
			jta.setText("");
			while(input.hasNext())
			{
				content +=input.nextLine();
			}
			
			Pattern pattern = Pattern.compile("<div class=\"en-content\">(.+?)</div>");
			Matcher matcher = pattern.matcher(content);
			if(matcher.find())
			{
				String group = matcher.group(1).replaceAll(" ","");
				
				Pattern p = Pattern.compile("<strong>(.+?)</span>");
				Matcher m = p.matcher(group);
				while(m.find())
				{
					jta.append(m.group(1).replaceAll("</strong><span>", "") + '\n');
				}
			}
			else
			{
				jta.append("抱歉，百度词典中没有收录与“" + word + "”有关的结果!");
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
	}	*/
}
