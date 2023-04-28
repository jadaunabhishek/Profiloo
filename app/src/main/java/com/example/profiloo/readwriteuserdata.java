package com.example.profiloo;

//write and retreive data, we use this constructor

public class readwriteuserdata {
    public String RegisterNumber, PhoneNumber, Department;

    public readwriteuserdata(){};

    public readwriteuserdata(String textregisternumber, String textphonenumber, String textdepartment) {

        this.RegisterNumber = textregisternumber;
        this.PhoneNumber = textphonenumber;
        this.Department = textdepartment;
    }
}
