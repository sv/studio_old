/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studio.utils;

import java.io.Closeable;
import javax.swing.SwingWorker;

/**
 *
 * @author svidyuk
 */
public abstract class CloseableSwingWorker<T,V> extends SwingWorker<T, V> implements Closeable {

}