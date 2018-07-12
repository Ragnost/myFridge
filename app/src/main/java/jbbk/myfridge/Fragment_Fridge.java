package jbbk.myfridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Fragment_Fridge extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FloatingActionButton addButton;
    private Date today = new Date();
    private View list_overview;
    private Integer icons[];
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd.MM.yyyy");


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment_Fridge() {
        // Required empty public constructor
    }

    public static Fragment_Fridge newInstance(String param1, String param2) {
        Fragment_Fridge fragment = new Fragment_Fridge();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*Beispiel-Datensatze*/
    String[] name = {
            "Kürbis",
            "Tomate",
            "Kartoffel"
    };
    String[] ablaufdatumString = {
            "01.09.2018",
            "16.07.2018",
            "16.07.2019"};
    String[] stueckzahl = {"2", "3", "500"};
    String[] countdown = new String[stueckzahl.length];
    /* ------------------*/

    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private EditText mEdit;

    private PopupWindow mPopupWindow;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView");
        list_overview = inflater.inflate(R.layout.list_overview, container, false);
        addButton = list_overview.findViewById(R.id.addButton);
        mRelativeLayout = list_overview.findViewById(R.id.test);





        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle).create();
                LayoutInflater inflaterR = getActivity().getLayoutInflater();
                final View dialogView = inflaterR.inflate(R.layout.fragment_add_item, null);
                builder.setView(dialogView);

                FloatingActionButton addLebensmittel = dialogView.findViewById(R.id.addLebensmittel);

                addLebensmittel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("button wurde gedrückt");
                        mEdit   = dialogView.findViewById(R.id.addNameID);
                        System.out.println("Ausgabe: " + mEdit.getText());
                    }
                });

                builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();


            }
        });


        try

        {
            System.out.println("Datum2: " + dateFormat.format(dateFormat.parse(ablaufdatumString[0])));
            for (int i = 0; i < ablaufdatumString.length; i++) {
                long unterschied = dateFormat.parse(ablaufdatumString[i]).getTime() - today.getTime();
                countdown[i] = String.valueOf(TimeUnit.DAYS.convert(unterschied, TimeUnit.MILLISECONDS));
            }

        } catch (
                java.text.ParseException e)

        {
            e.printStackTrace();
        }

        /*
        for (int i = 0; i < icons.length; i++) {
            if (name[i].equals("ü")) {
                name[i] = "ue";
            }
            icons[i] = this.getResources().getIdentifier(name[i].toLowerCase() + ".jpg", "drawable", this.getContext().getPackageName());
        }
        */

        ListAdapter listAdapterClass = new ListAdapter(this.getActivity(), name, stueckzahl, ablaufdatumString, icons, countdown);
        ListView myListView = list_overview.findViewById(R.id.listoverview_id);
        myListView.setAdapter(listAdapterClass);
        return list_overview;
    }


}
