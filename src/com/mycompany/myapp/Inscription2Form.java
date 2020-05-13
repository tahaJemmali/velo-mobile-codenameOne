/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author USER
 */
public class Inscription2Form extends Form {
    public Inscription2Form(Resources resourceObjectInstance,Form inscriptionForm) {
        
        setLayout(BoxLayout.yCenter());
        setTitle("Inscription");
        getToolbar().addMaterialCommandToLeftBar("Back",FontImage.MATERIAL_ARROW_BACK, e-> inscriptionForm.showBack());
        
        
        //installSidemenu(resourceObjectInstance);
        Container cnt = new Container(BoxLayout.y());
        
        Label labelAddresse = new Label("Adresse");
        Label labelTel = new Label("Numéro téléphone");

        Label labelEmpty = new Label(" ");

        
        TextField tfAdresse = new TextField("","Adresse");
        TextField tfTel= new TextField("","Numéro téléphone");

        
        Button btnInscription = new Button("S'inscrire");


        cnt.addAll(labelAddresse,tfAdresse,labelTel,tfTel,labelEmpty);
            
        this.addAll(cnt,btnInscription);
                
                }
}
