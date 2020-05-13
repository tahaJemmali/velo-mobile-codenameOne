/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.BaseForm;
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
    Database db ;
    
    private ServiceUser() {
        req = new ConnectionRequest();
    }

    public void resetCurrentUser (){
        currentUser=null;
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
            System.out.println("el username éli bech ylogini : "+t.getUsername()+t.getPassword());
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

                  //  System.out.println(tasksListJson.get("message"));
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

    public void addUserFromInscription(User u) {
        String url = Statics.BASE_URL + "/registerM" ;
        req.setUrl(url);
        req.addArgument("username", u.getUsername());
        req.addArgument("email", u.getEmail());
        req.addArgument("password", u.getPassword());
        req.addArgument("address", u.getAddress());
        req.addArgument("photo", u.getPhoto());
        req.addArgument("phone", String.valueOf(u.getPhone()));
        
       /* System.out.println("username"+ u.getUsername());
        System.out.println("email"+ u.getEmail());
        System.out.println("password"+ u.getPassword());
        System.out.println("address"+ u.getAddress());
        System.out.println("photo"+ u.getPhoto());
        System.out.println("phone"+ String.valueOf(u.getPhone())); */
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
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
/*
    public ArrayList<User> getAllUsers() {
        String url = Statics.BASE_URL + "/users";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
   */ 
    public User getUser(){
        return currentUser;
    }
    
    public User getUser(User u){
        // if email exist return the user in db
        //create new user
        return currentUser;
    }
    
    public User parseUser(Map<String, Object> json){
                        User user = new User();
                        
               // System.out.println(json.get("user").getClass()); 
                
           Map<String, Object> obj =  (Map<String, Object>) json.get("user");
           
                  // System.out.println("AAAAAAAAA : "+list.get("username"));

                //System.out.println((obj.get("username").toString())+"  "+obj.get("email").toString());
                
                Double newData = new Double((double) obj.get("id"));
                int value = newData.intValue();
                user.setId(value);
                
                user.setUsername((obj.get("username").toString()));
                user.setPassword(obj.get("password").toString());
                user.setEmail(obj.get("email").toString());
                user.setPhoto(obj.get("photo").toString());
                
                String val =  (String) obj.get("phone");
                user.setPhone(Integer.parseInt(val));
                
                user.setAddress(obj.get("address").toString());
                user.setScore((int) Float.parseFloat(obj.get("score").toString()));
                //user.setLast_login();

        return user;
    }
    
    public User CheckifEmailexists(User u){
        String url = Statics.BASE_URL + "/userEmail";
        req.setUrl(url);
        req.addArgument("email", u.getEmail());
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
        return currentUser;
    }
    
    public void createTempImageStorage(){
        
        try {
                    db = Database.openOrCreate("dbvelo");
                    db.execute("Create table if not exists userImage"
                            +"(id Integer primary KEY AUTOINCREMENT,"
                            +"path TEXT"
                            + ")");
                    db.close(); 
                } catch (IOException ex) {
                    System.out.println("1");
                    System.out.println(ex.getMessage());
                }
    }
    public void insertTempImageStorage(User u){
        System.out.println("inserting  ---  "+u.getPhoto()+" "+u.getId());
        try {
                    db = Database.openOrCreate("dbvelo");
                    String req ="INSERT INTO userImage(id,path)"+
                            "values"
                            +"('"+u.getId()+"','"+u.getPhoto()+"')";
                    
                    db.execute(req);
                    db.close(); 
                } catch (IOException ex) {
                    System.out.println("2");
                    
                    System.out.println(ex.getMessage());
                }
    }
    public void updateTempImageStorage(User u){
        System.out.println("updating ---  "+u.getPhoto()+" "+u.getId());
        try {
                    db = Database.openOrCreate("dbvelo");
                    String req ="UPDATE userImage SET PATH='"+u.getPhoto()+"' where id="+u.getId();              
                    db.execute(req);
                    db.close(); 
                } catch (IOException ex) {
                    System.out.println("3");
                    System.out.println(ex.getMessage());
                }
    }
    public User checkTempImageStorage(User u){   
            User user = null;
        try {
                    db = Database.openOrCreate("dbvelo");
                    String req ="SELECT * FROM userImage where id="+u.getId();
                    Cursor res = db.executeQuery(req);
                    while(res.next()){
            Row r = res.getRow();
            user = new User();
            user.setId(r.getInteger(0));
            user.setPhoto(r.getString(1));
        }
                   res.close();
                    db.close(); 
                } catch (IOException ex) {
                    System.out.println("4");
                    System.out.println(ex.getMessage());
                }
        return user;
    }
    public void createOrUpdateTempImageStorage(User u){
        User user = checkTempImageStorage(u);
        if (user==null)
            insertTempImageStorage(u);
        else
            updateTempImageStorage(u);
    }
    public void deleteTempImageStorage(){
        System.out.println("Trunkating all table userImage in sqlLite");
        try {
                    db = Database.openOrCreate("dbvelo");
                    String req ="DELETE FROM  userImage ";              
                    db.execute(req);
                    db.close(); 
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
    }
    public void insertIfNotExistTempImageStorage(User u){
        User user = checkTempImageStorage(u);
        if (user==null)
        { //u.setPhoto("file://C:/wamp64/www/integrationvelo/web/uploads/"+u.getPhoto());
            u.setPhoto("file://C:/wamp64/www/integrationvelo/web/"+u.getPhoto());
            if(BaseForm.flagFacebook){
                System.out.println("flag facebbok on");
                u.setPhoto("file://C:/Users/USER/AppData/Local/Temp/temp9207021888993082913s..jpg");
            }
         //u.setPhoto("file://C:/Users/USER/AppData/Local/Temp/temp4007535237334755113s..jpg");
            insertTempImageStorage(u);
        }
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
                try{
                 t.setPhoto(obj.get("photo").toString());
                }catch(NullPointerException ex){
                    t.setPhoto("uploads/default.jpg");
                }
                users.add(t);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return users;
    }
}
