package com.example.tunijob.adapter.model;

public class OfferSubmitted {
    private String titreoffre;
    private String idcandidat;
    private String identreprise;
    private String cvurl;

    public OfferSubmitted() {
    }

    public OfferSubmitted(String titreoffre, String idcandidat, String identreprise, String cvurl) {
        this.titreoffre = titreoffre;
        this.idcandidat = idcandidat;
        this.identreprise = identreprise;
        this.cvurl = cvurl;
    }

    public String getTitreoffre() {
        return titreoffre;
    }

    public void setTitreoffre(String titreoffre) {
        this.titreoffre = titreoffre;
    }

    public String getIdcandidat() {
        return idcandidat;
    }

    public void setIdcandidat(String idcandidat) {
        this.idcandidat = idcandidat;
    }

    public String getIdentreprise() {
        return identreprise;
    }

    public void setIdentreprise(String identreprise) {
        this.identreprise = identreprise;
    }

    public String getCvurl() {
        return cvurl;
    }

    public void setCvurl(String cvurl) {
        this.cvurl = cvurl;
    }
}

