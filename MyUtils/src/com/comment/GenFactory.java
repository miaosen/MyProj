package com.comment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class GenFactory {
	
	private Context context;
	
	
	private static Map<Integer,String> fieldMap;
	
//	public GenFactory(Context context){
//		this.context=context;
//	}
	
	
	public static String getNameById(Context context,
			int id ){
		String idName=null;
		if(fieldMap!=null&&fieldMap.containsKey(id)){
			idName=fieldMap.get(id);
		}else{
			idName=getNamesById(context,id);
		}
		return idName;
	}
	
	/**
	 * 通过资源ID获取字段名称
	 * @param context
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getNamesById(Context context,
			int id ) {
		String packageName = context.getPackageName();
		Class r = null;
		String idName=null;
		try {
			r = Class.forName(packageName + ".R");
			Class[] classes = r.getClasses();
			Class desireClass = null;
			//TODO 静态map优化
			for (int i = 0; i < classes.length; ++i) {
				Logger.info("classes[i].getName()========="+classes[i].getName());
				if (classes[i].getName().split("\\$")[1].equals("id")) {
					desireClass = classes[i];
					break;
				}
			}
			if (desireClass != null) {
				Field field[]=desireClass.getFields();
				for (int i = 0; i < field.length; i++) {
					//Logger.info("field[i].getName()========="+field[i].getName());
					
					if(field[i].getInt(desireClass)==id){
						idName=field[i].getName();
					}
					//保存为静态常量
					if(fieldMap==null){
						fieldMap=new HashMap<Integer,String>();
					}
					fieldMap.put(id, idName);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		//Logger.info("idName========="+idName);
		return idName;
	}

}
