package com.taokn.crawler.crawler_webmagic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static Properties getProperties(String path) throws FileNotFoundException {
		InputStream inStream = new FileInputStream(new File(path));
		Properties p = new Properties();
		try {
			p.load(inStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p;
	}
	
	public static List<String> readLine(String filePath) {
		List<String> resultList = new ArrayList<>();
        try {
        	File file = new File(filePath);
            //文件不存在时候，主动穿件文件。
            if(!file.exists()){
                file.createNewFile();
            }
        	InputStreamReader read = new InputStreamReader(new FileInputStream(filePath),"utf-8");
        	BufferedReader reader=new BufferedReader(read);       
            String line;       
            while ((line = reader.readLine()) != null)   
            {        
            	resultList.add(line.trim());
            }         
            read.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }
	
	/**
	 * 获取指定HTML标签的指定属性的值
	 * @param source 要匹配的源文本
	 * @param element 标签名称
	 * @param attr 标签的属性名称
	 * @return 属性值列表
	 */
	public static List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1).trim();
			result.add(r);
		}
		return result;
	}
	
	public static void fileChaseFW(String filePath, List<String> contentList) {
        try {
            //构造函数中的第二个参数true表示以追加形式写文件
        	File file = new File(filePath);
            //文件不存在时候，主动穿件文件。
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath,true);
            for(String content: contentList) {
            	fw.write(content);
                fw.write("\r\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("文件写入失败！" + e);
        }
    }
	
	public static void fileChaseFW(String filePath, String content) {
        try {
            //构造函数中的第二个参数true表示以追加形式写文件
        	File file = new File(filePath);
            //文件不存在时候，主动穿件文件。
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath,true);
        	fw.write(content);
            fw.write("\r\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("文件写入失败！" + e);
        }
    }
}
