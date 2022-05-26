package com.example.leaguetarget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SeriesActivity extends AppCompatActivity {

    ImageView lecLogo;
    ImageView lcsLogo;
    ImageView lckLogo;

    private static final String TAG = "SeriesActivity";

    //vars
    public ArrayList<String> mNames = new ArrayList<>();
    public ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        lecLogo = findViewById(R.id.lecImg);
        lcsLogo = findViewById(R.id.lcsImg);
        lckLogo = findViewById(R.id.lckImg);

        lecLogo.setOnClickListener(v -> {
            mNames.removeAll(mNames);
            mImageUrls.removeAll(mImageUrls);

            lecLogo.setAlpha((float) 1);
            lcsLogo.setAlpha((float) 0.2);
            lckLogo.setAlpha((float) 0.2);

            getSeriesTeams("4176");
        });

        lcsLogo.setOnClickListener(v -> {
            mNames.removeAll(mNames);
            mImageUrls.removeAll(mImageUrls);

            lecLogo.setAlpha((float) 0.2);
            lcsLogo.setAlpha((float) 1);
            lckLogo.setAlpha((float) 0.2);

            getSeriesTeams("4357");
        });

        lckLogo.setOnClickListener(v -> {
            mNames.removeAll(mNames);
            mImageUrls.removeAll(mImageUrls);

            lecLogo.setAlpha((float) 0.2);
            lcsLogo.setAlpha((float) 0.2);
            lckLogo.setAlpha((float) 1);

            getSeriesTeams("4250");
        });

//        initImageBitmaps();

        lcsLogo.setAlpha((float) 0.2);
        lckLogo.setAlpha((float) 0.2);
        getSeriesTeams("4176");

    }

    private void getSeriesTeams(String seriesId) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.pandascore.co/lol/series/"+ seriesId +"/teams?token=mpqUX6IQw_RWfbhwhlgRJ4A56hEa0ebXdc3A5adwRkWh1b4hanY";

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    try {
                        JSONArray leagueSeriesJson = new JSONArray(myResponse);

                        for (int y = 0; y < leagueSeriesJson.length(); y++) {
                            String teamName = leagueSeriesJson.getJSONObject(y).optString("name");
                            String teamLogo = leagueSeriesJson.getJSONObject(y).optString("image_url");

                            mNames.add(teamName);
                            mImageUrls.add(teamLogo);
                        }

                        runOnUiThread(() -> initRecyclerView(mNames, mImageUrls, apiUrl));

                    } catch (JSONException e) {
                        Log.i(TAG, e.toString());
                    }

                } else {
                    SeriesActivity.this.runOnUiThread(() -> Log.i(TAG, "Not working"));
                }
            }
        });
    }

    public void initRecyclerView(ArrayList<String> mNames, ArrayList<String> mImageUrls, String seriesApiLink) {
        RecyclerView recyclerView = findViewById(R.id.players_RecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, seriesApiLink);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}