package com.example.olgacoll.sifu.model;

import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by olgacoll on 11/5/17.
 */

public class Incidencia {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("client")
    @Expose
    private String client;

    public Incidencia(){

    }

    public Incidencia(String name, String last_name, String company, String description, String email, String phone, String site, String client) {
        this.name = name;
        this.last_name = last_name;
        this.company = company;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.site = site;
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", site='" + site + '\'' +
                ", client='" + client + '\'' +
                '}';
    }
}
