package jbbk.myfridge;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Fragment_Fridge ist das erste Element des Tab Layouts und
 * beinhaltet eine Liste mit allen Artikeln im Kühlschrank.
 * Zusätzlich noch weitere grafische Objekte, welche Methoden zum Hinzufügen,
 * Erweitern, Ändern der Liste hinterlegt haben.
 **/
public class Fragment_Fridge extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FloatingActionButton addButton;
    private View list_overview;
    private Integer icons[];
    private int vitalyValue = 0;
    private DatabaseHelper dbHelper = new DatabaseHelper();
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd.MM.yyyy");
    private ArrayList<DatabaseHelper> dbFood;
    private SeekBar vitalyBar;
    private Context mContext;
    private FloatingActionButton addLebensmittel;
    private RelativeLayout mRelativeLayout;
    private EditText nameInput, countInput;
    private DatePicker simpleDatePicker;
    private ListAdapter listAdapterClass;
    private String nameWatcher, ablaufDatumWatcherString, countWatcher;
    private ListView myListView;
    private DatabaseHandler dbHandler;

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
        dbHandler = new DatabaseHandler(mContext);
        dbHandler.getFoodFromDB();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list_overview = inflater.inflate(R.layout.list_overview, container, false);
        addButton = list_overview.findViewById(R.id.addButton);
        mRelativeLayout = list_overview.findViewById(R.id.test);

        dbFood = new ArrayList<>();
        dbFood.clear();
        for (int i = 0; i < dbHandler.getProfilesCount(); i++) {
            dbFood.add(new DatabaseHelper(dbHandler.getName().get(i), dbHandler.getAblaufdatum().get(i), dbHandler.getStueckzahl().get(i), dbHandler.getVitaly().get(i)));
        }


        listAdapterClass = new ListAdapter(this.getActivity(), dbFood);

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
                simpleDatePicker = dialogView.findViewById(R.id.simpleDatePicker);
                countInput = dialogView.findViewById(R.id.addCountLebensmittel);
                vitalyBar = dialogView.findViewById(R.id.seekBar1);

                vitalyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        vitalyValue = seekBar.getProgress();
                    }
                });
                System.out.println("bar: " + vitalyBar);

                nameInput.addTextChangedListener(watcher);
                countInput.addTextChangedListener(watcher);

                addLebensmittel.setEnabled(false);
                addLebensmittel.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                addLebensmittel.setAlpha(0.33f);

                addLebensmittel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Bewertung: " + vitalyValue);

                        countWatcher = String.valueOf(checkMe(countWatcher));
                        dbHandler.insertFood(nameWatcher, countWatcher, dateToString(simpleDatePicker), vitalyValue);
                        dbHandler.getFoodFromDB();
                        listAdapterClass.add(new DatabaseHelper(nameWatcher, dateToString(simpleDatePicker), countWatcher, vitalyValue));
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

        /**Lange auf ein Element klicken um es zu löschen**/
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(":::::" + dbFood.get(i).getName());
                dbHandler.deleteRow(dbFood.get(i).getName());
                dbFood.remove(i);
                dbHandler.getFoodFromDB();
                dbHandler.getShoppingList();

                listAdapterClass.notifyDataSetChanged();
                return false;
            }
        });


        /** POPUP menu für das Inkrementieren und Dekrementieren der Stückzahl eines Eintrags. **/
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("open dialog");
                final String nameOfClickedItem = dbFood.get(i).getName();
                final Integer vitalityofClickedItem = dbFood.get(i).getVitaly();
                final String ablaufdatumOfClickedItem = dbFood.get(i).getAblaufdatum();
                final String stueckzahlOfClickedItem = dbFood.get(i).getStueckzahl();
                final String count = dbFood.get(i).getStueckzahl();
                final int toDelteinList = i;

                final PopupMenu popupMenu = new PopupMenu(mContext, myListView, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_plusminus, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        String itemTitle = item.getTitle().toString();
                        switch (itemTitle) {
                            //increase
                            case "Hinzufügen":
                                int tmp = Integer.parseInt(stueckzahlOfClickedItem);
                                tmp++;
                                String countOfClickedItem = String.valueOf(tmp);

                                listAdapterClass.add(new DatabaseHelper(nameOfClickedItem, ablaufdatumOfClickedItem, countOfClickedItem, vitalityofClickedItem));
                                dbFood.remove(toDelteinList);
                                dbHandler.changeStueckzahl(nameOfClickedItem, count, 1);
                                dbHandler.getFoodFromDB();
                                dbHandler.close();
                                listAdapterClass.notifyDataSetChanged();
                                break;

                            //decrease
                            case "Rausnehmen":
                                int tmpor = Integer.parseInt(stueckzahlOfClickedItem);

                                if ((tmpor--) > 1) {

                                    String reducedcounter = String.valueOf(tmpor);

                                    listAdapterClass.add(new DatabaseHelper(nameOfClickedItem, ablaufdatumOfClickedItem, reducedcounter, vitalityofClickedItem));
                                    dbFood.remove(toDelteinList);
                                    dbHandler.changeStueckzahl(nameOfClickedItem, count, 0);
                                    dbHandler.getFoodFromDB();
                                    dbHandler.close();
                                } else {
                                    dbHandler.deleteRow(dbFood.get(toDelteinList).getName());
                                    dbFood.remove(toDelteinList);
                                    dbHandler.getFoodFromDB();
                                }
                                listAdapterClass.notifyDataSetChanged();
                                break;
                            default:
                                System.out.print("Popup schließt sich");
                                popupMenu.dismiss();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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

    /**
     * DateObjekt wird zu einem String gemacht.
     *
     * @return Ablaufdatum als String.
     **/
    public String dateToString(DatePicker _date) {
        int day = _date.getDayOfMonth();
        int month = _date.getMonth() + 1;
        int year = _date.getYear();
        String dayString = String.valueOf(day);
        String monthString = String.valueOf(month);
        String yearString = String.valueOf(year);
        return ablaufDatumWatcherString = dayString + "." + monthString + "." + yearString;
    }

    public static int checkMe(String s) {
        try {
            int tmp = Integer.parseInt(s);
            return tmp;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

}
