package jbbk.myfridge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class Fragment_ShoppingList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View overview;
    private ListAdapterShopping listAdapterShopping;
    private Context mContext;
    private DatabaseHandler dbHandler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView myListView;
    private RelativeLayout mRelativeLayout;
    private ArrayList<String> dbDeletedFood;

    public Fragment_ShoppingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ShoppingList.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ShoppingList newInstance(String param1, String param2) {
        Fragment_ShoppingList fragment = new Fragment_ShoppingList();
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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        overview = inflater.inflate(R.layout.einkaufslist_overview, container, false);
        mRelativeLayout = overview.findViewById(R.id.Fragment_ShoppingListID);


        dbDeletedFood = new ArrayList<>();
        dbDeletedFood.clear();


        dbHandler.getShoppingList();
        //System.out.println("Size: " + dbHandler.getNameShoppinglistElements().size());

        for (int i = 0; i < dbHandler.getNumberofShoppingElements(); i++) {
            System.out.println("[" + i + "] " + dbHandler.getNameShoppinglistElements().get(i));
            dbDeletedFood.add(dbHandler.getNameShoppinglistElements().get(i));

        }

        //dbDeletedFood.add("Tomate");
        //dbDeletedFood.add("Kürbis");


        listAdapterShopping = new ListAdapterShopping(this.getActivity(), dbDeletedFood);
        myListView = overview.findViewById(R.id.einkaufswagen_overview);
        myListView.setAdapter(listAdapterShopping);

        listAdapterShopping.notifyDataSetChanged();
        dbHandler.getShoppingList();


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(":::::" + dbDeletedFood.get(i));
                dbHandler.deleteRow(dbDeletedFood.get(i));
                dbHandler.deleteShoppingName(dbDeletedFood.get(i));
                dbDeletedFood.remove(i);
                dbHandler.getFoodFromDB();
                listAdapterShopping.notifyDataSetChanged();
                dbHandler.close();
                return false;
            }
        });


        return overview;
    }

}