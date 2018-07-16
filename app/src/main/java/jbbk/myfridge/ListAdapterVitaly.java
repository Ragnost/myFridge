package jbbk.myfridge;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ListAdapterVitaly extends ArrayAdapter<DatabaseHelper> {


    private Activity context;
    private View listView;


    public ListAdapterVitaly(Activity context, ArrayList<DatabaseHelper> obj) {
        super(context, R.layout.list_item, obj);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listView = inflater.inflate(R.layout.list_item, null, true);



        return listView;
    }


}
