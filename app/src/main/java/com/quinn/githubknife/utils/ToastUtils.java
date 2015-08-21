package com.quinn.githubknife.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Quinn
 * @date 2015-3-23
 */
public class ToastUtils {
	/**
	 * 弹出Toast
	 * 
	 * @param msg
	 */
	public static void showMsg(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
	}

	public static void showMsg(Context cont, int msg) {
		if(cont == null){
			L.i("LOG","context = null");
		}else{
			L.i("LOG","context != null");

		}
		Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
	}

	public static void showMsg(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
	
	public static void showMsg(Context cont, int msg, int time){
		Toast.makeText(cont, msg, time).show();
	}
}
