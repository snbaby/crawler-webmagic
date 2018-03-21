package com.taokn.crawler.crawler_webmagic.util;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class TaoBaoPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setDomain("s.taobao.com").setCharset("GBK")
			.addHeader("Referer", "http://www.taobao.com/").setRetryTimes(3).setSleepTime(5000);

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		if (page.getHtml().css(".taogongzai").all().size() != 0) {// 表示当前URL无法查询到数据
			page.putField("pageExist", false);// 表示能无法查询到数据
			System.out.println("-----------.taogongzai");
		} else if (page.getHtml().css("a.total").all().size() != 0) {// 表示列表为推荐系列无法查询到数据
			page.putField("pageExist", false);// 表示能无法查询到数据
			System.out.println("-----------a.total");
		} else {
			page.putField("pageExist", true);// 表示能查询到数据
			Selectable selectable = page.getHtml().css("span[data-nick][data-item]");
			List<String> result = selectable.all();
			page.putField("result", result);
			page.putField("url", page.getUrl());
			System.out.println("-----------span[data-nick][data-item]");
		}
	}
}
