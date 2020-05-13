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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.Statics;


public class ServiceProduit {
    
    public static ServiceProduit instance = null;
    private ConnectionRequest req;
    private static ArrayList<Produit> produits;
    public HashMap<Produit,ArrayList<Images>> pMap ;
    private ServiceProduit() {
        req = new ConnectionRequest();
    }

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }
    
    public Map<Produit,ArrayList<Images>> getAllProduits() {
        String url = Statics.BASE_URL + "/produits";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProduits(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return pMap;
    }
    
    public ArrayList<Produit> parseProduits(String jsonText) {
        try {
            pMap = new HashMap<>();
            produits = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            //System.out.println("haw haw "+list);
            
            for (Map<String, Object> obj : list) {
                
                Produit p = new Produit();
                Images i = new Images();
                
                p.setId(Integer.parseInt((String) obj.get("id")));
                p.setReference((obj.get("refrence").toString()));
                p.setName((obj.get("name").toString()));
                p.setCategory((obj.get("category").toString()));
                p.setDescription((obj.get("description").toString()));
                p.setStock(Integer.parseInt((String) obj.get("stock")));
                p.setPrice(Double.parseDouble((String) obj.get("price")));
                
                i.setId(Integer.parseInt((String) obj.get("iid")));
                i.setImage(Statics.BASE_URL+"/"+(obj.get("image").toString()));
                i.setProduit(p);
                
                AjouterMap(p,i);
               
                produits.add(p);
            }
        } catch (IOException ex) {

        }
        return produits;
    }
    public void AjouterMap(Produit p,Images i){
        for (Produit pp : pMap.keySet())
        {
            if(pp.getId()==p.getId())
            {pMap.get(pp).add(i);
                return;}
        }
        
        pMap.put(p,new ArrayList<Images>());
        pMap.get(p).add(i);
        
        /*if (pMap.containsKey(p))
        {pMap.get(p).add(i);}
        else{
            pMap.put(p,new ArrayList<Images>());
            pMap.get(p).add(i);}*/
    }
}
