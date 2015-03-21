package kdmprj.umkc.edu.kdmpr1;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by venkatareddy on 2/27/2015.
 */
public class CustomAdapter extends CursorAdapter{
    private static final String TAG ="CustomAdapter" ;
    Context mContext;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, Cursor cc) {
        super(context,cc);
        mContext= context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v=mInflater.inflate(R.layout.row, null);
        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor cursor) {
        TextView row1 = (TextView)  v.findViewById(R.id.row1);
        TextView row2 = (TextView)  v.findViewById(R.id.row2);
        row1.setText(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.DISEASE_NAME)));
    }



    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
