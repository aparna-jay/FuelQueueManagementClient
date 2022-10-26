/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fuelqueuemanagement.database.stationModel;

import java.util.ArrayList;
import java.util.List;

//Resources - Custom Adapter - https://www.youtube.com/watch?v=8QDTiOUOfKQ, https://larntech.net/android-listview-with-searchview-and-onitemclicklistener/
public class FindNearFuelStations extends AppCompatActivity {

    List<stationModel> stationModelList = new ArrayList<>();
    ListView listView;
    CustomAdapter customAdapter;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_near_fuel_stations);

        listView = findViewById(R.id.listview);

        //Get all stations from session
        stationModelList = SessionHandler.allStations;

        //Define custom adapter
        customAdapter = new CustomAdapter(stationModelList,this);
        listView.setAdapter(customAdapter);
    }

    //Create search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("Main"," data search"+newText);
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.searchView){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Custom adapter class
    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<stationModel> stationModelli;
        private List<stationModel> stationModelListFiltered;
        private Context context;

        public CustomAdapter(List<stationModel> stationModelli, Context context) {
            this.stationModelli = stationModelli;
            this.stationModelListFiltered = stationModelli;
            this.context = context;
        }

        @Override
        public int getCount() {
           return stationModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return stationModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //Get selected station details
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_itemsdetails,null);

            TextView id = view.findViewById(R.id.stationID);
            TextView names = view.findViewById(R.id.name);
            TextView emails = view.findViewById(R.id.email);
            TextView pqueuels = view.findViewById(R.id.pqueuel);
            TextView dqueuels = view.findViewById(R.id.dqueuel);
            TextView pstatuss = view.findViewById(R.id.pstatus);
            TextView dstatuss = view.findViewById(R.id.dstatus);

            id.setText(stationModelListFiltered.get(position).getStationId());
            names.setText(stationModelListFiltered.get(position).getStationName());
            emails.setText(stationModelListFiltered.get(position).getEmail());
            pqueuels.setText(Integer.toString(stationModelListFiltered.get(position).getPetrolQueueLength()));
            dqueuels.setText(Integer.toString(stationModelListFiltered.get(position).getDieselQueueLength()));
            pstatuss.setText(stationModelListFiltered.get(position).getPetrolAvailability());
            dstatuss.setText(stationModelListFiltered.get(position).getDieselAvailability());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            return view;
        }



        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    int resultcount = 0;
                    FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = stationModelli.size();
                        filterResults.values = stationModelli;

                    }else{
                        List<stationModel> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(stationModel stationModel:stationModelli){
                            if(stationModel.getStationName().contains(searchStr) || stationModel.getEmail().contains(searchStr)){
                                resultsModel.add(stationModel);
                                filterResults.count = resultsModel.size();
                                filterResults.values = resultsModel;
                                resultcount++;
                            }
                        }
                        if(resultcount == 0){
                            filterResults.count = stationModelli.size();
                            filterResults.values = stationModelli;
                        }


                    }

                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    stationModelListFiltered = (List<stationModel>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }

    //Use handlers to refresh activity - https://www.tutorialspoint.com/how-to-run-a-method-every-10-seconds-in-android
    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

}