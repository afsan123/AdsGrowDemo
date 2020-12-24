package com.example.adsgrowdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PageListActivity extends AppCompatActivity implements MyAdapter.OnPageClickListener {

    private Button mLogoutBtn;
    private FirebaseAuth mAuth;
    private ArrayList<FBPage> fbPages=new ArrayList<>();
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_list);
        RecyclerView rv_page_list = findViewById(R.id.rv_page_list);
        myAdapter = new MyAdapter(this,fbPages, this::onPageClick);
        rv_page_list.setLayoutManager(new LinearLayoutManager(this));
        rv_page_list.setAdapter(myAdapter);
        mLogoutBtn = (Button) findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();
        updatePageList();
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut(); //Firebase logout
                LoginManager.getInstance().logOut(); //Facebook logout
                updateUI();
            }
        });
    }

    private void updatePageList() {
        String path = "/me/accounts";
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                path,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        if (response.getRawResponse() != null) {
                            Log.d("pageResponse", response.getRawResponse());
                            JSONObject object = response.getJSONObject();
                            try {
                                JSONArray data=object.getJSONArray("data");
                                for (int i=0;i<data.length();i++) {
                                    fbPages.add(new FBPage(data.getJSONObject(i).optString("id"),data.getJSONObject(i).optString("name")));
                                }
                                myAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(PageListActivity.this, response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("pageGetError", response.getError().getErrorMessage());
                        }
                    }

                }
        ).executeAsync();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            updateUI();
        }
    }

    private void updateUI() {
        Toast.makeText(PageListActivity.this, "You are logged out", Toast.LENGTH_LONG).show();

        Intent pageListIntent = new Intent(PageListActivity.this, MainActivity.class);
        startActivity(pageListIntent);
        finish();
    }

    @Override
    public void onPageClick(int position) {
        fbPages.get(position);
    }
}