package com.quinn.httpknife.github;

import java.util.List;
import java.util.Map;


/**
 * 
 * @author Quinn
 * 
 */
public interface Github {


	public void makeAuthRequest(String token);
	
	public Map<String,String> configreHttpHeader();
	
	/**
	 * If the token has already existed,list all token and find it out,remove it and recreate it.
	 * @return
	 */
	public String createToken(String username, String password) throws GithubError,AuthError;
	
	
	/**
	 * List all token,the "token" attribute is empty.
	 */
	public String findCertainTokenID(String username, String password) throws GithubError;
	
	
	/**
	 * Remove token
	 */
	public void removeToken(String username, String password) throws GithubError;
	
	/**
	 * login 
	 */
	public User authUser(String token) throws GithubError;
	

	public List<User> myFollwers(String token, int page) throws GithubError;
	
	public List<User> myFollwerings(String token, int page) throws GithubError;
	
	public List<User> follwerings(String user,int page) throws GithubError;
	
	public List<User> followers(String user,int page) throws GithubError;

	public List<Repository> repo(String user,int page) throws GithubError;

	public List<Repository> starred(String user,int page) throws GithubError;

	public User user(String user) throws GithubError;

	public List<Event> receivedEvent(String user,int page) throws GithubError;

	public List<Event> userEvent(String user, int page) throws GithubError;

	public List<Event> repoEvent(String user, String repo,int page) throws GithubError;

	public boolean hasFollow(String targetUser) throws GithubError;

	public boolean follow(String targetUser) throws GithubError;

	public boolean unfollow(String targetUser) throws GithubError;

	public String readme(String owner, String repo) throws GithubError;

	public boolean hasStarRepo(String owner,String repo) throws GithubError;

	public boolean starRepo(String owner,String repo) throws GithubError;

	public boolean unStarRepo(String owner,String repo) throws GithubError;

	//GET /repos/:owner/:repo/stargazers
	public List<User> stargazers(String owner,String repo,int page) throws GithubError;

	//GET /repos/:owner/:repo/forks
	public List<User> forkers(String owner,String repo,int page) throws GithubError;

	//POST /repos/:owner/:repo/forks
	public boolean fork(String owner,String repo) throws GithubError;

	//GET /repos/:owner/:repo/collaborators
	public List<User> collaborators(String owner,String repo,int page) throws GithubError;

	//GET /repos/:owner/:repo/commits
	//public boolean commits(String owner,String repo) throws GithubError;

	//GET /repos/:owner/:repo/git/trees/:sha
	public Tree getTree(String owner,String repo,String sha) throws GithubError;

}
