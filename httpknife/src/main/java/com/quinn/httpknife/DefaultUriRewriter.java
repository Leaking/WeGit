package com.quinn.httpknife;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.protocol.HTTP;

public class DefaultUriRewriter implements UrlRewriter {

	/**
	 * 1,使用URI toASCILL 2,使用URLEncodedUtils.format
	 */
	@Override
	public String rewriteWithParam(String originUrl, Map<?, ?> params) {
		try {
			if(params == null || params.isEmpty())
				return originUrl;
			final StringBuilder finalUrl = new StringBuilder(originUrl);
			format(originUrl,finalUrl);
			//添加参数
			Entry<?, ?> entry;
			for(Iterator<?> iterator = params.entrySet().iterator();iterator.hasNext();){
				entry = (Entry<?, ?>) iterator.next();
				addParam(entry.getKey().toString(), entry.getValue(), finalUrl);
			}
			System.out.println("add params url ===== ");
			System.out.println(finalUrl);
			String decodedURL = URLDecoder.decode(finalUrl.toString(), HTTP.UTF_8);
			URL _url = new URL(decodedURL);
			URI _uri = new URI(_url.getProtocol(), _url.getUserInfo(),
					_url.getHost(), _url.getPort(), _url.getPath(),
					_url.getQuery(), _url.getRef());
			return _uri.toASCIIString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return originUrl;
	}

	/**
	 * 处理两个情况
	 * 1，还没添加参数之前，url末尾没有斜杆，如果添加参数，需要先把斜杆加上
	 * 2，考虑是否原本就有参数
	 */
	public void format(String originUrl,StringBuilder finalUrl){
		if(originUrl.indexOf(":") + 2 == originUrl.lastIndexOf("/")){
			finalUrl.append("/");
		}
		final int queryStart = originUrl.indexOf('?');
		final int lastChar = finalUrl.length() - 1;
		if (queryStart == -1)
			finalUrl.append('?');
		else if (queryStart < lastChar && originUrl.charAt(lastChar) != '&')
			finalUrl.append('&');
	}
	
	/**
	 * 参数是普通字符串，参数也可能是数组 
	 * @param name
	 * @param value
	 * @param url
	 * @return
	 */
	public StringBuilder addParam(String name, Object value,StringBuilder finalUrl){
		if (value != null && value.getClass().isArray())
			value = arrayToList(value);
		
		//数组格式的值
		if (value instanceof Iterable<?>) {
			Iterator<?> iterator = ((Iterable<?>) value).iterator();
			while (iterator.hasNext()) {
				finalUrl.append(name);
				finalUrl.append("[]=");
				Object element = iterator.next();
				if (element != null)
					finalUrl.append(element);
				if (iterator.hasNext())
					finalUrl.append("&");
			}
		} else {
			finalUrl.append(name);
			finalUrl.append("=");
			if (value != null)
				finalUrl.append(value);
		}

		return finalUrl;
	}
	
	/**
	 * 数组转化为List
	 * @param array
	 * @return
	 */
	private static List<Object> arrayToList(final Object array) {
		if (array instanceof Object[])
			return Arrays.asList((Object[]) array);

		List<Object> result = new ArrayList<Object>();
		// Arrays of the primitive types can't be cast to array of Object, so
		// this:
		if (array instanceof int[])
			for (int value : (int[]) array)
				result.add(value);
		else if (array instanceof boolean[])
			for (boolean value : (boolean[]) array)
				result.add(value);
		else if (array instanceof long[])
			for (long value : (long[]) array)
				result.add(value);
		else if (array instanceof float[])
			for (float value : (float[]) array)
				result.add(value);
		else if (array instanceof double[])
			for (double value : (double[]) array)
				result.add(value);
		else if (array instanceof short[])
			for (short value : (short[]) array)
				result.add(value);
		else if (array instanceof byte[])
			for (byte value : (byte[]) array)
				result.add(value);
		else if (array instanceof char[])
			for (char value : (char[]) array)
				result.add(value);
		return result;
	}

	
	
	@Override
	public String rewriteWithEncoding(String originUrl) {
		return null;
	}

}
