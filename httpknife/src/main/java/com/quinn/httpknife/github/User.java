package com.quinn.httpknife.github;

public class User {

	private String login;
	private String id;
	private String avatar_url;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	@Override
	public String toString() {
		return "User [login=" + login + ", id=" + id + ", avatar_url="
				+ avatar_url + "]";
	}
	
	
	
}


//"login": "lyt1025",
//"id": 4133185,
//"avatar_url": "https://avatars.githubusercontent.com/u/4133185?v=3",
//"gravatar_id": "",
//"url": "https://api.github.com/users/lyt1025",
//"html_url": "https://github.com/lyt1025",
//"followers_url": "https://api.github.com/users/lyt1025/followers",
//"following_url": "https://api.github.com/users/lyt1025/following{/other_user}",
//"gists_url": "https://api.github.com/users/lyt1025/gists{/gist_id}",
//"starred_url": "https://api.github.com/users/lyt1025/starred{/owner}{/repo}",
//"subscriptions_url": "https://api.github.com/users/lyt1025/subscriptions",
//"organizations_url": "https://api.github.com/users/lyt1025/orgs",
//"repos_url": "https://api.github.com/users/lyt1025/repos",
//"events_url": "https://api.github.com/users/lyt1025/events{/privacy}",
//"received_events_url": "https://api.github.com/users/lyt1025/received_events",
//"type": "User",
//"site_admin": false