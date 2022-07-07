package com.kevin.breatheezy.preferences;

import com.orhanobut.hawk.Hawk;

public class GlobalPreferenceManager {

    public static void setUserName(String val){
        Hawk.put("USER_NAME", val);
    }
    public static String getUserName(){
        if(Hawk.contains("USER_NAME")){
            return Hawk.get("USER_NAME");
        }else
            return "";
    }

    public static void setHeight(String val){
        Hawk.put("HEIGHT", val);
    }
    public static String getHeight(){
        if(Hawk.contains("HEIGHT")){
            return Hawk.get("HEIGHT");
        }else
            return "";
    }

    public static void setWeight(String val){
        Hawk.put("WEIGHT", val);
    }
    public static String getWeight(){
        if(Hawk.contains("WEIGHT")){
            return Hawk.get("WEIGHT");
        }else
            return "";
    }

    public static void setGenderAsMale(boolean val){
        Hawk.put("GENDER", val);
    }
    public static boolean isGenderMale(){
        if(Hawk.contains("GENDER")){
            return Hawk.get("GENDER");
        }else
            return true;
    }




    public static void setDoctorPhNo(String val){
        Hawk.put("DOCTOR_PH", val);
    }
    public static String getDoctorPhNo(){
        if(Hawk.contains("DOCTOR_PH")){
            return Hawk.get("DOCTOR_PH");
        }else
            return "";
    }

    public static void setFriendPhNo(String val){
        Hawk.put("FRIEND_PH", val);
    }
    public static String getFriendPhNo(){
        if(Hawk.contains("FRIEND_PH")){
            return Hawk.get("FRIEND_PH");
        }else
            return "";
    }

    public static void setFamilyPhNo(String val){
        Hawk.put("FAMILY_PH", val);
    }
    public static String getFamilyPhNo(){
        if(Hawk.contains("FAMILY_PH")){
            return Hawk.get("FAMILY_PH");
        }else
            return "";
    }

    public static void setAmbulancePhNo(String val){
        Hawk.put("AMBULANCE_PH", val);
    }
    public static String getAmbulancePhNo(){
        if(Hawk.contains("AMBULANCE_PH")){
            return Hawk.get("AMBULANCE_PH");
        }else
            return "";
    }
}
