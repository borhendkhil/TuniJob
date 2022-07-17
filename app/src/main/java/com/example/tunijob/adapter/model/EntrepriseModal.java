package com.example.tunijob.adapter.model;



public class EntrepriseModal {
    private String ide;
    private String email;
    private String motdepasse;
    private String nom;
    private String tel;
    private String adresseville;
    private String logourl;

    public EntrepriseModal(String ide, String email, String motdepasse, String nom, String tel, String adresseville, String logourl) {
        this.ide = ide;
        this.email = email;
        this.motdepasse = motdepasse;
        this.nom = nom;
        this.tel = tel;
        this.adresseville = adresseville;
        this.logourl = logourl;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
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

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }
}

