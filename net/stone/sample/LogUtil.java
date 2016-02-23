package stone.sample;

public class LogUtil {
	public static void info(String str){
		System.out.println(str);
	}
	public static void error(String str){
		System.out.println("error:"+str);
	}
	public static void debug(Object obj){
		System.out.println(String.valueOf(obj));
	}
	public static void debug(String str){
		System.out.println("debug:"+str);
	}
}
