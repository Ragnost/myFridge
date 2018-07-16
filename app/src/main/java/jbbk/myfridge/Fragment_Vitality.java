package jbbk.myfridge;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment_Vitality extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseHandler dbHandler;
    private Context mContext;
    private TextView vitalyTextView;
    private View overview;


    public Fragment_Vitality() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment_Vitality newInstance(String param1, String param2) {
        Fragment_Vitality fragment = new Fragment_Vitality();
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
        mContext = getActivity().getApplicationContext();


        dbHandler = new DatabaseHandler(mContext);
        dbHandler.getFoodFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        overview = inflater.inflate(R.layout.fragment_vitality, container, false);
        dbHandler.getFoodFromDB();
        vitalyTextView = overview.findViewById(R.id.vitalyID);

        ImageView iconView = overview.findViewById(R.id.vitaltyImage);

        float calcVitaly = 0;

        int allCount = 0;
        int count = dbHandler.getProfilesCount();
        for (int i = 0; i < count; i++) {
            calcVitaly = calcVitaly + (dbHandler.getVitaly().get(i)*Integer.parseInt(dbHandler.getStueckzahl().get(i)));
            allCount = allCount + Integer.parseInt(dbHandler.getStueckzahl().get(i));
        }
        calcVitaly = calcVitaly / allCount;
        Integer imgGoodJob = overview.getResources().getIdentifier("good_job", "mipmap", this.getContext().getPackageName());
        Integer imgBadJob = overview.getResources().getIdentifier("bad_job", "mipmap", this.getContext().getPackageName());
        Integer imgNeutralJob = overview.getResources().getIdentifier("neutral_job", "mipmap", this.getContext().getPackageName());


        System.out.println("Das Essverhalten ist: " + calcVitaly);

        int value = (int) calcVitaly;

        if(value > 6){
            iconView.setImageResource(imgGoodJob);
        }
        if(value <= 6 && calcVitaly > 4){
            iconView.setImageResource(imgNeutralJob);
        }
        if(value <= 4){
            iconView.setImageResource(imgBadJob);
        }

        return overview;
    }


}
