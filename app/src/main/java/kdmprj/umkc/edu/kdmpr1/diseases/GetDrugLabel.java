package kdmprj.umkc.edu.kdmpr1.diseases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDrugLabel {
	public String getDrugLabel(String set_id) throws IOException{
		String uri="https://api.fda.gov/drug/label.json?search=set_id:"+set_id;
		URL url = new URL(uri);
		 HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String output,response=" ";
		while ((output = br.readLine()) != null) {
			response=response+output;
		}
		return response;
	}

}
