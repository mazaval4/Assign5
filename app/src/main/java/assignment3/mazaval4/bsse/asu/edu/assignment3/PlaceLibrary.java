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

import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by mazaval4 on 2/8/2017.
 */

public class PlaceLibrary {

    private final static PlaceLibrary instance = new PlaceLibrary();
    ArrayList<PlaceDescription> lib = new ArrayList<PlaceDescription>();

    private PlaceLibrary() {}

    public static PlaceLibrary getInstance() {
        return instance;
    }


    protected void fromJson(AssetManager assets) {
        String json = null;
        String addressTitle;
        String addressStreet;
        String name;
        String image;
        String description;
        String category;
        double elevation,latitude,longitude;
        PlaceDescription placeDescription = null;


        try {

            InputStream is = assets.open("places.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);

            JSONArray jsonObjectNames = obj.names();

            JSONObject jsonDescriptionObjects = null;

            for (int x = 0; x < obj.length(); x++) {

                jsonDescriptionObjects = obj.getJSONObject(jsonObjectNames.getString(x));

                addressTitle = jsonDescriptionObjects.getString("address-title");
                addressStreet = jsonDescriptionObjects.getString("address-street");
                elevation = jsonDescriptionObjects.getDouble("elevation");
                latitude  = jsonDescriptionObjects.getDouble("latitude");
                longitude = jsonDescriptionObjects.getDouble("longitude");
                name = jsonDescriptionObjects.getString("name");
                image = jsonDescriptionObjects.getString("image");
                description = jsonDescriptionObjects.getString("description");
                category = jsonDescriptionObjects.getString("category");

                placeDescription = new PlaceDescription(addressTitle,addressStreet,elevation,latitude,longitude,name,
                        image,description,category);
                lib.add(placeDescription);
            }


        }

        catch (IOException ex) {
            ex.printStackTrace();
            android.util.Log.d("ERROR", "CANNOT PARSE JSON");
        }
        catch (org.json.JSONException ex) {
            ex.printStackTrace();
        }
    }

    //use this to both add and edit the current field
    protected void addObject(PlaceDescription pd) {

        lib.add(pd);

    }

    public void fromList(ArrayList<PlaceDescription> pl)
    {

    }
    //if user wants to remove the place description
    protected String removeObject(String name) {

        String returnString = "";
        for(int x = 0; x < lib.size(); x++){
            PlaceDescription desc = lib.get(x);
            String descName = desc.getName();
            if(descName.equals(name)){
                lib.remove(x);
                returnString = "Success";
            }
            else{
             returnString = "error";
            }
        }
        return returnString;
    }

    protected PlaceDescription getPlaceDesc(String name) {

        String returnString = "";
        PlaceDescription returnDesc = null;
        for(int x = 0; x < lib.size(); x++){
            PlaceDescription desc = lib.get(x);
            String descName = desc.getName();
            if(descName.equals(name)){
              returnDesc =  desc;
            }
        }
        return returnDesc;
    }

    //use this to populate the text views to edit
    protected ArrayList getLibrary(){
        return lib;
    }

}
