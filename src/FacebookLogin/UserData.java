/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FacebookLogin;

/**
 *
 * @author USER
 */
interface UserData {
    public String getId();

    public String getEmail();

    public String getFirstName();

    public String getLastName();

    public String getImage();

    public void fetchData(String token, Runnable callback);
}
