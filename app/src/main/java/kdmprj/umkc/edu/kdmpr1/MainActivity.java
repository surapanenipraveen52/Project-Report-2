package kdmprj.umkc.edu.kdmpr1;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kdmprj.umkc.edu.kdmpr1.diseases.GetDiseases;
import kdmprj.umkc.edu.kdmpr1.speechtotext.SpeechToText;
import kdmprj.umkc.edu.kdmpr1.speechtotext.VoicetoText;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener{

    private static final String TAG = "MAinActivity";
    private FeedReaderDbHelper mDbHelper;
    TextToSpeech tts;


    private ListView ls;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(getApplicationContext(),this);

        Spinner spinner= (Spinner)findViewById(R.id.spinner);
        ls= (ListView)findViewById(R.id.listView);
        Button add= (Button)findViewById(R.id.add);
        Button done= (Button)findViewById(R.id.done);
        Button delete= (Button)findViewById(R.id.delete);
        mDbHelper = new FeedReaderDbHelper(getApplicationContext());
        tts.speak("Welcome to MobileDoc",TextToSpeech.QUEUE_FLUSH,null);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Cursor cc = db.rawQuery("DELETE FROM " + FeedReaderContract.FeedEntry.TABLE_NAME,null );

            }
        });
      try {
//          Cursor cc = db.rawQuery("SELECT DISTINCT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
          customAdapter = new CustomAdapter(getApplication(), new GetDiseases(this).getDiseases());
          ls.setAdapter(customAdapter);
//          String db = DatabaseUtils.dumpCursorToString(cc);
//          Log.v(TAG, "" + db);
      }
      catch (Exception e)
      {
            Log.v(TAG,"exception");
      }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item= parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.USER_ID, "1");
                values.put(FeedReaderContract.FeedEntry.DISEASE_NAME, item);
                //				values.put(FeedEntry.EMAIL, email.getText().toString());

                // Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(
                        FeedReaderContract.FeedEntry.TABLE_NAME,
                        FeedReaderContract.FeedEntry.DISEASE_NAME,
                        values);
       try {
           Cursor cc = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
           customAdapter.changeCursor(cc);
           customAdapter.notifyDataSetChanged();
       }
       catch (Exception e){

       }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak("Welcome to MobileDoc",TextToSpeech.QUEUE_ADD,null);

                Intent i = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int status) {
       //tts.speak("Welcome to MobileDoc",TextToSpeech.QUEUE_ADD,null);
       // Intent mTutorial = new Intent(MainActivity.this, SpeechToText.class);
        //this.startActivity(mTutorial);
       // VoicetoText vt= new VoicetoText(MainActivity.this);
        //vt.voiceToText();
    }
    public void analyzeVoiceResults(List<String> voiceResults){
        Log.v(TAG,voiceResults.toString());
    }
}