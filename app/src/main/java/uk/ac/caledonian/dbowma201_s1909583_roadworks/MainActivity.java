package uk.ac.caledonian.dbowma201_s1909583_roadworks;
//Bowman Dylan s1909583
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //boring boilerplate - butterknife or kotlin would of been better
    //i decided instead of micro managing the one array list it would be better to have a default store and a dedicated store for the adapter
    ArrayList<Roadwork> roadworkArray;
    ArrayList<Roadwork> adapterArray;
    RoadworksAdapter adapter;
    ListView listView;
    EditText searchInput;
    Button clearButton;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising my vars
        roadworkArray = new ArrayList<>();
        adapterArray = new ArrayList<>();
        searchInput = findViewById(R.id.search_input);
        clearButton = findViewById(R.id.clear_button);
        searchButton = findViewById(R.id.search_button);

        //async stuff
        RoadworkFetchAsync async = new RoadworkFetchAsync();
        async.execute("https://trafficscotland.org/rss/feeds/roadworks.aspx");

        //list view stuff
        adapter = new RoadworksAdapter(this,R.layout.roadwork_view,adapterArray);
        listView = (ListView) findViewById(R.id.roadwork_list);
        listView.setAdapter(adapter);

        //button stuff
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchRepo(searchInput.getText().toString());
            }
        });

        //more butt-stuff
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            adapter.clear();
            adapter.addAll(roadworkArray);
            }
        });
    }

    //big scray async class
    public class  RoadworkFetchAsync extends AsyncTask<String, Integer, Nullable> {
        protected Nullable doInBackground(String... strings) {
            String result = "";
            //reads a URL and opens up an input streams for the buffer reader to interpit then close
            try {
                URL url = new URL(strings[0]);
                String inputLine;
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                }
                //IS is parsed here
                parseData(result);
                in.close();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override //the adapter array is populated from the immuted roadwork array
        protected void onPostExecute(Nullable nullable) {
            for(int i = 0; i < roadworkArray.size(); i++){
                adapterArray.add(roadworkArray.get(i));
            }
            adapter.notifyDataSetChanged();
            super.onPostExecute(nullable);
        }

        //you shall not parse -- but please do
        public void parseData(String data) throws XmlPullParserException, IOException {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(data));
            Roadwork roadwork = new Roadwork();
            int eventType = parser.getEventType();

            //runs through tags and inspects what they are before deciding which variable goes where
            //Roadwork is created by start tag and added to roadwork array by end tag
            while (eventType != XmlPullParser.END_DOCUMENT) {
               if(eventType == XmlPullParser.START_TAG){
                   if(parser.getName().equals("item")){
                        roadwork = new Roadwork();
                   }
                   else if(parser.getName().equals("title")){
                       roadwork.setTitle(parser.nextText());
                   }
                   else if(parser.getName().equals("description")){
                       roadwork.setDescription(parser.nextText());
                   }
                   else if(parser.getName().equals("link")){
                       roadwork.setLink(parser.nextText());
                   }
                   else if(parser.getName().equals("pubDate")){
                       roadwork.setPubDate(parser.nextText());
                   }
               }
               else if(eventType == XmlPullParser.END_TAG){
                   if(parser.getName().equals("item")){
                       roadworkArray.add(roadwork);
                   }
               }
                eventType = parser.next();
            }
        }
    }

    //search function checks titles and dates if they contain the search params, clears existing adapter array and populates with new data
    public void searchRepo(String search) {
        ArrayList<Roadwork> searchArray = new ArrayList<>();
        for(int i = 0; i < roadworkArray.size(); i++){
            if(roadworkArray.get(i).getTitle().contains(search.toUpperCase()) || roadworkArray.get(i).getStartDate().toUpperCase().contains(search.toUpperCase())){
                searchArray.add(roadworkArray.get(i));

            }
            System.out.println(search);
        }

        adapter.clear();
        adapter.addAll(searchArray);
        adapter.notifyDataSetChanged();
    }
}
