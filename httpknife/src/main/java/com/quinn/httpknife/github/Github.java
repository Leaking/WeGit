package com.quinn.httpknife.github;

import java.util.List;
import java.util.Map;


/**
 * 
 * @author Quinn
 * 
 */
public interface Github {

	
	
	public Map<String,String> configreHttpHeader();
	
	/**
	 * If the token has already existed,list all token and find it out,remove it and recreate it.
	 * @return
	 */
	public String createToken(String username, String password) throws IllegalStateException;
	
	
	/**
	 * List all token,the "token" attribute is empty.
	 */
	public String findCertainTokenID(String username, String password) throws IllegalStateException;
	
	
	/**
	 * Remove token
	 */
	public void removeToken(String username, String password) throws IllegalStateException;
	
	/**
	 * login 
	 */
	public void loginUser(String token) throws IllegalStateException;
	

	public List<User> myFollwers(String token, int page) throws IllegalStateException;
	
	public List<User> myFollwerings(String token) throws IllegalStateException;
	
	public List<User> follwerings(String user) throws IllegalStateException;
	
	public List<User> followers(String user) throws IllegalStateException;

	
	

}
