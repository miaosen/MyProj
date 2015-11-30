package com.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.comment.Logger;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
	
	/**
	 * 获取activity根布局
	 * @param context
	 * @return
	 */
	public static ViewGroup getRootViewGroup(Context context){
		ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ((Activity) context)
				.getWindow().getDecorView().findViewById(android.R.id.content))
				.getChildAt(0);
		return viewGroup;
	}
	
	
	/**
	 * @note 获取一个View下所有的view
	 * */
	public List<View> getAllChildViews(Context context) {
		View view = ((Activity)context).getWindow().getDecorView();
		return getAllChildViews(view);
	}

	private List<View> getAllChildViews(View view) {
		List<View> allchildren = new ArrayList<View>();
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				allchildren.add(viewchild);
				allchildren.addAll(getAllChildViews(viewchild));
			}
		}
		return allchildren;
	}
	
	
	
	public static int getLayoutByName(Context context, String name) {
		return getResounceByName(context, "layout", name);
	}

	public static int getIdByName(Context context, String name) {
		return getResounceByName(context, "id", name);
	}

	public static int getDrawableByName(Context context, String name) {
		return getResounceByName(context, "drawable", name);
	}

	public static int getRawByName(Context context, String name) {
		return getResounceByName(context, "raw", name);
	}

	public static int getColorByName(Context context, String name) {
		return getResounceByName(context, "color", name);
	}

	public static String getStringByName(Context context, String name) {
		return context.getResources().getString(
				getResounceByName(context, "string", name));
	}
	
	/**
	 * 通过名称获取资源ID
	 * 
	 * @param context
	 * @param restype
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getResounceByName(Context context, String restype,
			String name) {
		String packageName = context.getPackageName();
		Class r = null;
		int id = 0;
		try {
			r = Class.forName(packageName + ".R");
			Class[] classes = r.getClasses();
			Class desireClass = null;
			//TODO 静态map优化
			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(restype)) {
					desireClass = classes[i];
					break;
				}
			}
			if (desireClass != null) {
				Field field = desireClass.getField(name);
				if (field != null)
					id = field.getInt(desireClass);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 通过资源ID获取字段名称
	 * @param context
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getNameById(Context context,
			int id) {
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
