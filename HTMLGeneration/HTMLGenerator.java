/*
Author: Ramiro Collados
This class is responsible of writting the HTML output generatede by the program.
 */
package HTMLGeneration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


public class HTMLGenerator {
    // Attributes
    public static String htmlFilePath=null;
    
    private String filePath = null;
    private  ObservableList<String> codeList = FXCollections.observableArrayList();
    private ObservableList<String> tablesWritten = FXCollections.observableArrayList();
    private int errorListCounter=0;
    private  ListView<String> ListVariablesNotUsed = new ListView<>();
    private  ListView<String> TablesUsed = new ListView<>();
    private  ListView<String> MNamesList = new ListView<>();
    private  ListView<String> CallUBEAPPList = new ListView<>();
    private  ListView<String> ParamNotPassedList = new ListView<>();
    private  ListView<String> FunctionsCalledList = new ListView<>();
    private  ListView<String> QuerisToRevList = new ListView<>();
    private  ListView<String> BSFNNotFound = new ListView<>();   
    private  ListView<String> VariablesNamingData = new ListView<>();
    private  ListView<String> ConditionsList = new ListView<>();
    private  ListView<String> CommentCodeList = new ListView<>();    
    private  ListView<String> PoorlyUsedVars = new ListView<>();
    private  ListView<String> ConstantsFound = new ListView<>();
    private  ListView<String> SetUserSelectionList = new ListView<>();
    private  ListView<String> ConditionsToRev = new ListView<>();
    private  ListView<String> duplicatedCodeList = new ListView<>();
    //Setters
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCodeList(ObservableList<String> codeList) {
        this.codeList = codeList;
    }

    public void setListVariablesNotUsed(ListView<String> ListVariablesNotUsed) {
        this.ListVariablesNotUsed = ListVariablesNotUsed;
        if(!this.ListVariablesNotUsed.getItems().isEmpty())
            errorListCounter++;
    }

    public void setTablesUsed(ListView<String> TablesUsed) {
        this.TablesUsed = TablesUsed;
    }

    public void setMNamesList(ListView<String> MNamesList) {
        this.MNamesList = MNamesList;
        if(!this.MNamesList.getItems().isEmpty())
            errorListCounter++;
    }

    public void setCallUBEAPPList(ListView<String> CallUBEAPPList) {
        this.CallUBEAPPList = CallUBEAPPList;
    }

    public void setParamNotPassedList(ListView<String> ParamNotPassedList) {
        this.ParamNotPassedList = ParamNotPassedList;
        if(!this.ParamNotPassedList.getItems().isEmpty())
            errorListCounter++;
    }

    public void setFunctionsCalledList(ListView<String> FunctionsCalledList) {
        this.FunctionsCalledList = FunctionsCalledList;
    }

    public void setQuerisToRevList(ListView<String> QuerisToRevList) {
        this.QuerisToRevList = QuerisToRevList;
        if(!this.QuerisToRevList.getItems().isEmpty())
            errorListCounter++;
    }

    public void setBSFNNotFound(ListView<String> BSFNNotFound) {
        this.BSFNNotFound = BSFNNotFound;
        if(!this.BSFNNotFound.getItems().isEmpty())
            errorListCounter++;
    }

    public void setVariablesNamingData(ListView<String> VariablesNamingData) {
        this.VariablesNamingData = VariablesNamingData;
        if(!this.VariablesNamingData.getItems().isEmpty())
            errorListCounter++;
    }

    public void setConditionsList(ListView<String> ConditionsList) {
        this.ConditionsList = ConditionsList;
    }

    public void setCommentCodeList(ListView<String> CommentCodeList) {
        this.CommentCodeList = CommentCodeList;
    }

    public void setPoorlyUsedVars(ListView<String> PoorlyUsedVars) {
        this.PoorlyUsedVars = PoorlyUsedVars;
        if(!this.PoorlyUsedVars.getItems().isEmpty())
            errorListCounter++;
    }

    public void setConstantsFound(ListView<String> ConstantsFound) {
        this.ConstantsFound = ConstantsFound;
    }

    public void setSetUserSelectionList(ListView<String> SetUserSelectionList) {
        this.SetUserSelectionList = SetUserSelectionList;
    }

    public void setConditionsToRev(ListView<String> ConditionsToRev) {
        this.ConditionsToRev = ConditionsToRev;
        if(!this.ConditionsToRev.getItems().isEmpty())
            errorListCounter++;
    }

    public void setDuplicatedCodeList(ListView<String> duplicatedCodeList) {
        this.duplicatedCodeList = duplicatedCodeList;
    }
    
   

     
    //Methods
    private String getHTMLfileTitle (){
        String fileTitle=null;
        String strippedCodeLine = null;
        
        for (String codeLine : codeList) {
            strippedCodeLine = codeLine.substring(codeLine.indexOf(" "));
            if(strippedCodeLine.contains("Listing of ER")){
                fileTitle = strippedCodeLine.substring(strippedCodeLine.lastIndexOf(":")+1);
                break;
            }
        }
        
        if(fileTitle==null){
            for (String codeLine : codeList) {
                strippedCodeLine = codeLine.substring(codeLine.indexOf(" "));
                if(strippedCodeLine.contains(":")){
                    fileTitle = strippedCodeLine.substring(strippedCodeLine.indexOf(":")+1);
                    break;
                }               
            }
        }
        
        fileTitle = fileTitle.replaceAll("[^a-zA-Z0-9\\s]", "");
        fileTitle = fileTitle.trim().replaceAll(" +", " ");
        
        return fileTitle;
        
    }
    
    private String getResultMessage(){
        String messageResult=null;
        
        if(errorListCounter>3){
            //error
            messageResult= "Code needs to be reviewed";
        }else{
            if(errorListCounter>=1){
                //Warning
                messageResult= "Code might have to be reviewed";
            }else{
                //All good
                messageResult= "No errors detected";
            }       
        }
        
        return messageResult;
    }
    
    private String getCSSStyleForResultMessage(){
        String style=null;
        
        if(errorListCounter>3){
            //error
            style= "errorResult";
        }else{
            if(errorListCounter>=1){
                //Warning
                style= "warningResult";
            }else{
                //All good
                style= "okResult";
            }       
        }
        
        return style;
    }
    
    private void separatorHTML(PrintWriter pw){
         pw.println("<span style=\"display:inline-block; width: 24px;\"></span>");
    }
    
    
    //Writers to HTML file
    private void writeHTMLMasterHeading(PrintWriter pw, String fileTitle){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        pw.println("<div id=\"masterHeading\">");
        pw.println("<h1>"+fileTitle+"</h1>");
        pw.println("<p>File generated by JDE Code Reviewer - "+ dateFormat.format(date) +"</p>");
        pw.println("</div>");
    }
    
    private void writeCSSFile (PrintWriter pw) throws FileNotFoundException, IOException{    
        InputStream in = getClass().getResourceAsStream("/StyleSheets/HTMLStyle.css"); 
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine=null;

        while ((strLine = br.readLine()) != null){ 
                pw.println(strLine);  
        }
        
        in.close();
        br.close();
    }
    
    private void writeHTMLHead(PrintWriter pw, String fileTitle) throws IOException{
        pw.println("<head>");

            pw.println("<title>"+fileTitle+"</title>");
     
            pw.println("<style>");
                writeCSSFile(pw);           
            pw.println("</style>");    

        pw.println("</head>");
    }
  
    private void writeLinksOfNavBar(PrintWriter pw){

        pw.println("<div class=\"navbar\">");
        
        pw.println("<a class=\"a-totop\" href=\"#masterHeading\">To Top</a>"); 
        

        if(errorListCounter>0){
             pw.println("<a class=\"a-message\">Errors</a>"); 
        }
        
        for (String tb : tablesWritten) {                      
            if(errorListCounter==0){
               pw.println("<a class=\"a-message\">Information</a>"); 
            }
            pw.println("<a class=\"a-list\" href=\"#"+tb+"\">"+tb+"</a>"); 
            errorListCounter--;
        }

        pw.println("</div>");
    }
    
    private void writeReviewResult(PrintWriter pw){
        String messageResult=null;
        String style=null;
        
        messageResult=getResultMessage();
        style=getCSSStyleForResultMessage();
        
//        pw.println("<div class=\""+style+"\">");
        pw.println("<h3 class=\""+style+"\">"+messageResult+"</h3>");
//        pw.println("</div>");
    }
    
    private void writeCodeList(PrintWriter pw){
        pw.println("<h2>Code:</h2>");
        pw.println("<hr>");
        pw.println("<div id=\"code\">");
        
        for (String codeLine : codeList) {
            pw.println("<pre>"+codeLine+"</pre>");
        }
        
        pw.println("</div>"); 
        pw.println("<hr>");
    }
    
    
    
    private void writeListsToTables(PrintWriter pw, ListView<String> list, String tableHeader){
    // This method will write tables of each list. The div tag will save the id of each table to use links later.
    String strippedCodeLine=null;
    String lineNumberText=null;

        if(!list.getItems().isEmpty()){
            pw.println("<div id=\""+tableHeader+"\">");
            pw.println("<table>");
            pw.println("<tr><th>"+tableHeader+"</th></tr>");

            for (int i = 0; i < list.getItems().size(); i++) {
                if(list.getItems().get(i).startsWith("Line")){
                    strippedCodeLine = list.getItems().get(i); 
                    lineNumberText = strippedCodeLine.substring(0, strippedCodeLine.indexOf(" ")).concat("     ");
                    strippedCodeLine=strippedCodeLine.substring(strippedCodeLine.indexOf(" "));

                    pw.println("<tr><td><pre id=\"tablepreformat\">"+lineNumberText+"</pre>"+strippedCodeLine+"</tr>");  
                } else{               
                    pw.println("<tr><td>"+list.getItems().get(i)+"</tr>");
                }
            }

            pw.println("</table>");
            pw.println("</div>");

            tablesWritten.add(tableHeader);
        }
    }
    
    private void writeErrorLists(PrintWriter pw){
        String tableHeader = null;
        
        if(errorListCounter>0){
            pw.println("<h2>Possible errors found:</h2>");
        
            tableHeader="Variables not used";
            writeListsToTables(pw, ListVariablesNotUsed, tableHeader);  
            separatorHTML(pw);
            tableHeader="Poorly used variables";
            writeListsToTables(pw, PoorlyUsedVars, tableHeader);
            separatorHTML(pw);
            tableHeader="Poorly named variables";
            writeListsToTables(pw, VariablesNamingData, tableHeader);
            separatorHTML(pw);
            tableHeader="Queries to review";
            writeListsToTables(pw, QuerisToRevList, tableHeader);
            separatorHTML(pw);
            tableHeader="Parameters not passed";
            writeListsToTables(pw, ParamNotPassedList , tableHeader);
            separatorHTML(pw);
            tableHeader="Missing Names found";
            writeListsToTables(pw, MNamesList, tableHeader);
            separatorHTML(pw);
            tableHeader="BSFN not found";
            writeListsToTables(pw, BSFNNotFound, tableHeader);
            separatorHTML(pw);
            tableHeader="Conditions to review";
            writeListsToTables(pw, ConditionsToRev, tableHeader);
            separatorHTML(pw);
        }
    }
    
    private void writeInfoLists(PrintWriter pw){
        String tableHeader=null;

        pw.println("<h2>Information:</h2>");

        tableHeader="Queries";
        writeListsToTables(pw, TablesUsed, tableHeader);
        separatorHTML(pw);  
        tableHeader="Functions";
        writeListsToTables(pw, FunctionsCalledList, tableHeader);
        separatorHTML(pw);
        tableHeader="Conditions";
        writeListsToTables(pw, ConditionsList , tableHeader);
        separatorHTML(pw);
        tableHeader="Duplicated Code";
        writeListsToTables(pw, duplicatedCodeList , tableHeader);
        separatorHTML(pw);
        tableHeader="Calls APPLs/UBEs";
        writeListsToTables(pw, CallUBEAPPList, tableHeader);
        separatorHTML(pw);
        tableHeader="Set User Sel";
        writeListsToTables(pw, SetUserSelectionList, tableHeader);
        separatorHTML(pw);
        tableHeader="Commented";
        writeListsToTables(pw, CommentCodeList, tableHeader);
        separatorHTML(pw);
        tableHeader="Constants";
        writeListsToTables(pw, ConstantsFound, tableHeader);
        separatorHTML(pw);
    }
    
  
    
    // MAIN METHOD
    public void  generateHTML () throws IOException{
        String fileTitle=getHTMLfileTitle();
        
        //Set physical file creation
        htmlFilePath=System.getProperty("user.home") + "\\Desktop\\JDECR-"+fileTitle+".html";
        FileWriter file = new FileWriter(htmlFilePath);
        PrintWriter  pw = new PrintWriter(file); 
        
        
        pw.println("<!DOCTYPE html>");
        pw.println("<html lang=\"en\" >");
        
        writeHTMLHead(pw, fileTitle);
        
        pw.println("<body>");      
            writeHTMLMasterHeading(pw, fileTitle); 
            writeReviewResult(pw);
            writeCodeList(pw);
            
            separatorHTML(pw);
            
            writeErrorLists(pw);            
            writeInfoLists(pw);
        
            writeLinksOfNavBar(pw);   
        pw.println( "</body>");
              
        pw.println("</html>");
        pw.close();
    }
     
}
