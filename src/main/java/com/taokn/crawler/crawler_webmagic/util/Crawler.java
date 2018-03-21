package com.taokn.crawler.crawler_webmagic.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.taokn.crawler.crawler_webmagic.constant.Constant;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;

public class Crawler {

	// 待查询的商品
	private List<String> goodsNameList = new ArrayList<>();
	// 商家白名单
	private List<String> bussinessWhiteList = new ArrayList<>();
	// 工具JS文件存放地址
	private String crawlJsPath = "";
	// 工具EXE文件存放地址
	private String phantomJsExePath = "";
	// 查询页数
	private int queryPageNumber = 1;
	// 商品ID输出地址
	private String goodsIdOutputPath = "";
	// 商品Id输出的List
	private List<String> goodsIdOutputList = new ArrayList<>();
	// 商品ID历史文件地址
	private String historyGoodsIdOutputPath = "";
	// 商品Id输出的List
	private List<String> historyGoodsIdOutputList = new ArrayList<>();
	// 需要查询的商品URL
	private List<String> goodsNameUrlList = new ArrayList<>();

	public Crawler(Properties applicationProperties) {
		goodsNameList = Utils.readLine(applicationProperties.getProperty(Constant.GOODS_NAME_INPUT_PATH));
		bussinessWhiteList = Utils.readLine(applicationProperties.getProperty(Constant.BUSSINESS_WHITE_LIST_PATH));
		crawlJsPath = applicationProperties.getProperty(Constant.CRAWL_JS_PATH);
		phantomJsExePath = applicationProperties.getProperty(Constant.PHANTOMJS_EXE_PATH);
		queryPageNumber = Integer.parseInt(applicationProperties.getProperty(Constant.QUERY_PAGE_NUMBER));
		goodsIdOutputPath = applicationProperties.getProperty(Constant.GOODS_ID_OUTPUT_PATH);
		historyGoodsIdOutputPath = applicationProperties.getProperty(Constant.HISTORY_GOODS_ID_OUTPUT_PATH);
		historyGoodsIdOutputList = Utils.readLine(historyGoodsIdOutputPath);
		for (int index = 0; index < queryPageNumber; index++) {
			for (String goodsName : goodsNameList) {
				try {
					goodsNameUrlList.add("https://s.taobao.com/search?q=" + URLEncoder.encode(goodsName, "utf-8")
							+ "&s=" + index * 44);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void start() {
		System.out.println("-----------------------goodsNameUrlList"+goodsNameUrlList.size());
		PhantomJSDownloader phantomDownloader = new PhantomJSDownloader(phantomJsExePath, crawlJsPath).setRetryNum(3);
		CollectorPipeline<ResultItems> collectorPipeline = new ResultItemsCollectorPipeline();
		Spider.create(new TaoBaoPageProcessor()).addUrl(goodsNameUrlList.toArray(new String[goodsNameUrlList.size()]))
				.setDownloader(phantomDownloader).addPipeline(collectorPipeline)
				.thread(20).run();
		//(Runtime.getRuntime().availableProcessors() - 1) << 1

		List<ResultItems> resultItemsList = collectorPipeline.getCollected();
		goodsNameUrlList.clear();
		List<String> resultList = new ArrayList<>();
		for (ResultItems resultItems : resultItemsList) {
			if ((boolean) resultItems.get("pageExist")) {// 判断当前页面是否存在
				resultList = resultItems.get("result");
				if (resultList.isEmpty()) {// 没有获取到数据需要重新获取
					goodsNameUrlList.add(resultItems.get("url").toString());
				} else {
					for (String resultStr : resultList) {
						if (!bussinessWhiteList.containsAll(Utils.match(resultStr, "span", "data-nick"))) {
							goodsIdOutputList.addAll(Utils.match(resultStr, "span", "data-item"));
						}
					}
				}
			}
		}
		if (!goodsNameUrlList.isEmpty()) {
			System.out.println("---------------faild:" + goodsNameUrlList.size());
			start();
		}
	}

	public void outPut() {
		List<String> tempList = new ArrayList<String>();
		for (String goodsIdOutput : goodsIdOutputList) {
			if ((!tempList.contains(goodsIdOutput))&&(!historyGoodsIdOutputList.contains(goodsIdOutput))) {
				tempList.add(goodsIdOutput);
			}
		}
		Utils.fileChaseFW(goodsIdOutputPath, tempList);
		Utils.fileChaseFW(historyGoodsIdOutputPath, tempList);
	}

}
