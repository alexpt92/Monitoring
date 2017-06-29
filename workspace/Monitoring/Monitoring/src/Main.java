
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Maain {

	public static void main(String[] args) {
		try {
			// XMLReader erzeugen
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			//ZipFile f = new ZipFile("http://dblp.uni-trier.de/xml/dblp.xml.gz");
			
			
			// Pfad zur XML Datei
			FileReader reader = new FileReader(
					"/home/kiesant/Downloads/dblp.xml");
			InputSource inputSource = new InputSource(reader);

			// DTD kann optional übergeben werden
			inputSource.setSystemId("/home/kiesant/Downloads/dblp.dtd");

			// PersonenContentHandler wird übergeben
			xmlReader.setContentHandler(new ConfigHandler());

			// Parsen wird gestartet
			xmlReader.parse(inputSource);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}

class ConfigHandler extends DefaultHandler {
	// Map<String, Date> mapeins = new HashMap<String, Date>();
	Map<String, Date> mapzwei = new HashMap<String, Date>();
	String aktuellerStream;
	StringBuilder textContent = new StringBuilder();
	Map<String, Conf> finalmap = new HashMap<String, Conf>();
	
	
	int ohneMonat=0;
	int counter;
	int titlecounter;
	private static long time;

	public boolean insideStream = false, insideTitle = false,
			insideProceeding = false;
	// private ArrayList<Person> allePersonen = new ArrayList<Person>();
	private String currentValue;

	// private Person person;

	// Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
	// gespeichert
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (insideTitle && insideStream && insideProceeding) {
			currentValue = new String(ch, start, length);
			textContent.append(ch, start, length);

			String[] parts = currentValue.split(",");
			// String month = parts[6]; // 004
			// String year = parts[7];
			// int jahr= Integer.parseInt(year);
			// mapzwei.put(aktuellerStream, currentValue);
		}
	}

	// Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (qName.equals("proceedings")) {
			insideProceeding = true;

			if (atts.getValue("key") != null) {
				String key = atts.getValue("key");
				aktuellerStream = key;
				// System.out.print(aktuellerStream+" --> ");

			}

			if (Pattern.matches(("conf/.*/\\d\\d\\d\\d[a-zA-Z_0-9]*"), aktuellerStream)) {

				// System.out.print(aktuellerStream+" --> ");
				// System.out.println(textContent.toString());
				counter++;

				String[] tmpa;
				tmpa=aktuellerStream.split("/");
				String tmp=tmpa[0]+"/"+tmpa[1]+"/";
				
				Conf c = new Conf(new Date(),0);
				
				if(finalmap.isEmpty()){
					finalmap.put(tmp, c);
				}else{
					
						if(!finalmap.containsKey(tmp)){
							finalmap.put(tmp, c);
						}
					
					
				}
			    
				
				insideStream = true;

			}
		}

		if (qName == "title" && insideStream) {
			insideTitle = true;
			titlecounter++;
			
		}


	}

	// Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		
		if (qName.equals("title") && insideStream && insideProceeding) {
			insideTitle = false;
			insideProceeding = false;
			String text = textContent.toString();
			
			//pattern um zu gucken ob aktueller Steam das Jahr enthält
			Pattern regex =Pattern.compile("\\d\\d\\d\\d");
			Matcher regexMatcher = regex.matcher(aktuellerStream);
			
			if (aktuellerStream != null) {
				String map;
				Date zero=new Date(1900,0,15);
				
				if(text.contains("January")){
					map=text.substring(text.indexOf("January"));
					mapzwei.put(aktuellerStream, toDate(map));
					
				}else{
					
				if(text.contains("February")){
					map=text.substring(text.indexOf("February"));
					
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("March")){
					map=text.substring(text.indexOf("March"));
					
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("April")){
					map=text.substring(text.indexOf("April"));
				
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("May")){
					map=text.substring(text.indexOf("May"));
					
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("June")){
					map=text.substring(text.indexOf("June"));
					
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("July")){
					map=text.substring(text.indexOf("July"));
			
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("August")){
					map=text.substring(text.indexOf("August"));
			
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("September")){
					map=text.substring(text.indexOf("September"));
			
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("October")){
					map=text.substring(text.indexOf("October"));
			
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("November")){
					map=text.substring(text.indexOf("November"));
			
					mapzwei.put(aktuellerStream, toDate(map));
				}else{
					
				if(text.contains("December")){
					map=text.substring(text.indexOf("December"));
			
					mapzwei.put(aktuellerStream, toDate(map));
					// es gehen 1500 verloren --> Kein Monatsname?
				}
				else{
					ohneMonat++;
					if(regexMatcher.find()){
						int jahr = Integer.parseInt(aktuellerStream.substring(regexMatcher.start(),regexMatcher.start()+4));
						Date datum = new Date(jahr,0,15);
						mapzwei.put(aktuellerStream,datum) ;
					}else{
						mapzwei.put(aktuellerStream, zero);
					}
				
				}
				}}}}}}}}}}}
				
				
				
				
			}
			//System.out.println(text);
			textContent.delete(0, textContent.length());
		}
		if (insideStream
				&& (qName.equals("article") || qName.equals("inproceedings")
						|| qName.equals("proceedings") || qName.equals("book")
						|| qName.equals("incollection")
						|| qName.equals("phdthesis") || qName
							.equals("masterthesis"))) {
			if (qName.equals("proceedings")) {
				insideProceeding = false;
			}
			// streamName = "";
			insideStream = false;

		}
		
	}

	public Date toDate(String s){
		
		int tag=15;
		int monat=0;
		int jahr=1900;
		Pattern regex =Pattern.compile("\\d\\d\\d\\d");
		Matcher regexMatcher = regex.matcher(s);
		Pattern days=Pattern.compile("\\d\\d");
		Pattern oneday=Pattern.compile("\\d");
		Matcher daysMatcher=days.matcher(s);
		Matcher onedayMatcher=oneday.matcher(s);

		//Berechnung des Monats
		if(s.contains("January")){
			monat=0;
			
		}else{
			
		if(s.contains("February")){
			monat=1;
		}else{
			
		if(s.contains("March")){
			monat=2;
		}else{
			
		if(s.contains("April")){
			monat=3;
		}else{
			
		if(s.contains("May")){
			monat=4;
		}else{
			
		if(s.contains("June")){
			monat=5;
		}else{
			
		if(s.contains("July")){
			monat=6;
		}else{
			
		if(s.contains("August")){
			monat=7;
		}else{
			
		if(s.contains("September")){
			monat=8;
		}else{
			
		if(s.contains("October")){
			monat=9;
		}else{
			
		if(s.contains("November")){
			monat=10;
		}else{
			
		if(s.contains("December")){
			monat=11;
			// es gehen 1500 verloren --> Kein Monatsname?
		}}}}}}}}}}}}
		// Berechnung des Tages

				// vorher gucken ob wirklich zahlen des Tages drinstehen
				

				 
					if(daysMatcher.find()){
						tag=Integer.parseInt(s.substring(daysMatcher.start(),daysMatcher.start()+2));
					}else{
						if(onedayMatcher.find()){
							tag=Integer.parseInt(s.substring(onedayMatcher.start(),onedayMatcher.start()+1));
						}
						
					}
					
				
				
				//
				// Berechnung des Jahres

				
				if(regexMatcher.find()){
				jahr =Integer.parseInt(s.substring(regexMatcher.start(),regexMatcher.start()+4));
				}		
				

				Date datum = new Date(jahr - 1900, monat, tag);

				return datum;
			}

	public void endDocument() throws SAXException {

		
		try {
            PrintStream ps;
            ps = new PrintStream(new File("MapZweiPrint.txt"));

        for (Entry<String, Date> entry : mapzwei.entrySet()) {
            ps.println(entry.getKey() + " ;" + entry.getValue());       
         
            }ps.close(); 
            }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		
		try {
            PrintStream ps;
            ps = new PrintStream(new File("FinalMapPrint.txt"));

        for (Entry<String, Conf> entry : finalmap.entrySet()) {
            ps.println(entry.getKey() + "; "+ entry.getValue().getDate() +"; " + entry.getValue().getYear());       
         
            }ps.close(); 
            }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		
        System.out.println("File printed.");

		
		
		System.out.println("Anzahl der Tags: " + counter);	
		System.out.println("Anzahl der titelTags: " + titlecounter);
		System.out.println(mapzwei.get("conf/mobihoc/2015mh"));
		System.out.println("end");
		System.out.println(mapzwei.size());
		System.out.println("Laufzeit: " + time + "ms");
		System.out.println("Titel ohne Monat"+ohneMonat);
		System.out.println(mapzwei.get("conf/padl/2012"));
		System.out.println(mapzwei.get("conf/its/2016"));
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public void startDocument() throws SAXException {
		System.out.println("Document starts.");

		time = System.currentTimeMillis();
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}
}
