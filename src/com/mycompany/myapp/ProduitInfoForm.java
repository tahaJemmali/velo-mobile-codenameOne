/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.DefaultLookAndFeel;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.Images;
import entities.Panier;
import entities.Produit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import services.ServiceImages;
import services.ServicePanier;
import utils.ImageList;
import utils.Statics;
/**
 *
 * @author USER
 */
public class ProduitInfoForm extends BaseForm {
    
    ArrayList<Images> images;
    Produit produit ;
    Container tabsFlow;
    List<Component> tabsFlowList ;
    List<RadioButton> RBList ;
    ImageList imodel ;
    public static ProduitInfoForm PIcurrent;
    Label lq;
    
    
    public ProduitInfoForm() {
        this(Resources.getGlobalResources());
    }
    
    public ProduitInfoForm(Resources resourceObjectInstance){    
    }
    
    public ProduitInfoForm(Resources resourceObjectInstance,Form f,Produit p){
  PIcurrent=this;
  RBList = new ArrayList<>();
  
  installSidemenu(resourceObjectInstance);
  getToolbar().addMaterialCommandToLeftBar("Retour", FontImage.MATERIAL_ARROW_BACK, e->f.showBack());
  produit=p;
  tabsFlowList= new ArrayList<>();
  this.setLayout(BoxLayout.y());

 Container SlideImage = new Container(new BorderLayout());

ArrayList<String> arli = new ArrayList<>();

for (Images i  :ServiceImages.getInstance().getAllImages(p) ){
        arli.add(i.getImage());
        /*RB */
        RadioButton rb = new RadioButton();
        rb.setEnabled(false);
        RBList.add(rb);
        rb.addActionListener(e->{imodel.setSelectedIndex(RBList.indexOf(rb));System.out.println("cliked");});
        /*RB */
    }
new ButtonGroup(RBList.toArray(new RadioButton[RBList.size()]));
imodel = new ImageList(arli.toArray(new String[arli.size()]));

ImageViewer iv = new ImageViewer(imodel.getItemAt(0));

iv.setImageList(imodel);
SlideImage.add(BorderLayout.CENTER,iv);

/*RB*/
Style ss = UIManager.getInstance().getComponentStyle("Button");
ss.setFgColor(0x000000);
FontImage radioEmptyImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_UNCHECKED, ss);
FontImage radioFullImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_CHECKED, ss);
((DefaultLookAndFeel)UIManager.getInstance().getLookAndFeel()).setRadioButtonImages(radioFullImage, radioEmptyImage, radioFullImage, radioEmptyImage);

RBList.get(0).setSelected(true);
Container tabsFlow = FlowLayout.encloseCenter(RBList.toArray(new RadioButton[RBList.size()]));
   tabsFlow.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   tabsFlow.getUnselectedStyle().setBackgroundGradientEndColor(0xFFFFFF);
   tabsFlow.getUnselectedStyle().setBackgroundGradientStartColor(0xFFFFFF);
/*RB*/

/**/
Container ContainsImages = new Container(BoxLayout.y());
ContainsImages.getAllStyles().setMargin(20,0,20,20);
//ContainsImages.getAllStyles().setBorder(Border.createLineBorder(0));
   ContainsImages.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   ContainsImages.getUnselectedStyle().setBackgroundGradientEndColor(0xFFFFFF);
   ContainsImages.getUnselectedStyle().setBackgroundGradientStartColor(0xFFFFFF);
ContainsImages.add(SlideImage);
ContainsImages.add(tabsFlow);
/**/

add(ContainsImages);

/* */
Container cPrix = new Container();
Style s = new Style();
Container icon = new Container();
FontImage img = FontImage.createMaterial(FontImage.MATERIAL_LOCAL_OFFER,s);
icon.add(img);
cPrix.addAll(icon,new Label("Prix: "+p.getPrice()+" Dt"));
   cPrix.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   cPrix.getUnselectedStyle().setBackgroundGradientEndColor(0xF8F8F8);
   cPrix.getUnselectedStyle().setBackgroundGradientStartColor(0xF8F8F8);
   cPrix.getAllStyles().setBorder(Border.createLineBorder(1,0xAAAAAA));
   cPrix.getAllStyles().setMargin(20,20,20,20);

add(cPrix);

/* */
//TextArea taQuantite = new TextArea();

Container cTitle  = new Container(BoxLayout.x());

Container cQuantiteR  = new Container(BoxLayout.xRight());
Container cQuantiteL  = new Container(BoxLayout.x());
Container cQuantite = new Container(BoxLayout.x());

Container ContainsTQ = new Container(BoxLayout.y());
    

Button btnMoins = new Button("-");
//Button AjouterPanier = new Button("Ajouter au Panier");
Font materialFont = FontImage.getMaterialDesignFont();
//int size = Display.getInstance().convertToPixels(1, true);
//materialFont = materialFont.derive(size, Font.STYLE_PLAIN);
//materialFont = materialFont.derive(size, FontImage.MATERIAL_ADD_SHOPPING_CART);
Button AjouterPanier = new Button("Ajouter");
//AjouterPanier.setIcon(FontImage.create("\uE162", AjouterPanier.getUnselectedStyle(), materialFont));
AjouterPanier.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_SHOPPING_CART,s,6));

Button btnPlus = new Button("+");

lq = new Label("1");
//lq.getAllStyles().setPadding(0,0,10,10);
lq.getAllStyles().setMarginBottom(20);
//lq.getAllStyles().setPaddingRight(5);

btnMoins.addActionListener(e->{ 
    int x=Integer.parseInt(lq.getText());
if(x>1)
{
    x--;
    lq.setText(String.valueOf(x));
}

});

btnPlus.addActionListener(e->{ 
    int x=Integer.parseInt(lq.getText());
    x++;
    lq.setText(String.valueOf(x));
});


btnMoins.getAllStyles().setBorder(Border.createLineBorder(1));
btnMoins.getAllStyles().setBackgroundType(BACKGROUND_NONE);
btnMoins.getAllStyles().setFgColor(0x000000);


btnPlus.getAllStyles().setBorder(Border.createLineBorder(1));
btnPlus.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
btnPlus.getAllStyles().setFgColor(0x000000);

ContainsTQ.getAllStyles().setBorder(Border.createLineBorder(1,0xAAAAAA));


   cQuantite.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   cQuantite.getUnselectedStyle().setBackgroundGradientEndColor(0xFFFFFF);
   cQuantite.getUnselectedStyle().setBackgroundGradientStartColor(0xFFFFFF);
   
   cTitle.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   cTitle.getUnselectedStyle().setBackgroundGradientEndColor(0xF8F8F8);
   cTitle.getUnselectedStyle().setBackgroundGradientStartColor(0xF8F8F8);
   
   
   btnMoins.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   btnMoins.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
   btnMoins.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);
   
   
   btnPlus.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   btnPlus.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
   btnPlus.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);
   
   
AjouterPanier.getAllStyles().setBorder(Border.createLineBorder(1));
AjouterPanier.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
AjouterPanier.getAllStyles().setFgColor(0x000000);
AjouterPanier.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
AjouterPanier.getUnselectedStyle().setBackgroundGradientEndColor(0xFAFAFA);
AjouterPanier.getUnselectedStyle().setBackgroundGradientStartColor(0xFAFAFA);
   
/**/
img = FontImage.createMaterial(FontImage.MATERIAL_ALL_INBOX,s);
Container icon2 = new Container();
icon2.add(img);
cTitle.addAll(icon2,new Label("Quantité "));


img = FontImage.createMaterial(FontImage.MATERIAL_ADD_SHOPPING_CART,s,10);
Container icon4 = new Container();
icon4.add(img);


cQuantiteR.addAll(btnMoins,lq,btnPlus);
cQuantiteL.addAll(/*icon4,*/AjouterPanier);

cQuantite.addAll(cQuantiteL,new Label("    "),cQuantiteR);
ContainsTQ.addAll(cTitle,cQuantite);

ContainsTQ.getAllStyles().setMargin(0,0,20,20);
add(ContainsTQ);

/**/

AjouterPanier.addActionListener(e->{

ServicePanier.getInstance().addPanier(p,currentUser,Integer.parseInt(lq.getText()));
    
PanierForm pf = new PanierForm(resourceObjectInstance,f);
pf.show();

});

/* */
Container cTitleD  = new Container(BoxLayout.x());
   cTitleD.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   cTitleD.getUnselectedStyle().setBackgroundGradientEndColor(0xF8F8F8);
   cTitleD.getUnselectedStyle().setBackgroundGradientStartColor(0xF8F8F8);
  
    img = FontImage.createMaterial(FontImage.MATERIAL_INFO,s);
    Container icon3 = new Container();
    icon3.add(img);
    cTitleD.addAll(icon3,new Label("Description "));
Container cDescription = new Container(BoxLayout.y());


TextArea taDescription = new TextArea(p.getDescription());
   cDescription.addAll(cTitleD,taDescription);
   cDescription.getAllStyles().setMargin(20,0,20,20);
   cDescription.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
   cDescription.getUnselectedStyle().setBackgroundGradientEndColor(0xFFFFFF);
   cDescription.getUnselectedStyle().setBackgroundGradientStartColor(0xFFFFFF);
   cDescription.getAllStyles().setBorder(Border.createLineBorder(1,0xAAAAAA));

add(cDescription);
/* */
    }
    
    public void setRBButton(int p){
        RBList.get(p).setSelected(true);
    }

}
