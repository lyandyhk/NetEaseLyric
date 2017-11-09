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
 * �����������ڸ����ʱ����ǰ��΢��
 * ��ס����΢��������������
 * ����������.lrc�ļ����ļ��е�lrc��ʽ��ʱ���ᣬ����xx:xx.xx�������ʽ
 * ���������·����ǰһ����ȡ���ĸ���ļ���λ�ã���ʹ���߲�Ҫ�Ķ��ļ������ļ�λ�ã�������Ϻ��ٽ�lrc�ļ����в���
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
			System.out.println("δ�ҵ�ָ�������ļ�:  src/doTheCase/config.properties");
			e.printStackTrace();
		}
		//���ҵ�[],���ж������ǲ���xx:xx.xx���ǵĻ��ٴ���
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
				System.out.println("���Ӳ�����������");
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
				System.out.println("���Ӳ�����������");
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
				System.out.println("���������������");
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
			System.out.println("������������ļ�·��");
			e.printStackTrace();
			System.exit(0);
		}
		String line = "";
		try{
			while((line = br.readLine()) != null){
				Pattern p = Pattern.compile("\\[\\d{2}:\\d{2}\\.\\d{2}\\]");
			    Matcher m = p.matcher(line); // ��ȡ matcher ����
			    StringBuilder sb = new StringBuilder(line);
			    if(m.find()){
			    	int start = m.start();
			    	int end = m.end();
			    	String sub = line.substring(start, end);
			    	String txtMin = line.substring(1, 3);
			    	String txtSec = line.substring(4, 6);
			    	String txtMsec = line.substring(7, 9);
//			    	System.out.println(txtMin+":"+txtSec+":"+txtMsec);
			    	// ���Ӵ���
			    	if(minflag !=0){
			    		int  result;
			    		if(minflag == 1){
			    			result = Integer.valueOf(txtMin)+Integer.valueOf(min);
			    		}
			    		else{
			    			result = Integer.valueOf(txtMin)-Integer.valueOf(min);
			    			
			    		}
			    		singleChange(sb, 0, result);
			    	}
			    	// ���봦��
			    	if(secflag !=0){
			    		int  result;
			    		if(secflag == 1){
			    			result = Integer.valueOf(txtSec)+Integer.valueOf(sec);
			    		}
			    		else{
			    			result = Integer.valueOf(txtSec)-Integer.valueOf(sec);
			    		}
			    		singleChange(sb, 1, result);		    			
			    	}
			    	// ���봦��
			    	if(msecflag !=0){
			    		int  result;
			    		if(msecflag == 1){
			    			result = Integer.valueOf(txtMsec)+Integer.valueOf(msec);
			    		}
			    		else{
			    			result = Integer.valueOf(txtMsec)-Integer.valueOf(msec);
			    		}
			    		singleChange(sb, 2, result);	
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
	public static StringBuilder singleChange(StringBuilder sb,int type,int result){
		switch (type) {
			case 0:{
				if(result<0)
					throw new RuntimeException("�����ڴ�������˸������������");
				String lastMin = String.valueOf(result);
				if(lastMin.length() == 1)
					lastMin = "0"+lastMin;
				sb.replace(1, 3, lastMin);
				return sb;
			}
			case 1:{
				String lastSec = null;
				if(result<0){
					//����ӽ�1
					System.out.println(sb.toString());
					System.out.println(result);
					String temp = sb.substring(1, 3);
					int tempInt = Integer.valueOf(temp)-1;
					singleChange(sb,0,tempInt);
					result = result + 60;
				}else if(result>60){
					//�������1
					String temp = sb.substring(1, 3);
					int tempInt = Integer.valueOf(temp)+1;
					singleChange(sb,0,tempInt);
					result = result - 60;
				}
				lastSec = String.valueOf(result);
				if(lastSec.length() == 1)
					lastSec = "0"+lastSec;
				sb.replace(4, 6, lastSec);
				return sb;
			}
			case 2:{
				String lastMsec = null;
				if(result<0){
					//�����ӽ�1
					String temp = sb.substring(4, 6);
					int tempInt = Integer.valueOf(temp)-1;
					singleChange(sb,1,tempInt);
					result = result + 100;
				}else if(result>100){
					//��������1
					String temp = sb.substring(4, 6);
					int tempInt = Integer.valueOf(temp)+1;
					singleChange(sb,1,tempInt);
					result = result - 100;
				}
				lastMsec = String.valueOf(result);
				if(lastMsec.length() == 1)
					lastMsec = "0"+lastMsec;
				sb.replace(7, 9, lastMsec);
				return sb;
			}
			default:{
				return sb;
			}
		}
	}
	public static void main(String[] args) {
		doTheChange();
	}
}
