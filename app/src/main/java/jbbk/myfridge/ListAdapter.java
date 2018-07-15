package jbbk.myfridge;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListAdapter extends ArrayAdapter<DatabaseHelper> {


    private Activity context;
    private View listView;
    private Date today = new Date();
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd.MM.yyyy");

/*
    public ListAdapter(Activity context, String name[], String countdown[], String stueckzahl[]) {
        super(context, R.layout.list_item, name);
        this.context = context;
        this.name = name;
        this.countdown = countdown;
        this.stueckzahl = stueckzahl;
    }*/

    public ListAdapter(Activity context, ArrayList<DatabaseHelper> obj) {
        super(context, R.layout.list_item, obj);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listView = inflater.inflate(R.layout.list_item, null, true);

        DatabaseHelper model = getItem(position);

        TextView nameView = listView.findViewById(R.id.list_item_name_id);
        TextView ablaufdatumView = listView.findViewById(R.id.list_item_ablaufdatum_id);
        TextView stueckzahlView = listView.findViewById(R.id.list_item_stueckzahl_id);
        TextView countdownView = listView.findViewById(R.id.list_item_countdown_id);
        ImageView iconView = listView.findViewById(R.id.list_item_icon_ID);

        nameView.setText(model.getName());
        ablaufdatumView.setText("Haltbarkeit");
        stueckzahlView.setText("Anzahl: " + model.getStueckzahl());


        long unterschied = 0;
        try {
            unterschied = dateFormat.parse(model.getAblaufdatum()).getTime() - today.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String countdown = String.valueOf(TimeUnit.DAYS.convert(unterschied, TimeUnit.MILLISECONDS));



        countdownView.setText(countdown + " Tage");

        String fileName = replaceUmlaute(model.getName().toLowerCase());

        if (listView.getResources().getIdentifier(fileName, "mipmap", this.getContext().getPackageName()) != 0) {
            iconView.setImageResource(listView.getResources().getIdentifier(fileName, "mipmap", this.getContext().getPackageName()));
        } else {
            iconView.setImageResource(listView.getResources().getIdentifier("unbekannt", "mipmap", this.getContext().getPackageName()));
        }
        return listView;
    }

    private static String[][] UMLAUT_REPLACEMENTS = {
            {new String("Ä"), "Ae"}, {new String("Ü"), "Ue"},
            {new String("Ö"), "Oe"}, {new String("ä"), "ae"},
            {new String("ü"), "ue"}, {new String("ö"), "oe"},
            {new String("ß"), "ss"}};

    public static String replaceUmlaute(String orig) {
        String result = orig;
        for (int i = 0; i < UMLAUT_REPLACEMENTS.length; i++) {
            result = result.replace(UMLAUT_REPLACEMENTS[i][0], UMLAUT_REPLACEMENTS[i][1]);
        }
        return result;
    }


}
