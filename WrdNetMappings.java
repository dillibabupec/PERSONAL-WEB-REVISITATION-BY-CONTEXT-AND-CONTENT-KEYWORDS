package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author gts
 */
public class WrdNetMappings {
HashMap hmWrdMap=new HashMap();
           HashMap domWrdMap=new HashMap();
    public WrdNetMappings()
    {

       try
        {
           String strLine="";
           
            FileInputStream fstream = new FileInputStream("webapps/CollaborativeTagging/Nlp/wn21-20.noun");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null)
            {
                if(strLine!="")
                {
                    if(strLine.contains(" ") )
                    {
                        String[] wrdLine=strLine.split(" ");
                        hmWrdMap.put(wrdLine[0].trim(),wrdLine[1].trim());
                    }
                }
            }
          //  ReadLines(m,path);

            //System.out.println("hmWrdMap "+hmWrdMap.size());
              fstream = new FileInputStream("webapps/CollaborativeTagging/Nlp/wn-domains");
             br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null)
            {
                if(strLine!="")
                {
                        String key=strLine.substring(0,10);
                         String val=strLine.substring(10,strLine.length());
                        domWrdMap.put(key.trim(),val.trim());
                }
            }
             //System.out.println("domWrdMap "+domWrdMap.size());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
    }

   public String doMappingOf(String key)
           {
           String maptoAptKey=(String)hmWrdMap.get(key);
           //System.out.println(maptoAptKey+"-n");
           String DomforMappedKey=(String)domWrdMap.get(maptoAptKey+"-n");
               //System.out.println("Domain for Key is "+DomforMappedKey);
               return  DomforMappedKey;
   }

}
