package feedback.mpnsc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by swatiG on 13-07-2015.
 */
public class MapsActivity extends FragmentActivity  {

    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to the Google Map
        mMap = mapFragment.getMap();
       // String location="DLF Phase III, Gurgaon";
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Getting a place from the places list
       // HashMap<String, String> hmPlace = list.get(i);

        // Getting latitude of the place
       // double lat = Double.parseDouble(hmPlace.get("lat"));

        // Getting longitude of the place
      //  double lng = Double.parseDouble(hmPlace.get("lng"));

        // Getting name
       // String name = hmPlace.get("formatted_address");
       // Log.e("name :->", name);

        LatLng latLng = new LatLng(Double.parseDouble(DataHolderClass.getInstance().get_lat()),
                Double.parseDouble(DataHolderClass.getInstance().get_long()));
//        LatLng latLng = new LatLng(lat, lng);
        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker
      //  markerOptions.title(name);

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions).showInfoWindow();

        // Locate the first location
//        if(i==0) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

//        }
       String location=DataHolderClass.getInstance().get_new_meter_cons_add();

        if(location==null || location.equals("")){
            Toast.makeText(getBaseContext(), "No Place is entered", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://maps.googleapis.com/maps/api/geocode/json?";

        try {
            // encoding special characters like space in the user input place
            location = URLEncoder.encode(location, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String address = "address=" + location;

        String sensor = "sensor=false";

        // url , from where the geocoding data is fetched
        url = url + address + "&" + sensor;

        // Instantiating DownloadTask to get places from Google Geocoding service
        // in a non-ui thread
      /*  DownloadTask downloadTask = new DownloadTask();

        // Start downloading the geocoding places
        downloadTask.execute(url);*/
    }

/*
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){

            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserTask parserTask = new ParserTask();

            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }


    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                *//** Getting the parsed data as a an ArrayList *//*
                places = parser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){

            // Clears all the existing markers
            mMap.clear();

            for(int i=0;i<list.size();i++){

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);

                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                String name = hmPlace.get("formatted_address");
                Log.e("name :->", name);

                LatLng latLng = new LatLng(lat, lng);

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker
                markerOptions.title(name);

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions).showInfoWindow();

                // Locate the first location
                if(i==0) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

                }
            }
        }
    }*/

   /* private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }*/


}