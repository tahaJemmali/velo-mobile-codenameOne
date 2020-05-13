/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.Validator;
import entities.User;
import utils.ValidateInput;

/**
 *
 * @author USER
 */
public class InscriptionForm extends Form {
    private InscriptionForm current ;
    private Inscription2Form inscription2Form ;
    public User u = new User();
    Form lf ;
    //private boolean flag = false;
    
    public InscriptionForm(Resources resourceObjectInstance,Form loginForm) {
        current = this;
        lf=loginForm;
        inscription2Form = new Inscription2Form(resourceObjectInstance,current);
        
        setLayout(BoxLayout.yCenter());
        setTitle("Inscription");
        getToolbar().addMaterialCommandToLeftBar("Retour",FontImage.MATERIAL_ARROW_BACK, e-> loginForm.showBack());
        
        //installSidemenu(resourceObjectInstance);
        Container cnt = new Container(BoxLayout.y());
        
        Label labelusername = new Label("Username");
        Label labelEmail = new Label("Adresse E-mail");
        Label labelmdp = new Label("Password");
        Label labelmdp2 = new Label("Confirm Password");
        Label labelEmpty = new Label(" ");

        
        TextField tfUsername = new TextField("","Username");
        TextField tfPassword = new TextField("","Password",21,TextField.PASSWORD);
        TextField tfEmail = new TextField("","E-mail");
        TextField tfPassword2 = new TextField("","Password",21,TextField.PASSWORD);
        
        //Button btnInscription = new Button("S'inscrire");


        cnt.addAll(labelusername,tfUsername,labelEmail,tfEmail,labelmdp,tfPassword,labelmdp2,tfPassword2,labelEmpty);
            
        this.addAll(cnt);
        
        Validator val = new Validator();
        
        val.addConstraint(tfUsername,ValidateInput.getInstance().usernameConstraint);
        val.addConstraint(tfPassword,ValidateInput.getInstance().constraintMdp);
        
        val.addConstraint(tfPassword2,new Constraint() {
            @Override
            public boolean isValid(Object value) {
                String v = String.valueOf(value);
                if (!v.equals(tfPassword.getText()))
                    return false;
                return true;
            }
            @Override
            public String getDefaultFailMessage() {
                return "Mot de passe invalide!";
            }
        });
        val.addConstraint(tfEmail,ValidateInput.getInstance().emailConstraint);
        
Button btncheck = new Button("Suivant");
            btncheck.addActionListener(e->{

            u.setUsername(tfUsername.getText());
            u.setEmail(tfEmail.getText());
            u.setPassword(tfPassword.getText());
            inscription2Form.show();
            });
            val.addSubmitButtons(btncheck);
            
            
        /*getToolbar().addMaterialCommandToRightBar("Suivant",FontImage.MATERIAL_ARROW_FORWARD, e->{
        });*/
        
        add(btncheck);
        
                }
}

/*
public Validator addSubmitButtons(Component... cmp) {
        boolean isV = isValid();
        for(Component c : cmp) {
            submitButtons.add(c);
            c.setEnabled(isV);
        }
        return this;
    }
*/