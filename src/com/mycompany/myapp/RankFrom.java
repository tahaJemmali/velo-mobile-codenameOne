/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.progress.CircleFilledProgress;
import com.codename1.progress.CircleProgress;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.BaseForm.currentUser;
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
import services.ServiceUser;
import utils.Statics;

/**
 *
 * @author tahtouh
 */
public class RankFrom extends BaseForm {

    private RankFrom current;
    private Resources resourceObjectInstance;
    private float percent = 0;
    private int x = 0;
    EncodedImage encc;

    public RankFrom(com.codename1.ui.util.Resources resourceObjectInstance, BaseForm EvenementForm) {
        try {
                encc = EncodedImage.create("/giphy.gif");
            } catch (IOException ex) {
                System.out.println("enc ERR");
            }
        this.resourceObjectInstance = resourceObjectInstance;
        setLayout(BoxLayout.y());
        setTitle("Classments");
        getToolbar().addMaterialCommandToLeftBar("Evenements", FontImage.MATERIAL_ARROW_BACK, e -> EvenementForm.showBack());
        Container players = new Container(BoxLayout.y());
        ArrayList<User> list = ServiceUser.getInstance().getAllUsers();
        int i = 0;
        String UID;

        final CircleFilledProgress p = new CircleFilledProgress();

        for (User ex : list) {
            i++;
            if (ex.getUsername().equals(currentUser.getUsername())) {
                percent = ((list.size()-i+1) * 100) / list.size();
                percent = Math.round(percent);
                p.setProgress((int) percent);
                if (percent <= 50) {
                    p.setTextColor(0xfffffff);
                }
            }

            String LabelStyle = "StatusBar";
            url = Statics.BASE_URL + "/" + ex.getPhoto();
            if (i == 1) {
                UID = "rank";
                LabelStyle = "Title";
            } else if (i == 2) {
                UID = "secound";

            } else if (i == 3) {
                UID = "third";

            } else {
                UID = "default";
            }
            Container mainContent = BoxLayout.encloseY(
                    BoxLayout.encloseX(
                            new Label("Joueur :", LabelStyle),
                            new Label(ex.getUsername(), LabelStyle)),
                    BoxLayout.encloseX(
                            new Label("Score :", LabelStyle),
                            new Label("" + ex.getScore(), LabelStyle))
            );
            
            try {
                System.out.println("url : " + url);
                img = URLImage.createToStorage(encc, url, url, URLImage.RESIZE_SCALE);
                System.out.println("Done");
            } catch (NullPointerException exx) {
                img = resourceObjectInstance.getImage("holder.jpg");
                System.out.println("exception : " + exx);
            }
            imgv = new ImageViewer(img);
            Container imgPlayer = BorderLayout.center(mainContent).add(BorderLayout.WEST, imgv);
            imgPlayer.setUIID(UID);
            players.add(imgPlayer);
            ;
        }
        add(new Label("Vous avez un score supérieur à " + percent + "% "));
         add(new Label("des joueurs :"));
        
        add(p);
        add(players);
    }
}
