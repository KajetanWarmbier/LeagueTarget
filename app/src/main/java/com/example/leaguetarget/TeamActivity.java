package com.example.leaguetarget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TeamActivity extends AppCompatActivity {

    private static final String TAG = "TEAMINDEX";

    int teamId;
    String seriesApiLink;

    ImageView teamLogo;
    TextView team_acronym;
    TextView team_fullname;

    private ArrayList<String> playersImages = new ArrayList<>();
    private ArrayList<String> playersFullnames = new ArrayList<>();
    private ArrayList<String> playersNicknames = new ArrayList<>();
    private ArrayList<String> playersNationalityFlag = new ArrayList<>();
    private ArrayList<String> playersAges = new ArrayList<>();
    private ArrayList<String> playersRoles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamLogo = findViewById(R.id.team_logo_activity);
        team_acronym = findViewById(R.id.team_acronym);
        team_fullname = findViewById(R.id.team_fullname);

        teamId = getIntent().getIntExtra("teamId", 0);
        seriesApiLink = getIntent().getStringExtra("seriesApiLink");

        getSeriesTeams();
    }

    private void getSeriesTeams() {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = seriesApiLink;

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
                        JSONObject teamJson = leagueSeriesJson.getJSONObject(teamId);
                        JSONArray teamPlayers = teamJson.getJSONArray("players");
                        Log.i(TAG, teamPlayers.toString());

                        for (int y = 0; y < teamPlayers.length(); y++) {
                            String image = teamPlayers.getJSONObject(y).optString("image_url");
                            String fullname = teamPlayers.getJSONObject(y).optString("first_name") + " " + teamPlayers.getJSONObject(y).optString("last_name");
                            String nickname = "\"" + teamPlayers.getJSONObject(y).optString("name") + "\"";
                            String nationality = teamPlayers.getJSONObject(y).optString("nationality").toLowerCase(Locale.ROOT);
                            String age = String.valueOf(teamPlayers.getJSONObject(y).optInt("age"));

                            String role = new String();

                            switch (teamPlayers.getJSONObject(y).optString("role")) {
                                case "jun":
                                    role = "JUNGLER";
                                    break;
                                case "adc":
                                    role = "ADC";
                                    break;
                                case "sup":
                                    role = "SUPPORT";
                                    break;
                                case "mid":
                                    role = "MIDLANER";
                                    break;
                                case "top":
                                    role = "TOPLANER";
                                    break;
                                default:
                                    role = "NaN";
                                    break;
                            }

                            playersImages.add(image);
                            playersFullnames.add(fullname);
                            playersNicknames.add(nickname);
                            playersNationalityFlag.add(nationality);
                            playersAges.add(age);
                            playersRoles.add(role);
                        }

                        TeamActivity.this.runOnUiThread(() -> {
                            Glide.with(TeamActivity.this)
                                    .asBitmap()
                                    .load(teamJson.optString("image_url"))
                                    .into(teamLogo);
                            team_acronym.setText(teamJson.optString("acronym"));
                            team_fullname.setText(teamJson.optString("name"));

                            initRecyclerView(playersImages, playersFullnames, playersNicknames, playersNationalityFlag, playersAges, playersRoles);
                        });
//                        String acronym = leagueSeriesJson.getJSONObject(8).optString("acronym");
//                        String image_url = leagueSeriesJson.getJSONObject(8).optString("image_url");
//                        String name = leagueSeriesJson.getJSONObject(8).optString("name");

                    } catch (JSONException e) {
                        Log.i(TAG, e.toString());
                    }

                } else {
                    TeamActivity.this.runOnUiThread(() -> Log.i(TAG, "Not working"));
                }
            }
        });
    }

    public void initRecyclerView(ArrayList<String> playersImages, ArrayList<String> playersFullnames, ArrayList<String> playersNicknames, ArrayList<String> playersNationalityFlag, ArrayList<String> playersAges, ArrayList<String> playersRoles) {
        RecyclerView recyclerView = findViewById(R.id.team_players_RecyclerView);
        RecyclerViewAdapterPlayers adapter = new RecyclerViewAdapterPlayers(this, playersImages, playersFullnames, playersNicknames, playersNationalityFlag, playersAges, playersRoles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

