package jbbk.myfridge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class Fragment_Fridge extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View list_overview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment_Fridge() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Fridge.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        list_overview = inflater.inflate(R.layout.list_overview, container, false);
        String[] name = {
                "Kürbis",
                "Tomate"
        };

        String[] ablaufdatum = {"11.05.1996", "31.12.2099"};

        String[] stueckzahl = {"2", "3"};

        ListAdapter listAdapterClass = new ListAdapter(this.getActivity(), name, stueckzahl, ablaufdatum);

        ListView myListView = list_overview.findViewById(R.id.listoverview_id);

        myListView.setAdapter(listAdapterClass);

        System.out.println("\n\n Debug:" + listAdapterClass + "\n\n");

        return list_overview;
    }


}
