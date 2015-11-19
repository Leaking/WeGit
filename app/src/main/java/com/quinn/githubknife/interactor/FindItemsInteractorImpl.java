package com.quinn.githubknife.interactor;

import android.content.Context;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadItemListListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.github.RepoSearch;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.Tree;
import com.quinn.httpknife.github.User;
import com.quinn.httpknife.github.UserSearch;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Quinn on 7/20/15.
 */
public class FindItemsInteractorImpl implements FindItemsInteractor {

    private final static String TAG = FindItemsInteractor.class.getSimpleName();
    private final static int LOAD_MORE_FAIL = 3;
    private final static int LOAD_FIRST_FAIL = 2;
    private final static int LOAD_SUCCESS = 1;

    private GitHubAccount gitHubAccount;
    private OnLoadItemListListener listener;
    private GithubService service;
    private Context context;

    public FindItemsInteractorImpl(Context context, final OnLoadItemListListener listener) {
        this.context = context;
        this.service = RetrofitUtil.getJsonRetrofitInstance(context).create(GithubService.class);
        this.gitHubAccount = GitHubAccount.getInstance(context);
        this.listener = listener;
    }


    @Override
    public void loadFollowerings(final String user, final int page) {

        final Call<List<User>> call = service.follwerings(user, String.valueOf(page));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {

                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadFollowerings(user,page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);
            }
        });



    }

    @Override
    public void loadFollwers(final String user, final int page) {

        final Call<List<User>> call = service.followers(user, String.valueOf(page));

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadFollwers(user, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });
    }

    @Override
    public void loadAuthUser() {

    }

    @Override
    public void loadAuthRepos() {

    }

    @Override
    public void loadRepo(final String user, final int page) {
        Call<List<Repository>> call = service.userRepo(user, String.valueOf(page));
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadRepo(user, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }
            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });
    }

    @Override
    public void loadStarredRepo(final String user, final int page) {
        Call<List<Repository>> call = service.starredRepo(user, String.valueOf(page));
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Response<List<Repository>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadStarredRepo(user, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });






    }

    @Override
    public void loadReceivedEvents(final String user, final int page) {
        Call<List<Event>> call = service.receivedEvent(user, String.valueOf(page));
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadReceivedEvents(user, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }
            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });



    }

    @Override
    public void loadUserEvents(final String user, final int page) {
        Call<List<Event>> call = service.publicEvent(user, String.valueOf(page));
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadUserEvents(user, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }
            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });
    }

    @Override
    public void loadRepoEvents(String user, String repo, int page) {

    }

    @Override
    public void loadStargazers(final String owner, final String repo, final int page) {
        final Call<List<User>> call = service.stargazers(owner, repo,String.valueOf(page));

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadStargazers(owner, repo, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });
    }

    @Override
    public void loadForkers(final String owner, final String repo, final int page) {
        final Call<List<User>> call = service.forkers(owner, repo, String.valueOf(page));

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadForkers(owner, repo, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });
    }

    @Override
    public void loadCollaborators(final String owner, final String repo, final int page) {
        final Call<List<User>> call = service.collaborators(owner, repo, String.valueOf(page));

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadCollaborators(owner, repo, page);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });
    }

    @Override
    public void loadTree(final String owner, final String repo, final String sha) {
        final Call<Tree> call = service.getTree(owner, repo, sha);

        call.enqueue(new Callback<Tree>() {
            @Override
            public void onResponse(Response<Tree> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadTree(owner, repo, sha);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body().getTree());
                } else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure();
            }
        });
    }

    @Override
    public void searchUsers(final List<String> keywords, final int page) {
        StringBuilder keywordsParams = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            if (i != keywords.size() - 1)
                keywordsParams.append(keywords.get(i) + "+");
            else
                keywordsParams.append(keywords.get(i));
        }
        final Call<UserSearch> call = service.searchUser(keywordsParams.toString(), String.valueOf(page));

        call.enqueue(new Callback<UserSearch>() {
            @Override
            public void onResponse(Response<UserSearch> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    searchUsers(keywords, page);
                } else if (response.isSuccess()) {
                    List<User> users = response.body().getItems();
                    User user = new User();
                    user.setFollowers(response.body().getTotal_count());
                    users.add(0, user);
                    listener.onFinished(users);
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });

    }

    @Override
    public void searchRepos(final List<String> keywords, final int page) {

        StringBuilder keywordsParams = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            if (i != keywords.size() - 1)
                keywordsParams.append(keywords.get(i) + "+");
            else
                keywordsParams.append(keywords.get(i));
        }
        final Call<RepoSearch> call = service.searchRepo(keywordsParams.toString(), String.valueOf(page));

        call.enqueue(new Callback<RepoSearch>() {
            @Override
            public void onResponse(Response<RepoSearch> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    searchUsers(keywords, page);
                } else if (response.isSuccess()) {
                    List<Repository> repos = response.body().getItems();
                    Repository repo = new Repository();
                    repo.setForks_count(response.body().getTotal_count());
                    repos.add(0,repo);
                    listener.onFinished(repos);
                } else {
                    handleFailure(page);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure(page);

            }
        });

    }

    @Override
    public void loadBranches(final String owner, final String repo) {
        final Call<List<Branch>> call = service.getBranches(owner, repo);

        call.enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Response<List<Branch>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if (response.code() == 401) {
                    gitHubAccount.invalidateToken(RetrofitUtil.token);
                    loadBranches(owner, repo);
                } else if (response.isSuccess()) {
                    listener.onFinished(response.body());
                } else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleFailure();
            }
        });
    }


    public void handleFailure(int page){
        if (page == 1) {
            listener.onError(true, context.getResources().getString(R.string.fail_load));
        } else {
            listener.onError(false, context.getResources().getString(R.string.fail_loadMore));
        }
    }

    public void handleFailure(){
        listener.onError(true, context.getResources().getString(R.string.fail_load));
    }


}
