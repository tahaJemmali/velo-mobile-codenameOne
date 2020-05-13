/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.User;
import java.io.IOException;
import javafx.scene.image.ImageView;
import services.ServiceUser;
import utils.Statics;

/**
 * Utility methods common to forms e.g. for binding the side menu
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {
    
      public static boolean flagFacebook = false;
      public static User currentUser;
      static LoginForm loginForm;

     
      protected User getCurrentUser() {
        return new User();
      }
      
        EncodedImage enc ;
        ImageViewer imgv ;
        Image img;
        String url = Statics.BASE_URL+"/"+currentUser.getPhoto();
        
    public void installSidemenu(Resources res) {
        //if (!flagFacebook){
        User userr = new User();
        userr=currentUser;
        ServiceUser.getInstance().insertIfNotExistTempImageStorage(userr);
        //}
        try {
            System.out.println("aze baseform");
            Image selection = res.getImage("hd.png");
            /*   System.out.println("AFFICHAGE 2 ");
            
            if (currentUser!=null)
            System.out.println(currentUser.toString());
            if (loginForm!=null)*/
            // System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA -- "+this.loginForm);
            //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA -- "+getCurrentUser());
            
            
            Image accImage = null;
            if (isCurrentAcceuil()) {
                accImage = selection;
            }
            
            Image profilImage = null;
            if (isCurrentProfil()) {
                profilImage = selection;
            }
            
            Image cmdImage = null;
            if (isCurrentCommande()) {
                cmdImage = selection;
            }
            
            Image eventImage = null;
            if (isCurrentEvenement()) {
                eventImage = selection;
            }
            
            Image produitImage = null;
            if (isCurrentProduit()) {
                produitImage = selection;
            }
            
            Image repImage = null;
            if (isCurrentLocation()) {
                repImage = selection;
            }
            
            Image locImage = null;
            if (isCurrentReparation()) {
                locImage = selection;
            }
            
            Image drImage = null;
            if (isCurrentDridi()) {
                drImage = selection;
            }
            
            /* Button inboxButton = new Button("Inbox", inboxImage);
            inboxButton.setUIID("SideCommand");
            inboxButton.getAllStyles().setPaddingBottom(0);
            
            Container inbox = FlowLayout.encloseMiddle(inboxButton);
            inbox.setLeadComponent(inboxButton);
            inbox.setUIID("SideCommand");
            inboxButton.addActionListener(e -> new LoginForm().show());
            getToolbar().addComponentToSideMenu(inbox);*/
            
            getToolbar().addCommandToSideMenu("Acceuil", accImage, e ->new AcceuilForm(res).show());
            getToolbar().addCommandToSideMenu("Profil", profilImage, e -> {new ProfilForm(res).show();
            });
            getToolbar().addCommandToSideMenu("Mes Commandes", cmdImage, e -> {
            });
            getToolbar().addCommandToSideMenu("Evenement", eventImage, e -> new EvenementForm(res).show());
            
            getToolbar().addCommandToSideMenu("Produit", produitImage, e -> { new ProduitForm(res).show();
            });
            getToolbar().addCommandToSideMenu("Réparation", repImage, e -> {
            });
            getToolbar().addCommandToSideMenu("Location", locImage, e -> {
            });
            getToolbar().addCommandToSideMenu("Module amine", drImage, e -> {
            });
            
            
            try {
                enc = EncodedImage.create("/giphy.gif");
            } catch (IOException ex) {
                System.out.println("enc ERR");
            }
            
            //       User u = new User();
            // spacer
            if (flagFacebook)
                url = currentUser.getPhoto();
            img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
            
            //imgv = new ImageViewer(img);
            
            Container cnt_image = new Container();
            /* ROUND IMAGE*/
            Label picture = new Label("", "Container");
            //cnt_image.add(picture);
            int width = Display.getInstance().getDisplayWidth();
            Image capturedImage ;
            User u = new User();
            u = currentUser;
            
            capturedImage = img;
            
            if (ServiceUser.getInstance().checkTempImageStorage(u)!=null/* && !flagFacebook*/){
                img = Image.createImage(ServiceUser.getInstance().checkTempImageStorage(u).getPhoto());
             Image resizedImage = img.scaledWidth(Math.round(Display.getInstance().getDisplayWidth()/4)); 
             capturedImage=resizedImage;
            }
            
            width=capturedImage.getWidth();
            Image roundMask = Image.createImage(width, capturedImage.getHeight(), 0xff000000);
            
            Graphics gr = roundMask.getGraphics();
            gr.setColor(0xffffff);
            gr.fillArc(0, 0, width, width, 0, 360);
            Object mask = roundMask.createMask();
            capturedImage = capturedImage.applyMask(mask);
            picture.setIcon(capturedImage);
            this.revalidate();
            /* ROUND IMAGE*/
            //cnt_image.revalidate();
            /*W/O RI */
            cnt_image.revalidate();
            cnt_image.add(picture);
            //cnt_image.add(imgv);
            /*W/O RI*/
            cnt_image.revalidate();
            
            //Form hi = new Form("Hi World", BoxLayout.y());
            //hi.add(imgv);
            
            // ImageView im = new ImageView(Statics.BASE_URL+"/"+currentUser.getPhoto());
            
            // System.out.println(Statics.BASE_URL+"/"+currentUser.getPhoto());
            
            
            /*
            Style style = cnt_image.getAllStyles();
            Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            style.setBorder(RoundBorder.create().
            rectangle(true).
            color(0xffffff).
            strokeColor(0).
            strokeOpacity(120).
            stroke(borderStroke));
            */
            
            //cnt_image.getUnselectedStyle().setPadding(3,8,8,3);
            //cnt_image.getAllStyles().setBorder(RoundBorder.create().shadowOpacity(100));
            
            getToolbar().addComponentToSideMenu(cnt_image);
            getToolbar().addComponentToSideMenu(new Label(currentUser.getUsername(), "SideCommandNoPad"));
            getToolbar().addComponentToSideMenu(new Label(currentUser.getEmail(), "SideCommandSmall"));
            
            getToolbar().addCommandToSideMenu("Déconnexion", null, e -> {
                //new LoginForm(res).show();
                // user = null ;
                // u.setEmail(currentUser.getEmail());
                //System.out.println(" LOFIN FORM shiow back -- "+this.loginForm);
                //if (currentUser!=null)
                // System.out.println("USERRRRR . shiow back -- "+currentUser.toString());
                ServiceUser.getInstance().resetCurrentUser();
                loginForm.showBack();
                currentUser=null;
            });
            
            // System.out.print("Mon email : ther "+u.getEmail());
        } catch (IOException ex) {
        }
    }
    
    public void showToast(String text) {
        Image errorImage = FontImage.createMaterial(FontImage.MATERIAL_ERROR, UIManager.getInstance().getComponentStyle("Title"), 4);
        ToastBar.Status status = ToastBar.getInstance().createStatus();
        status.setMessage(text);
        status.setIcon(errorImage);
        status.setExpires(2000);
        status.show();
    }
    
    protected boolean isCurrentAcceuil() {
        return false;
    }

    protected boolean isCurrentProfil() {
        return false;
    }

    protected boolean isCurrentEvenement() {
        return false;
    }

    protected boolean isCurrentCommande() {
        return false;
    }

    protected boolean isCurrentProduit() {
        return false;
    }

    protected boolean isCurrentReparation() {
        return false;
    }

    protected boolean isCurrentLocation() {
        return false;
    }

    protected boolean isCurrentDridi() {
        return false;
    }

}
