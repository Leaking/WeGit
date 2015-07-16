/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quinn.githubknife.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AccountsException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

/**
 * GitHub account model
 */
public class GitHubAccount {

    private static final String TAG = "GitHubAccount";

    public static final String ACCOUNT_TYPE = "com.githubknife";

    private final Account account;

    private final AccountManager manager;

        Context context;
    /**
     * Create account wrapper
     *
     * @param account
     * @param manager
     */
    public GitHubAccount(final Account account, final AccountManager manager,Context context) {
        this.account = account;
        this.manager = manager;
        this.context = context;
    }

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return account.name;
    }

    /**
     * Get password
     *
     * @return password
     */
    public String getPassword() {
        return manager.getPassword(account);
    }

    /**
     * Get auth token
     *
     * @return token
     */
    public String getAuthToken() {

        final AccountManagerFuture<Bundle> future = manager.getAuthToken(account, ACCOUNT_TYPE, null, (Activity)context, null, null);

        try {
            Bundle result = future.getResult();
            return result.getString(AccountManager.KEY_AUTHTOKEN);
        } catch (AccountsException e) {
            Log.e(TAG, "Auth token lookup failed", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Auth token lookup failed", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + account.name + ']';
    }
}
