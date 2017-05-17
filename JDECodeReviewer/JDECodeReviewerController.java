//Author: Ramiro Collados

package JDECodeReviewer;

import HTMLGeneration.HTMLGenerator;
import Various.SystemClipboard;
import MasterManagers.FileProcessManager;
import MasterManagers.ListMasterManager;
import Various.FunctionsCalledListFilter;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.ToolTipManager;


public class JDECodeReviewerController implements Initializable {
    //Global variables
    private static String filePath;
    public static String line;
    public static boolean variableCodeScreen = false;

    private final Tooltip mousePositionToolTip = new Tooltip("");
    private ObservableList<String> functionsCalledCopy = FXCollections.observableArrayList();
    private ObservableList<String> codeLinesListWithLineNumbers = FXCollections.observableArrayList();
    private ObservableList<String> codeLinesList = FXCollections.observableArrayList();
    
    //FXML Variables
    @FXML
    public  TextField txtPathInput;
    @FXML
    private Button btnBrowse;
    @FXML
    private  ListView<String> ListVariablesNotUsed;
    @FXML 
    private ListView<String> TablesUsed;
    @FXML 
    private ListView<String> MNamesList;
    @FXML 
    private ListView<String> CallUBEAPPList;
    @FXML 
    private ListView<String> ParamNotPassedList;
    @FXML 
    private ListView<String> FunctionsCalledList;
    @FXML 
    private ListView<String> QuerisToRevList;
    @FXML  
    private ListView<String> BSFNNotFound;
    @FXML 
    private ListView<String> VariablesNamingData;
    @FXML 
    private ListView<String> ConditionsList;
    /* FIXME: Never used, review */
    @FXML
    private ComboBox<String> LinesComboBox;
    @FXML
    private ImageView StatusImage;
    @FXML 
    private ListView<String> CommentCodeList;
    @FXML 
    private ListView<String> Sections;
    @FXML 
    private ListView<String> SectionsCalled;
    @FXML 
    private ListView<String> Forms;
    @FXML 
    private ListView<String> ListedNERs;
    @FXML
    private Tab SectionsTab;
    @FXML
    private Tab FormsTab;
    @FXML
    private Tab NERsTab;
    @FXML 
    private ListView<String> FormsCalled;
    @FXML
    private Text numberOfForms;
    @FXML
    private Text numberOfFormsCalled;
    @FXML
    private Text numberOfSectionsCalled;
    @FXML
    private Text numberOfSections;
    @FXML  
    private ListView<String> DuplicityCallsList;
    @FXML
    private ListView<String> PoorlyUsedVars;
    @FXML
    public CheckBox CompareCheckbox;
    @FXML 
    private ListView<String> ConstantsFound;
    @FXML 
    private ListView<String> SetUserSelectionList;
    @FXML
    private Button btnExpCompare;
    @FXML
    private ListView<String> ConditionsToRev;
    @FXML
    private Button btnExportHTML;
    @FXML
    private Text txtCodeLines;
    @FXML
    private ListView<String> duplicatedCodeList;
    
    // Get Set methods
    public static void setFilePath(String filePath) {
        JDECodeReviewerController.filePath = filePath;
    }

    public static String getFilePath() {
        return filePath;
    }

    //Init//
    @Override
    public void initialize(URL url, ResourceBundle rb)  {

        ToolTipManager.sharedInstance().setEnabled(true);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(100);
  
        makeItGlow(btnBrowse);
       
        btnExpCompare.setDisable(true);
        btnExportHTML.setDisable(true);
    } 

    
    @FXML
    private void OpenFile(MouseEvent event) throws IOException {

        if(!txtPathInput.getText().isEmpty()){
            String command = "cmd /c start "+ txtPathInput.getText();

            //format for folders that have spaces
            command=command.replaceAll("\\\\", "\"\\\\\"");
            command=command.replaceFirst("\"", "");

            Runtime.getRuntime().exec(command);
        }
    }
      
    @FXML
    private void BrowseFileButton(ActionEvent event) throws IOException {

        /* BUG: stage is always null Review */
        Component stage = null;
        String userDir = System.getProperty("user.home");
        
        
        JFileChooser fileChooser = new JFileChooser(userDir +"/Desktop");
        fileChooser.setDialogTitle("Select the code");
        fileChooser.showOpenDialog(stage);
        
        try {
            File file = fileChooser.getSelectedFile();
            txtPathInput.setText(file.getAbsolutePath());
            filePath=file.getAbsolutePath();
        } catch (Exception ignored) {
        }    
        
        txtPathInput.setText(filePath);
        setResults();
    } 

    @FXML
    private void OpenCodeButton(KeyEvent event) throws Exception {
        JDECodeReviewerCodeScreen codeScreen = new JDECodeReviewerCodeScreen();
        Stage stage = new Stage();

        try {
            if(!line.isEmpty()){   
                if(event.getCode().toString().equals("SPACE")){
                    codeScreen.start(stage);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void OpenHelpScreen(MouseEvent event) throws Exception {
        JDECodeReviewerHelpScreen helpScreen = new JDECodeReviewerHelpScreen();
        Stage stage = new Stage();
        
        helpScreen.start(stage);
    }

    @FXML
    private void GenerateCompareExportFile(MouseEvent event) {
        btnExpCompare.setEffect(null);
        
        try {
            String filePath=System.getProperty("user.home") + "/Desktop";
            filePath=filePath+getFileNameFromDirectory(txtPathInput);
            filePath=filePath.replaceAll(".txt", "");
            filePath=filePath+" Export.txt";
            FileWriter file = new FileWriter(filePath);

            PrintWriter pw = new PrintWriter(file); 
            
            String previousLine=null;

            for (String aCodeLinesList : codeLinesList) {

                if (!aCodeLinesList.isEmpty()) {
                    if (!aCodeLinesList.equals(previousLine)) {
                        pw.println(aCodeLinesList);
                    }
                }

                previousLine = aCodeLinesList;
            }
            
            pw.close();
            
            Desktop.getDesktop().open(new File(filePath));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GenerateExportHTML(MouseEvent event) {
        //HTML GENERATION CALL
        HTMLGenerator HTMLGenerator = new HTMLGenerator();
        //set data to generate
        HTMLGenerator.setCodeList(codeLinesListWithLineNumbers);
        
        HTMLGenerator.setListVariablesNotUsed(ListVariablesNotUsed);
        HTMLGenerator.setParamNotPassedList(ParamNotPassedList);
        HTMLGenerator.setVariablesNamingData(VariablesNamingData);
        HTMLGenerator.setMNamesList(MNamesList);
        HTMLGenerator.setBSFNNotFound(BSFNNotFound);
        HTMLGenerator.setPoorlyUsedVars(PoorlyUsedVars);
        HTMLGenerator.setQuerisToRevList(QuerisToRevList);
        HTMLGenerator.setConditionsToRev(ConditionsToRev);
        HTMLGenerator.setConditionsList(ConditionsList);
        HTMLGenerator.setDuplicatedCodeList(duplicatedCodeList);
        HTMLGenerator.setCallUBEAPPList(CallUBEAPPList);
        HTMLGenerator.setTablesUsed(TablesUsed);
        HTMLGenerator.setFunctionsCalledList(FunctionsCalledList);
        HTMLGenerator.setSetUserSelectionList(SetUserSelectionList);
        HTMLGenerator.setConstantsFound(ConstantsFound);
        HTMLGenerator.setCommentCodeList(CommentCodeList);
        //....

        try {
            HTMLGenerator.generateHTML();
            openHTMLFile();
        } catch (Exception ignored) {
        } 
    }
    
    ////////////////////////////////////////////////////////////////////////
    //Saves to open portion of code depending on selected and show TOOLTIPS
    ////////////////////////////////////////////////////// //////////////
    
    @FXML
    private void SelectLineAndSave(MouseEvent event) throws IOException {
        String line = ConditionsList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);      
            showAndSetTooltip(ConditionsList); 
        }
    }
    
    @FXML
    private void SaveLineAndSaveMissingNames(MouseEvent event) throws IOException {
        String line = MNamesList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);      
            showAndSetTooltip(MNamesList); 
        }
    }

    @FXML
    private void SaveLineAndSaveBSFNNotFound(MouseEvent event) throws IOException {
        String line = BSFNNotFound.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);
            showAndSetTooltip(BSFNNotFound); 
        }
    } 
    
    @FXML
    private void SaveLineAndSaveBSFNUsed(MouseEvent event) throws IOException {  
        String line = FunctionsCalledList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);  
            showAndSetTooltip(FunctionsCalledList);     
        }
    }

    @FXML
    private void SaveLineAndSaveParamNotPassed(MouseEvent event) throws IOException {
        String line = ParamNotPassedList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);
            showAndSetTooltip(ParamNotPassedList);      
        }
    }
    
    @FXML
    private void SaveLineAndSaveCallsAPPLUBE(MouseEvent event) throws IOException {
        String line = CallUBEAPPList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);  
            showAndSetTooltip(CallUBEAPPList); 
        }
    }
    
    @FXML
    private void SaveLineAndSaveCommentedCode(MouseEvent event) throws IOException {
        String line = CommentCodeList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);  
            showAndSetTooltip(CommentCodeList);
        }
    }
    
    @FXML
    private void SelectLineAndSaveTables(MouseEvent event) throws IOException {
        String line = TablesUsed.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);
            showAndSetTooltip(TablesUsed);
        }
        
    }
    
    @FXML
    private void SelectLineAndSaveTablesRev(MouseEvent event) throws IOException {
        String line = QuerisToRevList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);  
            showAndSetTooltip(QuerisToRevList);
        }
    }
    
    @FXML
    private void SaveLineAndSaveConstantsCode(MouseEvent event) throws IOException {
        String line = ConstantsFound.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);       
            showAndSetTooltip(ConstantsFound);
        }
    }
    
    @FXML
    private void SelectLineAndSaveSerUsers(MouseEvent event) throws IOException {
        String line = SetUserSelectionList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);        
            showAndSetTooltip(SetUserSelectionList);
        }
    }
    
    @FXML
    private void saveLineAndSaveDuplicated(MouseEvent event) throws IOException {
        String line = duplicatedCodeList.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line =line;
        
        if(line != null){
            JDECodeReviewerCodeSceneController.setCodeLine(line);        
            showAndSetTooltip(duplicatedCodeList);
        }
    }

    
     //////////////////////////////////////////////////////////////////////////////// 
    
    @FXML
    private void VarNotUsedTooltip(MouseEvent event) throws IOException {
        if(!ListVariablesNotUsed.getItems().isEmpty() && ListVariablesNotUsed.getSelectionModel().getSelectedItem()!=null){
            showAndSetTooltipForVars(ListVariablesNotUsed);
        }     
    }
    
    @FXML
    private void VarBadNameTooltip(MouseEvent event) throws IOException {
        if(!VariablesNamingData.getItems().isEmpty() && VariablesNamingData.getSelectionModel().getSelectedItem()!=null){
            showAndSetTooltipForVars(VariablesNamingData);
        }
    }

    @FXML
    private void VarWrongUseTooltip(MouseEvent event) throws IOException {
        if(!PoorlyUsedVars.getItems().isEmpty()  && PoorlyUsedVars.getSelectionModel().getSelectedItem()!=null ){
            showAndSetTooltipForVars(PoorlyUsedVars);
        } 
    }
    
    // Drag and drop event
    @FXML
    private void DragDone(DragEvent event) throws IOException {
        
        txtPathInput.setText(filePath);
        setResults();
    }
     ////////////////////////////////////////////////////////////////////////////////
    
    
    //Copy Lines commands
    @FXML
    private void CopyLineVarNotUsed(KeyEvent event) {
        String line = ListVariablesNotUsed.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line=line;
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        ListVariablesNotUsed.setOnKeyReleased(e -> {        
            //If C is pressed copy line, if space is pressed call code screen and show where variable appears.
            if (e.getCode() == KeyCode.C) {
               SystemClipboard.copy(JDECodeReviewerController.line);
            }   
        });

        startNewCodeScreen(event);
        variableCodeScreen=false;
    }
    
    @FXML
    private void CopyLineVarPoorlyUsed(KeyEvent event) {
        String line = PoorlyUsedVars.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line=line;
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        PoorlyUsedVars.setOnKeyReleased(e -> {        
            //If C is pressed copy line, if space is pressed call code screen and show where variable appears.
            if (e.getCode() == KeyCode.C) {
               SystemClipboard.copy(JDECodeReviewerController.line);
            }   
        });

        startNewCodeScreen(event);
        variableCodeScreen=false;
    }
    
    @FXML
    private void CopyLineVarPoorlyNamed(KeyEvent event) {
        String line = VariablesNamingData.getSelectionModel().getSelectedItem();        
        JDECodeReviewerController.line=line;
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        VariablesNamingData.setOnKeyReleased(e -> {        
            //If C is pressed copy line, if space is pressed call code screen and show where variable appears.
            if (e.getCode() == KeyCode.C) {
               SystemClipboard.copy(JDECodeReviewerController.line);
            }   
        });

        startNewCodeScreen(event);
        variableCodeScreen=false;
    }

    private void startNewCodeScreen(KeyEvent event) {
        JDECodeReviewerCodeScreen codeScreen = new JDECodeReviewerCodeScreen();
        Stage stage = new Stage();

        try {
            if(!JDECodeReviewerController.line.isEmpty()){
                if(event.getCode().toString().equals("SPACE")){
                    variableCodeScreen=true;
                    codeScreen.start(stage);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void CopyLineConditions(KeyEvent event) {
        String line = ConditionsList.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
            
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
        
    }
    
    @FXML
    private void CopyLineTables(KeyEvent event){
        String line = TablesUsed.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }

    @FXML
    private void CopyLineTablesRev(KeyEvent event) {
        String line = QuerisToRevList.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }

    @FXML
    private void CopyLineFunctions(KeyEvent event) {
        String line = FunctionsCalledList.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }

    @FXML
    private void CopyLineParamNotPassed(KeyEvent event) {
        String line = ParamNotPassedList.getSelectionModel().getSelectedItem();
    
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
        
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }

    @FXML
    private void CopyLineCallUBEAPPL(KeyEvent event) {
        String line = CallUBEAPPList.getSelectionModel().getSelectedItem();
        
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
            
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }

    @FXML
    private void CopyLineCommented(KeyEvent event) {
        String line = CommentCodeList.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
            
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }
    
    @FXML
    private void CopyLineConstants(KeyEvent event) {
        String line = ConstantsFound.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
            
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }
    
    @FXML
    private void CopyLineSetUsers(KeyEvent event) {
        String line = SetUserSelectionList.getSelectionModel().getSelectedItem();
        
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
            
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }
    
    @FXML
    private void copyLineDuplicated(KeyEvent event) {
        String line = duplicatedCodeList.getSelectionModel().getSelectedItem();
       
        if(line != null){
            JDECodeReviewerController.line = line.substring(11).trim();
        }
            
        JDECodeReviewerCodeSceneController.setCodeLine(line);
        if(event.getCode().toString().equals("C")){
            SystemClipboard.copy(JDECodeReviewerController.line);
        }
    }
    
    
    @FXML 
    private void FunctionCallsFilterClick(MouseEvent event) {
        if(!DuplicityCallsList.getItems().isEmpty()){
            FunctionsCalledList.setItems(functionsCalledCopy);

            FunctionsCalledListFilter listFilter = new FunctionsCalledListFilter(FunctionsCalledList.getItems());

            FunctionsCalledList.setItems(listFilter.formatList(DuplicityCallsList.getSelectionModel().getSelectedItem())); 
       }
    } 
    
    @FXML
    private void CompCheckBoxClicked(MouseEvent event) {
        if(CompareCheckbox.isSelected()){
            makeItGlow(btnBrowse);
              
        }else{
            btnBrowse.setEffect(null);
        }     
    }
    
    

    
    private void openHTMLFile() throws IOException{
        if(!HTMLGenerator.htmlFilePath.isEmpty()){
            String command = "cmd /c start "+ HTMLGenerator.htmlFilePath;
            
           //format for folders that have spaces
            command=command.replaceAll("\\\\", "\"\\\\\"");
            command=command.replaceFirst("\"", "");

            Runtime.getRuntime().exec(command);
        }
    }
    
    private String[] getSectionAndEventOfLine(String line) {
        String [] retrievedData = {"","",""} ;
        char lineChar;
        StringBuilder lineNumber= new StringBuilder();
        int lineNumberInt;
        String bufferLine;
        
        
        for (int i = 5; i < 10; i++) {
            lineChar = line.charAt(i);
            if(Character.isDigit(lineChar)){
                lineNumber.append(lineChar);
            }    
        }
        lineNumberInt = Integer.parseInt(lineNumber.toString());
        
        for (int i = 1; i < lineNumberInt; i++) {
            bufferLine=codeLinesListWithLineNumbers.get(i);
            if(bufferLine.contains(line)){
                break;
            }else{
                if(bufferLine.contains("SECTION:") || bufferLine.contains("FORM:")){
                    retrievedData[0]= bufferLine;
                }
                if(bufferLine.contains("CONTROL:")){
                    retrievedData[1]= bufferLine;
                }
                if(bufferLine.contains("EVENT:")){
                    retrievedData[2]= bufferLine;
                }
            } 
        }     

        return retrievedData;
    }
    
    private String[] getVarEventSection(String line) {
        String[] retrievedData = {"",""} ;
        String bufferLine;
        
        
        for (int i = 1; i < codeLinesListWithLineNumbers.size(); i++) {
            bufferLine=codeLinesListWithLineNumbers.get(i);
            if(bufferLine.contains(line)){
                break;
            }else{
                if(line.startsWith("frm")){
                    if(bufferLine.contains("FORM:")){
                        retrievedData[0]= bufferLine;
                    }
                }else{
                    if(bufferLine.contains("SECTION:") || bufferLine.contains("CONTROL:")){
                        retrievedData[0]= bufferLine;
                    }
                }    
                if(bufferLine.contains("EVENT:")){
                    retrievedData[1]= bufferLine;
                }
            } 
        }     

        return retrievedData;
    }
    
    private void showAndSetTooltip(ListView<String> list) {
        //Tooltip EVENT AND SECTION
        String msg;
        String[] SectionEventData;
        if(!list.getItems().isEmpty()){
            SectionEventData=getSectionAndEventOfLine(list.getSelectionModel().getSelectedItem());
            //If not found do not show tooltip
            if(!SectionEventData[0].equals("")){
                //If Control found, format it
                if(!SectionEventData[1].equals("")){
                    msg=(SectionEventData[0]+"\n"+SectionEventData[1]+"\n"+SectionEventData[2]);
                }else{
                    msg=(SectionEventData[0]+"\n"+SectionEventData[2]);
                }
                mousePositionToolTip.setText(msg);
                list.setTooltip(mousePositionToolTip);
                if(SectionEventData[0].isEmpty()){
                    list.setTooltip(null);
                }
            }
        } 
    }
    
    private void showAndSetTooltipForVars(ListView<String> list) {
        //Tooltip EVENT AND SECTION
        String [] SectionEventData;
        String varPrefix= list.getSelectionModel().getSelectedItem();
        varPrefix=varPrefix.substring(0, 3);
        
        if(!varPrefix.equals("rpt")){
            if(!list.getItems().isEmpty()){   
                SectionEventData=getVarEventSection(list.getSelectionModel().getSelectedItem()); 
                //If Control found, format it
                String msg =(SectionEventData[0]+"\n"+SectionEventData[1]);

                mousePositionToolTip.setText(msg);
                list.setTooltip(mousePositionToolTip);     
                if(SectionEventData[0].isEmpty()){
                    list.setTooltip(null);
                }
            }
        }else{
//            mousePositionToolTip.setText("Global");
              list.setTooltip(null);
        } 
    }

    private void makeItGlow (Button button){
        final Glow glow = new Glow();
        glow.setLevel(0.7);
        button.setEffect(glow);

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(glow.levelProperty(), 0.4);
        final KeyFrame kf = new KeyFrame(Duration.millis(1200), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();        

    }
    
    private void growIcon (ImageView image){

        ScaleTransition trans = new ScaleTransition(Duration.millis(300), image);

        trans.setFromX(0.40);
        trans.setToX(1.2);
        trans.setFromY(0.40);
        trans.setToY(1.2);

        trans.setAutoReverse(true);

        trans.play();  

    }
    
    private String getFileNameFromDirectory (TextField txtPathInput){
        
        String fileName = txtPathInput.getText();
        int lastSlashIndex=fileName.lastIndexOf('\\');
  
            
        fileName=fileName.substring(lastSlashIndex, fileName.length());
        return fileName;
    }
    
    
    
   /////////////////////////////////////////////////////////////////////////////   
    
    private void setImageResultIcon(){
        int errorCounter = 0;
        
        //check how many errors found and set image icon
        if(!VariablesNamingData.getItems().isEmpty()){
            errorCounter++;
        }
        if(!BSFNNotFound.getItems().isEmpty()){
            errorCounter++;
        } 
        if(!ParamNotPassedList.getItems().isEmpty()){
            errorCounter++;
        } 
        if(!MNamesList.getItems().isEmpty()){
            errorCounter++;
        }
        if(!ListVariablesNotUsed.getItems().isEmpty()){
            errorCounter++;
        }
        if(!PoorlyUsedVars.getItems().isEmpty()){
            errorCounter++;
        }
        if(!QuerisToRevList.getItems().isEmpty()){
            errorCounter++;
        }
        if(!ConditionsToRev.getItems().isEmpty()){
            errorCounter++;
        }
        
        
        if(errorCounter>=4){
            StatusImage.setImage(new Image("/Images/error.png"));
        }else{
            if(errorCounter==0){
                 StatusImage.setImage(new Image("/Images/ok.jpg"));
            }else{
                 StatusImage.setImage(new Image("/Images/warning.png"));
            }
        }
            
        growIcon(StatusImage);
    }
    
    private void setTexts (){
        numberOfForms.setText(String.valueOf(Forms.getItems().size()));
        numberOfFormsCalled.setText(String.valueOf(FormsCalled.getItems().size()));
        numberOfSections.setText(String.valueOf(Sections.getItems().size()));
        numberOfSectionsCalled.setText(String.valueOf(SectionsCalled.getItems().size()));
        
        txtCodeLines.setText(String.valueOf(codeLinesList.size())+" code lines");          
    }
    
    private void setResults() throws IOException{
        SectionsTab.setDisable(false);
        FormsTab.setDisable(false);
        NERsTab.setDisable(false);
        btnBrowse.setEffect(null);  
        btnExpCompare.setDisable(true);
        
        ListMasterManager listMasterManager = new ListMasterManager();
        FileProcessManager fileProcessManager = new FileProcessManager(txtPathInput.getText(), listMasterManager);
        
        if(CompareCheckbox.isSelected()){
            fileProcessManager.initializeCodeListForCompare(codeLinesList);
            btnExpCompare.setDisable(false);
        }else{
            fileProcessManager.initializeCodeLists();
        }
        
        fileProcessManager.processListManagers();
        
        
        codeLinesListWithLineNumbers = fileProcessManager.getCodeListWithLineNumbers();
        codeLinesList = fileProcessManager.getCodeList();
        
        ListVariablesNotUsed.setItems(listMasterManager.getVariablesNotUsedList());
        TablesUsed.setItems(listMasterManager.getQueriesInCodeList());
        MNamesList.setItems(listMasterManager.getMissingNamesList());
        CallUBEAPPList.setItems(listMasterManager.getReportsAndAppllCalledList());
        ParamNotPassedList.setItems(listMasterManager.getParametersNotPassedList());        
        FunctionsCalledList.setItems(listMasterManager.getFunctionsCalledList());
        QuerisToRevList.setItems(listMasterManager.getQueriesToReviewList());
        BSFNNotFound.setItems(listMasterManager.getBSFNnotFoundList());
        VariablesNamingData.setItems(listMasterManager.getVariablesPoorlyNamedList());
        ConditionsList.setItems(listMasterManager.getConditionsList());
        CommentCodeList.setItems(listMasterManager.getCommentedCodeList());
        Sections.setItems(listMasterManager.getSectionsInCodeList());     
        Forms.setItems(listMasterManager.getFormsList());   
        ListedNERs.setItems(listMasterManager.getListedNERsList());
        SectionsCalled.setItems(listMasterManager.getSectionsCalledList());
        FormsCalled.setItems(listMasterManager.getFormsCalledList());      
        PoorlyUsedVars.setItems(listMasterManager.getVariablesPoorlyUsedList());
        ConstantsFound.setItems(listMasterManager.getConstantsFoundList());
        SetUserSelectionList.setItems(listMasterManager.getSetUserSelectionList());
        DuplicityCallsList.setItems(listMasterManager.getRepeatedFunctionsList());
        ConditionsToRev.setItems(listMasterManager.getConditionsToReviewList());
        duplicatedCodeList.setItems(listMasterManager.getDuplicatedCodeList());
        
        functionsCalledCopy = listMasterManager.getFunctionsCalledList(); //used to filter functions list
            
        //Dsiable tabs if necessary
        if(Sections.getItems().isEmpty()){
            SectionsTab.setDisable(true);
        }
        if(Forms.getItems().isEmpty()){
            FormsTab.setDisable(true);        
        }       
        if(ListedNERs.getItems().isEmpty()){
            NERsTab.setDisable(true);
        }   
        
        btnExportHTML.setDisable(false);
        
        setTexts();
        setImageResultIcon();
    }

    
}
    

   


