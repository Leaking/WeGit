package com.quinn.githubknife.interactor;

import android.content.Context;
import android.util.Log;

import com.quinn.githubknife.R;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.listener.OnLoadItemListListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.githubknife.utils.Constants;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Branch;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.github.RepoSearch;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.Tree;
import com.quinn.httpknife.github.TrendingRepo;
import com.quinn.httpknife.github.User;
import com.quinn.httpknife.github.UserSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        service.follwerings(user, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<User>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleFailure(page);
                    }

                    @Override
                    public void onNext(Response<List<User>> response) {
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
                });
    }

    @Override
    public void loadFollwers(final String user, final int page) {

        service.followers(user, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<User>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleFailure(page);
                    }

                    @Override
                    public void onNext(Response<List<User>> response) {
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

    public void trendingRepos(final String url, final TrendingRepo.SINCE_TYPE sinceType){
        Observable.create(new Observable.OnSubscribe<List<TrendingRepo>>() {
            @Override
            public void call(Subscriber<? super List<TrendingRepo>> subscriber) {
                // request
                ArrayList<TrendingRepo> repos = new ArrayList<>();
                try {
                    Log.i(TAG, "trendingRepos URL " + url);
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByClass("repo-list-name");
                    TrendingRepo repo;
                    for (Element element : elements) {
                        repo = new TrendingRepo();
                        Element hrefElement = element.child(0);
                        String href = hrefElement.attr("href");
                        Log.i(TAG, "href = " + href);
                        repo.setFull_name(href.substring(1));
                        String[] splits = href.split("/");
                        repo.setName(splits[2]);
                        User owner = new User();
                        owner.setLogin(splits[1]);
                        repo.setOwner(owner);


                        Element despElement = element.nextElementSibling();
                        String desp = despElement.text();
                        Log.i(TAG, "desp = " + desp);
                        repo.setDescription(desp);

                        Element newStarsElement = despElement.nextElementSibling();
                        String starsDetail = newStarsElement.text();
                        Log.i(TAG, "starsDetial = " + starsDetail);



                        //正则解析语言、Star数量
                        Pattern patternLanguage = Pattern.compile("[A-Za-z]{1,}"); //头个单词就是语言类别
                        Pattern patternStarNum = Pattern.compile("[0-9]{1,}"); //头个数字就是Star数量
                        Matcher matcherLanguage = patternLanguage.matcher(starsDetail);
                        Matcher matcherStarNum = patternStarNum.matcher(starsDetail);

                        repo.setSince_type(sinceType);

                        if (matcherStarNum.find()) {
                            repo.setAddStars(Integer.parseInt(matcherStarNum.group().trim()));
                        } else {
                            Log.i(TAG, "matcherStarNum.find fail" + starsDetail);
                        }

                        if (matcherLanguage.find()) {
                            repo.setLanguage(matcherLanguage.group());
                            if(!starsDetail.startsWith(repo.getLanguage())){
                                repo.setLanguage("");
                            }
                        } else {
                            Log.i(TAG, "matcherLanguage.find fail" + starsDetail);
                        }

                        repos.add(repo);
                    }
                    subscriber.onNext(repos);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error = " + e.toString());
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TrendingRepo>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onError(true, context.getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(List<TrendingRepo> trendingRepos) {
                        listener.onFinished(trendingRepos);
                    }
                });
    }

    @Override
    public void trendingUsers(String keywords, TrendingRepo.SINCE_TYPE sinceType){

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
