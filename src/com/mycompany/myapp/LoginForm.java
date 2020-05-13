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
import FacebookLogin.FacebookData;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.io.Preferences;
import com.codename1.io.Storage;
import com.codename1.notifications.LocalNotification;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;
import entities.User;
import java.io.IOException;
import java.util.List;
import javafx.scene.layout.Background;
import services.ServiceUser;
import utils.Statics;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class LoginForm extends Form {

    private LoginForm current;
    FacebookData fbd ;
    EncodedImage enc ;
        ImageViewer imgv ;
        Image img;
    public LoginForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    public LoginForm(Resources resourceObjectInstance) {
        
        //ServiceUser.getInstance().deleteTempImageStorage();
        //initGuiBuilderComponents(resourceObjectInstance);
        current = this;
        fbd = new FacebookData(resourceObjectInstance,current);
        setLayout(BoxLayout.yCenter());
        setTitle("Sign in");
        //installSidemenu(resourceObjectInstance);
        add(resourceObjectInstance.getImage("logoASFER.png"));
        Container cnt = new Container(BoxLayout.y());
        
        Label labelusername = new Label("Username");
        Label labelll = new Label(" ");
        Label labellll = new Label(" ");
        Label labelmdp = new Label("Password");
        TextField tfUsername = new TextField("Fahd","Username");
        TextField tfPassword = new TextField("jakiechan","Password",21,TextField.PASSWORD);
        Button btnLogin = new Button("Login");
        Button mdpOublie = new Button ("Mot de passe oublié ?");
        mdpOublie.setUIID("CenterLabelSmall");
        Button btnInscription = new Button ("S'inscrire");
        
        Button btnFacebook = new Button ("Se connecter avec facebook");
        labelusername.setAlignment(CENTER);
        labelmdp.setAlignment(CENTER);
        cnt.addAll(labelusername,tfUsername,labelmdp,tfPassword);
        
        mdpOublie.getAllStyles().setBorder(Border.createEmpty());
        mdpOublie.getAllStyles().setBackgroundType(BACKGROUND_NONE);
        mdpOublie.getAllStyles().setFgColor(10);
        mdpOublie.setAlignment(RIGHT);
        
        //btnInscription.getAllStyles().setBorder(Border.createEmpty());
        btnInscription.getAllStyles().setBackgroundType(BACKGROUND_NONE);
        btnInscription.getAllStyles().setFgColor(10);
        
        btnFacebook.getAllStyles().setBackgroundType(BACKGROUND_NONE);
        btnFacebook.getAllStyles().setFgColor(10);
        
        
        Container ctnbtn = new Container(BoxLayout.yBottom());
        //btnInscription.setAlignment(BOTTOM);
        btnInscription.setUIID("CalendarHourSelected");
        btnFacebook.setUIID("CalendarHourSelected");
        ctnbtn.addAll(btnFacebook,btnInscription);
        
/*
myComponent.getAllStyles().setBorder(Border.createEmpty());
myComponent.getAllStyles().setBackgroundType(BACKGROUND_NONE);
myComponent.getAllStyles().setBgTransparency(255);
myComponent.getAllStyles().setBgColor(myColor);
*/
        mdpOublie.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        mdpOublie.addActionListener(e->{new MdpOublieForm1(current).show();});
                
        this.addAll(cnt,mdpOublie,btnLogin,labelll,labellll,ctnbtn);
        
        btnInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new InscriptionForm(resourceObjectInstance,current).show();
            }
        });
        
        FacebookData Facebookuser = new FacebookData(resourceObjectInstance,current);
        Login fb = FacebookConnect.getInstance();
        btnFacebook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
        String clientId = "571715303461279";
        String redirectURI = "http://localhost/"; 
        String clientSecret = "99091b71c26aad52de9b776dfb9d352b";
        //facebookLogout();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        fbd.doLogin(fb, Facebookuser, true);
               
            }
        });
        
            btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                              if ((tfUsername.getText().length()==0)||(tfPassword.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        User t = new User(tfUsername.getText(), tfPassword.getText());
                        if( ServiceUser.getInstance().login(t))
                        {Dialog.show("Success","Connection accepted",new Command("OK"));
                        
                        
                        User user = new User();
                        BaseForm.flagFacebook=false;
                        BaseForm.currentUser=ServiceUser.getInstance().getUser();
                        AcceuilForm ac = new AcceuilForm(resourceObjectInstance);
                        ac.init(ServiceUser.getInstance().getUser(), current);
                        System.out.println(ServiceUser.getInstance().getUser());
                        ac.show();
                        //ac.revalidate();
                        //textfield  a 0
                        tfUsername.setText("");
                        tfPassword.setText("");
                        }
                        else
                            Dialog.show("ERROR", "Incorrecte", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
            }
        });
              
    /*
        try {
            enc = EncodedImage.create("/giphy.gif");
        } catch (IOException ex) {
        }
                String url = Statics.BASE_URL+"/uploads/default.jpg";
        img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        
            
        imgv = new ImageViewer(img);
        
        
        add(img);
        
Button closeButton = new Button("",img);
Style closeStyle = closeButton.getAllStyles();
closeStyle.setFgColor(0x000000);
closeStyle.setBgTransparency(BACKGROUND_NONE);
//closeStyle.setBackgroundType(BACKGROUND_NONE);

closeStyle.setBgTransparency(0);
closeStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
closeStyle.setPadding(3, 3, 3, 3);
closeStyle.setBorder(RoundBorder.create().shadowOpacity(100));
//FontImage.setMaterialIcon(closeButton, FontImage.MATERIAL_CLOSE);

//add(closeButton);
*/

    }

  public void test(int w,int l,String url){
      Image maskImage = Image.createImage(w, l);
    Graphics g = maskImage.getGraphics();
    g.setAntiAliased(true);
    g.setColor(0x000000);
    g.fillRect(0, 0, w, l);
    g.setColor(0xffffff);
    g.fillArc(0, 0, w, l, 0, 360);
    Object mask = maskImage.createMask();

    //CONNECT TO CLOUDINARY 
    /*com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(ObjectUtils.asMap(
            "cloud_name", "REMOVED",
            "api_key", "REMOVED",
            "api_secret", "REMOVED"));
    // Disable private CDN URLs as this doesn't seem to work with free accounts
    cloudinary.config.privateCdn = false;*/

    //CREATE IMAGE PLACEHOLDERS 
    Image placeholder = Image.createImage(w, l);
    EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);

    //DOWNLOAD IMAGE
   /* Image img2 = cloudinary.url()
            .type("fetch") // Says we are fetching an image
            .format("jpg") // We want it to be a jpg
            .transformation(
                    new Transformation()
                    .crop("thumb").gravity("faces")
                    .image(encImage, url);*/

        img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

    // Add the image to a label and place it on the form.
    //GetCircleImage(img2);
    Label label = new Label();
    img.applyMask(mask);   // If you remove this line , the image will no longer be displayed, I will only see a rounded white circle ! I am not sure what this is doing, it might be simply stalling the process until the image is downloaded? or maybe somehow calling repaint or revalidate
    label.setIcon( img.applyMask(mask));
  }

}
