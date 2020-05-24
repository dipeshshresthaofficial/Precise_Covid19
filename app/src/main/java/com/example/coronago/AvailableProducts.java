package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableProducts extends AppCompatActivity {

    public static String number;

    TextView text_to_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_products);

        number = session();
        Toast.makeText(AvailableProducts.this,"number is="+number,Toast.LENGTH_SHORT).show();

        text_to_show = (TextView)findViewById(R.id.multitext1);
        getData();


    }

    //this function will help to get the multiple data from the firebase database
    String s1="";
    public void getData(){

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Tools");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    //data.getKey();
                    System.out.println("children " + data.getKey());
                    Object l = dataSnapshot.child(data.getKey()).child(AvailabilityActivity.temp).getValue();
                    Object l1 = dataSnapshot.child(data.getKey()).child("hospital_name").getValue();
                    s1  = s1+"\nHospital name = "+l1.toString()+"\nphone number is ="+data.getKey()+"\n"+"The available "+AvailabilityActivity.temp+" is ="+l.toString()+"\n";
                    text_to_show.setText(s1);
                    Toast.makeText(AvailableProducts.this,"the value available is ="+dataSnapshot.child(data.getKey()).child(AvailabilityActivity.temp).getValue(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        };
        myRef.addValueEventListener(postListener);

    }


    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement(AvailableProducts.this);
        return sessionManagement.getSession();
    }
}
