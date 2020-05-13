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
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author tahtouh
 */
public class ServiceUser {

    public ArrayList<User> users;
    private User currentUser ;
    public static ServiceUser instance = null;
    public boolean resultOK;
     public boolean login;
    private ConnectionRequest req;

    private ServiceUser() {
        req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public boolean login(User t) {
        String url = Statics.BASE_URL + "/login_api";
        login = false;

        req.setUrl(url);
        req.setPost(true);
        req.addArgument("username", t.getUsername());
        req.addArgument("password", t.getPassword());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
                JSONParser j = new JSONParser();
                String jsonText = new String(req.getResponseData());
                try {
                    Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

                    System.out.println(tasksListJson.get("message"));
                    if (tasksListJson.get("message").equals("true")) {
                        login = true;
                        currentUser = parseUser(tasksListJson);
                    }
                    
                } catch (IOException ex) {

                }
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return login;
    }

    public boolean addUser(User t) {
        String url = Statics.BASE_URL + "/tasks/" + t.getUsername() + "/" + t.getPassword();
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

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                User t = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                t.setUsername((obj.get("username").toString()));
                t.setPassword(obj.get("password").toString());
                users.add(t);
            }
        } catch (IOException ex) {

        }
        return users;
    }

    public ArrayList<User> getAllUsers() {
        String url = Statics.BASE_URL + "/rank";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUserRanking(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    
    public User getUser(){
        return currentUser;
    }
    
    public User parseUser(Map<String, Object> json){
                        User user = new User();
                        
                System.out.println(json.get("user").getClass()); 
                
           Map<String, Object> obj =  (Map<String, Object>) json.get("user");
           
Double newData = new Double((double) obj.get("id"));
                int value = newData.intValue();
                user.setId(value);
                System.out.println((obj.get("username").toString())+"  "+obj.get("email").toString());
                user.setUsername((obj.get("username").toString()));
                user.setPassword(obj.get("password").toString());
                user.setEmail(obj.get("email").toString());
                user.setPhoto(obj.get("photo").toString());
                 user.setScore((int) Float.parseFloat(obj.get("score").toString()));
        return user;
    }
    
    
         public ArrayList<User> parseUserRanking(String jsonText) {
            ArrayList<User> users = new ArrayList<>();
        try {
          
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                User t = new User();
                float id = Float.parseFloat(obj.get("score").toString());
                t.setScore((int) id);
                t.setUsername(obj.get("username").toString());
                 t.setPhoto(obj.get("photo").toString());
                users.add(t);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return users;
    }
}
