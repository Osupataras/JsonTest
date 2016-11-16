package com.osypchuk.taras.jsontest;

/**
 * Created by Taras on 14.11.2016.
 */

class Match {
    int hero_icon_id;
    String hero_name;
    String match_id;
    String start_time;
    String lobby_type;
    String win;

    Match (int hero_icon_id, String hero_name, String match_id,String start_time,String lobby_type,String win){
        this.hero_icon_id = hero_icon_id;
        this.hero_name = hero_name;
        this.match_id = match_id;
        this.start_time = start_time;
        this.lobby_type = lobby_type;
        this.win = win;
    }


}
