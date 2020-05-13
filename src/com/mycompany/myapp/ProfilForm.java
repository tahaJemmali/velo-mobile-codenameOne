/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.Validator;
import static com.mycompany.myapp.BaseForm.currentUser;
import static com.mycompany.myapp.BaseForm.flagFacebook;
import entities.Panier;
import entities.User;
import java.io.IOException;
import services.ServicePanier;
import services.ServiceUser;
import utils.Statics;


/**
 *
 * @author USER
 */
public class ProfilForm extends BaseForm {
    

        Image imgg;
        String urll = Statics.BASE_URL+"/"+currentUser.getPhoto();
        EncodedImage placeholderr ;
        ProfilForm currentForm ;
        boolean flagImageFromStorage = false;
        String path;
        //File file;
       // private String imagesPath ="C:\\wamp64\\www\\integrationvelo\\web\\uploads\\" ;
       // "file://C:/wamp64/www/integrationvelo/web/uploads/"

    public ProfilForm() {
        this(Resources.getGlobalResources());
    }
    
    public ProfilForm(Resources resourceObjectInstance){
        installSidemenu(resourceObjectInstance);
        currentForm=this;
        ServiceUser.getInstance().createTempImageStorage();
        // try {
        //placeholderr = EncodedImage.create("/giphy.gif");
          int mm = Display.getInstance().convertToPixels(7);
        placeholderr = EncodedImage.createFromImage(Image.createImage(mm * 7, mm * 7, 0), true);
   // } catch (IOException ex) {
   //     System.out.println("Err encodedimage");
  //  }
         
        this.setLayout(BoxLayout.y());
        Container imageProfilContainer = new Container(new FlowLayout(Component.CENTER));
        Label picture = new Label("", "Container");
        imageProfilContainer.add(picture);
        
       
        if (flagFacebook)
            urll = currentUser.getPhoto();
        imgg = URLImage.createToStorage(placeholderr, urll, urll, URLImage.RESIZE_SCALE);
        
            try {
                initProfileImage(picture);
            } catch (IOException ex) {
            }
        
        
        
Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
Image camera = FontImage.createMaterial(FontImage.MATERIAL_SAVE, s);
getToolbar().addCommandToRightBar("", camera, (ev) -> {
    if (path!=null){
        User user = new User();
        user = currentUser;
        user.setPhoto(path);
        ServiceUser.getInstance().createOrUpdateTempImageStorage(user);
    }

    //redirect to same page
  
});

        add(new Label(" "));
        add(imageProfilContainer);
        
        Container editbtnContainer = new Container(new FlowLayout(Component.CENTER));
        Button editbtn = new Button ("edit image");

        editbtn.setUIID("Button");
editbtn.addActionListener(e->{
    try {
        int width = Display.getInstance().getDisplayWidth();
        width=562;
        path = Capture.capturePhoto(width, -1);
        //file= new File(path);
        if (path!=null){
        Image capturedImage = Image.createImage(path);      
        Image roundMask = Image.createImage(width, capturedImage.getHeight(), 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, width, width, 0, 360);
        Object mask = roundMask.createMask();
        capturedImage = capturedImage.applyMask(mask);
        picture.setIcon(capturedImage);
        currentForm.revalidate();
        System.out.println(path);
        //System.out.println(DeplaceFiles());
        }
    } catch(IOException err) {
        Log.e(err);
    }
});
/*Style ss = new Style();
ss.setBackgroundType(Style.BACKGROUND_NONE);
ss.setBackgroundGradientEndColor(Style.BACKGROUND_NONE);
ss.setBackgroundGradientStartColor(Style.BACKGROUND_NONE);*/

editbtn.getAllStyles().setPadding(0, 0, 0, 0);
//editbtn.getAllStyles().setBorder(Border.createLineBorder(1,Style.BACKGROUND_NONE));
editbtn.getAllStyles().setBackgroundType(Style.BACKGROUND_NONE);
editbtn.getAllStyles().setFgColor(0x000000);
editbtn.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_NONE);
//editbtn.getUnselectedStyle().setBackgroundGradientEndColor(0xAFAFAF);
//editbtn.getUnselectedStyle().setBackgroundGradientStartColor(0xAFAFAF);
//editbtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_EDIT,ss,4));
/*
editbtn.getAllStyles().setBorder(Border.createLineBorder(1));
editbtn.getAllStyles().setBackgroundType(BACKGROUND_NONE);
editbtn.getAllStyles().setFgColor(0x000000);
editbtn.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
editbtn.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
editbtn.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);*/

        editbtnContainer.add(editbtn);
        add(editbtnContainer);
        
        TextModeLayout tl = new TextModeLayout(4, 2);
        TextModeLayout t2 = new TextModeLayout(2, 1);

Container f = new Container(tl);
Container f2 = new Container(t2);


/*
TextComponent title = new TextComponent().label("Username");
TextComponent price = new TextComponent().label("Email");
TextComponent location = new TextComponent().label("Address");
PickerComponent date = PickerComponent.createDate(new Date()).label("Date");
TextComponent description = new TextComponent().label("Télephone").multiline(true);
*/
TextComponent username = new TextComponent().label("Username");
TextComponent email = new TextComponent().label("Email");
TextComponent address = new TextComponent().label("Address");
TextComponent phone = new TextComponent().label("Téléphone");

username.getField().setText(currentUser.getUsername());
email.getField().setText(currentUser.getEmail());
        //System.out.println(currentUser.getAddress());
address.getField().setText(currentUser.getAddress());
phone.getField().setText(String.valueOf(currentUser.getPhone()));

Validator val = new Validator();


TextComponent score = new TextComponent().label("Score");
score.getField().setText(String.valueOf(currentUser.getScore()));


//val.addConstraint(title, new LengthConstraint(2));
//val.addConstraint(price, new NumericConstraint(true));
f.addAll(username,email,address,phone);
f2.addAll(score);
/*f.add(tl.createConstraint().widthPercentage(100), title);
f.add(tl.createConstraint().widthPercentage(100), date);
f.add(location);
f.add(price);
f.add(tl.createConstraint().horizontalSpan(2), description);
f.setEditOnShow(title.getField());*/
        add(f);
        add(new Label(" "));
        add(f2);
    }
    
    public void initProfileImage(Label picture) throws IOException{
        //int width = Display.getInstance().getDisplayWidth();
        int width = Display.getInstance().getDisplayWidth();
        /*int mm = Display.getInstance().convertToPixels(7);
        placeholderr = EncodedImage.createFromImage(Image.createImage(mm * 7, mm * 7, 0), true);*/
        //Image capturedImage = Image.createImage(Capture.capturePhoto(width, -1));
        Image capturedImage  ;
       // Capture.ca
                //capturedImage = Image.createImage(Statics.BASE_URL+"/"+currentUser.getPhoto());
                
//imgg = Image.createImage("file://C:/Users/USER/AppData/Local/Temp/temp8139800281479792154s..jpg");
User u = new User();
u = currentUser;
if (ServiceUser.getInstance().checkTempImageStorage(u)!=null)
    imgg = Image.createImage(ServiceUser.getInstance().checkTempImageStorage(u).getPhoto());
 Image resizedImage = imgg.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() /2)); //change value as necessary

                capturedImage = resizedImage;
                width=capturedImage.getWidth();
                Image roundMask = Image.createImage(width, capturedImage.getHeight(), 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, width, width, 0, 360);
        Object mask = roundMask.createMask();
        capturedImage = capturedImage.applyMask(mask);
        picture.setIcon(capturedImage);
        this.revalidate(); 
    }
    
    public void getNewPhotoPath (String path){
        
    }
    /*
    public String DeplaceFiles() throws FileNotFoundException, IOException{
        String ext = null;
            String string = null;
            if (file!=null){
            //for (File file : files){
                //System.out.println(file.getAbsoluteFile());
                //System.out.println(file);
                
                  FileInputStream in = new FileInputStream(file);
                   ext = "."+FilenameUtils.getExtension(file.getName());
                   string = getAlphaNumericString();
                  FileOutputStream ou = new FileOutputStream(imagesPath+string+ext);         
                  //System.out.println("IMAGE FEL WAMP : "+imagesPath+string+ext);
                  //Images image = new Images();
                  //image.setImage("Products/"+string+ext);
                  //AjouterImageProduit(p,image);
                   //  u.setPhoto("uploads/"+string+ext);  
  BufferedInputStream bin = new BufferedInputStream(in);
  BufferedOutputStream bou = new BufferedOutputStream(ou);
  int b=0;
  while(b!=-1){
  b=bin.read();
  bou.write(b);
  }
  bin.close();
  bou.close();
        }
            
        return string+ext;
    }
        public String getAlphaNumericString() 
    { int n=30;
          String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        StringBuilder sb = new StringBuilder(n); 
        for (int i = 0; i < n; i++) { 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
              sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
        return sb.toString(); 
    }*/
    

}
