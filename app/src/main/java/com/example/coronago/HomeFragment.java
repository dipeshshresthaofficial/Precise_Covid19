package com.example.coronago;

import android.content.Intent;
import android.os.Bundle;
//import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
import java.net.URISyntaxException;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    Button covidTest;
    Button updateStock;
    Button covidInfo;
    Button checkForAvailability;
    Button entertainment;
    Button games;
    public static String user_type;

    String user_mobile;


//    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_home,container,false);


        //accessing passed string from login activity

//        Intent intent = getActivity().getIntent(); //In fragment we have to use ".getActivity()" before using ".getIntent()"
//        user_type =  intent.getStringExtra("user_type");
//
////      had to add user_type!=null because of error check this: https://stackoverflow.com/questions/27895108/nullpointerexception-attempt-to-invoke-virtual-method-boolean-java-lang-string
//        if(user_type!=null && user_type.equals("normal")){
//
//            Button b1 = (Button) view.findViewById(R.id.updateStockBtn);
//            b1.setVisibility(View.GONE);
//        }


        //Checking the session of the user
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        user_mobile = sessionManagement.getSession();

        System.out.println(")_0))))))))))))))))))) "+user_mobile);

        getData();



//        Setting event listener for BTN in fragment
        covidTest = (Button) view.findViewById(R.id.covidTestBtn);
        covidTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("*********************"+user_mobile);
                if(user_mobile.equals("none")){
                    //if nobody is logged in then go to Login Activity
                    Intent myIntent = new Intent((MainActivity)getActivity(), LoginActivity.class);
                    startActivity(myIntent);

                }
                else{
                    Intent covidTestIntent = new Intent((MainActivity)getActivity(), CovidTestActivity.class);
                    startActivity(covidTestIntent);
                }

            }
        });

        updateStock = (Button) view.findViewById(R.id.updateStockBtn);
        //Retriving user type from session
//        user_type = sessionManagement.getUserType();

//        System.out.println("####### "+user_type);

        //showing update stock btn to only those customers who are health workers
//        if(user_type!=null && user_type.equals("normal")){
//
//            updateStock.setVisibility(View.GONE);
//        }
        updateStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("*********************"+user_mobile);
                if(user_mobile.equals("none")){
                    //if nobody is logged in then go to Login Activity
                    Intent myIntent = new Intent((MainActivity)getActivity(), LoginActivity.class);
                    startActivity(myIntent);

                }
                else{
                    //goto Update Stock Activity

                    Intent updateIntent = new Intent((MainActivity)getActivity(), activity_stock2.class);
                    startActivity(updateIntent);
                }
            }
        });

        covidInfo = (Button) view.findViewById(R.id.covidInfoBtn);
        covidInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent((MainActivity)getActivity(), CovidInfo.class);
                startActivity(infoIntent);
            }
        });


        checkForAvailability = (Button) view.findViewById(R.id.checkBtn);
        checkForAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent((MainActivity)getActivity(), AvailabilityActivity.class);
                startActivity(myIntent);

            }
        });
        entertainment = (Button) view.findViewById(R.id.entertainmentBtn);
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entertainmentIntent = new Intent((MainActivity)getActivity(), EntertainmentActivity.class);
                startActivity(entertainmentIntent);
            }
        });

        games =(Button)view.findViewById(R.id.games);
        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startgame = new Intent((MainActivity)getActivity(), games.class);
                startActivity(startgame);
            }
        });

        return view;
    }

    //function to check user is hospital or normal
    public void getData(){

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Customers");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    //data.getKey();
                    if(data.getKey().equals(user_mobile)){
                        //setting the name and dateof birth
                        System.out.println("children " + data.getKey());
                        Object l = dataSnapshot.child(data.getKey()).child("type").getValue();
                        if(l.toString().equals("health")){
                            //updateStock.setVisibility(View.GONE);
                        }
                        else{
                            updateStock.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        };
        myRef.addValueEventListener(postListener);

    }

}
