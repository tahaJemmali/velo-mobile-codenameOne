/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.messaging.Message;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import entities.User;
import java.util.Random;
import services.ServiceUser;

/**
 *
 * @author USER
 */
public class MdpOublieForm2 extends Form {

    public User user;
    public MdpOublieForm2 currentForm ;
    private String code ;
    
    public MdpOublieForm2(User u, MdpOublieForm1 mdpOublieForm1,Form f) {
        user=u;
        currentForm=this;
        
        this.setLayout(BoxLayout.yCenter());
        
        Label l = new Label("Récuperation de compte");
        TextField tfCode = new TextField("","Code de confirmation");
        
        getToolbar().addMaterialCommandToLeftBar("Retour",FontImage.MATERIAL_ARROW_LEFT, e-> mdpOublieForm1.showBack());
        getToolbar().addMaterialCommandToRightBar("Suivant",FontImage.MATERIAL_ARROW_RIGHT, e-> {
            if (tfCode.getText().equals(code))
                new MdpOublieForm3(user,currentForm,f).show();
        });
        
        Container c = new Container(new FlowLayout(Component.CENTER));
        Container c2 = new Container(new FlowLayout(Component.CENTER));
        c.add(l);
        c2.add(new Label(user.getEmail()));
        addAll(c,c2,tfCode);

        generateCode();
        SendCodeToUser();
        
    }

    private void generateCode() {
code = String.valueOf(new Random().nextInt(999999)); 
    
    }

    private void SendCodeToUser() {
        Message m = new Message("Code de confirmation : "+code);
        Display.getInstance().sendMessage(new String[] {user.getEmail()}, "Récuperation de compte", m);
    }
    
}
