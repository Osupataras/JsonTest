package com.osypchuk.taras.jsontest;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 22.11.2016.
 */

public class JsonAsynkTaskLoader extends AsyncTaskLoader<List<Match>> {

    public String my_account_id = "";
    private List<Match> match = new ArrayList<>();
    public final static String acc = "account";

    public JsonAsynkTaskLoader(Context context, Bundle bndl) {
        super(context);
        if (bndl != null) {
            bndl.getString(acc);
        }
    }

    @Override
    public List<Match> loadInBackground() {
        if (match == null) {
            return null;
        } else {
            GetMatches getMatches = new GetMatches();
            return getMatches.GetListFromJson(my_account_id);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(List<Match> data) {
        super.deliverResult(data);
    }
}
