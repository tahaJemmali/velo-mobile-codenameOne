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

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import entities.Evenement;
import entities.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import services.ServiceEvenement;
import utils.Statics;
import com.codename1.progress.ArcProgress;
import com.codename1.progress.CircleFilledProgress;
import com.codename1.progress.CircleProgress;
import com.codename1.ui.Slider;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.util.UITimer;
//import java.util.concurrent.TimeUnit;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class EvenementForm extends BaseForm {

    private EvenementForm current;
    private Container events;
    private Resources resourceObjectInstance;
    private boolean lastDate;

    private boolean ok;
    final CircleProgress pr = new CircleProgress();
    static int i = 0;

    public EvenementForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    public EvenementForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        this.resourceObjectInstance = resourceObjectInstance;
                getToolbar().addMaterialCommandToRightBar("Classment",FontImage.MATERIAL_SCORE, e->{ 
               new RankFrom(resourceObjectInstance,current).show();
        });
        ServiceEvenement.getInstance().fetchEvenementDB();
        current = this;
        events = new Container();
        initGuiBuilderComponents(resourceObjectInstance);
        setLayout(BoxLayout.y());
        setScrollableY(true);
        getContentPane().setScrollVisible(false);
        getToolbar().setUIID("Container");
        Button b = new Button(" ");
        b.setUIID("Container");
        getToolbar().setTitleComponent(b);
        getTitleArea().setUIID("Container");
        installSidemenu(resourceObjectInstance);

        gui_Calendar_1.setTwoDigitMode(true);
        Picker p = new Picker();
        b.addActionListener(e -> {

            p.pressed();
            p.released();
            setBlue();
        });
        p.addActionListener(e -> {
            gui_Calendar_1.setCurrentDate(p.getDate());
            gui_Calendar_1.setSelectedDate(p.getDate());
            gui_Calendar_1.setDate(p.getDate());
            setBlue();
        });
        p.setFormatter(new SimpleDateFormat("MMMM"));
        p.setDate(new Date());
        p.setUIID("CalendarDateTitle");
        Container cnt = BoxLayout.encloseY(
                p,
                new Label(resourceObjectInstance.getImage("calendar-separator.png"), "CenterLabel")
        );
        BorderLayout bl = (BorderLayout) gui_Calendar_1.getLayout();
        Component combos = bl.getNorth();
        gui_Calendar_1.replace(combos, cnt, null);

        gui_Calendar_1.addActionListener(e -> {
            if (lastDate) {
                setBlue();
                lastDate = false;
            }
            this.removeComponent(events);
            events = new Container();
            Evenement ev = new Evenement();
            ev = ServiceEvenement.getInstance().getEvent(gui_Calendar_1.getDate());
            if (ev.getId() != -1) {
                lastDate = true;
                events = createEntry(ev.getId(), resourceObjectInstance, ServiceEvenement.getInstance().particper("participer", ev.getId(), BaseForm.currentUser.getId()), "10:15", "11:45", ev.getLocation(), ev.getTitre(), ev.getDescription(), Statics.BASE_URL + "/" + ev.getImage());
            } else {
                events.add(new Label("Aucun Evenement"));
            }
            add(events);
        });
    }


    private Container createEntry(int id, Resources res, boolean participer, String startTime, String endTime, String location, String title, String attendance, String... images) {
        ok = participer;
        Button time = new Button("Particper", "CalendarHourSelected");
        if (participer) {
            time.setText("Annuler");
            time.setUIID("CalendarHourUnselected");

        }

        time.addActionListener(l -> {
            if (ok) {
                ServiceEvenement.getInstance().particper("annuler", id, BaseForm.currentUser.getId());
                Dialog.show("Annuler", "Votre participation " + title + "  à est annulé.", new Command("Ok"));
                time.setUIID("CalendarHourSelected");
                time.setText("Particper");
                ok = false;
            } else {
                ServiceEvenement.getInstance().particper("ok", id, BaseForm.currentUser.getId());
                Dialog.show("Succès", "Vous participer à l'évenement " + title, new Command("Ok"));
                time.setUIID("CalendarHourUnselected");
                time.setText("Annuler");
                ok = true;
            }

        });

        Container circleBox = BoxLayout.encloseY(new Label(res.getImage("label_round-selected.png")),
                new Label("-", "OrangeLine"),
                new Label("-", "OrangeLine")
        );
        // Container cnt = new Container(BoxLayout.x());
        Container mainContent = BoxLayout.encloseY(
                BoxLayout.encloseX(
                        new Label(title, "SmallLabel"),
                        new Label(" ", "SmallLabel"),
                        new Label(startTime, "TinyThinLabel"),
                        new Label("-", "TinyThinLabel"),
                        new Label(endTime, "TinyThinLabel")),
                new Label(attendance, "TinyThinLabel")
        //  ,cnt
        );
        Container img = BoxLayout.encloseXCenter();
        EncodedImage enc = EncodedImage.createFromImage(resourceObjectInstance.getImage("unnamed.jpg"), true);
        for (String att : images) {
            img.add(URLImage.createToStorage(enc, "event", att));
        }

        Label redLabel = new Label("", "RedLabelRight");
        FontImage.setMaterialIcon(redLabel, FontImage.MATERIAL_LOCATION_ON);
        Container loc = BoxLayout.encloseY(
                redLabel,
                new Label("Location:", "TinyThinLabelRight"),
                new Label(location, "TinyBoldLabel")
        );


        mainContent = BorderLayout.center(mainContent).
                add(BorderLayout.WEST, FlowLayout.encloseCenter(circleBox)).
                add(BorderLayout.EAST, loc)
                .add(BorderLayout.SOUTH, img);

        return BorderLayout.center(mainContent)
                .add(BorderLayout.SOUTH, time);

    }

////-- DON'T EDIT BELOW THIS LINE!!!
    protected com.codename1.ui.Calendar gui_Calendar_1 = new com.codename1.ui.Calendar();
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.GridLayout(2, 1));
        setInlineStylesTheme(resourceObjectInstance);
        setInlineStylesTheme(resourceObjectInstance);
        setTitle("");
        setName("CalendarForm");
        gui_Calendar_1.setInlineStylesTheme(resourceObjectInstance);

        gui_Calendar_1.setName("Calendar_1");

        setBlue();

        addComponent(gui_Calendar_1);
    }// </editor-fold>

    public void setBlue() {
        lastDate = false;
        gui_Calendar_1.setMultipleSelectionEnabled(true);
        ArrayList<Date> selectedDays = new ArrayList();
        ArrayList<Evenement> list = ServiceEvenement.getInstance().getAllEvenements();

        for (Evenement ex : list) {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ex.getDate_debut());
                if (new Date().getTime() < date1.getTime()) {
                    selectedDays.add(date1);
                }

            } catch (ParseException exc) {
                System.out.println(exc);
            }
        }

        gui_Calendar_1.setSelectedDays(selectedDays, "taha");
        gui_Calendar_1.setMultipleSelectionEnabled(false);
    }

//-- DON'T EDIT ABOVE THIS LINE!!!
    @Override
    protected boolean isCurrentEvenement() {
        return true;
    }

    @Override
    protected void initGlobalToolbar() {
        setToolbar(new Toolbar(true));
    }

}
