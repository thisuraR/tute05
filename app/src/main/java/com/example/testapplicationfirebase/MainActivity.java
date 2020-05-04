package com.example.testapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    EditText txtID, txtName, txtAdd, txtConNo;
    Button btnSave, btnShow, btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Student std;
    long maxid = 0;

    private void clearControls() {

        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.idno);
        txtName = findViewById(R.id.name);
        txtAdd = findViewById(R.id.add);
        txtConNo = findViewById(R.id.conNo);

        btnSave = findViewById(R.id.save);
        btnShow = findViewById(R.id.show);
        btnDelete = findViewById(R.id.delete);
        btnUpdate = findViewById(R.id.update);
        //    std = new Student();


    }
    //save
    public void Save( View view){

        std = new Student();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Student");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //     DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Student");

        try {
            if (TextUtils.isEmpty(txtID.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter an id", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtName.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtAdd.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_SHORT).show();
            else {

                std.setID(txtID.getText().toString().trim());
                std.setName(txtName.getText().toString().trim());
                std.setAddress(txtAdd.getText().toString().trim());
                std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                //dbRef.push().setValue(std);
                //  dbRef.child("Std1").setValue(std);
                dbRef.child(String.valueOf(maxid+1)).setValue(std);

                Toast.makeText(getApplicationContext(), "data saved successfully", Toast.LENGTH_SHORT).show();
                clearControls();
            }
        } catch (
                NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "invalid contact no", Toast.LENGTH_SHORT).show();
        }
    }

    //show

    public void Show(View view){

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("5");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                    txtID.setText(dataSnapshot.child("id").getValue().toString());
                    txtName.setText(dataSnapshot.child("name").getValue().toString());
                    txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                    txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(),"No source to display", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    //update
    public void Update( View view){


        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Std1")) {

                    try {

                        std.setID(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setAddress(txtAdd.getText().toString().trim());
                        std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                        dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                        dbRef.setValue(std);
                        clearControls();

                        Toast.makeText(getApplicationContext(), "data updated successfully", Toast.LENGTH_SHORT).show();

                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "invalid contact no", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "no source to update", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //delete button

    public void Delete( View view){

        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("student");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Std1")){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("student").child("Std1");
                    dbRef.removeValue();
                    clearControls();
                    Toast.makeText(getApplicationContext(), "data deleted successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "no source to delete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
