package jbbk.myfridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
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
    private String countFromXML;
    private String nameFromXML;
    private String dateFromXML;
    private Context mContext;
    private Activity mActivity;
    private FloatingActionButton addLebensmittel;
    private RelativeLayout mRelativeLayout;
    private EditText nameInput, countInput;
    private DatePicker simpleDatePicker;
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
                final AlertDialog builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle).create();
                LayoutInflater inflaterR = getActivity().getLayoutInflater();
                final View dialogView = inflaterR.inflate(R.layout.fragment_add_item, null);
                builder.setView(dialogView);

                addLebensmittel = dialogView.findViewById(R.id.addLebensmittel);
                nameInput = dialogView.findViewById(R.id.addNameID);
                simpleDatePicker = dialogView.findViewById(R.id.simpleDatePicker); // initiate a date picker
                countInput = dialogView.findViewById(R.id.addCountLebensmittel);

                int day = simpleDatePicker.getDayOfMonth();
                int month = simpleDatePicker.getMonth() + 1;
                int year = simpleDatePicker.getYear();

                String dayString = String.valueOf(day);
                String monthString = String.valueOf(month);
                String yearString = String.valueOf(year);

                countFromXML = countInput.getText().toString();
                nameFromXML = String.valueOf(nameInput.getText());
                dateFromXML = dayString + "." + monthString + "." + yearString;

                nameInput.addTextChangedListener(watcher);
                countInput.addTextChangedListener(watcher);



                addLebensmittel.setEnabled(false);
                addLebensmittel.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                addLebensmittel.setAlpha(0.33f);
                System.out.println("Farbe: " + addLebensmittel.getBackground());
                addLebensmittel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
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




       /* for (int i = 0; i < name.length; i++) {
            if (name[i].equals("ü")) {
                name[i] = "ue";
            }
            icons[i] = this.getResources().getIdentifier(name[i].toLowerCase() + ".png", "drawable", this.getContext().getPackageName());
            //System.out.println("IConName: " + name[1]);
            //System.out.println("Icon: " + icons[i]);
        }*/


        ListAdapter listAdapterClass = new ListAdapter(this.getActivity(), name, stueckzahl, ablaufdatumString, icons, countdown);
        ListView myListView = list_overview.findViewById(R.id.listoverview_id);
        myListView.setAdapter(listAdapterClass);
        return list_overview;
    }


    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String n = nameInput.getText().toString();
            String c = countInput.getText().toString();
            addLebensmittel.setEnabled(n.length() > 0 && c.length() > 0);
            if (addLebensmittel.isEnabled()) {
                addLebensmittel.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(2, 212, 255)));
                addLebensmittel.setAlpha(1f);
            } else {
                addLebensmittel.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                addLebensmittel.setAlpha(0.33f);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
