/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.sun.prism.paint.Color;
import entities.Images;
import entities.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import services.ServiceProduit;
import utils.Statics;

/**
 *
 * @author USER
 */
public class ProduitForm extends BaseForm {
    
    ArrayList<Map<String, Object>> data ;
    Image imgg;
    ProduitForm currentForm;
    
    public ProduitForm() {
        this(Resources.getGlobalResources());
    }
    
    public ProduitForm(Resources resourceObjectInstance){
  currentForm=this;
  installSidemenu(resourceObjectInstance);
  getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SHOPPING_CART, e->new PanierForm(resourceObjectInstance,currentForm).show());

  
  this.setLayout(new BorderLayout());
  
  Tabs tabs = new Tabs();
       tabs.setTabPlacement(Component.TOP); 
  addData(null);
  DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
  MultiList mlTout = new MultiList(model);
  
  /*addData("Velo");
  model = new DefaultListModel<>(data);
  MultiList mlVelo = new MultiList(model);
  
  addData("Accessoire");
  model = new DefaultListModel<>(data);
  MultiList mlAccessoires = new MultiList(model);
  
  addData("Piece de rechange");
  model = new DefaultListModel<>(data);
  MultiList mlPiecesderechange = new MultiList(model);*/
  
  setStyleML(mlTout);
  /*setStyleML(mlVelo);
  setStyleML(mlAccessoires);
  setStyleML(mlPiecesderechange);*/
  mlTout.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
HashMap <String,Object> m =  (HashMap <String,Object>) mlTout.getSelectedItem() ;
                Produit p = (Produit) m.get("produit");
                //System.out.println(mlTout.getSelectedItem().getClass()+" "+m.get("produit")); 
                new ProduitInfoForm(resourceObjectInstance,currentForm,p).show();      }
  });
   /* mlTout.addSelectionListener(new SelectionListener () {
            @Override
            public void selectionChanged(int oldSelected, int newSelected) {
               
                HashMap <String,Object> m =  (HashMap <String,Object>) mlTout.getSelectedItem() ;
                Produit p = (Produit) m.get("produit");
                //System.out.println(mlTout.getSelectedItem().getClass()+" "+m.get("produit")); 
                new ProduitInfoForm(resourceObjectInstance,currentForm,p).show();
            }
        });*/
    
 //add(BorderLayout.CENTER,ml);
 tabs.addTab("Tout", BoxLayout.encloseY(mlTout));
 /*tabs.addTab("Velos", BoxLayout.encloseY(mlVelo));
 tabs.addTab("Accessoires", BoxLayout.encloseY(mlAccessoires));
 tabs.addTab("Pieces de rechange", BoxLayout.encloseY(mlPiecesderechange));*/
 
 add(BorderLayout.CENTER, tabs);
    }
    
    
    private Map<String, Object> createListEntry(Produit p,Images i) {
  Map<String, Object> entry = new HashMap<>();
  String URLIMAGE = i.getImage();
  imgg = URLImage.createToStorage(enc,URLIMAGE,URLIMAGE, URLImage.RESIZE_SCALE);
  entry.put("Line1", p.getName());
  entry.put("Line2", p.getPrice()+" Dt");
  entry.put("icon", imgg);
  entry.put("produit",p);
  
  return entry;
}
    private void addData(String cat){
  data = new ArrayList<>();
  int mm = Display.getInstance().convertToPixels(3);
  EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(mm * 3, mm * 4, 0), true);
  Images im = new Images();
  for (Produit p : ServiceProduit.getInstance().getAllProduits().keySet()){
      if (cat!=null){
      if (p.getCategory().equals(cat)){
     //String URLIMAGE = "http://127.0.0.1:8000/Products/k9JrLFaoJumFNOscjigZt6W3a37Cbe.png";
     int f=0;
     for (Images i : ServiceProduit.getInstance().pMap.get(p))
               {
                   if (f==0){
                   //System.out.println(i.toString());
                   //URLIMAGE = i.getImage();
                   im=i;
                   f=1;}
               }
  //imgg = URLImage.createToStorage(enc,Statics.BASE_URL+"/"+URLIMAGE,Statics.BASE_URL+"/"+URLIMAGE, URLImage.RESIZE_SCALE);
         data.add(createListEntry(p,im));
      }   
    }
      else{
       //    String URLIMAGE = "http://127.0.0.1:8000/Products/k9JrLFaoJumFNOscjigZt6W3a37Cbe.png";
     int f=0;
     for (Images i : ServiceProduit.getInstance().pMap.get(p))
               {
                   if (f==0){
                   //System.out.println(i.toString());
                   //URLIMAGE = i.getImage();
                   im=i;
                   f=1;}
               }
  //imgg = URLImage.createToStorage(enc,Statics.BASE_URL+"/"+URLIMAGE,Statics.BASE_URL+"/"+URLIMAGE, URLImage.RESIZE_SCALE);
         data.add(createListEntry(p,im));
      }
  }
  //afficher();
    }
    
    public void setStyleML(MultiList ml){
  ml.getStyle().setBgColor(0xFFFFFF);
  ml.getStyle().setBgColor(0xFFFFFF, true);
  Style s =new Style();
  s.setBgColor(0xFFFFFF);
  ml.setSelectedStyle(s);
  ml.setUnselectedStyle(s);
    }
    
    /*public void afficher(){
	   for (Produit p : ServiceProduit.getInstance().pMap.keySet())
           {System.out.println(p.toString());
               for (Images i : ServiceProduit.getInstance().pMap.get(p))
               {
                   System.out.println(i.toString());
                   
               }
           }
    }*/
    
/* myComponent.getAllStyles().setBorder(Border.createEmpty());
myComponent.getAllStyles().setBackgroundType(BACKGROUND_NONE);
myComponent.getAllStyles().setBgTransparency(255);
myComponent.getAllStyles().setBgColor(myColor);*/
}
