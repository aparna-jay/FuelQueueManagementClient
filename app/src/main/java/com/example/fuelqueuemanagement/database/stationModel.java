/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement.database;

//Model class for filling stations
public class stationModel {

    //Attributes
    String stationId, stationName, email, address, password, petrolAvailability,  dieselAvailability;
    int petrolQueueLength, dieselQueueLength;

    //Constructor
    public stationModel(String stationId, String stationName, String email, String address, String password, String petrolAvailability, String dieselAvailability, int petrolQueueLength, int dieselQueueLength) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.petrolAvailability = petrolAvailability;
        this.dieselAvailability = dieselAvailability;
        this.petrolQueueLength = petrolQueueLength;
        this.dieselQueueLength = dieselQueueLength;
    }

    //Getters and setters
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPetrolAvailability() {
        return petrolAvailability;
    }

    public void setPetrolAvailability(String petrolAvailability) {
        this.petrolAvailability = petrolAvailability;
    }

    public String getDieselAvailability() {
        return dieselAvailability;
    }

    public void setDieselAvailability(String dieselAvailability) {
        this.dieselAvailability = dieselAvailability;
    }

    public int getPetrolQueueLength() {
        return petrolQueueLength;
    }

    public void setPetrolQueueLength(int petrolQueueLength) {
        this.petrolQueueLength = petrolQueueLength;
    }

    public int getDieselQueueLength() {
        return dieselQueueLength;
    }

    public void setDieselQueueLength(int dieselQueueLength) {
        this.dieselQueueLength = dieselQueueLength;
    }
}
