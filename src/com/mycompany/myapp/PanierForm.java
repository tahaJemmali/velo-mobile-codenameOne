/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;
import entities.Images;
import entities.Panier;
import entities.Produit;
import services.ServicePanier;
import services.ServiceProduit;
import utils.Statics;

/**
 *
 * @author USER
 */

public class PanierForm extends BaseForm {
    
    Label lq;
    Label lqTotal;
    PanierForm currentForm;
    Form AllprodForm ;
    
    public PanierForm() {
        this(Resources.getGlobalResources());
    }
    public PanierForm(Resources resourceObjectInstance){
        
    }
    public PanierForm(Resources resourceObjectInstance,Form f){
        AllprodForm=f;
        currentForm=this;
        installSidemenu(resourceObjectInstance);
        this.setLayout(BoxLayout.y());
        //ServicePanier.getInstance().getAllPanier(currentUser); // select * from panier where id client = client
        
        for (Panier panier : ServicePanier.getInstance().getAllPaniers(currentUser))
        {
            //System.out.println(panier.getProduit().getName()+"   "+panier.getQuantite());
            add(createpanierBox(panier.getId(),panier.getProduit(),panier.getQuantite()));
        }
        
        Container CviderPanier = new Container();
        Style s = new Style();
        Button viderPanierbtn = new Button("  Vider le panier");
viderPanierbtn.addActionListener(e->{
    AllprodForm.showBack();
});
viderPanierbtn.getAllStyles().setPadding(2, 2, 10, 10);
viderPanierbtn.getAllStyles().setBorder(Border.createLineBorder(1));
viderPanierbtn.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
viderPanierbtn.getAllStyles().setFgColor(0x000000);
viderPanierbtn.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
viderPanierbtn.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
viderPanierbtn.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);
viderPanierbtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_SHOPPING_CART,s,4));
//CviderPanier.add(viderPanierbtn);

Container Ccontinuershopping = new Container();
        Button continuershoppingbtn = new Button("Continuer le shopping");
continuershoppingbtn.addActionListener(e->{
    AllprodForm.showBack();
});

continuershoppingbtn.getAllStyles().setPadding(2, 2, 10, 10);
continuershoppingbtn.getAllStyles().setBorder(Border.createLineBorder(1));
continuershoppingbtn.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
continuershoppingbtn.getAllStyles().setFgColor(0x000000);
continuershoppingbtn.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
continuershoppingbtn.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
continuershoppingbtn.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);
continuershoppingbtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_LEFT,s,6));

//Ccontinuershopping.add(continuershoppingbtn);

addAll(viderPanierbtn,continuershoppingbtn);

    }
    
    public Container createpanierBox(int id,Produit p,int quantite){
        Container Call = new Container(BoxLayout.y());
        Container C = new Container(BoxLayout.y());
        
        Container cTitle  = new Container(BoxLayout.x());
   cTitle.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   cTitle.getUnselectedStyle().setBackgroundGradientEndColor(0xF8F8F8);
   cTitle.getUnselectedStyle().setBackgroundGradientStartColor(0xF8F8F8);
   Style s = new Style();
   img = FontImage.createMaterial(FontImage.MATERIAL_LIST_ALT,s);
Container icon2 = new Container();
icon2.add(img);

int fontSize = Display.getInstance().convertToPixels(3);
Label L = new Label(p.getName()+" - "+p.getPrice()+" Dt");
cTitle.addAll(icon2,L);
 L.getUnselectedStyle().setFont(Font.createSystemFont(1, 1, 4));

        Call.getAllStyles().setMargin(20,20,20,20);
        Call.getAllStyles().setBorder(Border.createLineBorder(1,0xAAAAAA));

        Container Cimage = new Container();
        Cimage.getAllStyles().setMargin(10,10,10,10);
        Cimage.getAllStyles().setBorder(Border.createLineBorder(1,0xAAAAAA));

        ImageViewer imgv ;
        Image img;
        int f=0;
        Images im = new Images();
         
  for (Produit pp : ServiceProduit.getInstance().getAllProduits().keySet()){
      
      if (pp.equals(p)){
     for (Images i : ServiceProduit.getInstance().pMap.get(pp))
               {
                  if (f==0){
                   im=i;
                   f=1;}
               }
      }
  }
        img = URLImage.createToStorage(enc,im.getImage(),im.getImage(), URLImage.RESIZE_SCALE);
        imgv = new ImageViewer(img);
        Cimage.add(imgv);
        
        Container Cinfo = new Container(BoxLayout.y());
        Label ref = new Label(p.getReference());
        Label cat = new Label(p.getCategory());
        ref.getUnselectedStyle().setFont(Font.createSystemFont(1, 1, 3));
        cat.getUnselectedStyle().setFont(Font.createSystemFont(1, 1, 3));
        Cinfo.addAll(ref,cat);
        
        Container CQ = new Container(BoxLayout.y());

//Cplusoumoins.addAll(btnMoins,lq,btnPlus);
//Cplusoumoins.setSameHeight(btnMoins,lq,btnPlus);
        lqTotal = new Label("total : "+quantite+" x "+p.getPrice());
        Label ll= new Label();
        Label lt = new Label(String.valueOf(quantite*p.getPrice())+" Dt");
        
        lqTotal.getUnselectedStyle().setFont(Font.createSystemFont(1, 1, 1));
        ll.getUnselectedStyle().setFont(Font.createSystemFont(1, 1, 1));
        lt.getUnselectedStyle().setFont(Font.createSystemFont(1, 1, 1));
        
        CQ.addAll(lqTotal/*,ll*/,lt);
        
        Container Cdelete = new Container(BoxLayout.xRight());
        /*Image imgg = FontImage.createMaterial(FontImage.MATERIAL_DELETE,s,4);
    Cdelete.add(imgg);*/
        
Button delete = new Button("");

delete.addActionListener(e->{
    
  Panier pan = new Panier();
  pan.setId(id);
  ServicePanier.getInstance().supprimerPanier(pan);
  AllprodForm.showBack();
});

delete.getAllStyles().setPadding(0, 0, 0, 0);
delete.getAllStyles().setBorder(Border.createLineBorder(1));
delete.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
delete.getAllStyles().setFgColor(0xFFFFFF);
delete.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
delete.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
delete.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);
delete.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE,s,4));
       Cdelete.add(delete); 
       
        Container CunderTitle = new Container(BoxLayout.x());
        CunderTitle.add(BoxLayout.encloseYCenter(Cimage));
        CunderTitle.addAll(Cinfo,CQ);
        
        C.addAll(cTitle,CunderTitle,Cdelete);
        
        Call.addAll(C);
        return Call;
    }
    
    
    
}
