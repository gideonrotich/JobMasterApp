package com.example.jobmasterapp.Model

class User {

    private var fullname: String = ""
    private var email: String = ""
    private var mobile: String = ""
    private var uid: String = ""
    private var image: String = ""
    private var profession: String = ""

    constructor()

    constructor(fullname: String, email: String, mobile: String, uid: String, image: String,profession: String)
    {
        this.fullname = fullname
        this.email = email
        this.mobile = mobile
        this.uid = uid
        this.image = image
        this.profession = profession
    }

    fun getFullname(): String
    {
        return fullname
    }

    fun setFullname(fullname: String)
    {
        this.fullname = fullname
    }

    fun getEmail(): String
    {
        return email
    }

    fun setEmail(email: String)
    {
        this.email = email
    }


    fun getMobile(): String
    {
        return mobile
    }

    fun setMobile(mobile: String)
    {
        this.mobile = mobile
    }

    fun getUid(): String
    {
        return uid
    }

    fun setUid(uid: String)
    {
        this.uid = uid
    }

    fun getImage(): String
    {
        return image
    }

    fun setImage(image: String)
    {
        this.image = image
    }

    fun getProfession(): String
    {
        return profession
    }

    fun setProfession(profession: String)
    {
        this.profession = profession
    }



}