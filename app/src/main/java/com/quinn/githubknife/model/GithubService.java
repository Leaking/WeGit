package com.quinn.githubknife.model;

import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.Empty;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.github.RepoSearch;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.Tree;
import com.quinn.httpknife.github.User;
import com.quinn.httpknife.github.UserSearch;

import java.util.List;

import retrofit.Call;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Quinn on 10/10/15.
 */
public interface GithubService{

    //Api about user


    @GET("/user")
    Call<User> authUser();

    @GET("/users/{username}")
    Call<User> user(@Path("username") String username);

    @GET("/users/{user}/following?perpage=10")
    Call<List<User>> follwerings(@Path("user") String user,@Query("page") String page);

    @GET("/users/{user}/followers?perpage=10")
    Call<List<User>> followers(@Path("user") String user,@Query("page") String page);

    @GET("/users/{user}/repos?sort=pushed&perpage=10")
    Call<List<Repository>> userRepo(@Path("user") String user,@Query("page") String page);

    @GET("/users/{user}/starred?&perpage=10")
    Call<List<Repository>> starredRepo(@Path("user") String user,@Query("page") String page);

    @GET("/users/{user}/received_events?&perpage=10")
    Call<List<Event>> receivedEvent(@Path("user") String user,@Query("page") String page);

    @GET("/user/starred/{owner}/{repo}")
    Call<List<User>> hasStar(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);

    @PUT("/user/starred/{owner}/{repo}")
    Call<List<User>> star(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);

    @DELETE("/user/starred/{owner}/{repo}")
    Call<List<User>> unStar(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);


    //Api about repo



    @GET("/repos/{owner}/{repo}/stargazers?&perpage=10")
    Call<List<User>> stargazers(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);

    @GET("/repos/{owner}/{repo}/forks?&perpage=10")
    Call<List<User>> forkers(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);

    @GET("/repos/{owner}/{repo}/collaborators?&perpage=10")
    Call<List<User>> collaborators(@Path("owner") String owner, @Path("repo") String repo,@Query("page") String page);

    @GET("/repos/{owner}/{repo}/branches/")
    Call<List<Branch>> getBranches(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/git/trees/{sha}?&perpage=10")
    Call<Tree> getTree(@Path("owner") String owner, @Path("repo") String repo,@Path("sha") String sha);

    @GET("/repos/{owner}/{repo}/contents/{path}")
    Call<String> getRawContent(@Path("owner") String owner, @Path("repo") String repo,@Path("path") String path);

    //Api about search
    @GET("/search/users?&perpage=10")
    Call<UserSearch> searchUser(@Query("q") String q, @Query("page") String page);

    @GET("/search/repositories?&perpage=10")
    Call<RepoSearch> searchRepo(@Query("q") String q, @Query("page") String page);




    //==========
    //Api about user-user relation
    //==========

    @GET("/user/following/{username}")
    Call<Empty> hasFollow(@Path("username") String username);

    @PUT("/user/following/{username}")
    Call<Empty> follow(@Path("username") String username);

    @DELETE("/user/following/{username}")
    Call<Empty> unFollow(@Path("username") String username);


}
