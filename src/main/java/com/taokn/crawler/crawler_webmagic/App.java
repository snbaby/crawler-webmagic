package com.taokn.crawler.crawler_webmagic;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import com.taokn.crawler.crawler_webmagic.constant.Constant;
import com.taokn.crawler.crawler_webmagic.util.Crawler;
import com.taokn.crawler.crawler_webmagic.util.Utils;

/**
 * sn_baby@qq.com
 *
 */
public class App {
	private static Properties applicationProperties;

	public static void main(String[] args) throws FileNotFoundException {
		BasicConfigurator.configure();
		// 判斷參數是否正常
		if (args.length != 1) {
			System.out.println("请传入配置文件的地址");
			return;
		}
		// 判斷參數文件是否存在
		applicationProperties = Utils.getProperties(args[0]);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = new Date();
		Utils.fileChaseFW(applicationProperties.getProperty(Constant.GOODS_ID_OUTPUT_PATH),
				"----start----" + df.format(startDate) + "----start----");
		Utils.fileChaseFW(applicationProperties.getProperty(Constant.HISTORY_GOODS_ID_OUTPUT_PATH),
				"----start----" + df.format(startDate) + "----start----");
		Crawler crawler = new Crawler(applicationProperties);
		crawler.start();
		crawler.outPut();
		Date endDate = new Date();
		System.out.println("----start--" + df.format(startDate));
		System.out.println("----end--" + df.format(endDate));
		Utils.fileChaseFW(applicationProperties.getProperty(Constant.GOODS_ID_OUTPUT_PATH),
				"----end----" + df.format(endDate) + "----end----");
		Utils.fileChaseFW(applicationProperties.getProperty(Constant.HISTORY_GOODS_ID_OUTPUT_PATH),
				"----end----" + df.format(endDate) + "----end----");
	}
}
