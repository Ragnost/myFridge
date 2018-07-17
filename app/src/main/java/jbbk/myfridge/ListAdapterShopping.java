package jbbk.myfridge;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListAdapterShopping extends ArrayAdapter<DatabaseHelper> {

    private Activity context;
    private View listView;

    public ListAdapterShopping(Activity context, ArrayList<DatabaseHelper> obj) {
        super(context, R.layout.einkaufslist_item, obj);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listView = inflater.inflate(R.layout.list_item, null, true);
        DatabaseHelper model = getItem(position);

        TextView name = listView.findViewById(R.id.einkaufswagen_text);
        ImageView iconView = listView.findViewById(R.id.einkaufswagen_image);


        name.setText(model.getName());

        String fileName = replaceUmlaute(model.getName().toLowerCase());
        try {
            if (listView.getResources().getIdentifier(fileName, "mipmap", this.getContext().getPackageName()) != 0) {
                iconView.setImageResource(listView.getResources().getIdentifier(fileName, "mipmap", this.getContext().getPackageName()));
            } else {
                iconView.setImageResource(listView.getResources().getIdentifier("unbekannt", "mipmap", this.getContext().getPackageName()));
            }
        } catch (Exception e) {
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
