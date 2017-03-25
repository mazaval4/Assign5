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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    public ArrayAdapter<String> arrayAdapter;
    private String url = "http://10.0.2.2:9090";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayList<String> names = new ArrayList<String>();
        names.add("unknown");

        lv = (ListView) findViewById(R.id.mobile_list);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                names);
        lv.setAdapter(arrayAdapter);

        try{
            MethodInformation mi = new MethodInformation(this, url,"getNames",
                    new Object[]{});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String)lv.getItemAtPosition(i);
                Intent myIntent = new Intent(MainActivity.this, DisplayInfo.class);
                myIntent.putExtra("placename", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.print("in on resume");

        ArrayList<String> names = new ArrayList<String>();
        names.add("unknown");

        lv = (ListView) findViewById(R.id.mobile_list);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                names);
        lv.setAdapter(arrayAdapter);

        try{
            MethodInformation mi = new MethodInformation(this, url,"getNames",
                    new Object[]{});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {

            case R.id.action_settings:
                myIntent = new Intent(MainActivity.this, AddPlace.class);
                MainActivity.this.startActivity(myIntent);
                break;
            case R.id.bearing:
                myIntent = new Intent(MainActivity.this, Calculations.class);
                MainActivity.this.startActivity(myIntent);
                break;
            default:
                break;
        }

        return true;
    }

}