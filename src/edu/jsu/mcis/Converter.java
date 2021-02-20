package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            
            //for three main types
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            
            
            //for passing
            JSONArray currentData = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            
            String[] record;
            //String jsonString = "";
            
            record = iterator.next();

            for(String thing : record){
                colHeaders.add((String)thing);
            }
            
            
            while (iterator.hasNext()){
                //jsonObject = new JSONObject();

                record = iterator.next();
                
                
                
                currentData = new JSONArray();
                for(int i = 0; i < record.length; i++){
                    
                    if (i == 0){
                        
                        rowHeaders.add(((String)record[0]));
                        //System.out.println(record[0]);
                    }
                    else{
                        currentData.add(Integer.parseInt(record[i]));
                    }
                }

                data.add(currentData);
                
            }
            jsonObject.put("colHeaders", colHeaders);
            jsonObject.put("rowHeaders", rowHeaders);
            jsonObject.put("data", data);
            
            
            
            results = jsonObject.toJSONString();
            //System.out.println(results);

            

        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject =(JSONObject)parser.parse(jsonString);
            
            
            JSONArray colHeaders = (JSONArray)jsonObject.get("colHeaders");
            //System.out.println(colHeaders.toString());
            JSONArray rowHeaders = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray data = (JSONArray)jsonObject.get("data");
            
            
            String[] lineOfData = new String[colHeaders.size()];
            JSONArray transferArray;
            
            for(int i = 0; i < colHeaders.size(); i++){
                lineOfData[i] = (String)colHeaders.get(i);
            }
            
            csvWriter.writeNext(lineOfData);
            
            
            //String[] lineOfData = new String[colHeaders.size()];
            
            //System.out.println(rowHeaders.size());
            
            
            for(int i = 0; i < data.size(); i++){
                
                lineOfData = new String[colHeaders.size()];
                //System.out.println(lineOfData.toString());
                //arrayOfData = new String[rowHeaders.size()-1];
                
                
                //csvWriter.writeNext(lineOfData);
                
                for(int j = 1; j < colHeaders.size(); j++){
                    
                    lineOfData[0] = (String)(rowHeaders.get(i));
                    //System.out.println("ErrorCheck");
                    transferArray = (JSONArray)data.get(i);
                    
                    //System.out.println(data.get(j-1));

                    
                    //System.out.println(j);
                    lineOfData[j] = transferArray.get(j-1).toString();
                    //System.out.println("ErrorCheck");
                    //System.out.println(j);
                    //System.out.println(i);
                }
                
                csvWriter.writeNext(lineOfData);
                //System.out.println(data.toString());
                //data
            }
            
            results = writer.toString();
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}