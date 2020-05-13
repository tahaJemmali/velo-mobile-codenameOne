/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FacebookLogin;

import com.codename1.io.AccessToken;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.AcceuilForm;
import com.mycompany.myapp.BaseForm;
import com.mycompany.myapp.LoginForm;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import services.ServiceUser;

/**
 *
 * @author USER
 */
public class FacebookData implements UserData {
    String id;
    String email;
    String first_name;
    String last_name;
    String image;
    
    LoginForm f;
    Resources res ;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return first_name;
    }

    @Override
    public String getLastName() {
        return last_name;
    }

    @Override
    public String getImage() {
        return image;
    }

    public FacebookData(Resources res , LoginForm f){
        this.f=f;
        this.res=res;
    }
    @Override
    public void fetchData(String token, Runnable callback) {
        ConnectionRequest req = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                try {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> parsed = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
                    //User user = new User();
                    id = (String) parsed.get("id");
                    email = (String) parsed.get("email");
                    first_name = (String) parsed.get("first_name");
                    last_name = (String) parsed.get("last_name");
                    image = (String) ((Map) ((Map) parsed.get("picture")).get("data")).get("url").toString();
                        
                        /*user.setId(Integer.parseInt(id));
                        user.setEmail(email);
                        user.setUsername(first_name+"_"+id);
                        user.setPhoto(image);*/
                        
                        
                    //fahd.larayedh@gmail.com 

                } catch (Exception ex) {
                }
            }

            @Override
            protected void postResponse() {
                callback.run();
                
                        User user = new User();
                        //user.setId(Integer.parseInt(id.substring(0,6)));
                        
                        user.setEmail(email);
                        user.setUsername(first_name+"_"+id.substring(0,6));
                        user.setPhoto(image);
                        user.setPhone(000);
                        user.setAddress("Unknown");
                        user.setPassword("Unknown");
                        
                        //BaseForm.currentUser=user;
                        
                        System.out.println("Entring facebook : ");       
                        //System.out.println(ServiceUser.getInstance().CheckifEmailexists(user));
                        //Fahd_102136
                        // check if email user exsist
                        if (ServiceUser.getInstance().CheckifEmailexists(user)!=null)
                        {   System.out.println("email "+user.getEmail()+" exists !");
                            user = ServiceUser.getInstance().CheckifEmailexists(user);
                            user.setPassword("Unknown");
                        }
                        else{
                          System.out.println("email does not  exist .. creating new account for : "+user.getEmail());
                          ServiceUser.getInstance().addUserFromInscription(user);
                        }
                        System.out.println("check to login : "+ServiceUser.getInstance().login(user));
                        if(ServiceUser.getInstance().login(user))
                        {System.out.println("Logging in...");
                        Dialog.show("Success","Connection accepted",new Command("OK"));
                            System.out.println("setted fb flag to on");
                        BaseForm.flagFacebook=true;
                        BaseForm.currentUser=ServiceUser.getInstance().getUser();
                        AcceuilForm ac = new AcceuilForm(res);
                        ac.init(ServiceUser.getInstance().getUser(), f);
                        ac.show();
                        
                       /* AcceuilForm ac = new AcceuilForm(res);
                        ac.init(user,f);
                        ac.show();*/
                        }
            }

            @Override
            protected void handleErrorResponseCode(int code, String message) {
                if (code >= 400 && code <= 410) {
                    doLogin(FacebookConnect.getInstance(), FacebookData.this, true);
                    return;
                }
                super.handleErrorResponseCode(code, message);
            }
        };
        req.setPost(false);
        req.setUrl("https://graph.facebook.com/v2.10/me");
        req.addArgumentNoEncoding("access_token", token);
        req.addArgumentNoEncoding("fields", "id,email,first_name,last_name,picture.width(512).height(512)");
        NetworkManager.getInstance().addToQueue(req);
        
                        /*User user = new User();
                        
                        user.setId(Integer.parseInt(id));
                        user.setEmail(email);
                        user.setUsername(first_name+"_"+id);
                        user.setPhoto(image);
                        //BaseForm.currentUser=ServiceUser.getInstance().getUser(user);
                        BaseForm.currentUser=user;
                        AcceuilForm ac = new AcceuilForm(res);
                        ac.init(user,f);
                        ac.show();*/
        
    }
    
    public void doLogin(Login lg, UserData data, boolean forceLogin) {
    if (!forceLogin) {
        if (lg.isUserLoggedIn()) {
            //process Facebook login with "data" here
            return;
        }

        String token = Preferences.get("token", (String) null);

        if (/*getToolbar() != null &&*/ token != null) {
            long tokenExpires = Preferences.get("tokenExpires", (long) -1);
            if (tokenExpires < 0 || tokenExpires > System.currentTimeMillis()) {
                data.fetchData(token, () -> {
                    //process Facebook login with "data" here
                });
                return;
            }
        }
    }

    lg.setCallback(new LoginCallback() {
        @Override
        public void loginFailed(String errorMessage) {
            Dialog.show("Error Logging In", "There was an error logging in with Facebook: " + errorMessage, "Ok", null);
        }

        @Override
        public void loginSuccessful() {
            data.fetchData(lg.getAccessToken().getToken(), () -> {
                Preferences.set("token", lg.getAccessToken().getToken());
                Preferences.set("tokenExpires", tokenExpirationInMillis(lg.getAccessToken()));
                //process Facebook login with "data" here
            });
        }
    });
    lg.doLogin();
}

    long tokenExpirationInMillis(AccessToken token) {
    String expires = token.getExpires();
    if (expires != null && expires.length() > 0) {
        try {
            long l = (long) (Float.parseFloat(expires) * 1000);
            return System.currentTimeMillis() + l;
        } catch (NumberFormatException ex) {
        }
    }
    return -1;
}
    

    
}
