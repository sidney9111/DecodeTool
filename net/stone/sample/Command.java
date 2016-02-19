package stone.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {
	 public static void exeCmd(String commandStr){  
	        BufferedReader br = null;  
	        try {  
	            Process p = Runtime.getRuntime().exec(commandStr);  
	            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
	            String line = null;  
	            StringBuilder sb = new StringBuilder();  
	            while ((line = br.readLine()) != null) {  
	                sb.append(line + "\n");  //如果是mac 或者linux是不是这个换行啊
	            }  
	            System.out.println(sb.toString());
//	            System.out.println("execute finished");
	            if (p.waitFor() != 0) {  
	                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
	                    System.err.println("命令执行失败!");  
	            }  
	            br.close();  
	            //in.close();  
	            
	        } catch (Exception e) {  
	        	if (br != null)  
	            {  
	        		try {
						br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            }
	            e.printStackTrace();
	            //throw e;
	        }   

	    }  
}
