/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author USER
 */
public class Images {
  
    private int id;
    private Produit produit;
    private String image;

    public int getId() {
        return id;
    }

    public Produit getProduit() {
        return produit;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduit(Produit p) {
        this.produit = p;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    @Override
    public String toString (){
        return "ID : "+id+" Produit id : "+produit.getId()+" Image : "+image;

    }
    @Override
   public boolean equals(Object o) {
	if (o==null) {
		System.out.println("Object null");
		return false;
	}
    if ((o instanceof Images)) 
	{
                final Images e = (Images) o ;
                if (this.getImage().equals(e.getImage()) ) 
                {
                return true;
                }
                else {
                    return false;
                }   
	}
		return false;
   }
   
}
