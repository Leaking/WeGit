package com.quinn.githubknife.account;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.utils.PreferenceUtils;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;


public class LoginActivity extends BaseActivity {


    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private Bundle mResultBundle = null;

    private EditText username;
    private EditText password;
    private Button submit;
    private String accountName;
    private String mAuthTokenType;
    private String accountType;
    private AccountManager mAccountManager;

    private Github github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        github = new GithubImpl(this);
        mAccountAuthenticatorResponse =
                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }

        setContentView(R.layout.activity_login);
        mAccountManager = AccountManager.get(getBaseContext());


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);

        accountName = getIntent().getStringExtra(Authenticator.ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(Authenticator.ARG_AUTH_TYPE);
        accountType = getIntent().getStringExtra(Authenticator.ARG_ACCOUNT_TYPE);

//        if (mAuthTokenType == null)
        mAuthTokenType = Authenticator.AUTHTOKEN_TYPE_FULL_ACCESS;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumbit(username.getText().toString(),password.getText().toString());

            }
        });

    }

    public void sumbit(final String username,final String password) {


        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                Log.d("udinic", "TAG" + "> Started authenticating");

                String authtoken = null;
                Bundle data = new Bundle();
                try {
                    authtoken = getToken(username, password);
                    System.out.println("token == " + authtoken);

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, username);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putString(Authenticator.PARAM_USER_PASS, password);

                } catch (Exception e) {
                    data.putString("KEY_ERROR_MESSAGE", e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra("KEY_ERROR_MESSAGE")) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra("KEY_ERROR_MESSAGE"), Toast.LENGTH_SHORT).show();
                } else {
                    finishLogin(intent);
                }
            }
        }.execute();


    }


    public void finishLogin(Intent intent){
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(Authenticator.PARAM_USER_PASS);
        PreferenceUtils.putString(this,PreferenceUtils.Key.ACCOUNT,accountName);
        final Account account = new Account(accountName, accountType);
        if (getIntent().getBooleanExtra(Authenticator.ARG_IS_ADDING_NEW_ACCOUNT, true)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, mAuthTokenType, authtoken);
        } else {
            mAccountManager.setPassword(account, accountPassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public String getToken(String username,String password){
        String token = github.createToken(username,password);
        return token;
       // return "0f7d54c808684c0ad9a7c217b63b1c017bdae217";
    }

    /**
     * Set the result that is to be sent as the result of the request that caused this
     * Activity to be launched. If result is null or this method is never called then
     * the request will be canceled.
     * @param result this is returned as the result of the AbstractAccountAuthenticator request
     */
    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }


    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finish() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                        "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }

}
