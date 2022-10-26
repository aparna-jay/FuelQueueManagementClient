/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement;

import com.example.fuelqueuemanagement.database.stationModel;

import java.util.List;

//Store login session details
public class SessionHandler {
    public static String currentUser;
    public static stationModel station;
    public static List<stationModel> allStations;
}
