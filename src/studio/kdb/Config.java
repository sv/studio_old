/* Studio for kdb+ by Charles Skelton
   is licensed under a Creative Commons Attribution-Noncommercial-Share Alike 3.0 Germany License
   http://creativecommons.org/licenses/by-nc-sa/3.0
   except for the netbeans components which retain their original copyright notice
*/

package studio.kdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.util.logging.Level;
import java.util.logging.Logger;
import studio.core.DefaultAuthenticationMechanism;
import java.io.*;
import java.util.*;
import java.util.List;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.awt.*;
import java.lang.reflect.Type;

public class Config
{

    static  class IConfig {
        private String encoding="UTF-8";
        private Font defaultFont= new Font("Monospaced", Font.PLAIN, 14);
        private String decimalFormat="#.#######";
        private Set<String> qkeywords;
        private Date licenseAccepted;
        private String lruServer;
        private String lookandfeel;
        private boolean subscriptionEnabled;
        private boolean dictAsTable;
        private Set<String> mrufiles=new HashSet<String>();
        private Map<String,Server> servers=new HashMap<String,Server>();
        private Map<String,Color> tokens=new HashMap<String,Color>();

        public Map<String, Color> getTokens() {
            return tokens;
        }

        public void setTokens(Map<String, Color> tokens) {
            this.tokens = tokens;
        }

        public String getDecimalFormat() {
            return decimalFormat;
        }

        public void setDecimalFormat(String decimalFormat) {
            this.decimalFormat = decimalFormat;
        }

        public Font getDefaultFont() {
            return defaultFont;
        }

        public void setDefaultFont(Font defaultFont) {
            this.defaultFont = defaultFont;
        }

        public boolean isDictAsTable() {
            return dictAsTable;
        }

        public void setDictAsTable(boolean dictAsTable) {
            this.dictAsTable = dictAsTable;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public Date getLicenseAccepted() {
            return licenseAccepted;
        }

        public void setLicenseAccepted(Date licenseAccepted) {
            this.licenseAccepted = licenseAccepted;
        }

        public String getLookandfeel() {
            return lookandfeel;
        }

        public void setLookandfeel(String lookandfeel) {
            this.lookandfeel = lookandfeel;
        }

        public String getLruServer() {
            return lruServer;
        }

        public void setLruServer(String lruServer) {
            this.lruServer = lruServer;
        }

        public Set<String> getMrufiles() {
            return mrufiles;
        }

        public void setMrufiles(Set<String> mrufiles) {
            this.mrufiles = mrufiles;
        }

        public Set<String> getQkeywords() {
            return qkeywords;
        }

        public void setQkeywords(Set<String> qkeywords) {
            this.qkeywords = qkeywords;
        }

        public Map<String,Server> getServers() {
            return servers;
        }

        public void setServers(Map<String,Server> servers) {
            this.servers = servers;
        }

        public boolean isSubscriptionEnabled() {
            return subscriptionEnabled;
        }

        public void setSubscriptionEnabled(boolean subscriptionEnabled) {
            this.subscriptionEnabled = subscriptionEnabled;
        }
    }

    class FontInstanceCreator implements InstanceCreator<Font> {

        public Font createInstance(Type type) {
            return new Font("Monospaced", Font.PLAIN, 14);
        }
    }
 
    class ColorSerializer implements JsonSerializer<Color>, JsonDeserializer<Color>, InstanceCreator<Color> {

        public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getRGB());
        }

        public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return Color.decode(json.getAsString());
        }
        public Color createInstance(Type type) {
            return Color.black;
        }
    }


    public static void main(String[] args){
        Config c=Config.getInstance();
        IConfig ic=new IConfig();
        ic.setEncoding(c.getEncoding());
        ic.setDefaultFont(c.getFont());
        ic.setDecimalFormat(((DecimalFormat)c.getNumberFormat()).toLocalizedPattern());
        ic.setQkeywords(new HashSet<String>(Arrays.asList(c.getQKeywords())));
        ic.setSubscriptionEnabled(c.isSubsciptionEnabled());
        ic.setDictAsTable(c.isDictAsTable());
        ic.setMrufiles(new HashSet<String>(Arrays.asList(c.getMRUFiles())));
        ic.setLicenseAccepted(c.getLicenseAcceptedDate());
        Map<String,Server> map=new HashMap<String, Server>();
        for(Server next:c.getServers()){
            map.put(next.getName(),next);
        }
        ic.setServers(map);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(ic));
    }
    public static String imageBase="/de/skelton/images/";
    public static String imageBase2="/de/skelton/utils/";
    private static String path;
    private static String filename="studio.txt";
    private static String absoluteFilename;
    private static String version="1.1";
    private static Config instance=new Config();
    private IConfig iconfig;

    private Config()
    {
        this.iconfig=init();
    }

    public Font getFont()
    {
        return iconfig.getDefaultFont();
    }

    public String getEncoding()
    {
        return iconfig.getEncoding();
    }

    public void setFont(Font f)
    {
        iconfig.setDefaultFont(f);
        save();
    }
    
    public Color getColorForToken(String tokenType, Color defaultColor)
    {
        Color c= iconfig.getTokens().get(tokenType);
        if (c==null) {
            c=defaultColor;
            setColorForToken(tokenType,c);
        }
        return c;
    }

    public void setColorForToken(String tokenType, Color c)
    {
        iconfig.getTokens().put(tokenType, c);
            save();
    }
    
    public synchronized NumberFormat getNumberFormat()
    {
        return new DecimalFormat(iconfig.getDecimalFormat());
    }

    public void setNumberFormat(String format){
       iconfig.setDecimalFormat(format);
        save();
    }

    public static Config getInstance()
    {
        if (instance == null)
        {
            instance = new Config();
        }

        return instance;
    }

    private IConfig init()
    {
        IConfig r=null;
        path= System.getProperties().getProperty( "user.home");

        path= path + "/.studioforkdb";

        File f= new File( path);

        if( ! f.exists())
        {
            if( !f.mkdir())
            {
                // error creating dir
            }
        }

        absoluteFilename= path + "/" + filename;

        String candidate= absoluteFilename;
        try {
            GsonBuilder gsb=new GsonBuilder();
            gsb.registerTypeAdapter(Font.class, new FontInstanceCreator());
            gsb.registerTypeAdapter(Color.class, new ColorSerializer());

            Gson gson=gsb.create();
            r = gson.fromJson(new FileReader(candidate), IConfig.class);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        if(r==null) r=new IConfig();
        return r;
    }


    public void save()
    {
         GsonBuilder gsb=new GsonBuilder().setPrettyPrinting();
         gsb.registerTypeAdapter(Color.class, new ColorSerializer());
        Gson gson = gsb.create();
            try {
                FileWriter f=new FileWriter(absoluteFilename);
                gson.toJson(iconfig, f);
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }

    public String[] getQKeywords()
    {
        return iconfig.getQkeywords().toArray(new String[0]);
    }

    public String getLRUServer()
    {
        return iconfig.getLruServer();
    }

    public void setLRUServer(Server s)
    {
        iconfig.setLruServer(s.getName());

            save();
       
    }


    public void saveQKeywords( String [] keywords)
    {
        iconfig.setQkeywords(new HashSet<String>(Arrays.asList(keywords)));

        save();
    }
    
    public void setAcceptedLicense(Date d)
    {
        iconfig.setLicenseAccepted(d);
        save();
    }
    public Date getLicenseAcceptedDate(){
        return iconfig.getLicenseAccepted();
    }
    public boolean getAcceptedLicense()
    {
        Date d=iconfig.getLicenseAccepted();
        if(d == null)
            return false;
        
        if(Lm.buildDate.after(d))
            return false;
        
        return true;
    }
    
    public int getOffset( Server server)
    {
        if( server != null)
        {
            String name= server.getName();
            Server [] servers= getServers();

            for( int i= 0; i < servers.length; i++)
            {
                if( name.equals( servers[i].getName()))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public String [] getMRUFiles()
    {
        return iconfig.getMrufiles().toArray(new String[0]);
    }


    public void saveMRUFiles( String [] mruFiles)
    {
        iconfig.setMrufiles(new HashSet<String>(Arrays.asList(mruFiles)));

        save();
    }

    public String getLookAndFeel()
    {        
        return iconfig.getLookandfeel();
    }

    public void setLookAndFeel( String lf)
    {
       iconfig.setLookandfeel(lf);
        save();
    }

        public boolean isSubsciptionEnabled() {
        
        return iconfig.isSubscriptionEnabled();
    }

    public void setSubscriptionEnabled(boolean sub) {
       iconfig.setSubscriptionEnabled(sub);
        save();
    }

    public boolean isDictAsTable() {

        return iconfig.isDictAsTable();
    }

    public void setDictAsTable(boolean dt) {
    iconfig.setDictAsTable(dt);

        save();
    }

    public Server getServer( String server)
    {
        return iconfig.getServers().get(server);
    }

    public String[] getServerNames()
    {
        return iconfig.getServers().keySet().toArray(new String[iconfig.getServers().size()]);
    }

    public Server[] getServers()
    {
        return iconfig.getServers().values().toArray(new Server[iconfig.getServers().size()]);
    }

    public void removeServer(Server server)
    {
        iconfig.getServers().remove(server.getName());
        save();
    }

    public void saveServer(Server server)
    {
        iconfig.getServers().put(server.getName(),server);
        save();
    }

  
    public void addServer(Server server)
    {
       iconfig.getServers().put(server.getName(), server);

        save();
    }

  
}
