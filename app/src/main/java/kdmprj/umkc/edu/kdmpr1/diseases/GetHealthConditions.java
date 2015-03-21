package kdmprj.umkc.edu.kdmpr1.diseases;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHealthConditions {
    private static final String TAG = "GetHealthConditions";

    public String getHealthConditions(String drugName) {
        if(null==drugName || drugName.length()==0){
            return "no data";
        }try {
            String uri = "https://api.fda.gov/drug/event.json?search=patient.drug.medicinalproduct:%22" + drugName + "%22&count=patient.reaction.reactionmeddrapt.exact";
            Log.v(TAG,uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output, healthConditions = " ";
            while ((output = br.readLine()) != null) {
                healthConditions = healthConditions + output;
            }
            return healthConditions;
        }catch (FileNotFoundException e){
            return "error";
        }catch (IOException io){
            return "error";
        }
    }

}
