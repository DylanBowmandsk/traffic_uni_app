package uk.ac.caledonian.dbowma201_s1909583_roadworks;
//Bowman Dylan s1909583
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


//This adapter renders views for the listView component
public class RoadworksAdapter extends ArrayAdapter<Roadwork> {

    private int resourceLayout;
    private Context mContext;

    public RoadworksAdapter(Context context, int resource, ArrayList<Roadwork> items) {
        super(context, resource, items);
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //instantiates view object
        View v = convertView;
        //fetches current arraylist item
        Roadwork item = (Roadwork) getItem(position);

        //renders view
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        //initialises textfields and sets their values
        TextView roadworkView = (TextView) v.findViewById(R.id.title);
        TextView startDateView = (TextView) v.findViewById(R.id.start_date);
        TextView endDateView = (TextView) v.findViewById(R.id.end_date);
        roadworkView.setText(item.getTitle());
        startDateView.setText(item.getStartDate());
        endDateView.setText(item.getEndDate());

        //onclick renders new view and sends known object data to view
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DetailActivity.class);
                intent.putExtra("title",item.getTitle());
                intent.putExtra("start-date",item.getStartDate());
                intent.putExtra("end-date",item.getEndDate());
                intent.putExtra("delay",item.getDelay());
                intent.putExtra("link",item.getLink());
                intent.putExtra("published",item.getPubDate());
                getContext().startActivity(intent);
            }
        });

        return v;
    }
}
