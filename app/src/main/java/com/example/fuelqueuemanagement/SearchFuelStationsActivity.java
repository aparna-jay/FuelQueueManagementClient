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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;
import com.example.fuelqueuemanagement.database.stationModel;

import java.util.ArrayList;
import java.util.List;


public class SearchFuelStationsActivity extends AppCompatActivity {
    ListView listView;
    StationOnlineDBHelper stationOnlineDBHelper;
    Button joinQueue;
    CustomAdapter customAdapter;
    List<stationModel> stationModelList = new ArrayList<>();
    String selectStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fuel_stations);
        listView = findViewById(R.id.listview);
        joinQueue = findViewById(R.id.joinQueue);
        stationOnlineDBHelper = new StationOnlineDBHelper();

        listView = findViewById(R.id.listview);

        stationModelList = SessionHandler.allStations;

        customAdapter = new CustomAdapter(stationModelList,this);

        listView.setAdapter(customAdapter);

        joinQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(selectStation != null) {
                    if(SessionHandler.vehicleType.equals("Petrol")) {
                        stationOnlineDBHelper.IncreasePetrolQueueLength(selectStation);
                    }
                    else if(SessionHandler.vehicleType.equals("Diesel")){
                        stationOnlineDBHelper.IncreaseDieselQueueLength(selectStation);
                    }
                    Intent i = new Intent(SearchFuelStationsActivity.this, FuelStationDetailsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SearchFuelStationsActivity.this, "Please select station", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items,null);


            TextView names = view.findViewById(R.id.name);
            TextView emails = view.findViewById(R.id.email);
            TextView id = view.findViewById(R.id.stationID);

            names.setText(stationModelListFiltered.get(position).getStationName());
            emails.setText(stationModelListFiltered.get(position).getEmail());
            id.setText(stationModelListFiltered.get(position).getStationId());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     selectStation = stationModelListFiltered.get(position).getStationId();
                     SessionHandler.station = stationModelListFiltered.get(position);
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
}