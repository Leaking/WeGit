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

    public GithubImpl(Context context) {
        this.context = context;
        this.http = new HttpKnife(context);
        githubError = new GithubError(context.getString(R.string.network_error));
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
            //账号密码错误
            throw new AuthError("username or password is incorrect");
        }
        testResult(response);
        if (response.statusCode() == 422) {
            removeToken(username, password);
            return createToken(username, password);
        }

        Token token = new Gson().fromJson(response.body(), Token.class);
        System.out.println("token gson = " + token);
        return token.getToken();
    }

    @Override
    public String findCertainTokenID(String username, String password)
            throws GithubError {
        Response response = http.get(LIST_TOKENS).headers(configreHttpHeader())
                .basicAuthorization(username, password).response();
        if (response.isSuccess() == false)
            throw githubError;
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
            throws GithubError {
        String id = findCertainTokenID(username, password);
        Response response = http.delete(REMOVE_TOKEN + id)
                .headers(configreHttpHeader())
                .basicAuthorization(username, password).response();
        if (response.isSuccess() == false)
            throw githubError;
        testResult(response);
    }

    @Override
    public User authUser(String token) throws GithubError {
        Response response = http.get(LOGIN_USER).headers(configreHttpHeader()).tokenAuthorization(token).response();
        if (response.isSuccess() == false)
            throw githubError;
        testResult(response);
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
    public List<User> myFollwers(String token, int page) throws GithubError {
        Response response = http.get(MY_FOLLOWERS, pagination(page)).headers(configreHttpHeader()).tokenAuthorization(token).response();
        if (response.isSuccess() == false)
            throw githubError;
        testResult(response);
        Gson gson = new Gson();
        List<User> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        System.out.println("tuserlis = " + tokenList);

        return tokenList;
    }

    @Override
    public List<User> myFollwerings(String token, int page) throws GithubError {
        Response response = http.get(MY_FOLLOWERSINGS, pagination(page)).headers(configreHttpHeader()).tokenAuthorization(token).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<User> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return tokenList;
    }

    @Override
    public List<User> follwerings(String user, int page) throws GithubError {
        String url = API_HOST + "users/" + user + "/following";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<User> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return tokenList;
    }

    @Override
    public List<User> followers(String user, int page) throws GithubError {
        String url = API_HOST + "users/" + user + "/followers";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<User> tokenList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return tokenList;
    }

    @Override
    public List<Repository> repo(String user, int page) throws GithubError {
        String url = API_HOST + "users/" + user + "/repos?sort=pushed";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<Repository> repoList = gson.fromJson(response.body(),
                new TypeToken<List<Repository>>() {
                }.getType());
        System.out.println("reposlist = " + repoList);
        return repoList;

    }

    @Override
    public List<Repository> starred(String user, int page) throws GithubError {
        String url = API_HOST + "users/" + user + "/starred";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<Repository> repoList = gson.fromJson(response.body(),
                new TypeToken<List<Repository>>() {
                }.getType());
        System.out.println("reposlist = " + repoList);
        return repoList;
    }

    @Override
    public User user(String username) throws GithubError {
        String url = API_HOST + "users/" + username;
        Response response = http.get(url).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        User user = gson.fromJson(response.body(), User.class);
        return user;
    }

    @Override
    public List<Event> receivedEvent(String user, int page) throws GithubError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        String url = API_HOST + "users/" + user + "/received_events";
        Response response = http.get(url, params).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<Event> events = gson.fromJson(response.body(),
                new TypeToken<List<Event>>() {
                }.getType());
        System.out.println("events = " + events);
        return events;
    }

    @Override
    public List<Event> userEvent(String user, int page) throws GithubError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        String url = API_HOST + "users/" + user + "/events";
        Response response = http.get(url, params).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<Event> events = gson.fromJson(response.body(),
                new TypeToken<List<Event>>() {
                }.getType());
        System.out.println("events = " + events);
        return events;
    }

    @Override
    public List<Event> repoEvent(String user, String repo, int page) throws GithubError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PAGE, String.valueOf(page));
        String url = API_HOST + "repos/" + user + "/" + repo + "/events";
        Response response = http.get(url, params).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<Event> events = gson.fromJson(response.body(),
                new TypeToken<List<Event>>() {
                }.getType());
        System.out.println("events = " + events);
        return events;
    }

    @Override
    public boolean hasFollow(String targetUser) throws GithubError {
        String url = API_HOST + "user/following/" + targetUser;
        Response response = http.get(url).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            System.out.println("header = " + response.headers());
            System.out.println("header = " + response.statusCode());

        }
        if (response.statusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean follow(String targetUser) throws GithubError {
        String url = API_HOST + "user/following/" + targetUser;
        Response response = http.put(url).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else
            System.out.println("header = " + response.headers());
            System.out.println("header = " + response.statusCode());


        if (response.statusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean unfollow(String targetUser) throws GithubError {
        String url = API_HOST + "user/following/" + targetUser;
        Response response = http.delete(url).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            System.out.println("header = " + response.headers());
            System.out.println("header = " + response.statusCode());
        }
        if (response.statusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String readme(String owner, String repo) throws GithubError {
        String url = API_HOST + "repos/" + owner + "/" +repo + "/readme";
        Response response = http.get(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        if (response.isSuccess() == false)
            throw githubError;



        return response.body();
    }

    @Override
    public boolean hasStarRepo(String owner, String repo) throws GithubError {
        String url = API_HOST + "user/starred/" + owner + "/" +repo;
        Response response = http.get(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        if (response.isSuccess() == false)
            throw githubError;
        System.out.println("hasStarRepo statusCode " + response.statusCode());
        if(response.statusCode() == 204){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean starRepo(String owner, String repo) throws GithubError {
        String url = API_HOST + "user/starred/" + owner + "/" +repo;
        Response response = http.put(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        if (response.isSuccess() == false)
            throw githubError;
        System.out.println("starRepo statusCode " + response.statusCode());
        if(response.statusCode() == 204){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean unStarRepo(String owner, String repo) throws GithubError {
        String url = API_HOST + "user/starred/" + owner + "/" +repo;
        Response response = http.delete(url).headers(configreHttpHeader()).accept("application/vnd.github.VERSION.html").response();
        if (response.isSuccess() == false)
            throw githubError;
        System.out.println("unStarRepo statusCode " + response.statusCode());
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
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<User> userList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return userList;
    }

    @Override
    public List<User> forkers(String owner, String repo,int page) throws GithubError {
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/forks";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
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
    public boolean fork(String owner, String repo) throws GithubError {
        return false;
    }

    @Override
    public List<User> collaborators(String owner,String repo,int page) throws GithubError {
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/collaborators";
        Response response = http.get(url, pagination(page)).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
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
    public Tree getTree(String owner, String repo, String ref) throws GithubError {
         //GET /repos/:owner/:repo/git/trees/:sha
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/git/trees/" + ref;
        Response response = http.get(url).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        Tree tree = gson.fromJson(response.body(),Tree.class);
        return tree;
    }

    @Override
    public List<User> searchUser(List<String> keywords)  throws GithubError {
        //GET /search/users
        StringBuilder keywordsParams = new StringBuilder();
        for(int i = 0; i < keywords.size();i++){
            if(i != keywords.size()-1)
                keywordsParams.append(keywords.get(i) + "+");
            else
                keywordsParams.append(keywords.get(i));
        }
        String url = API_HOST + "search/users?q=" + keywordsParams.toString();
        Response response = http.get(url).headers(configreHttpHeader()).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        Gson gson = new Gson();
        ArrayList<User> userList = gson.fromJson(response.body(),
                new TypeToken<List<User>>() {
                }.getType());
        return null;
    }

    @Override
    public List<Repository> searchRepo(List<String> keywords) {
        return null;
    }

    @Override
    public String getRawContent(String owner, String repo, String path) throws GithubError {
        ///repos/:owner/:repo/contents/:path
        String url = API_HOST + "repos/" + owner + "/" + repo  + "/contents/" + path;
        Response response = http.get(url).headers(configreHttpHeader()).header("Accept", ACCEPT_RAW).response();
        if (response.isSuccess() == false)
            throw githubError;
        else {
            testResult(response);
        }
        return response.body();
    }


    public void testResult(Response response) {

        System.out.println(response.statusCode());
        System.out.println(response.headers());
       // System.out.println(response.body());
    }

}
