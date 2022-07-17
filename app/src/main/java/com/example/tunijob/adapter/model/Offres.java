package com.example.tunijob.adapter.model;

public class Offres {
    private  String description;
    private String experience;
    private String logoentreprise;
    private String postes;
    private String titre;
    private String identreprise;
    private String idoffre;

    public Offres() {
    }

    public Offres(String description, String experience, String logoentreprise, String postes, String titre, String identreprise, String idoffre) {
        this.description = description;
        this.experience = experience;
        this.logoentreprise = logoentreprise;
        this.postes = postes;
        this.titre = titre;
        this.identreprise = identreprise;
        this.idoffre = idoffre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLogoentreprise() {
        return logoentreprise;
    }

    public void setLogoentreprise(String logoentreprise) {
        this.logoentreprise = logoentreprise;
    }

    public String getPostes() {
        return postes;
    }

    public void setPostes(String postes) {
        this.postes = postes;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIdentreprise() {
        return identreprise;
    }

    public void setIdentreprise(String identreprise) {
        this.identreprise = identreprise;
    }

    public String getIdoffre() {
        return idoffre;
    }

    public void setIdoffre(String idoffre) {
        this.idoffre = idoffre;
    }
}
