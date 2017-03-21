package main;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class Utils {

	/**
	 * 获取配置参数
	 * @return
	 */
	public static Properties getJMXConf() {
		try {
			Properties properties = new Properties();
			InputStream in = TomcatMonitor.class.getClassLoader()
					.getResourceAsStream("JMXConf.properties");
			properties.load(in);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getFiledName(Object object) {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			String[] fieldNames = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();
			}
			return fieldNames;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getFieldValueByName(String fieldName, Object object)   
	{      
	   try   
	   {      
	       String firstLetter = fieldName.substring(0, 1).toUpperCase();      
	       String getter = "get" + firstLetter + fieldName.substring(1);      
	       Method method = object.getClass().getMethod(getter, new Class[] {});      
	       Object value = method.invoke(object, new Object[] {});      
	       return value;      
	   } catch (Exception e)   
	   {      
	       System.out.println("属性不存在");      
	       return null;      
	   }      
	}    
}
