package kdmprj.umkc.edu.kdmpr1;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kdmprj.umkc.edu.kdmpr1.Meds.GetMeds;
import kdmprj.umkc.edu.kdmpr1.diseases.GetDiseases;
import kdmprj.umkc.edu.kdmpr1.diseases.GetHealthConditions;

/**
 * Created by praveen on 3/12/2015.
 */
public class AnalyzeUserInputs extends AsyncTask{
    private static final String TAG = "AnalyzeUserInputs";
    Context mContext;
    String drugname;
    TextView percentTextView;
    int perce;
    Map<String,Integer> medsMap= new HashMap<String, Integer>();
    public AnalyzeUserInputs(String s, String drugName, Context context, TextView percent) {
        Log.v(TAG,"AnalyzeUserInputs Constructor");
        drugname=drugName;
        mContext=context;
        percentTextView=percent;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            List<String> medsList= new GetMeds().getMeds("condition/bronchitis.html");
            if(!(null !=drugname || drugname.length()==0))
            {
                if (!medsList.contains(drugname))
                medsList.add(drugname);
            }
            for (String oneMed : medsList) {
                String json_hc = new GetHealthConditions().getHealthConditions(oneMed);
                if(json_hc.contains("error") ){
                    if( json_hc.contains("no data")){
                        break;
                    }
                    medsMap.put(oneMed,0);
                    break;
                }
                Log.v(TAG,json_hc);
                 JSONObject responeJson = (JSONObject) new JSONParser().parse(json_hc);
                JSONArray healthConditionsJson = (JSONArray) responeJson.get("results");
                List<String> healthConditionsArray = new ArrayList<String>();
                for (int i = 0; i < healthConditionsJson.size(); i++) {
                    Object o = healthConditionsJson.get(i);
                    JSONObject oneCondition = (JSONObject) o;
                    healthConditionsArray.add((String) oneCondition.get("term"));
                }
                int effectiveness = new GetDiseases(mContext).getEffectivePercent(healthConditionsArray);
                medsMap.put(oneMed,effectiveness);

            }
            Log.v(TAG," meds map"+medsMap.toString());
            System.out.println(" "+medsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.v(TAG,"perc = "+perce);
        percentTextView.setText(""+perce);

    }
}