package kdmprj.umkc.edu.kdmpr1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kdmprj.umkc.edu.kdmpr1.diseases.GetDiseases;
import kdmprj.umkc.edu.kdmpr1.diseases.GetHealthConditions;
import kdmprj.umkc.edu.kdmpr1.speechtotext.GetChunks;
import kdmprj.umkc.edu.kdmpr1.speechtotext.VoicetoText;

/**
 * Created by venkatareddy on 2/27/2015.
 */
public class SearchActivity extends Activity {
    private static final String TAG ="SearchActivity" ;
    String item;
     TextView percent;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        final EditText drug=(EditText)findViewById(R.id.editText);
        Button searchButton = (Button)findViewById(R.id.search);
//         percent = (TextView)findViewById(R.id.percent);
        listView = (ListView)findViewById(R.id.resList);
        Spinner spinner= (Spinner)findViewById(R.id.spinner2);
//        final TextView searchButton=(TextView)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "search onclick");
                new AnalyzeUserInputs(item, drug.getText().toString(), SearchActivity.this, percent, listView).execute();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG,"spinner onclick");
               item= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int i=0;
        new VoicetoText(SearchActivity.this).voiceToText();
    }

    public void analyzeVoiceResults(ArrayList<String> matches) {
       List<String> chunks = new GetChunks().getChunks(matches.get(0).toString(),SearchActivity.this);
        if(null!= chunks && chunks.size()>1) {
            new AnalyzeUserInputs(item, chunks.get(2).toString(), SearchActivity.this, percent, listView).execute();
        }else{
            new AnalyzeUserInputs(item, "defa", SearchActivity.this, percent, listView).execute();
        }

    }
}
