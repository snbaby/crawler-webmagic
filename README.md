# crawler-webmagic

# 必要环境
1、JDK/MAVEN

2、工程目录 使用  maven install 进行打包

3、运行环境为

# 执行命令
1、使用 -jar xxx.jar E:\Test\application.property 启动程序,(E:\Test\application.property)为配置文件的绝对路径，（xxx.jar）为maven打包出来的jar包名字

# 注意事项
1、商家白名单BUSSINESS_WHITE_LIST_PATH 所对应的文件格式需要为txt,编码为UTF-8，且一行一个商家

2、待查询的商品名称GOODS_NAME_INPUT_PATH 所对应的文件格式需要为txt,编码为UTF-8，且一行一个待查询的商品

# 配置文件 application.property

#######每次执行需按需修改

#商家白名单

BUSSINESS_WHITE_LIST_PATH: F:\BUSSINESS_WHITE_LIST_PATH.txt

#待查询的商品名称

GOODS_NAME_INPUT_PATH: F:\GOODS_NAME_INPUT_PATH.txt

#查询出来的商品ID

GOODS_ID_OUTPUT_PATH: F:\GOODS_ID_OUTPUT_PATH.txt


#######设置后不用每次进行修改

#历史查询出来的商品ID

HISTORY_GOODS_ID_OUTPUT_PATH: F:\HISTORY_GOODS_ID_OUTPUT_PATH.txt

#工具JS文件存放地址

CRAWL_JS_PATH: F:\crawl.js

#工具EXE文件存放地址

PHANTOMJS_EXE_PATH: F:\phantomjs.exe

#查询页数

QUERY_PAGE_NUMBER: 1


