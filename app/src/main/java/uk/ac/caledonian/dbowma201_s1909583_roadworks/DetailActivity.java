package uk.ac.caledonian.dbowma201_s1909583_roadworks;
//Bowman Dylan s1909583
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    TextView locationView;
    TextView startDateView;
    TextView endDateView;
    TextView delayView;
    TextView linkView;
    TextView publishedView;
    String title;
    String startDate;
    String endDate;
    String delay;
    String link ;
    String published;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeViews();
        getBundleData();
        setViewData();

    }
    public void initializeViews(){
        locationView = findViewById(R.id.location);
        startDateView = findViewById(R.id.start_date);
        endDateView = findViewById(R.id.end_date);
        delayView = findViewById(R.id.delay);
        linkView = findViewById(R.id.link);
        publishedView = findViewById(R.id.published);
    }

    public void getBundleData(){
        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");
        startDate = extras.getString("start-date");
        endDate = extras.getString("end-date");
        delay = extras.getString("delay");
        link = extras.getString("link");
        published = extras.getString("published");
    }

    public void setViewData(){
        locationView.setText(title);
        startDateView.setText(startDate);
        endDateView.setText(endDate);
        delayView.setText(delay);
        linkView.setText(link);
        publishedView.setText(published);
    }
}