/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import entities.User;
import services.ServiceUser;

/**
 *
 * @author USER
 */
public class MdpOublieForm1 extends Form {
    
    MdpOublieForm1 currentForm;
    
    public MdpOublieForm1(Form f){
        currentForm = this;
        
        this.setLayout(BoxLayout.yCenter());
        
        Label l = new Label("Récuperation de compte");
        TextField tfEmail = new TextField("","Adresse e-mail");
        
        getToolbar().addMaterialCommandToLeftBar("Retour",FontImage.MATERIAL_ARROW_LEFT, e-> f.showBack());
        getToolbar().addMaterialCommandToRightBar("Suivant",FontImage.MATERIAL_ARROW_RIGHT, e-> {
        User u = new User();
        u.setEmail(tfEmail.getText());
            if (ServiceUser.getInstance().CheckifEmailexists(u)!=null){
                u=ServiceUser.getInstance().CheckifEmailexists(u);
                new MdpOublieForm2(u,currentForm,f).show();
            }
            ServiceUser.getInstance().resetCurrentUser();
        });
        Container c = new Container(new FlowLayout(Component.CENTER));
        c.add(l);
        addAll(c,tfEmail);

    }
}
