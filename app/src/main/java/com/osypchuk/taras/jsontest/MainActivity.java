package com.osypchuk.taras.jsontest;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import info.androidhive.jsonparsing.R;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Match>>,View.OnClickListener {

    TextView acc;
    public String my_account_id, steam_url_matches_request,url,url_match_details_request;
    private String TAG = MainActivity.class.getSimpleName();
    static final int id_id = 1;
    static int last_acc = 0;

    private ProgressDialog pDialog;
    private List<Match> match;
    private RVAdapter adapter;
    private RecyclerView rv;

    Loader<List<Match>> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc = (TextView) findViewById(R.id.account_name);
        acc.setText("account id");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        Bundle bndl = new Bundle();
        bndl.putString(JsonLoader.acc,"169660175");
        loader = getLoaderManager().initLoader(id_id,bndl,this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}

        Loader<List<Match>> loader;

        my_account_id = data.getStringExtra("acc");
        steam_url_matches_request = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=73EE0AFD6C12F2AB4605F611C2AB86EF&account_id=";
        url = steam_url_matches_request + my_account_id+"&matches_requested=10";
        url_match_details_request = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=73EE0AFD6C12F2AB4605F611C2AB86EF&account_id=";

        acc.setText(my_account_id);

        if (Integer.parseInt(my_account_id) == last_acc){
            loader = getLoaderManager().getLoader(last_acc);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString(JsonLoader.acc,my_account_id);
            loader = getLoaderManager().restartLoader(id_id,bundle,this);
            last_acc = Integer.parseInt(my_account_id);
        }
        loader.forceLoad();
        }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Loader<List<Match>> jsonloader = null;
        if (id == id_id)
            jsonloader = new JsonLoader(this,args);

        return jsonloader;

    }

    @Override
    public void onLoadFinished(Loader<List<Match>> loader, List<Match> data) {

        adapter = new RVAdapter(data);
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }


    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,RequestActivity.class);
        startActivityForResult(intent, 1);

    }

    /**
     * Async task class to get json by making HTTP call
     */
//    private class GetMatches extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//
//            String jsonMatches = JsonDownloader(url);
//
//            Log.e(TAG, "Response from url: " + jsonMatches);
//
//            if (jsonMatches != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonMatches);
//
//                    // Getting JSON Array node
//                    JSONObject result = jsonObj.getJSONObject("result");
//                    JSONArray matches = result.getJSONArray("matches");
//
//                    match = new ArrayList<>();
//                    for (int i = 0; i < matches.length(); i++){
//
//                        JSONObject lastmatch = matches.getJSONObject(i);
//
//                        String match_id = lastmatch.getString("match_id");
//                        String url_match_details = url_match_details_request+my_account_id+
//                                "&match_id="+match_id;
//
//                        String jsonMatchDetails = JsonDownloader(url_match_details);
//                        Boolean radiant_win = true;
//                        if (jsonMatchDetails != null) {
//                            try {
//                                JSONObject jsonObjDet = new JSONObject(jsonMatchDetails);
//                                JSONObject resultDet = jsonObjDet.getJSONObject("result");
//                                radiant_win = resultDet.getBoolean("radiant_win");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                        String match_seq_num = lastmatch.getString("match_seq_num");
//                        String start_time = lastmatch.getString("start_time");
//
//
//                        int lobby_type = lastmatch.getInt("lobby_type");
//                        String slobby_type = "";
//                        switch (lobby_type) {
//                            case -1:
//                                slobby_type = "Invalid";break;
//                            case 0:
//                                slobby_type = "Public matchmaking";break;
//                            case 1:
//                                slobby_type = "Practice";break;
//                            case 2:
//                                slobby_type = "Tournament";break;
//                            case 3:
//                                slobby_type = "Tutorial";break;
//                            case 4:
//                                slobby_type = "Co-op with AI";break;
//                            case 5:
//                                slobby_type = "Team match";break;
//                            case 6:
//                                slobby_type = "Solo queue";break;
//                            case 7:
//                                slobby_type = "Ranked matchmaking";break;
//                            case 8:
//                                slobby_type = "Solo Mid 1 vs 1";break;
//                        }
//
//                        String radiant_team_id = lastmatch.getString("radiant_team_id");
//                        String dire_team_id = lastmatch.getString("dire_team_id");
//
//                        long timestamp = Long.parseLong(start_time)*1000;
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.getDefault());
//                        Date d = new Date (timestamp);
//                        String start = dateFormat.format(d);
//
//                        JSONArray players = lastmatch.getJSONArray("players");
//                        String my_hero = "";
//                        int my_hero_id = 0;
//                        Boolean my_team_radiant = true;
//                        String my_player_slot = "";
//                        String isWon = "Lose";
//                        for (int j = 0; j < players.length(); j++){
//                            JSONObject player = players.getJSONObject(j);
//                            String account_id = player.getString("account_id");
//                            String player_slot = player.getString("player_slot");
//                            String hero_id = player.getString("hero_id");
//
//                            if(Get_heroID_by_accountID.accontID_confirming(account_id,my_account_id)){
//                                my_hero = Get_heroID_by_accountID.Get_heroNAME_by_heroId(Integer.parseInt(hero_id));
//                                my_hero_id = getResources().getIdentifier(my_hero , "drawable", getPackageName());
//                                my_player_slot = player_slot;
//                                if(Integer.parseInt(my_player_slot)<10){my_team_radiant = true;}
//                                else {my_team_radiant = false;}
//                            }
//
//                            HashMap<String, String> tenplayer = new HashMap<>();
//
//                            player.put("account_id",account_id);
//                            player.put("player_slot",player_slot);
//                            player.put("hero_id",hero_id);
//
////                            playerListItem.add(tenplayer);
//
//
//                        }
//                        Boolean win = false;
//                        if(my_team_radiant.equals(radiant_win)){isWon = "Won";}
//                        match.add(new Match(my_hero_id,my_hero,match_id,start,slobby_type,isWon));
//
//                    }
//
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });
//
//                }
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//            }
//
//            return null;
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            /**
//             * Updating parsed JSON data into ListView
//             * */
//
//
//
//        }
//
//
//    }
}