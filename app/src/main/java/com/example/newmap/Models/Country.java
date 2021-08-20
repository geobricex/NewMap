package com.example.newmap.Models;

public class Country {
    String Name = "";
    String Capital = "";
    String TD = "";
    String West = "";
    String East = "";
    String North = "";
    String South = "";
    String TelPref = "";
    String iso2 = "";
    String iso3 = "";
    String CountryInfo = "";
    String Flag = "";
    String Latitude = "";
    String Longitude = "";

    public Country() {

    }

    public Country(String name, String capital, String TD, String west, String east, String north, String south, String telPref, String iso2, String iso3, String countryInfo, String flag) {
        Name = name;
        Capital = capital;
        this.TD = TD;
        West = west;
        East = east;
        North = north;
        South = south;
        TelPref = telPref;
        this.iso2 = iso2;
        this.iso3 = iso3;
        CountryInfo = countryInfo;
        Flag = flag;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public String getTD() {
        return TD;
    }

    public void setTD(String TD) {
        this.TD = TD;
    }

    public String getWest() {
        return West;
    }

    public void setWest(String west) {
        West = west;
    }

    public String getEast() {
        return East;
    }

    public void setEast(String east) {
        East = east;
    }

    public String getNorth() {
        return North;
    }

    public void setNorth(String north) {
        North = north;
    }

    public String getSouth() {
        return South;
    }

    public void setSouth(String south) {
        South = south;
    }

    public String getTelPref() {
        return TelPref;
    }

    public void setTelPref(String telPref) {
        TelPref = telPref;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getCountryInfo() {
        return CountryInfo;
    }

    public void setCountryInfo(String countryInfo) {
        CountryInfo = countryInfo;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "  {\n" +
                "    \"Name\":\"" + Name + "\",\n" +
                "    \"Capital\":\"" + Capital + "\",\n" +
                "    \"TD\":\"" + TD + "\",\n" +
                "    \"West\":\"" + West + "\",\n" +
                "    \"East\":\"" + East + "\",\n" +
                "    \"North\":\"" + North + "\",\n" +
                "    \"South\":\"" + South + "\",\n" +
                "    \"TelPref\":\"" + TelPref + "\",\n" +
                "    \"iso2\":\"" + iso2 + "\",\n" +
                "    \"iso3\":\"" + iso3 + "\",\n" +
                "    \"CountryInfo\":\"" + CountryInfo + "\",\n" +
                "    \"Flag\":\"" + Flag + "\",\n" +
                "    \"Latitude\":\"" + Latitude + "\",\n" +
                "    \"Longitude\":\"" + Longitude + "\"\n" +
                "  }";
        //    String Name = "";
        //    String Capital = "";
        //    String TD = "";
        //    String West = "";
        //    String East = "";
        //    String North = "";
        //    String South = "";
        //    String TelPref = "";
        //    String iso2 = "";
        //    String iso3 = "";
        //    String CountryInfo = "";
    }
}
