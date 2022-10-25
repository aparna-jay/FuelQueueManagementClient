/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;
import com.example.fuelqueuemanagement.database.stationModel;
import java.util.List;


public class SearchFuelStationsActivity extends AppCompatActivity {
    ListView listView;
    StationOnlineDBHelper stationOnlineDBHelper;
    Button joinQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fuel_stations);
        listView = findViewById(R.id.listview);
        joinQueue = findViewById(R.id.joinQueue);
        stationOnlineDBHelper = new StationOnlineDBHelper();

        joinQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                stationOnlineDBHelper.IncreasePetrolQueueLength("787332222508283509383729");
            }
        });
        List<stationModel> stations = stationOnlineDBHelper.getAllStations(getApplicationContext());

    }
}