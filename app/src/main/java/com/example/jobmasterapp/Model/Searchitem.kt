package com.example.jobmasterapp.Model

class Searchitem {
    private var searchtext: String = ""
    private var uid: String = ""

    constructor()
    constructor(searchtext: String, uid: String) {
        this.searchtext = searchtext
        this.uid = uid
    }

    fun getSearchtext(): String
    {
        return searchtext
    }

    fun getSearchtext(searchtext: String)
    {
        this.searchtext = searchtext
    }

    fun getUid(): String
    {
        return uid
    }

    fun setUid(uid: String)
    {
        this.uid = uid
    }



}