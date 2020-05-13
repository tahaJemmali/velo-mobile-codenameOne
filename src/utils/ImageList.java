/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.EventDispatcher;
import com.mycompany.myapp.ProduitInfoForm;
import java.io.IOException;


/**
 *
 * @author USER
 */
public class ImageList implements ListModel<Image> {
final Style s = new Style();

 EncodedImage placeholder ;/*= EncodedImage.createFromImage(
        FontImage.createMaterial(FontImage.MATERIAL_SYNC, s).
                scaled(300, 300), false);*/

private int selection;
    private String[] imageURLs ;/*= {
        "http://awoiaf.westeros.org/images/thumb/9/93/AGameOfThrones.jpg/300px-AGameOfThrones.jpg",
        "http://awoiaf.westeros.org/images/thumb/3/39/AClashOfKings.jpg/300px-AClashOfKings.jpg",
        "http://awoiaf.westeros.org/images/thumb/2/24/AStormOfSwords.jpg/300px-AStormOfSwords.jpg",
        "http://awoiaf.westeros.org/images/thumb/a/a3/AFeastForCrows.jpg/300px-AFeastForCrows.jpg",
        "http://awoiaf.westeros.org/images/7/79/ADanceWithDragons.jpg"
    };*/
    private Image[] images;
    private EventDispatcher listeners = new EventDispatcher();

    public ImageList(String[] imageURLs) {
    try {
        placeholder = EncodedImage.create("/giphy.gif");
          int mm = Display.getInstance().convertToPixels(7);
        placeholder = EncodedImage.createFromImage(Image.createImage(mm * 7, mm * 7, 0), true);
    } catch (IOException ex) {
        System.out.println("Err encodedimage");
    }
        this.imageURLs=imageURLs;
        this.images = new EncodedImage[imageURLs.length];
        
    }
    
 public Image getItemAt(final int index) {
     
        if(images[index] == null) {
            images[index] = placeholder;
            
            Util.downloadUrlToStorageInBackground(imageURLs[index], "list" + index, (e) -> {
                    try {
                        images[index] = EncodedImage.create(Storage.getInstance().createInputStream("list" + index));
                        listeners.fireDataChangeEvent(index, DataChangedListener.CHANGED);
                        
                    } catch(IOException err) {
                        err.printStackTrace();
                    }
            });
        }
        return images[index];
    }
  public int getSize() {
        return imageURLs.length;
    }

    public int getSelectedIndex() {
        ProduitInfoForm.PIcurrent.setRBButton(selection);
        return selection;
    }

    public void setSelectedIndex(int index) { 
        //System.out.println("2");
        selection = index;
    }

    public void addDataChangedListener(DataChangedListener l) {
        
        listeners.addListener(l);
    }

    public void removeDataChangedListener(DataChangedListener l) {
        listeners.removeListener(l);
    }

    public void addSelectionListener(SelectionListener l) {
       
    }

    public void removeSelectionListener(SelectionListener l) {
    }

    public void addItem(Image item) {
    }

    public void removeItem(int index) {
    }
    
   
    
}
