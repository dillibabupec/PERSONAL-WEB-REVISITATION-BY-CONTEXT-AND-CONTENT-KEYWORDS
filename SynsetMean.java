package logic;

import java.io.FileInputStream;
import java.util.Vector;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

/*
Example code from Wicked Cool Java (No Starch Press)
Copyright (C) 2005 Brian D. Eubanks

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

Note: The LGPL licence can be found online at http://www.gnu.org

*/
/**
 * This class looks up word senses in JWordnet, and finds holonyms.
 */
public class SynsetMean {

	public String getDom(String wrd) throws JWNLException {
		configureJWordNet();
                Vector parentVec=new Vector();
		Dictionary dictionary = Dictionary.getInstance();
                IndexWordSet iws=dictionary.lookupAllIndexWords(wrd);
                IndexWord word=iws.getIndexWord(POS.NOUN);
                System.out.println("c"+word);
		//IndexWord word = dictionary.lookupIndexWord(POS.NOUN, wrd);
                if(word!=null)
                {
		Synset[] senses = word.getSenses();
		  Pointer[] holo = senses[0].getPointers(PointerType.HYPERNYM);
		  for (int j=0; j<holo.length; j++) {
		    Synset synset = (Synset) (holo[j].getTarget());
//		    Word synsetWord = synset.getWord(0);
//		    System.out.print("  -part-of-> " + synsetWord.getLemma());
                    Word[] wo=synset.getWords();
                  for(int jj=0;jj<wo.length;jj++)
                  {
                    //System.out.println(wo[jj].getLemma());
                        parentVec.add(wo[jj].getLemma());

                  }
		  }
                  String mappedDomain="";
                if(senses[0].getPOS().toString().equalsIgnoreCase("[POS: noun]"))
                {                   
                    StringBuffer sb=new StringBuffer();
                    sb.append(senses[0].getKey().toString());
                    while(sb.toString().length()!=8)
                    {
                       sb.insert(0, 0);
                    }
                    // System.out.println("abc "+sb.toString());
                    WrdNetMappings wnmp=new WrdNetMappings();
                     mappedDomain=wnmp.doMappingOf(sb.toString());
                }
                System.out.println("MappedDomain"+mappedDomain);
                if(mappedDomain==null)
                	mappedDomain="others";
                return parentVec.toString().trim()+"*"+mappedDomain;
            }
             else{
                return wrd.trim()+"*"+"Dom";
             }
	}

	public static void configureJWordNet() {
		// WARNING: This still does not work in Java 5!!!
		try {
			// initialize JWNL (this must be done before JWNL can be used)
			// See the JWordnet documentation for details on the properties file
			JWNL.initialize(new FileInputStream("webapps/CollaborativeTagging/Nlp/JWNLproperties.xml"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

}
