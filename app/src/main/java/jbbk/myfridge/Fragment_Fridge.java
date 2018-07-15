package jbbk.myfridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Fragment_Fridge extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FloatingActionButton addButton;
    private View list_overview;
    private Integer icons[];
    private DatabaseHelper dbHelper = new DatabaseHelper();
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd.MM.yyyy");
    private ArrayList<DatabaseHelper> dbFood;


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

    DatabaseHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();

        dbHandler = new DatabaseHandler(mContext);
        //dbHandler.insertFood("Kartoffel", "1", "11.10.2018");
        //dbHandler.clearDatabase();
        dbHandler.getFoodFromDB();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /* ------------------*/
    private String countFromXML;
    private String nameFromXML;
    private String dateFromXML;
    private Context mContext;
    private Activity mActivity;
    private FloatingActionButton addLebensmittel;
    private RelativeLayout mRelativeLayout;
    private EditText nameInput, countInput;
    private DatePicker simpleDatePicker, ablaufDatumWatcher;
    private ListAdapter listAdapterClass;
    private PopupWindow mPopupWindow;
    private String nameWatcher, ablaufDatumWatcherString;
    private String countWatcher;
    private ListView myListView;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        System.out.println("onCreateView");
        list_overview = inflater.inflate(R.layout.list_overview, container, false);
        addButton = list_overview.findViewById(R.id.addButton);
        mRelativeLayout = list_overview.findViewById(R.id.test);

        String[] _name = new String[dbHandler.getName().size()];
        _name = dbHandler.getName().toArray(_name);
        String[] _stueckzahl = new String[dbHandler.getStueckzahl().size()];
        _stueckzahl = dbHandler.getStueckzahl().toArray(_stueckzahl);
        String[] _ablaufdatum = new String[dbHandler.getAblaufdatum().size()];
        _ablaufdatum = dbHandler.getAblaufdatum().toArray(_ablaufdatum);

        /**
         * Datum durch Input ist String
         * Datum vom System wird als Date-Objekt abgespeichert
         * dateFormat = new SimpleDateFormat("dd.MM.yyyy")
         */


        System.out.println("Eintraege; " + dbHandler.getProfilesCount());
        dbFood = new ArrayList<>();
        for (int i = 0; i < dbHandler.getProfilesCount(); i++) {
            dbFood.add(new DatabaseHelper(_name[i], _ablaufdatum[i], _stueckzahl[i]));
        }


        listAdapterClass = new ListAdapter(this.getActivity(), dbFood);


        //listAdapterClass = new ListAdapter(this.getActivity(), _name, _stueckzahl, countdown);
        // ListAdapter listAdapterClass = new ListAdapter(this.getActivity(), name, stueckzahl, ablaufdatumString, icons, countdown);
        myListView = list_overview.findViewById(R.id.listoverview_id);
        myListView.setAdapter(listAdapterClass);


        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

                nameInput.addTextChangedListener(watcher);
                countInput.addTextChangedListener(watcher);
                /*simpleDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        ablaufDatumWatcher = datePicker;
                    }
                });
*/
                addLebensmittel.setEnabled(false);
                addLebensmittel.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                addLebensmittel.setAlpha(0.33f);
                addLebensmittel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int day = simpleDatePicker.getDayOfMonth();
                        int month = simpleDatePicker.getMonth() + 1;
                        int year = simpleDatePicker.getYear();

                        String dayString = String.valueOf(day);
                        String monthString = String.valueOf(month);
                        String yearString = String.valueOf(year);

                        ablaufDatumWatcherString = dayString + "." + monthString + "." + yearString;


                        /* In die Datenbank einfuegen */
                        dbHandler.insertFood(nameWatcher, countWatcher, ablaufDatumWatcherString);
                        /* Aus der Datenbank lesen (Ausgabe Konsole)*/
                        dbHandler.getFoodFromDB();


                        listAdapterClass.add(new DatabaseHelper(nameWatcher, ablaufDatumWatcherString, countWatcher));
                        listAdapterClass.notifyDataSetChanged();
                        dbHandler.close();
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


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(":::::" + dbFood.get(i).getName());
                // Geht noch nicht
                //dbHandler.deleteItem(dbFood.get(i).getName());
                dbFood.remove(i);
                listAdapterClass.notifyDataSetChanged();
                return false;
            }
        });


        /** POPUP FUER INKREMENTIEREN HIER REIN **/
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("open dialog");
                String nameOfClickedItem = dbFood.get(i).getName();
                /*
                * Fuers inkrementieren ne Methode in DatabaseHandler schreiben
                * Liste aktullisieren nicht vergessen
                * PopUp halbwegs schoen designen
                */
            }
        });

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
                nameWatcher = nameInput.getText().toString();
                countWatcher = countInput.getText().toString();

            }
        };


    }
