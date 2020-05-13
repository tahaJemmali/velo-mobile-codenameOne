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
import entities.Panier;
import entities.Produit;
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author USER
 */
public class ServicePanier {
    
    public static ServicePanier instance = null;
    private ConnectionRequest req;
    private static ArrayList<Panier> Panier;
    public boolean resultOK;
    public ArrayList<Panier> paniers;
    
    private ServicePanier() {
        req = new ConnectionRequest();
    }

    public static ServicePanier getInstance() {
        if (instance == null) {
            instance = new ServicePanier();
        }
        return instance;
    }
    
    
    public boolean addPanier(Produit p,User u, int quantite) {
       String url = Statics.BASE_URL + "/addPanier";
       
        req.setPost(true);
        
        req.addArgument("userid",String.valueOf(u.getId()));
        req.addArgument("produitid",String.valueOf(p.getId()));
        req.addArgument("quantite",String.valueOf(quantite));
        
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Panier> getAllPaniers(User u) {
        String url = Statics.BASE_URL + "/paniers";
        req.addArgument("userid",String.valueOf(u.getId()));
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                paniers = parsePaniers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return paniers;
    }
    
    public void supprimerPanier(Panier p){
        String url = Statics.BASE_URL + "/deletePanier";
        req.addArgument("pid",String.valueOf(p.getId()));
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //paniers = parsePaniers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public ArrayList<Panier> parsePaniers(String jsonText) {
        try {
            //pMap = new HashMap<>();
            paniers = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            //System.out.println("haw haw "+list);
            
            for (Map<String, Object> obj : list) {
                Panier p = new Panier();

               p.setId(Integer.parseInt((String) obj.get("cid")));

               /* Double newData = new Double((double) obj.get("cid"));
                int value = newData.intValue();
                p.setId(value);*/
                
                //p.setUser((User)obj.get("user_id"));
                
                p.setQuantite(Integer.parseInt((String) obj.get("quantity")));
                
               /* newData = new Double((double) obj.get("quantity"));
                value = newData.intValue();
                p.setQuantite(value);*/
                
                //System.out.println((String)obj.get("Name").toString());
                Produit prod = new Produit();
                prod.setId(Integer.parseInt((String) obj.get("id")));
                prod.setReference((obj.get("refrence").toString()));
                prod.setName((obj.get("name").toString()));
                prod.setCategory((obj.get("category").toString()));
                prod.setDescription((obj.get("description").toString()));
                prod.setStock(Integer.parseInt((String) obj.get("stock")));
                prod.setPrice(Double.parseDouble((String) obj.get("price")));
                
                p.setProduit(prod);
                
        //        p.setDate(obj.get("added_on").toString());

                
                paniers.add(p);
            }
        } catch (IOException ex) {

        }
        return paniers;
    }
}
