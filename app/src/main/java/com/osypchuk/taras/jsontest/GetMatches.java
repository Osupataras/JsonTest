package com.osypchuk.taras.jsontest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Taras on 22.11.2016.
 */

public class GetMatches {

    private String TAG = MainActivity.class.getSimpleName();
    private List<Match> match = new ArrayList<>();

    GetMatches (){};


    private String JsonDownloader (String url){
        HttpHandler sh = new HttpHandler();
        String json = sh.makeServiceCall(url);
        return json;
    }


    public List<Match> GetListFromJson (String my_account_id){

        String steam_url_matches_request = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=73EE0AFD6C12F2AB4605F611C2AB86EF&account_id=";
        String url = steam_url_matches_request + my_account_id+"&matches_requested=10";
        String url_match_details_request = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=73EE0AFD6C12F2AB4605F611C2AB86EF&account_id=";

        String jsonMatches = JsonDownloader(url);

        Log.e(TAG, "Response from url: " + jsonMatches);

        if (jsonMatches != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonMatches);

                // Getting JSON Array node
                JSONObject result = jsonObj.getJSONObject("result");
                JSONArray matches = result.getJSONArray("matches");

                match = new ArrayList<>();
                for (int i = 0; i < matches.length(); i++){

                    JSONObject lastmatch = matches.getJSONObject(i);

                    String match_id = lastmatch.getString("match_id");
                    String url_match_details = url_match_details_request+my_account_id+
                            "&match_id="+match_id;

                    String jsonMatchDetails = JsonDownloader(url_match_details);
                    Boolean radiant_win = true;
                    if (jsonMatchDetails != null) {
                        try {
                            JSONObject jsonObjDet = new JSONObject(jsonMatchDetails);
                            JSONObject resultDet = jsonObjDet.getJSONObject("result");
                            radiant_win = resultDet.getBoolean("radiant_win");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    String match_seq_num = lastmatch.getString("match_seq_num");
                    String start_time = lastmatch.getString("start_time");


                    int lobby_type = lastmatch.getInt("lobby_type");
                    String slobby_type = "";
                    switch (lobby_type) {
                        case -1:
                            slobby_type = "Invalid";break;
                        case 0:
                            slobby_type = "Public matchmaking";break;
                        case 1:
                            slobby_type = "Practice";break;
                        case 2:
                            slobby_type = "Tournament";break;
                        case 3:
                            slobby_type = "Tutorial";break;
                        case 4:
                            slobby_type = "Co-op with AI";break;
                        case 5:
                            slobby_type = "Team match";break;
                        case 6:
                            slobby_type = "Solo queue";break;
                        case 7:
                            slobby_type = "Ranked matchmaking";break;
                        case 8:
                            slobby_type = "Solo Mid 1 vs 1";break;
                    }

                    String radiant_team_id = lastmatch.getString("radiant_team_id");
                    String dire_team_id = lastmatch.getString("dire_team_id");

                    long timestamp = Long.parseLong(start_time)*1000;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.getDefault());
                    Date d = new Date (timestamp);
                    String start = dateFormat.format(d);

                    JSONArray players = lastmatch.getJSONArray("players");
                    String my_hero = "";
                    int my_hero_id = 0;
                    Boolean my_team_radiant = true;
                    String my_player_slot = "";
                    String isWon = "Lose";
                    for (int j = 0; j < players.length(); j++){
                        JSONObject player = players.getJSONObject(j);
                        String account_id = player.getString("account_id");
                        String player_slot = player.getString("player_slot");
                        String hero_id = player.getString("hero_id");

                        if(Get_heroID_by_accountID.accontID_confirming(account_id,my_account_id)){
                            my_hero = Get_heroID_by_accountID.Get_heroNAME_by_heroId(Integer.parseInt(hero_id));
//                                my_hero_id = getResources().getIdentifier(my_hero , "drawable", getPackageName());
                            my_player_slot = player_slot;
                            my_team_radiant = Integer.parseInt(my_player_slot) < 10;
                        }

                        HashMap<String, String> tenplayer = new HashMap<>();

                        player.put("account_id",account_id);
                        player.put("player_slot",player_slot);
                        player.put("hero_id",hero_id);

//                            playerListItem.add(tenplayer);


                    }
                    Boolean win = false;
                    if(my_team_radiant.equals(radiant_win)){isWon = "Won";}
                    match.add(new Match(my_hero_id,my_hero,match_id,start,slobby_type,isWon));

                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        }
        else {
            Log.e(TAG, "Couldn't get json from server.");

        }
        return match;

    }
}
