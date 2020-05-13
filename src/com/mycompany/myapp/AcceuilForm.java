/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.User;
import java.io.IOException;
import services.ServiceEvenement;
import services.ServiceUser;

/**
 *
 * @author tahtouh
 */
public class AcceuilForm extends BaseForm {

    public AcceuilForm() {
        this(Resources.getGlobalResources());
    }

    @Override
    protected User getCurrentUser() {
        return currentUser;
    }

    public void init(User user, LoginForm lf) {
        currentUser = user;
        loginForm = lf;
    }

    public AcceuilForm(Resources resourceObjectInstance) {
        installSidemenu(resourceObjectInstance);
         setLayout(BoxLayout.y());
      //  setScrollableY(true);
        setTitle("Bienvenu  "+currentUser.getUsername());
        Container paragraph=new Container(BoxLayout.y());
        paragraph.add(new Label("Qui somme nous ?","SmallLabel")).
                add(new TextArea("Velo.tn est une boutique en ligne qui vous permet de commander les derniers vélos et toutes sortes d'accessoires et de pièces de rechanges.\n" +
"Nous offrons également un service de réparation 24/7 et un service de location à un prix alléchant!\n" +
"Notre site Web vous donne également la possibilité de participer à différentes compétitions et de gagner de superbes récompenses.\n" +
"voulez-vous personnaliser votre propre vélo?\n" +" Velo.tn vous offre la possibilité de créer votre vélo de rêve!"));
        
        
                
        /*
        addAll(new Label("Ma tmousch hetha juste a3mlou copie menou l'5edmetkom"));
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceEvenement.getInstance().getAllEvenements().toString());
        add(sp);*/
add(paragraph).
       add(new WalkthruForm(resourceObjectInstance));

     //   add(  resourceObjectInstance.getImage("bla.jpg"));
    }

    @Override
    protected boolean isCurrentAcceuil() {
        return true;
    }

}
