package com.everday.lib_network.constants;
/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/21
* description: 网络配置
*/

public class Constants {
    /** 警务室系统 */
    public final static String ROOMSYSTEM = "1";
    /** 派出所系统 */
    public final static String POLICESYSTEM = "2";
    public static int CACHESIZE = 10*1024*1024; //缓存文件夹大小
    public static int CONNECTTIMEOUT = 10;  //设置链接超时
    public static int READTIMEOUT = 5000; //设置读取超时
    public static int WRITETIMEOUT = 5000; //设置写入超时
    public static int SUCCESS = 200;
    public static int ERROR = 400;
    public static String HOST = "https://zsjw.taiyuan.gov.cn/tyjws-app/";
}
