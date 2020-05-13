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
public class InscriptionForm extends Form {
    private InscriptionForm current ;
    private Inscription2Form inscription2Form ;
    //private boolean flag = false;
    
    public InscriptionForm(Resources resourceObjectInstance,Form loginForm) {
        current = this;
        inscription2Form = new Inscription2Form(resourceObjectInstance,current);
        
        setLayout(BoxLayout.yCenter());
        setTitle("Inscription");
        getToolbar().addMaterialCommandToLeftBar("Back",FontImage.MATERIAL_ARROW_BACK, e-> loginForm.showBack());
        
        getToolbar().addMaterialCommandToRightBar("Next",FontImage.MATERIAL_ARROW_FORWARD, e->{ 
            inscription2Form.show();
        });
        
        //installSidemenu(resourceObjectInstance);
        Container cnt = new Container(BoxLayout.y());
        
        Label labelusername = new Label("Username");
        Label labelEmail = new Label("Adresse E-mail");
        Label labelmdp = new Label("Password");
        Label labelmdp2 = new Label("Confirm Password");
        Label labelEmpty = new Label(" ");

        
        TextField tfUsername = new TextField("","Username");
        TextField tfPassword = new TextField("","Password",21,TextField.PASSWORD);
        TextField tfEmail = new TextField("","E-mail");
        TextField tfPassword2 = new TextField("","Password",21,TextField.PASSWORD);
        
        //Button btnInscription = new Button("S'inscrire");


        cnt.addAll(labelusername,tfUsername,labelEmail,tfEmail,labelmdp,tfPassword,labelmdp2,tfPassword2,labelEmpty);
            
        this.addAll(cnt);
                
                }
}
