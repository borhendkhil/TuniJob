package com.example.tunijob.adapter.model;

public class CandidatModel {
    private String Cin;
    private String email;
    private String motdepasse;
    private String nom;
    private String tel;
    private String adresseville;
    private String etat;

    public CandidatModel(String cin, String email, String motdepasse, String nom, String tel, String adresseville, String etat) {
        Cin = cin;
        this.email = email;
        this.motdepasse = motdepasse;
        this.nom = nom;
        this.tel = tel;
        this.adresseville = adresseville;
        this.etat = etat;
    }

    public String getCin() {
        return Cin;
    }

    public void setCin(String cin) {
        Cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresseville() {
        return adresseville;
    }

    public void setAdresseville(String adresseville) {
        this.adresseville = adresseville;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}


