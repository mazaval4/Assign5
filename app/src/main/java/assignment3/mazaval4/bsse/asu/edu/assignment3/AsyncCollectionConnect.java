package assignment3.mazaval4.bsse.asu.edu.assignment3;


/*
    WHne the user clicks the name of the place in the view then use the get method. example is here
     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedStudent = studentNamesSpinner.getSelectedItem().toString();
        android.util.Log.d(this.getClass().getSimpleName(),"spinner item selected "+selectedStudent);
        try{
            MethodInformation mi = new MethodInformation(this, urlET.getText().toString(),"get",
                    new String[]{selectedStudent});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception processing spinner selection: "+
                    ex.getMessage());
        }
    }

*/

/**
 * Created by mazaval4 on 3/24/2017.
 */

import android.os.AsyncTask;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class AsyncCollectionConnect extends AsyncTask<MethodInformation, Integer, MethodInformation> {
    @Override
    protected void onPreExecute(){
        android.util.Log.d(this.getClass().getSimpleName(),"in onPreExecute on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
    }

    @Override
    protected MethodInformation doInBackground(MethodInformation... aRequest){
        // array of methods to be called. Assume exactly one input, a single MethodInformation object
        android.util.Log.d(this.getClass().getSimpleName(),"in doInBackground on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
        try {
            JSONArray ja = new JSONArray(aRequest[0].params);
            android.util.Log.d(this.getClass().getSimpleName(),"params: "+ja.toString());
            String requestData = "{ \"jsonrpc\":\"2.0\", \"method\":\""+aRequest[0].method+"\", \"params\":"+ja.toString()+
                    ",\"id\":3}";
            android.util.Log.d(this.getClass().getSimpleName(),"requestData: "+requestData+" url: "+aRequest[0].urlString);
            JsonRPCRequestViaHttp conn = new JsonRPCRequestViaHttp((new URL(aRequest[0].urlString)), aRequest[0].parent);
            String resultStr = conn.call(requestData);
            aRequest[0].resultAsJson = resultStr;
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                    ex.getMessage());
        }
        return aRequest[0];
    }

    @Override
    protected void onPostExecute(MethodInformation res){
        android.util.Log.d(this.getClass().getSimpleName(), "in onPostExecute on " +
                (Looper.myLooper() == Looper.getMainLooper() ? "Main thread" : "Async Thread"));
        android.util.Log.d(this.getClass().getSimpleName(), " resulting is: " + res.resultAsJson);
        try {
            if (res.method.equals("getNames")) {
                JSONObject jo = new JSONObject(res.resultAsJson);
                JSONArray ja = jo.getJSONArray("result");
                android.util.Log.d(this.getClass().getSimpleName(), " JSON array is " +ja);
                ArrayList<String> al = new ArrayList<String>();
                for (int i = 0; i < ja.length(); i++) {
                    al.add(ja.getString(i));
                }
                android.util.Log.d(this.getClass().getSimpleName(), " Arraylist is " +al);
                res.parent.arrayAdapter.clear();
                for (int i = 0; i < al.size(); i++) {
                    res.parent.arrayAdapter.add(al.get(i));
                }
                res.parent.arrayAdapter.notifyDataSetChanged();
            } else if (res.method.equals("placeGet")) {
//                JSONObject jo = new JSONObject(res.resultAsJson);
//                Student aStud = new Student(jo.getJSONObject("result"));
//                res.parent.studentidET.setText((new Integer(aStud.studentid)).toString());
//                res.parent.nameET.setText(aStud.name);
            } else if (res.method.equals("add")){
                try{
                    // finished adding a student. refresh the list of students by going back to the server for names
                    MethodInformation mi = new MethodInformation(res.parent, res.urlString, "getNames", new Object[]{ });
                    AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
                } catch (Exception ex){
                    android.util.Log.w(this.getClass().getSimpleName(),"Exception processing getNames: "+
                            ex.getMessage());
                }
            }
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
        }
    }

}

