package kdmprj.umkc.edu.kdmpr1.diseases;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AnalyzeDrugLabel {
	public String  getDrugInfo(JSONObject drug, String key){
		String response="";
		System.out.println(drug);
		if(!drug.containsKey(key) || null==drug.get(key)){
			response="no such data";
		}else{
			JSONArray respArray=(JSONArray) drug.get(key);
			response=respArray.toJSONString();
		}
		return response;
	}
}
