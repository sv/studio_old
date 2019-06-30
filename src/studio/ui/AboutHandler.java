package studio.ui;

import java.awt.desktop.AboutEvent;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitResponse;

public class AboutHandler implements java.awt.desktop.AboutHandler  {
    private Studio s;

    public AboutHandler(Studio s) {
        this.s = s;
    }

    public void about() {
        s.about();
    }

    @Override
    public void handleAbout(AboutEvent e) {
        s.about();
    }
}
