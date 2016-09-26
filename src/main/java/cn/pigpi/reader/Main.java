package cn.pigpi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;

/**
 * 主程序
 * 
 * @author zhushiwei
 *
 */
public class Main {
	public static void main(String[] args) {
		Jedis jedis = JedisUtil.getInstance().getJedis("127.0.0.1", 6379);
		try {
			FileInputStream in = new FileInputStream(new File("D:/Sessions.txt"));  
			BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));  
			String lineTxt = null;
            while (true) {
            	lineTxt = br.readLine();
            	if (StringUtils.isEmpty(lineTxt)) {
            		System.out.println("还没有内容呢，机器人你快点啊！");
					Thread.sleep(5000);
				} else {
					System.out.println(lineTxt);
					//送入队列
					jedis.lpush(String.valueOf(System.currentTimeMillis()), lineTxt);
				}
            }
		}
		catch (Exception e) {
			System.err.println("read errors :" + e);
		}
	}
}
