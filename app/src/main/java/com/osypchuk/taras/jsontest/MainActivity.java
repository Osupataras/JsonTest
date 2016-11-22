package com.osypchuk.taras.jsontest;

import android.app.LoaderManager;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Match>>, View.OnClickListener {

    TextView acc;
    public String my_account_id;
    static final int id_id = 1;
    static String last_acc = "";

    private RVAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc = (TextView) findViewById(R.id.account_name);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        acc.setText("account id");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Loader<List<Match>> loader;

        my_account_id = data.getStringExtra("acc");
        acc.setText(my_account_id);
        Bundle bundle = new Bundle();
        bundle.putString(JsonAsynkTaskLoader.acc, my_account_id);


        if (my_account_id.equals(last_acc)) {
            loader = getLoaderManager().getLoader(Integer.parseInt(last_acc));
        } else {
            loader = getLoaderManager().restartLoader(id_id, bundle, this);
            last_acc = my_account_id;
        }
    }


    @Override
    public Loader<List<Match>> onCreateLoader(int id, Bundle args) {
        Loader<List<Match>> jsonasynkloader = null;
        if (id == id_id)
            jsonasynkloader = new JsonAsynkTaskLoader(this, args);

        return jsonasynkloader;

    }

    @Override
    public void onLoadFinished(Loader<List<Match>> loader, List<Match> data) {
        adapter = new RVAdapter(data);
        rv.setAdapter(adapter);
    }


    @Override
    public void onLoaderReset(Loader<List<Match>> loader) {
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RequestActivity.class);
        startActivityForResult(intent, 1);

    }
}
