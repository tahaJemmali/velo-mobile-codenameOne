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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import entities.Evenement;
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author tahtouh
 */
public class ServiceEvenement {

    public static ServiceEvenement instance = null;
    Database db;
    private ConnectionRequest req;
    public ArrayList<Evenement> events;
    private boolean particper = false;

    public static ServiceEvenement getInstance() {
        if (instance == null) {
            instance = new ServiceEvenement();
        }
        return instance;
    }

    public ServiceEvenement() {
        req = new ConnectionRequest();
    }

    public Evenement getEvent(Date date) {
        Evenement ev = new Evenement();
        try {
            db = Database.openOrCreate("velo");
            Cursor result = db.executeQuery("Select * from evenement");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date);
            while (result.next()) {
                Row r = result.getRow();
                String thisD = r.getString(4);
                if (strDate.equals(thisD)) {
                    ev.setId(r.getInteger(0));
                    ev.setTitre(r.getString(1));
                    ev.setDescription(r.getString(2));
                    ev.setImage(r.getString(3));
                    ev.setDate_creation(r.getString(4));
                    ev.setDate_debut(r.getString(5));
                    ev.setLocation(r.getString(6));
                }
            }
            db.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return ev;
    }

    public void creatLDB() {
        try {
            db = Database.openOrCreate("velo");
            db.execute("Create table if not exists evenement"
                    + "(id INTEGER PRIMARY KEY"
                    + ",titre TEXT"
                    + ",description TEXT"
                    + ",image TEXT"
                    + ",date_debut TEXT"
                    + ",date_creation TEXT"
                    + ",localisation TEXT)"
            );
            db.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public boolean particper(String func, int id_event, int id_user) {

        String url = Statics.BASE_URL + "/" + func + "/" + id_event + "/" + id_user;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(new String(req.getResponseData()));
                if (new String(req.getResponseData()).equals("true")) {
                    particper = true;
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return particper;
    }

    public void update(Evenement e) {
        try {
            String updateQuery = "UPDATE evenement set "
                    + "titre ='" + e.getTitre() + "', "
                    + "description='" + e.getDescription() + "', "
                    + "image='" + e.getImage() + "',"
                    + "date_debut='" + e.getDate_debut() + "',"
                    + "date_creation='" + e.getDate_creation() + "',"
                    + "localisation='" + e.getLocation() + "'"
                    +"where id='" + e.getId() + "'";
            db.execute(updateQuery);
            db.close();
        } catch (IOException ex) {
 System.out.println("update error"+ex);
        }
    }

    public void ajouterEvenement(Evenement e) {
        try {
            db = Database.openOrCreate("velo");
            String insertQuery = "INSERT INTO evenement (id,titre,description,image,date_debut,date_creation,localisation) VALUES ("
                    + "'" + e.getId() + "', "
                    + "'" + e.getTitre() + "', "
                    + "'" + e.getDescription() + "', "
                    + "'" + e.getImage() + "',"
                    + "'" + e.getDate_debut() + "',"
                    + "'" + e.getDate_creation() + "',"
                    + "'" + e.getLocation() + "')";
            db.execute(insertQuery);
            db.close();
        } catch (IOException ex) {
     update(e);
        }
    }

    public void fetchEvenementDB() {
        events = getAllEvenements();
        creatLDB();
        for (Evenement e : events) {
            ajouterEvenement(e);
        }
    }

    public ArrayList<Evenement> parseEvenements(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Evenement t = new Evenement();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                t.setDescription(obj.get("description").toString());
                t.setDate_creation(obj.get("date_creation").toString());
                t.setDate_debut(obj.get("date_debut").toString());
                t.setImage(obj.get("image").toString());
                t.setTitre(obj.get("titre").toString());
                t.setLocation(obj.get("localisation").toString());
                events.add(t);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return events;
    }

    public ArrayList<Evenement> getAllEvenements() {
        String url = Statics.BASE_URL + "/events";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvenements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    
   
}
