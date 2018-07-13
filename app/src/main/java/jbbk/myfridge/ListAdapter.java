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

import java.util.Date;

public class ListAdapter extends ArrayAdapter<String> {

    private String name[];
    private String stueckzahl[];
    private String ablaufdatum[];
    private Activity context;
    private Integer icon[];
    private String countdown[];

    public ListAdapter(Activity context, String name[], String stueckzahl[], String ablaufdatum[], Integer icon[], String countdown[]) {
        super(context, R.layout.list_item, name);
        this.context = context;
        this.name = name;
        this.stueckzahl = stueckzahl;
        this.ablaufdatum = ablaufdatum;
        this.icon = icon;
        this.countdown = countdown;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_item, null, true);


        //System.out.println("Countdown: " + countdown[position]);


        TextView nameView = listView.findViewById(R.id.list_item_name_id);
        TextView ablaufdatumView = listView.findViewById(R.id.list_item_ablaufdatum_id);
        TextView stueckzahlView = listView.findViewById(R.id.list_item_stueckzahl_id);
        TextView countdownView = listView.findViewById(R.id.list_item_countdown_id);
        //ImageView iconView = listView.findViewById(R.id.list_item_icon_ID);


        nameView.setText(name[position]);
        ablaufdatumView.setText("Haltbarkeit");
        stueckzahlView.setText("Anzahl: " + stueckzahl[position]);
        countdownView.setText(countdown[position] + " Tage");
        //iconView.setImageResource(icon[position]);


        return listView;
    }

}
