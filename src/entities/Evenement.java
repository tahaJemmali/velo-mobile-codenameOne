/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author tahtouh
 */
public class Evenement {
    private int id;
    private String titre;
    private String description;
    private String image;
    private String date_debut;
    private String date_creation;
    private String location;
    private User u;

    public Evenement() {
        id=-1;
    }
    

    public Evenement(String titre, String description, String image, String date_debut, String date_creation, String location) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.date_debut = date_debut;
        this.date_creation = date_creation;
        this.location = location;
    }
    

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public String getDate_creation() {
        return date_creation;
    }


    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", image=" + image + ", date_debut=" + date_debut + ", date_creation=" + date_creation + ", location=" + location +'}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
