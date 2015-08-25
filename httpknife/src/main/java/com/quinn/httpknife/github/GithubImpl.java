package com.quinn.httpknife.github;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quinn.httpknife.HttpKnife;
import com.quinn.httpknife.R;
import com.quinn.httpknife.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GithubImpl implements Github {

    private final static String[] SCOPES = {"public_repo","repo", "user", "gist"};
    public final static String HTTPS = "https://";
    public final static String HOST = "api.github.com";
    public final static String URL_SPLITTER = "/";
    public final static String API_HOST = HTTPS + HOST + URL_SPLITTER;

    public final static String ACCEPT_JSON = "application/vnd.github.beta+json";
    public final static String ACCEPT_RAW = "application/vnd.github.VERSION.raw";
    public final static String AGENT_USER = "WeGit/1.0";
    public final static String TOKEN_NOTE = "WeGit APP Token";

    public final static String CREATE_TOKEN = API_HOST + "authorizations"; // POST
    public final static String LIST_TOKENS = API_HOST + "authorizations"; // GET
    public final static String REMOVE_TOKEN = API_HOST + "authorizations"
            + URL_SPLITTER; // DELETE
    public final static String LOGIN_USER = API_HOST + "user";
    public final static String MY_FOLLOWERS = API_HOST + "user/followers";
    public final static String MY_FOLLOWERSINGS = API_HOST + "user/following";

    public final static int DEFAULT_PAGE_SIZE = 10;
    public final static String PAGE = "page";
    public final static String PER_PAGE = "per_page";

    private String token = null;
    private HttpKnife http;
    private Context context;
    private GithubError githubError;
    private AuthError authError;


    private final static int _422 = 422; //Sending invalid fields
    private final static int _400 = 400; //Sending the wrong type of JSON;Sending invalid JSON
    private final static int _401 = 401; //Bad credentials
    private final static int _403 = 403; //Maximum number of login attempts exceeded. Please try again later.





    public GithubImpl(Context context) {
        this.context = context;
        this.http = new HttpKnife(context);
        githubError = new GithubError(context.getString(R.string.network_error));
        authError = new AuthError("Token 失效");
    }

    public void filterError(Response response) throws GithubError,AuthError{
        if (response.isSuccess() == false)
            throw githubError;
        if (response.statusCode() == 401) {
            throw authError;
        }
    }

    @Override
    public String createToken(String username, String password)
            throws GithubError,AuthError {
        JSONObject json = new JSONObject();
        try {
            json.put("note", TOKEN_NOTE);
            JSONArray jsonArray = new JSONArray(Arrays.asList(SCOPES));
            json.put("scopes",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Response response = http.post(CREATE_TOKEN)
                .headers(configreHttpHeader())
                .basicAuthorization(username, password).json(json).response();
        if (response.isSuccess() == false)
            throw githubError;
        if (response.statusCode() == 401) {
            throw new AuthError("username or password is incorrect");
        }
        if (response.statusCode() == 422) {
            removeToken(username, password);
            return createToken(username, password);
        }
        Token token = new Gson().fromJson(response.body(), Token.class);
        return token.getToken();
    }

    @Override
    public String findCertainTokenID(String username, String password)
            throws GithubError, AuthError {
        Response response = http.get(LIST_TOKENS).headers(configreHttpHeader())
                .basicAuthorization(username, password).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<Token> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<Token>>() {
                }.getType());
        System.out.println("listToken gson = " + tokenList);
        for (int i = 0; i < tokenList.size(); i++) {
            Token token = tokenList.get(i);
            if (TOKEN_NOTE.equals(token.getNote()))
                return String.valueOf(token.getId());
        }
        return "";
    }

    @Override
    public void removeToken(String username, String password)
            throws GithubError,AuthError{
        String id = findCertainTokenID(username, password);
        Response response = http.delete(REMOVE_TOKEN + id)
                .headers(configreHttpHeader())
                .basicAuthorization(username, password).response();
        filterError(response);

    }

    @Override
    public User authUser(String token) throws GithubError ,AuthError{
        Response response = http.get(LOGIN_USER).headers(configreHttpHeader()).tokenAuthorization(token).response();
        filterError(response);
        Gson gson = new Gson();
        User user = gson.fromJson(response.body(), User.class);
        return user;
    }


    @Override
    public void makeAuthRequest(String token) {
        this.token = token;
    }

    @Override
    public Map<String, String> configreHttpHeader() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", ACCEPT_JSON);
        headers.put("User-Agent", AGENT_USER);
        if (token != null) {
            headers.put(HttpKnife.RequestHeader.AUTHORIZATION,
                    "Token " + token);
        }
        return headers;
    }

    public Map<String, String> pagination(int page) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        params.put(PER_PAGE, String.valueOf(DEFAULT_PAGE_SIZE));
        return params;
    }



    @Override
    public List<User> follwerings(String user, int page) throws GithubError,AuthError {
        String url = API_HOST + "users/" + user + "/following";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<User> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return tokenList;
    }

    @Override
    public List<User> followers(String user, int page) throws GithubError,AuthError {
        String url = API_HOST + "users/" + user + "/followers";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<User> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return tokenList;
    }

    @Override
    public List<Repository> repo(String user, int page) throws GithubError,AuthError {
        String url = API_HOST + "users/" + user + "/repos?sort=pushed";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
         filterError(response);
        Gson gson = new Gson();
        ArrayList<Repository> repoList = gson.fromJson(response.body(),
                new TypeToken<List<Repository>>() {
                }.getType());
        System.out.println("reposlist = " + repoList);
        return repoList;

    }

    @Override
    public List<Repository> starred(String user, int page) throws GithubError,AuthError {
        String url = API_HOST + "users/" + user + "/starred";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
       filterError(response);
        Gson gson = new Gson();
        ArrayList<Repository> repoList = gson.fromJson(response.body(),
                new TypeToken<List<Repository>>() {
                }.getType());
        System.out.println("reposlist = " + repoList);
        return repoList;
    }

    @Override
    public User user(String username) throws GithubError,AuthError {
        String url = API_HOST + "users/" + username;
        Response response = http.get(url).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        User user = gson.fromJson(response.body(), User.class);
        return user;
    }

    @Override
    public List<Event> receivedEvent(String user, int page) throws GithubError,AuthError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        String url = API_HOST + "users/" + user + "/received_events";
        Response response = http.get(url, params).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<Event> events = gson.fromJson(response.body(),
                new TypeToken<List<Event>>() {
                }.getType());
        System.out.println("events = " + events);
        return events;
    }

    @Override
    public List<Event> userEvent(String user, int page) throws GithubError,AuthError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        String url = API_HOST + "users/" + user + "/events";
        Response response = http.get(url, params).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<Event> events = gson.fromJson(response.body(),
                new TypeToken<List<Event>>() {
                }.getType());
        System.out.println("events = " + events);
        return events;
    }

    @Override
    public List<Event> repoEvent(String user, String repo, int page) throws GithubError,AuthError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        String url = API_HOST + "repos/" + user + "/" + repo + "/events";
        Response response = http.get(url, params).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<Event> events = gson.fromJson(response.body(),
                new TypeToken<List<Event>>() {
                }.getType());
        System.out.println("events = " + events);
        return events;
    }

    @Override
    public boolean hasFollow(String targetUser) throws GithubError,AuthError {
        String url = API_HOST + "user/following/" + targetUser;
        Response response = http.get(url).headers(configreHttpHeader()).response();
        filterError(response);
        if (response.statusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean follow(String targetUser) throws GithubError,AuthError {
        String url = API_HOST + "user/following/" + targetUser;
        Response response = http.put(url).headers(configreHttpHeader()).response();
        filterError(response);
        if (response.statusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean unfollow(String targetUser) throws GithubError,AuthError {
        String url = API_HOST + "user/following/" + targetUser;
        Response response = http.delete(url).headers(configreHttpHeader()).response();
        filterError(response);
        if (response.statusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String readme(String owner, String repo) throws GithubError,AuthError {
        String url = API_HOST + "repos/" + owner + "/" +repo + "/readme";
        Response response = http.get(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        filterError(response);
        return response.body();
    }

    @Override
    public boolean hasStarRepo(String owner, String repo) throws GithubError,AuthError {
        String url = API_HOST + "user/starred/" + owner + "/" +repo;
        Response response = http.get(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        filterError(response);
        if(response.statusCode() == 204){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean starRepo(String owner, String repo) throws GithubError,AuthError {
        String url = API_HOST + "user/starred/" + owner + "/" +repo;
        Response response = http.put(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        filterError(response);
        if(response.statusCode() == 204){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean unStarRepo(String owner, String repo) throws GithubError,AuthError {
        String url = API_HOST + "user/starred/" + owner + "/" +repo;
        Response response = http.delete(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        filterError(response);
        if(response.statusCode() == 204){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<User> stargazers(String owner, String repo,int page) throws GithubError {
        //GET /repos/:owner/:repo/stargazers
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/stargazers";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        Gson gson = new Gson();
        ArrayList<User> userList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return userList;
    }

    @Override
    public List<User> forkers(String owner, String repo,int page) throws GithubError,AuthError {
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/forks";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<Repository> repoList = gson.fromJson(response.body(),
                new TypeToken<List<Repository>>() {
                }.getType());
        ArrayList<User> userList = new ArrayList<User>();
        for(Repository repoItem : repoList){
            userList.add(repoItem.getOwner());
        }
        return userList;
    }

    @Override
    public boolean fork(String owner, String repo) throws GithubError,AuthError {
        return false;
    }

    @Override
    public List<User> collaborators(String owner,String repo,int page) throws GithubError,AuthError {
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/collaborators";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        filterError(response);
        if(response.statusCode() == 403){
            JSONObject body = response.json();
            try {
                throw new GithubError(body.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        ArrayList<User> userList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return userList;
    }

    @Override
    public Tree getTree(String owner, String repo, String ref) throws GithubError,AuthError {
         //GET /repos/:owner/:repo/git/trees/:sha
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/git/trees/" + ref;
        Response response = http.get(url).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        Tree tree = gson.fromJson(response.body(),Tree.class);
        return tree;
    }

    @Override
    public List<User> searchUser(List<String> keywords,int page)  throws GithubError,AuthError {
        //GET /search/users
        StringBuilder keywordsParams = new StringBuilder();
        for(int i = 0; i < keywords.size();i++){
            if(i != keywords.size()-1)
                keywordsParams.append(keywords.get(i) + "+");
            else
                keywordsParams.append(keywords.get(i));
        }
        String url = API_HOST + "search/users?q=" + keywordsParams.toString();
        System.out.println("searchUser url : " + url);
        Response response = http.get(url,pagination(page)).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<User> userList = new ArrayList<User>();
        try {
            JSONArray users = response.json().getJSONArray("items");
            userList = gson.fromJson(users.toString(),
                    new TypeToken<List<User>>() {
                    }.getType());
            /**
             * Add a first item to save the total_count,It do not mean  a user!!!
             */
            int total_count = response.json().getInt("total_count");
            System.out.println("search users total_count = " + total_count);
            User user = new User();
            user.setFollowers(total_count);
            userList.add(0, user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<Repository> searchRepo(List<String> keywords,int page) throws GithubError,AuthError {
        //GET /search/repositories
        StringBuilder keywordsParams = new StringBuilder();
        for(int i = 0; i < keywords.size();i++){
            if(i != keywords.size()-1)
                keywordsParams.append(keywords.get(i) + "+");
            else
                keywordsParams.append(keywords.get(i));
        }
        String url = API_HOST + "search/repositories?q=" + keywordsParams.toString();
        Response response = http.get(url,pagination(page)).headers(configreHttpHeader()).response();
        filterError(response);
        Gson gson = new Gson();
        ArrayList<Repository> userList = new ArrayList<Repository>();
        try {
            JSONArray repos = response.json().getJSONArray("items");
            userList = gson.fromJson(repos.toString(),
                    new TypeToken<List<Repository>>() {
                    }.getType());
            /**
             * Add a first item to save the total_count,It do not mean  a user!!!
             */
            int total_count = response.json().getInt("total_count");
            System.out.println("search repos total_count = " + total_count);
            Repository repo = new Repository();
            repo.setForks_count(total_count);
            userList.add(0,repo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public String getRawContent(String owner, String repo, String path) throws GithubError,AuthError {
        ///repos/:owner/:repo/contents/:path
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/contents/" + path;
        Response response = http.get(url).headers(configreHttpHeader()).header("Accept", ACCEPT_RAW).response();
        filterError(response);
        return response.body();
    }




}
