/*
 * Copyright 2017 Miguel Zavala,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: This shows a list of items and calculates great circle and bearing
 *
 * Ser423 Mobile Applications
 * see http://pooh.poly.asu.edu/Mobile
 * @author Miguel Zavala miguel.zavala@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version February 2017
 */
package assignment3.mazaval4.bsse.asu.edu.assignment3;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class DisplayInfo extends AppCompatActivity {

    private String url = "http://10.0.2.2:9090";
    final Context context = this;
    PlaceLibrary lib = PlaceLibrary.getInstance();
    public EditText editText1 = (EditText)findViewById(R.id.editText1);
//    EditText editText2 = (EditText)findViewById(R.id.editText2);
//    EditText editText3 = (EditText)findViewById(R.id.editText3);
//    EditText editText4 = (EditText)findViewById(R.id.editText4);
//    EditText editText5 = (EditText)findViewById(R.id.editText5);
//    TextView editText6 = (TextView) findViewById(R.id.editText6);
//    EditText editText7 = (EditText)findViewById(R.id.editText7);
//    EditText editText8 = (EditText)findViewById(R.id.editText8);
//    EditText editText9 = (EditText)findViewById(R.id.editText9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String value = null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("placename");
        }
        getSupportActionBar().setTitle(value);


        fillStuff(value);

        Button saveButton= (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Context context = getApplicationContext();
                String text = "Item Saved";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                saveStuff();
            }
        });

        Button deleteButton= (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView name = (TextView) findViewById(R.id.editText6);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Delete");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                lib.removeObject(name.getText().toString());
                                String text = "Item deleted";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                lib.removeObject(name.getText().toString());
                                JSONArray jsArray = new JSONArray(lib.getLibrary());
                                android.util.Log.d("Json",jsArray.toString());

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


    }


    public void fillStuff(String value){

        try{
            android.util.Log.w(this.getClass().getSimpleName(),"In fill stuff ");
            MethodInformation mi = new MethodInformation(this, url,"get",
                    new String[]{value});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }




    }

    public void saveStuff(){
        PlaceDescription pd;
        String addressTitle;
        String addressStreet;
        String name;
        String image;
        String description;
        String category;
        double elevation,latitude,longitude;
        PlaceLibrary lib = PlaceLibrary.getInstance();

        EditText editText1 = (EditText)findViewById(R.id.editText1);
        addressTitle = editText1.getText().toString();
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        addressStreet = editText2.getText().toString();
        EditText editText3 = (EditText)findViewById(R.id.editText3);
        elevation = Double.valueOf(editText3.getText().toString());
        EditText editText4 = (EditText)findViewById(R.id.editText4);
        latitude = Double.valueOf(editText4.getText().toString());
        EditText editText5 = (EditText)findViewById(R.id.editText5);
        longitude = Double.valueOf(editText5.getText().toString());
        TextView editText6 = (TextView) findViewById(R.id.editText6);
        name = editText6.getText().toString();
        EditText editText7 = (EditText)findViewById(R.id.editText7);
        image = editText7.getText().toString();
        EditText editText8 = (EditText)findViewById(R.id.editText8);
        description = editText8.getText().toString();
        EditText editText9 = (EditText)findViewById(R.id.editText9);
        category = editText9.getText().toString();

        pd = new PlaceDescription(addressTitle,addressStreet,elevation,latitude,longitude,name,image,description,category);
        lib.addObject(pd);

    }



}
