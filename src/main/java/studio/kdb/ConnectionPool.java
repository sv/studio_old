package studio.kdb;

import studio.core.AuthenticationManager;
import studio.core.Credentials;
import studio.core.IAuthenticationMechanism;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.IOException;
import kx.c.K4Exception;

public class ConnectionPool {

    static class ConnectionPoolHolder{
        static ConnectionPool instance=new ConnectionPool();
    }

    private Map<String,List<kx.c>> freeMap = new HashMap<String,List<kx.c>>();
    private Map<String,List<kx.c>> busyMap = new HashMap<String,List<kx.c>>();

    private ConnectionPool() {
    }

    public synchronized void purge(Server s) {
        List<kx.c> list = freeMap.get(s.getName());

        if (list != null) {
            for(kx.c c:list)
                c.close();
        }

        busyMap.put(s.getName(),new LinkedList<kx.c>());

        if (list != null)
            list.clear();

    //    primeConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.instance;
    }

    public synchronized kx.c leaseConnection(Server s) // throws IOException, c.K4Exception
    {
        kx.c c = null;

        List<kx.c> list = freeMap.get(s.getName());
        List<kx.c> dead = new LinkedList<kx.c>();

        if (list != null) {
            Iterator i = list.iterator();
            while (i.hasNext()) {
                c = (kx.c) i.next();

                if (c.isClosed()) {
//                    i.remove();
                    dead.add(c);
                    c = null;
                }
                else
                    break;
            }
        }
        else {
            list = new LinkedList<kx.c>();
            freeMap.put(s.getName(),list);
        }

        list.removeAll(dead);

        if (c == null)
            try {
                Class clazz = AuthenticationManager.getInstance().lookup(s.getAuthenticationMechanism());
                if (clazz == null) {
                }
                IAuthenticationMechanism authenticationMechanism = (IAuthenticationMechanism) clazz.newInstance();

                authenticationMechanism.setProperties(s.getAsProperties());
                Credentials credentials = authenticationMechanism.getCredentials();
                if (credentials.getUsername().length() > 0) {
                    String p = credentials.getPassword();

                    c = new kx.c(s.getHost(),s.getPort(),credentials.getUsername() + ((p.length() == 0) ? "" : ":" + p));
                }
                else
                    c = new kx.c(s.getHost(),s.getPort(),"");
            }
            catch (IOException ex) {
            }
            catch (ClassNotFoundException ex) {
            }
            catch (InstantiationException ex) {
            }
            catch (IllegalAccessException ex) {
            }
            catch (NoSuchMethodException ex) {
            }
            catch (IllegalArgumentException ex) {
            }
            catch (InvocationTargetException ex) {
            }
        else
            list.remove(c);

        list = busyMap.get(s.getName());
        if (list == null) {
            list = new LinkedList<kx.c>();
            busyMap.put(s.getName(),list);
        }

        list.add(c);

        return c;
    }

    public synchronized void freeConnection(Server s,kx.c c) {
        if (c == null)
            return;

        List<kx.c> list = busyMap.get(s.getName());

        // If c not in our busy list it has been purged, so close it
        if (list != null)
            if (!list.remove(c))
                c.close();

        if (!c.isClosed()) {
            list = freeMap.get(s.getName());
            if (list == null)
                c.close();
            else
                list.add(c);
        }
    }

    public void checkConnected(kx.c c) throws IOException,K4Exception {
        if (c.isClosed())
            c.reconnect(true);
    }
}