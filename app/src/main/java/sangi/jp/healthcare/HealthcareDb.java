package sangi.jp.healthcare;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;

/**
 * Created by J13012 on 2015/01/27.
 */
public class HealthcareDb extends Activity {
    static SQLiteDatabase healthcare_db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthcaredb);
        view();
    }

    private void view() {
        DatabaseHelper hlp = new DatabaseHelper(getApplicationContext());
        healthcare_db = hlp.getWritableDatabase();

        ListView listView = (ListView) findViewById(R.id.Listview1);

        try {
            Cursor cr = healthcare_db.rawQuery("Select * From healthcare_db Order By id desc", null);
            cr.moveToFirst();

            if (cr.getCount() > 0) {
                Integer[] data = new Integer[cr.getCount()];
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

                for (int cnt = 0; cnt < cr.getCount(); cnt++) {
                    data[cnt] = cr.getInt(0);
                    adapter.add("ID：" + cr.getString(0) + "日付：" + cr.getString(1) + "\n緯度：" + cr.getString(2) + "\n経度：" + cr.getString(3));
                    cr.moveToNext();
                    listView.setAdapter(adapter);
                }
            } else listView.setAdapter(null);
        } finally {
            healthcare_db.close();
        }
    }
}
