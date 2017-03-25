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

import org.json.JSONObject;

/**
 * Created by mazaval4 on 2/8/2017.
 */

public class PlaceDescription {

    String addressTitle;
    String addressStreet;
    String name;
    String image;
    String description;
    String category;
    double elevation,latitude,longitude;

    public PlaceDescription(String addressTitle, String addressStreet, double elevation,
                            double latitude, double longitude, String name, String image,
                            String description, String category){

        this.addressTitle = addressTitle;
        this.addressStreet = addressStreet;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
    }

    PlaceDescription(JSONObject jsonObj){
        try{
            System.out.println("Place description constructor with jsonObj: "+
                    jsonObj.toString());
            addressTitle = jsonObj.getString("address-title");
            addressStreet = jsonObj.getString("address-street");
            elevation = jsonObj.getDouble("elevation");
            latitude = jsonObj.getDouble("latitude");
            longitude = jsonObj.getDouble("longitude");
            name = jsonObj.getString("name");
            image = jsonObj.getString("image");
            description = jsonObj.getString("description");
            category = jsonObj.getString("category");

        }catch (Exception ex){
            System.out.println(this.getClass().getSimpleName()+
                    ": error converting from json string");
        }
    }

    public String getLongitude() {
        return Double.toString(longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return Double.toString(latitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getElevation() {
        return Double.toString(elevation);
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    @Override
    public String toString() {
        return "PlaceDescription{" +
                "addressTitle='" + addressTitle + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", elevation=" + elevation +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public JSONObject toJson(){
        JSONObject jo = new JSONObject();
        try{
            jo.put("address-title",addressTitle);
            jo.put("address-street",addressStreet);
            jo.put("elevation",elevation);
            jo.put("latitude",latitude);
            jo.put("longitude",longitude);
            jo.put("name",name);
            jo.put("image",image);
            jo.put("description",description);
            jo.put("category",category);
        }catch (Exception ex){
            System.out.println(this.getClass().getSimpleName()+
                    ": error converting to json");
        }
        return jo;
    }
}
