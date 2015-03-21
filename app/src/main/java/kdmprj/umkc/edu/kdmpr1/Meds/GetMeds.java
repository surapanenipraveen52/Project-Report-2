package kdmprj.umkc.edu.kdmpr1.Meds;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by praveen on 3/17/2015.
 */
public class GetMeds {
    public  List<String> getMeds(String url) throws Exception {
        List<String> medsList= new ArrayList<String>();
        HttpURLConnection FW31 = (HttpURLConnection) new URL("http://www.drugs.com/"+url).openConnection();
        FW31.setRequestMethod("GET");
        FW31.connect();
        java.io.BufferedInputStream in = new java.io.BufferedInputStream(FW31.getInputStream());
        byte[] data = new byte[1024];
        int i=0;
        String output="";
        while((i=in.read(data,0,1024))>=0)
        {
            String oneByte=new String(data,0,i);
            if(oneByte.contains("conditionDrugLog")){
                output +=oneByte;
            }
        }
        StringTokenizer t= new StringTokenizer(output,"\n");
        while(t.hasMoreElements()){
            String oneToken=(String) t.nextElement();
            if(oneToken.contains("conditionDrugLog")){
                int startIndex=oneToken.indexOf("<b>");
                int endIndex=oneToken.indexOf("</b>");
                medsList.add(oneToken.substring(startIndex+3, endIndex));
            }
        }
    return medsList;
    }
}
