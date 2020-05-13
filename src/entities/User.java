/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author USER
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    private int phone;
    private String roles;
    private String last_login;
    private int enabled;
    private String photo;
    private int score ;
    
    public User (){
    }
    
    public User (String username,String password){
        this.username=username;
        this.password=password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int id) {
        this.score = id;
    }
    public int getScore() {
       return this.score;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public String getRoles() {
        return roles;
    }

    public String getLast_login() {
        return last_login;
    }

    public int getEnabled() {
        return enabled;
    }

    public String getPhoto() {
        return photo;
    }
    
    @Override
    public String toString (){
        return "ID : "+id+" Username : "+username+" Password : "+password+" Email : "+email+" Address : "+address+" Phone : "
                +phone+" Roles : "+roles+" Last login : "+last_login+" Enabled : "+enabled+" Photo : "+photo;
    }
   
}
