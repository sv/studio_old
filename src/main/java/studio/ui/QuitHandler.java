package studio.ui;

import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitResponse;

public class QuitHandler implements java.awt.desktop.QuitHandler {
    private Studio s;

    public QuitHandler(Studio s) {
        this.s = s;
    }

    public boolean quit() {
        return s.quit();
    }

    @Override
    public void handleQuitRequestWith(QuitEvent e, QuitResponse response) {
        s.quit();
    }
}
