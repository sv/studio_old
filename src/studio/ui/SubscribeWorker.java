/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.ui;

import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import kx.c;
import kx.c.K4Exception;
import studio.kdb.ConnectionPool;
import studio.kdb.K.KBase;
import studio.kdb.K.KCharacterVector;
import studio.kdb.K.KList;
import studio.kdb.Server;
import studio.utils.CloseableSwingWorker;

/**
 *
 * @author svidyuk
 */
public class SubscribeWorker extends CloseableSwingWorker {

    private final c c;
    private final String tableName;
    private final QGrid grid;

    public SubscribeWorker(QGrid grid, kx.c c, String tableName) {
        this.grid = grid;
        this.c = c;
        this.tableName = tableName;
    }

    @Override
    protected Object doInBackground() throws Exception {
        try {
            Object r;
            while (!isCancelled()) {
                r = c.getResponse();
                System.out.println("recieved response");
                publish(r);
                System.out.println("cont listening");
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void process(List chunks) {
        for (Object next : chunks) {
            if (next instanceof KList) {
                KBase update = ((KList) next).at(2);
                grid.append(update);
                grid.getWa().resizeAllColumns();
            } else {
                System.out.println("error while processing result:" + next);
            }
        }
    }

    @Override
    protected void done() {
        if(isCancelled()) return;
        JOptionPane.showMessageDialog(null, "Error while subscribing table", "Subscribing " + tableName, JOptionPane.WARNING_MESSAGE);
    }

    public void usub(){
        try {
            c.k(new KCharacterVector(".u.del[`" + tableName + ";.z.w]"));
        } catch (K4Exception ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        this.cancel(true);
        if (c != null) {
            c.close();
            ConnectionPool.getInstance().freeConnection(new Server(), c);
        }
    }
}
