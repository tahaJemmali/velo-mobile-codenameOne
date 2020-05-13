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
import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;
import entities.User;
import java.util.List;
import javafx.scene.layout.Background;
import services.ServiceUser;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class LoginForm extends Form {

    private LoginForm current;
    
    public LoginForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    public LoginForm(Resources resourceObjectInstance) {
        //initGuiBuilderComponents(resourceObjectInstance);
        current = this;
        setLayout(BoxLayout.yCenter());
        setTitle("Sign in");
        //installSidemenu(resourceObjectInstance);
        Container cnt = new Container(BoxLayout.y());
        
        Label labelusername = new Label("Username");
        Label labelll = new Label(" ");
        Label labellll = new Label(" ");
        Label labelmdp = new Label("Password");
        TextField tfUsername = new TextField("taha","Username");
        TextField tfPassword = new TextField("123","Password",21,TextField.PASSWORD);
        Button btnLogin = new Button("Login");
        Button mdpOublie = new Button ("Mot de passe oublié ?");
        
        Button btnInscription = new Button ("S'inscrire");
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
        
        Container ctnbtn = new Container(BoxLayout.yBottom());
        //btnInscription.setAlignment(BOTTOM);
        ctnbtn.add(btnInscription);
        
/*
myComponent.getAllStyles().setBorder(Border.createEmpty());
myComponent.getAllStyles().setBackgroundType(BACKGROUND_NONE);
myComponent.getAllStyles().setBgTransparency(255);
myComponent.getAllStyles().setBgColor(myColor);
*/
        mdpOublie.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        
                
        this.addAll(cnt,mdpOublie,btnLogin,labelll,labellll,ctnbtn);
        
        btnInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new InscriptionForm(resourceObjectInstance,current).show();
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
                        BaseForm.currentUser=ServiceUser.getInstance().getUser();
                      
                        AcceuilForm ac = new AcceuilForm(resourceObjectInstance);
                        //EvenementForm ac = new EvenementForm(resourceObjectInstance);
                        ac.init(ServiceUser.getInstance().getUser(), current);
                        ac.show();
                        
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
    }


}
