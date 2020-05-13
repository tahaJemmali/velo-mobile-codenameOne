/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import entities.User;

/**
 *
 * @author USER
 */
public class MdpOublieForm3 extends Form {
    MdpOublieForm3 currentForm ;
    User user ;
    public MdpOublieForm3(User user, MdpOublieForm2 mdpOublieForm2,Form loginForm) {
        currentForm = this;
        this.user=user;
        
        this.setLayout(BoxLayout.yCenter());
       
        Label l = new Label("Récuperation de compte");
        TextField tfMdp1 = new TextField("","Password",21,TextField.PASSWORD);
        TextField tfMdp2 = new TextField("","Password",21,TextField.PASSWORD);
        
        getToolbar().addMaterialCommandToLeftBar("Retour",FontImage.MATERIAL_ARROW_LEFT, e-> loginForm.showBack());
        getToolbar().addMaterialCommandToRightBar("Suivant",FontImage.MATERIAL_ARROW_RIGHT, e-> {
            
            if (tfMdp1.getText().equals(tfMdp2.getText())){
               
                // user.setPassword(tfMdp1.getText());
                //ServiceUser.getInstance().updateMotdePasseUser(user);
                
                Dialog.show("Success","votre compte a été mis à jour",new Command("OK"));
                loginForm.showBack();
            }
            
            
        });
        
        Container c = new Container(new FlowLayout(Component.CENTER));
        Container c2 = new Container(new FlowLayout(Component.CENTER));
        
        c.add(l);
        c2.add(new Label(user.getEmail()));
        addAll(c,c2,new Label("Nouveau mot de passe"),tfMdp1,new Label("Confirmer mot de passe"),tfMdp2);
        
    }
    
}
