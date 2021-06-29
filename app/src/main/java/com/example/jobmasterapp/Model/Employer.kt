package com.example.jobmasterapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Employer(val fullnamerepresentative: String, val emailrepresentative:String,val mobilerepresentative:String,val industry: String,val website:String,val companyname:String,val aboutcompany:String,val headquarters:String,val uid:String,val founded:String,val companysize:String,val companyemail:String):
    Parcelable
{
    constructor(): this("","","","","","","","","","","","")
}