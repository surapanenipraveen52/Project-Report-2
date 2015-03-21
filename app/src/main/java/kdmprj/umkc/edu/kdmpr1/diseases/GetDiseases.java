package kdmprj.umkc.edu.kdmpr1.diseases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kdmprj.umkc.edu.kdmpr1.FeedReaderContract;
import kdmprj.umkc.edu.kdmpr1.FeedReaderDbHelper;

public class GetDiseases {
    private SQLiteDatabase db;
    public GetDiseases(Context context){
        db= context.openOrCreateDatabase(FeedReaderDbHelper.DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);


    }
    public Cursor getDiseases(){
        Cursor cc = db.rawQuery("SELECT DISTINCT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);

        return cc;
    }
    public List<String> getListDiseases()
    {
        ArrayList<String> mArrayList = new ArrayList<String>();
        Cursor cc=getDiseases();
        cc.moveToFirst();
        while(!cc.isAfterLast()) {
            mArrayList.add(cc.getString(cc.getColumnIndex(FeedReaderContract.FeedEntry.DISEASE_NAME))); //add the item
            cc.moveToNext();
        }
        return mArrayList;
    }

    public int getEffectivePercent(List<String> drugHealthConditions){
        int count=0;
		for(String oneCondition : drugHealthConditions){
			for(String oneDisease : getListDiseases()){
				if(oneDisease.toLowerCase().contains(oneCondition.toLowerCase())){
					count=count+5;
					System.out.println("THE DISEASE IS "+oneDisease+ "and condition is "+oneCondition);
					break;
				}
			}
		}
        return 100-count;

    }

}
