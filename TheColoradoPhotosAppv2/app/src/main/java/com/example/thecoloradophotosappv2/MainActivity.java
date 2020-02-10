package com.example.thecoloradophotosappv2;

import android.content.Context;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private CustomAdapter customAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    CustomAdapter adapter;
    public MainActivity mainActivity = null;
    public ArrayList<ContactModel> contactModels = new ArrayList<ContactModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainActivity = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        context = getApplicationContext();

        //Change 3 to your choice because here 3 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new GridLayoutManager(context, 3);

        new fetchData().execute();

        Log.e("MainA", "hello");
        //ArrayList<ContactModel> contactModels = new ArrayList<ContactModel>();
//        contactModels = db.getAllRecords();

        //ArrayList<ContactModel> contactModels = db.getAllRecords();
        for (ContactModel n : contactModels) {
            Log.e("MainB", "bye" + n);
            // System.out.println(n);
        }

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        customAdapter = new CustomAdapter(this, contactModels);

        recyclerView.setAdapter(customAdapter);
    }


    private class fetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            UIUtil.startProgressDialog(MainActivity.this, "Downloading data....");
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonString = null;

            try {

                URL url = new URL("https://api.flickr.com/services/feeds/photos_public.gne?tags=coloradomountains&format=json");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int lengthOfFile = urlConnection.getContentLength();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonString = buffer.toString();
                Log.e("Json1", jsonString);

                StringBuffer removeEnd = new StringBuffer(jsonString);
                removeEnd = removeEnd.deleteCharAt(removeEnd.length()-1);
                int startIndex = 0;
                int endIndex = 15;
                removeEnd = removeEnd.delete(startIndex, endIndex);

                jsonString = removeEnd.toString();
                Log.e("strippedJson", jsonString);

                JSONObject flickrFeed = new JSONObject(jsonString);

                JSONArray jsonArray = flickrFeed.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ContactModel contactModel = new ContactModel();
                    contactModel.setTitle(jsonObject.getString("title"));
                    contactModel.setLink(jsonObject.getString("link"));
                    JSONObject media = jsonObject.getJSONObject("media");
                    contactModel.setThumbnail(media.getString("m"));
                    contactModel.setDateTaken(jsonObject.getString("date_taken"));
                    contactModel.setDescription(jsonObject.getString("description"));
                    contactModel.setPublished(jsonObject.getString("published"));
                    contactModel.setAuthor(jsonObject.getString("author"));
                    contactModel.setAuthorID(jsonObject.getString("author_id"));
                    contactModel.setTags(jsonObject.getString("tags"));
                    contactModels.add(contactModel);
                }

                return jsonString;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return jsonString;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //adapter.notifyDataSetChanged();
            UIUtil.stopProgressDialog(MainActivity.this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
