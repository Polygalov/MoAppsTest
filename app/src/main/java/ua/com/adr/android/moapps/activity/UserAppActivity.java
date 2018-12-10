package ua.com.adr.android.moapps.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.adr.android.moapps.api.ApiUtils;
import ua.com.adr.android.moapps.api.AppListApi;
import ua.com.adr.android.moapps.model.AppListRequest;
import ua.com.adr.android.moapps.model.Datum;
import ua.com.adr.android.moapps.model.JSONResponse;
import ua.com.adr.android.moapps.R;
import ua.com.adr.android.moapps.RecyclerItemClickListener;
import ua.com.adr.android.moapps.adapter.DataAdapter;

public class UserAppActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView tvErr;
    DataAdapter adapter;
    private AppListApi mAppListApi;
    private ArrayList<Datum> mAppDataList;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);

        mAppListApi = ApiUtils.getAppListApi();

        mAppDataList = new ArrayList<>();

        tvErr = findViewById(R.id.tv_err);
        tvErr.setVisibility(View.GONE);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(UserAppActivity.this);
        refreshLayout.setColorSchemeResources(R.color.refresh_start, R.color.refresh_end);
        sendAppPost(getToken());
    }


    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Datum data) {
            String appUrl = data.getApplicationUrl();
            Intent intent = new Intent(UserAppActivity.this, PreviewActivity.class);
            intent.putExtra("app_url", appUrl);
            startActivity(intent);
        }
    };

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRefresh() {
        tvErr.setVisibility(View.GONE);
        sendAppPost(getToken());
    }

    public void sendAppPost(String token) {
        final ProgressDialog dialog;
        // Progress Dialog for User Interaction
        dialog = new ProgressDialog(UserAppActivity.this);
        dialog.setTitle(getString(R.string.text_wait));
        dialog.setMessage(getString(R.string.string_getting_json_message));
        dialog.show();

        AppListRequest mUserAccount = new AppListRequest(0, 0, 1000, token);
        mAppListApi.getApps(mUserAccount).enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                //Dismiss Dialog
                dialog.dismiss();
                if (response.isSuccessful()) {
                    mAppDataList = response.body().getDatas();
                    generateEmployeeList(mAppDataList);
                } else
                    Toast.makeText(UserAppActivity.this, R.string.err_service_unavailable, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                //Dismiss Dialog
                dialog.dismiss();
                tvErr.setVisibility(View.VISIBLE);
                Log.d("Retrofit", "ERROR");
            }
        });
        refreshLayout.setRefreshing(false);
    }

    private void generateEmployeeList(ArrayList<Datum> mDataList) {
        recyclerView = (RecyclerView) findViewById(R.id.app_list);
        adapter = new DataAdapter(mDataList , recyclerItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserAppActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public String getToken() {
        return LoginActivity.getTokenDefaults("token", this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_log_out:
                LoginActivity.setDefaults("token", "", "", "", this);
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
