/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.RegexConstraint;

/**
 *
 * @author USER
 */
public class ValidateInput {
    public static ValidateInput instance = null;

    
    private ValidateInput() {
    }
    public static ValidateInput getInstance() {
        if (instance == null) {
            instance = new ValidateInput();
        }
        return instance;
    }

    public RegexConstraint emailConstraint = new RegexConstraint("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$", "Email Address invalide!");
    public RegexConstraint usernameConstraint = new RegexConstraint("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$", "Username invalide!");
    public RegexConstraint adressConstraint = new RegexConstraint("^[#.0-9a-zA-Z\\s,-]+$", "Adresse invalide!");
    public Constraint constraintMdp = new Constraint(){
       @Override
       public boolean isValid(Object value) {
           String v = (String) value;
            if (v.length()<8){
                return false;
            }  
            return true;       
       }
       @Override
       public String getDefaultFailMessage() {
            return "Mot de passe invalide";
       }
    };
    public Constraint constraintPhone = new Constraint(){
       @Override
       public boolean isValid(Object value) {
        String firstN = "952";
            String oN ="0123456789";
            String v = (String) value;
            String vi;
            if (v.length()!=8){
                return false;
            }  
            int test=0;
            for (int i=0;i<3;i++){
                if (v.substring(0,1).equals(String.valueOf(firstN.charAt(i))) && test !=1){
                    test=1;
                }
            }
            if (test==0){
                return false;
            }
            for (int j=1;j<v.length();j++){
                vi = String.valueOf(v.charAt(j));
                test=0;
            for (int i=0;i<10;i++){
                if (vi.equals(String.valueOf(oN.charAt(i))))
                {   
                    test=1;
                }      
            }
            if (test==0)
                {
                    return false;
                }
            }
            return true;       
       }
       @Override
       public String getDefaultFailMessage() {
            return "Numéro de téléphone invalide";
       }
    };
}
