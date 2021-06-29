package com.example.jobmasterapp.Model

class Job
{
    private var jobtitle: String = ""
    private var jobdescription: String = ""
    private var responsibilities: String = ""
    private var skills: String = ""
    private var uid: String = ""
    private var education: String = ""
    private var joblocation: String = ""
    private var jobtype: String = ""
    private var deadline: String = ""
    private var companyname: String = ""
    private var category: String = ""
    private var salary: String = ""
    private var image: String = ""
    private var time: String = ""


    constructor()

    constructor(jobtitle: String,jobdescription: String,responsibilities: String,skills: String,
                uid: String,education: String,joblocation: String,jobtype: String,deadline: String,companyname: String,
                category: String,salary: String,image: String,time:String)
    {
        this.jobtitle = jobtitle
        this.jobdescription = jobdescription
        this.responsibilities = responsibilities
        this.skills = skills
        this.uid = uid
        this.education = education
        this.joblocation = joblocation
        this.jobtype = jobtype
        this.deadline = deadline
        this.companyname = companyname
        this.category = category
        this.salary = salary
        this.image = image
        this.time = time
    }

    fun getJobtitle(): String
    {
        return jobtitle
    }

    fun setJobtitle(jobtitle: String)
    {
        this.jobtitle = jobtitle
    }


    fun getJobdescription(): String
    {
        return jobdescription
    }

    fun setJobdescription(jobdescription: String)
    {
        this.jobdescription = jobdescription
    }

    fun getResponsibilities(): String
    {
        return responsibilities
    }

    fun setResponsibilities(responsibilities: String)
    {
        this.responsibilities = responsibilities
    }

    fun getSkills(): String
    {
        return skills
    }

    fun setSkills(skills: String)
    {
        this.skills = skills
    }

    fun getUid(): String
    {
        return uid
    }

    fun setUid(uid: String)
    {
        this.uid = uid
    }

    fun getEducation(): String
    {
        return education
    }

    fun setEducation(education: String)
    {
        this.education = education
    }

    fun getJoblocation(): String
    {
        return joblocation
    }

    fun setJoblocation(joblocation: String)
    {
        this.joblocation = joblocation
    }

    fun getJobtype(): String
    {
        return jobtype
    }

    fun setJobtype(jobtype: String)
    {
        this.jobtype = jobtype
    }
    fun getDeadline(): String
    {
        return deadline
    }

    fun setDeadline(deadline: String)
    {
        this.deadline = deadline
    }

    fun getCompanyname(): String
    {
        return companyname
    }

    fun setCompanyname(companyname: String)
    {
        this.companyname = companyname
    }

    fun getCategory(): String
    {
        return category
    }

    fun setCategory(category: String)
    {
        this.category = category
    }

    fun getSalary(): String
    {
        return salary
    }

    fun setSalary(salary: String)
    {
        this.salary = salary
    }

    fun getImage(): String
    {
        return image
    }

    fun setImage (image: String)
    {
        this.image = image
    }

    fun getTime(): String
    {
        return time
    }

    fun setTime (time: String)
    {
        this.time = time
    }

}