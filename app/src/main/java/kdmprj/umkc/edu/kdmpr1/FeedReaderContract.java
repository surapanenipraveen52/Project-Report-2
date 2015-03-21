package kdmprj.umkc.edu.kdmpr1;

import android.provider.BaseColumns;

public class FeedReaderContract {
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "DISEASES";
        public static final String USER_ID = "entryid";
//        public static final String FNAME = "title";
        public static final String DISEASE_NAME = "DISEASE";
//		public static final String EMAIL = "";
//		public static final String PASSWORD = "";
//		public static final String SUBSCRIBE = "";
//		public static final String ROLE = "";

    }
}
