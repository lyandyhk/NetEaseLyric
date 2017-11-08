package doTheCase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 本方法仅限于歌词延时或提前的微调
 * 记住，是微调！！！！！！
 * 操作对象是.lrc文件（文件中的lrc格式的时间轴，例如xx:xx.xx）这个形式
 * 操作对象的路径是前一步获取到的歌词文件的位置，请使用者不要改动文件名和文件位置，调整完毕后再将lrc文件进行操作
 * @author Febiven
 * 
 */

public class ChangeTheTime {
	private static Properties pro = new Properties();
	public static void doTheChange() {
		try {
			FileInputStream in = new FileInputStream("src/doTheCase/config.properties");
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("未找到指定配置文件:  src/doTheCase/config.properties");
			e.printStackTrace();
		}
		//先找到[],再判断里面是不是xx:xx.xx，是的话再处理
		String min = pro.getProperty("minute");
		String sec = pro.getProperty("second");
		String msec = pro.getProperty("millisecond");
		int minInt=0,minflag = 0,secInt = 0,secflag = 0,msecInt = 0,msecflag = 0;
		if(min.length() != 0){
			minflag = 1;
			char flag = min.charAt(0);
			if(flag == '+'){
				min = min.substring(1);
				minflag = 1;
			}
			else if(flag == '-'){
				min = min.substring(1);
				minflag = -1;
			}
			try{
				minInt = Integer.valueOf(min);				
			}catch(NumberFormatException e){
				System.out.println("分钟参数输入有误");
				System.exit(0);
			}
		}
		if(sec.length() != 0){
			secflag = 1;
			char flag = sec.charAt(0);
			if(flag == '+'){
				sec = sec.substring(1);
				secflag = 1;
			}
			else if(flag == '-'){
				sec = sec.substring(1);
				secflag = -1;
			}
			try{
				secInt = Integer.valueOf(sec);				
			}catch(NumberFormatException e){
				System.out.println("秒钟参数输入有误");
				System.exit(0);
			}
		}
		if(msec.length() != 0){
			msecflag = 1;
			char flag = msec.charAt(0);
			if(flag == '+'){
				msec = msec.substring(1);
				msecflag = 1;
			}
			else if(flag == '-'){
				msec = msec.substring(1);
				msecflag = -1;
			}
			try{
				msecInt = Integer.valueOf(msec);				
			}catch(NumberFormatException e){
				System.out.println("毫秒参数输入有误");
				System.exit(0);
			}
		}
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			System.out.println(pro.getProperty("savePath")+File.separator+pro.getProperty("songName")+".lrc");
			System.out.println(pro.getProperty("ChangeSavePath")+File.separator+pro.getProperty("songName")+".lrc");
			br = new BufferedReader(new FileReader(pro.getProperty("savePath")+File.separator+pro.getProperty("songName")+".lrc"));
			bw = new BufferedWriter(new FileWriter(pro.getProperty("ChangeSavePath")+File.separator+pro.getProperty("songName")+".lrc"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("请检查输入输出文件路径");
			e.printStackTrace();
			System.exit(0);
		}
		String line = "";
		try{
			while((line = br.readLine()) != null){
				Pattern p = Pattern.compile("\\[\\d{2}:\\d{2}\\.\\d{2}\\]");
			    Matcher m = p.matcher(line); // 获取 matcher 对象
			    StringBuilder sb = new StringBuilder(line);
			    if(m.find()){
			    	int start = m.start();
			    	int end = m.end();
			    	String sub = line.substring(start, end);
			    	String txtMin = line.substring(1, 3);
			    	String txtSec = line.substring(4, 6);
			    	String txtMsec = line.substring(7, 9);
//			    	System.out.println(txtMin+":"+txtSec+":"+txtMsec);
			    	// 分钟处理
			    	if(minflag !=0){
			    		String lastMin;
			    		if(minflag == 1){
			    			lastMin = String.valueOf(Integer.valueOf(txtMin)+Integer.valueOf(min));
			    		}
			    		else{
			    			lastMin = String.valueOf(Integer.valueOf(txtMin)-Integer.valueOf(min));
			    		}
			    		if(lastMin.length() == 1)
			    			lastMin = "0"+lastMin;
			    		sb.replace(1, 3, lastMin);
			    			
			    	}
			    	// 秒针处理
			    	if(secflag !=0){
			    		String lastSec;
			    		if(secflag == 1){
			    			lastSec = String.valueOf(Integer.valueOf(txtSec)+Integer.valueOf(sec));
			    		}
			    		else{
			    			lastSec = String.valueOf(Integer.valueOf(txtSec)-Integer.valueOf(sec));
			    		}
			    		if(lastSec.length() == 1)
			    			lastSec = "0"+lastSec;
			    		sb.replace(4, 6, lastSec);
			    			
			    	}
			    	// 毫秒处理
			    	if(msecflag !=0){
			    		String lastMsec;
			    		if(msecflag == 1){
			    			lastMsec = String.valueOf(Integer.valueOf(txtMsec)+Integer.valueOf(msec));
			    		}
			    		else{
			    			lastMsec = String.valueOf(Integer.valueOf(txtMsec)-Integer.valueOf(msec));
			    		}
			    		if(lastMsec.length() == 1)
			    			lastMsec = "0"+lastMsec;
			    		sb.replace(7, 9, lastMsec);
			    			
			    	}
//			    	System.out.println(sb.toString());
			    }
				bw.write(sb.toString());
				bw.write("\r\n");
			}
			br.close();
			bw.close();
		}catch(IOException e){
			
		}
			
	}
	public static void main(String[] args) {
		doTheChange();
	}
}
