package com.example.jobmasterapp.Model

class Category {
    private var categoryname: String = ""
    private var categorydetail: String = ""
    private var uid: String = ""
    private var image: String = ""

    constructor()

    constructor(categoryname: String,categorydetail: String,
                uid: String, image: String)

    {
        this.categoryname = categoryname
        this.categorydetail = categorydetail
        this.uid = uid
        this.image = image
    }

    fun getCategoryname(): String
    {
        return categoryname
    }

    fun setCategoryname(categoryname: String)
    {
        this.categoryname = categoryname
    }

    fun getCategorydetail(): String
    {
        return categorydetail
    }

    fun setCategorydetail(categorydetail: String)
    {
        this.categorydetail = categorydetail
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

    fun setImage (image: String)
    {
        this.image = image
    }





}