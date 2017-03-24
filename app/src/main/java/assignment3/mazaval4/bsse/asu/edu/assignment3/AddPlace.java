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
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

public class AddPlace extends AppCompatActivity {
    final Context context = this;
    String [] options = { "School", "Travel", "Hike" };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        NumberPicker picker = (NumberPicker) findViewById(R.id.numberPicker);
        picker.setMinValue(0);
        picker.setMaxValue(2);
        picker.setDisplayedValues( options );
        Button add = (Button) findViewById(R.id.addNewButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStuff();
                String text = "Item Added";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        });

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
        int index;
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
        NumberPicker picker = (NumberPicker)findViewById(R.id.numberPicker);
        index = picker.getValue();
        category = options[index];
        pd = new PlaceDescription(addressTitle,addressStreet,elevation,latitude,longitude,name,image,description,category);
        lib.addObject(pd);


    }


}
