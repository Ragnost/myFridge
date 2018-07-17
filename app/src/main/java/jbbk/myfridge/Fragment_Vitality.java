package jbbk.myfridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Letztes Fragment Element des Tab Layouts zeigt einem wie gesund man sich ernährt
 **/
public class Fragment_Vitality extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String randomSprueche[] = {
            "Morgens das Frühstück nicht vergessen",
            "3 Bier sind auch eine Mahlzeit",
            "Wann hast du zuletzt den Kühlschrank geputzt?",
            "Von Salat schrumpft der Bizeps"};
    private Random rnd;
    private String mParam1;
    private String mParam2;
    private DatabaseHandler dbHandler;
    private Context mContext;
    private TextView vitalyTextView;
    private View overview;
    private TextView randomSpruch;


    public Fragment_Vitality() {
        // Required empty public constructor
    }


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
        rnd = new Random();

        dbHandler = new DatabaseHandler(mContext);
        dbHandler.getFoodFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        overview = inflater.inflate(R.layout.fragment_vitality, container, false);
        dbHandler.getFoodFromDB();
        vitalyTextView = overview.findViewById(R.id.vitalyID);
        randomSpruch = overview.findViewById(R.id.randomText);
        ImageView iconView = overview.findViewById(R.id.vitaltyImage);

        float calcVitaly = 0;

        int allCount = 0;
        int count = dbHandler.getProfilesCount();
        for (int i = 0; i < count; i++) {
            calcVitaly = calcVitaly + (dbHandler.getVitaly().get(i) * Integer.parseInt(dbHandler.getStueckzahl().get(i)));
            allCount = allCount + Integer.parseInt(dbHandler.getStueckzahl().get(i));
        }
        calcVitaly = calcVitaly / allCount;
        Integer imgGoodJob = overview.getResources().getIdentifier("good_job", "mipmap", this.getContext().getPackageName());
        Integer imgBadJob = overview.getResources().getIdentifier("bad_job", "mipmap", this.getContext().getPackageName());
        Integer imgNeutralJob = overview.getResources().getIdentifier("neutral_job", "mipmap", this.getContext().getPackageName());


        System.out.println("Das Essverhalten ist: " + calcVitaly);

        int value = (int) calcVitaly;

        if (value > 6) {
            iconView.setImageResource(imgGoodJob);
        }
        if (value <= 6 && calcVitaly > 4) {
            iconView.setImageResource(imgNeutralJob);
        }
        if (value <= 4) {
            iconView.setImageResource(imgBadJob);}

        iconView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    int randomValue = getRandomNumberInRange(0, randomSprueche.length - 1);
                    randomSpruch.setText(randomSprueche[randomValue]);
                    randomSpruch.setBackgroundColor(Color.rgb(97, 131, 196));
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    randomSpruch.setText("");
                    randomSpruch.setBackgroundColor(Color.WHITE);
                }

                return true;
            }
        });

        return overview;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max muss groesser als min sein");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}
