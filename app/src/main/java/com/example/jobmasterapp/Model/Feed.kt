package com.example.jobmasterapp.Model

class Feed {
    private var feedtext: String = ""
    private var feeddetail: String = ""
    private var feedlink: String = ""
    private var uid: String = ""
    private var image: String = ""

    constructor()

    constructor(feedtext: String,feeddetail: String,feedlink:String,
                uid: String, image: String)

    {
        this.feedtext = feedtext
        this.feeddetail = feeddetail
        this.feedlink = feedlink
        this.uid = uid
        this.image = image
    }


    fun getFeedtext(): String
    {
        return feedtext
    }

    fun setFeedtext(feedtext: String)
    {
        this.feedtext = feedtext
    }

    fun getFeeddetail(): String
    {
        return feeddetail
    }

    fun setFeeddetail(feeddetail: String)
    {
        this.feeddetail = feeddetail
    }

    fun getFeedlink(): String
    {
        return feedlink
    }

    fun setFeedlink(feedlink: String)
    {
        this.feedlink = feedlink
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