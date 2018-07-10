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

public class ListAdapter extends ArrayAdapter<String> {

    private String name[];
    private String stueckzahl[];
    private String ablaufdatum[];
    private Activity context;

    public ListAdapter(Activity context, String name[], String stueckzahl[], String ablaufdatum[]) {
        super(context, R.layout.list_item, name);
        this.context = context;
        this.name = name;
        this.stueckzahl = stueckzahl;
        this.ablaufdatum = ablaufdatum;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_item, null, true);

        TextView nameView = listView.findViewById(R.id.list_item_name_id);
        TextView ablaufdatumView = listView.findViewById(R.id.list_item_ablaufdatum_id);
        TextView stueckzahlView = listView.findViewById(R.id.list_item_stueckzahl_id);
        ImageView iconView = listView.findViewById(R.id.list_item_icon_ID);

        System.out.println("Position: " + position);
        System.out.println("Name: " + name[position]);

        nameView.setText(name[position]);
        ablaufdatumView.setText(ablaufdatum[position]);
        stueckzahlView.setText(stueckzahl[position]);



        return listView;
    }

}
