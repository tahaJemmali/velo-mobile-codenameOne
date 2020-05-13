/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Images;
import entities.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author USER
 */
public class ServiceImages {
    
    public static ServiceImages instance = null;
    private ConnectionRequest req;
    private static ArrayList<Images> images;
    
    private ServiceImages() {
        req = new ConnectionRequest();
    }

    public static ServiceImages getInstance() {
        if (instance == null) {
            instance = new ServiceImages();
        }
        return instance;
    }
    
    public ArrayList<Images> getAllImages(Produit p){
        return   ServiceProduit.getInstance().pMap.get(p);
    }
}
