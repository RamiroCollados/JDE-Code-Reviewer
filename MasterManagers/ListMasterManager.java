/*
Author: Ramiro Collados
This class will call all the list managers. Some methods will recieve just a codeline.
*/
package MasterManagers;

import FullCodeBasedListManagers.ConditionsToReviewManager;
import FullCodeBasedListManagers.DuplicatedCodeManager;
import LineByLineListManagers.SectionsCalledManager;
import LineByLineListManagers.QueriesInCodeManager;
import LineByLineListManagers.SetUserSelectionManager;
import LineByLineListManagers.FunctionsCalledManager;
import LineByLineListManagers.FormsCalledManager;
import LineByLineListManagers.ConditionsManager;
import LineByLineListManagers.QueriesToReviewManager;
import LineByLineListManagers.MissingNamesManager;
import LineByLineListManagers.BSFNNotFoundManager;
import LineByLineListManagers.ConstantsFoundManager;
import LineByLineListManagers.ReportsAndApplCallsManager;
import LineByLineListManagers.SectionsManager;
import LineByLineListManagers.ParametersNotPassedManager;
import LineByLineListManagers.ListedNERsManager;
import LineByLineListManagers.CommentedCodeManager;
import FullCodeBasedListManagers.VariablesNotUsedManager;
import FullCodeBasedListManagers.VariablesPoorlyNamedManager;
import FullCodeBasedListManagers.VariablesPoorlyUsedManager;
import LineByLineListManagers.FormsManager;
import LineByLineListManagers.RepeatedFunctionsCalledManager;
import Various.VariableFinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class ListMasterManager {
    
    private BSFNNotFoundManager bSFNnotFoundManager = new BSFNNotFoundManager();
    private CommentedCodeManager commentedCodeManager = new CommentedCodeManager();
    private ConditionsManager conditionsManager = new ConditionsManager();
    private ConstantsFoundManager constantsFoundManager = new ConstantsFoundManager();
    private FormsCalledManager formsCalledManager = new FormsCalledManager();
    private FormsManager formsManager = new FormsManager();
    private FunctionsCalledManager functionsCalledManager = new FunctionsCalledManager();
    private ListedNERsManager listedNERsManager = new ListedNERsManager();
    private MissingNamesManager missingNamesManager = new MissingNamesManager();
    private ParametersNotPassedManager parametersNotPassedManager = new ParametersNotPassedManager();
    private QueriesInCodeManager queriesInCodeManager = new QueriesInCodeManager();
    private QueriesToReviewManager queriesToReviewManager = new QueriesToReviewManager();
    private ReportsAndApplCallsManager reportsAndApplCallsManager = new ReportsAndApplCallsManager();
    private SectionsCalledManager sectionsCalledManager = new SectionsCalledManager();
    private SectionsManager sectionsManager = new SectionsManager();
    private SetUserSelectionManager setUserSelectionManager = new SetUserSelectionManager();
    private RepeatedFunctionsCalledManager repeatedFunctionsCalledManager = new RepeatedFunctionsCalledManager();
    
    private VariablesNotUsedManager variablesNotUsedManager = new VariablesNotUsedManager();
    private VariablesPoorlyNamedManager variablesPoorlyNamedManager = new VariablesPoorlyNamedManager();
    private VariablesPoorlyUsedManager variablesPoorlyUsedManager = new VariablesPoorlyUsedManager();
    private ConditionsToReviewManager conditionsToReviewManager = new ConditionsToReviewManager();
    private DuplicatedCodeManager duplicatedCodeManager = new DuplicatedCodeManager();
    

    public void writeLineByLineLists(String lineNumber, String codeLine){
        if(!codeLine.contains("// ")){
            if(!codeLine.contains("!")){
                bSFNnotFoundManager.writeBSFNNotFoundList(lineNumber, codeLine);
                conditionsManager.writeConditionsFoundInList(lineNumber, codeLine);
                constantsFoundManager.writeConstantFoundInList(lineNumber, codeLine);
                functionsCalledManager.writeFunctionsCalledList(lineNumber, codeLine);
                formsCalledManager.writeFormsCalledList(codeLine);
                formsManager.writeFormsInCodeList(lineNumber, codeLine);
                listedNERsManager.writeListedNERsList(lineNumber, codeLine);
                missingNamesManager.writeMissingNamesList(lineNumber, codeLine);
                parametersNotPassedManager.writeParametersNotPassedList(lineNumber, codeLine);
                queriesInCodeManager.writeQueriesInCodeList(lineNumber, codeLine);
                queriesToReviewManager.writeQueryToReviewList(lineNumber, codeLine);
                reportsAndApplCallsManager.writeReportsAndApplCalledList(lineNumber, codeLine);
                sectionsCalledManager.writeSectionsCalledList(codeLine);
                sectionsManager.writeSectionsInCodeList(codeLine);
                setUserSelectionManager.writeSetUserFoundInList(lineNumber, codeLine);
                
            }else{
                commentedCodeManager.writeCommentedCodeList(lineNumber, codeLine);   
            }  
        }    
    }
       
    public void writeFullCodeBasedManagerLists (ObservableList<String> codeList, ObservableList<String> codeListWithLineNumbers){
        VariableFinder variableFinder = new VariableFinder(codeList);
        
        ObservableList<String> variablesInCode = FXCollections.observableArrayList();
        variablesInCode = variableFinder.retrieveVariableList();
     
        variablesNotUsedManager.setVariablesFoundList(variablesInCode);
        variablesNotUsedManager.writeVariablesNotUsedList(codeList);
        
        variablesPoorlyNamedManager.setVariablesFoundList(variablesInCode);
        variablesPoorlyNamedManager.writePoorlyNamedVariablesList();
        
        variablesPoorlyUsedManager.setVariablesFoundList(variablesInCode);
        variablesPoorlyUsedManager.writeVariablesPoorlyUsedList(codeList);
        
        
        conditionsToReviewManager.setCodeList(codeList);
        conditionsToReviewManager.setSetUserSelectionsList(setUserSelectionManager.getSetUserSelectionList());
        conditionsToReviewManager.writeConditionsToReviewList();
        

        queriesToReviewManager.validateAuditFieldsInTables(queriesInCodeManager.getQueriesInCodeList(), codeListWithLineNumbers);
        queriesToReviewManager.validateOpenCloseInTables(codeList);
        
        
        repeatedFunctionsCalledManager.setFunctionsList(functionsCalledManager.getFunctionsCalledList());
        repeatedFunctionsCalledManager.writeRepeatedFunctionsList();
        
        
        duplicatedCodeManager.setCodeList(codeList);
        duplicatedCodeManager.writeDuplicatedCodeList();

    }
    
    
    
    
    
    
    
    
    public ObservableList<String> getBSFNnotFoundList(){
        return bSFNnotFoundManager.getbSFNNotFoundList();  
    }
    
    public ObservableList<String> getCommentedCodeList(){
        return commentedCodeManager.getCommentedCodeList();  
    }
    
    public ObservableList<String> getConditionsList(){
        return conditionsManager.getConditionsList();  
    }
    
    public ObservableList<String> getConstantsFoundList(){
        return constantsFoundManager.getConstantsFoundList();  
    }
    
    public ObservableList<String> getFormsCalledList(){
        return formsCalledManager.getFormsCalledList();  
    }
    
    public ObservableList<String> getFormsList(){
        return formsManager.getFormsList();  
    }
    
    public ObservableList<String> getFunctionsCalledList(){
        return functionsCalledManager.getFunctionsCalledList();  
    }
    
    public ObservableList<String> getListedNERsList(){
        return listedNERsManager.getListedNERsList();  
    }
    
    public ObservableList<String> getMissingNamesList(){
        return missingNamesManager.getMissingNamesList();  
    }
    
    public ObservableList<String> getParametersNotPassedList(){
        return parametersNotPassedManager.getParametersNotPassedList();  
    }
     
    public ObservableList<String> getQueriesInCodeList(){
        return queriesInCodeManager.getQueriesInCodeList();  
    }
    
    public ObservableList<String> getQueriesToReviewList(){
         return queriesToReviewManager.getQueriesToReviewList();  
    }
    
    public ObservableList<String> getReportsAndAppllCalledList(){
         return reportsAndApplCallsManager.getReportsAndAppllCalledList();  
    }
    
    public ObservableList<String> getSectionsCalledList(){
         return sectionsCalledManager.getSectionsCalledList();  
    }
    
    public ObservableList<String> getSectionsInCodeList(){
         return sectionsManager.getSectionsInCodeList();  
    }
    
    public ObservableList<String> getSetUserSelectionList(){
         return setUserSelectionManager.getSetUserSelectionList();  
    }
    
    public ObservableList<String> getVariablesPoorlyNamedList(){
        return variablesPoorlyNamedManager.getVariablesPoorlyNamedList();
    }
    
    public ObservableList<String> getVariablesNotUsedList(){
        return variablesNotUsedManager.getVariablesNotUsedList();
    }
    
    public ObservableList<String> getVariablesPoorlyUsedList(){
        return variablesPoorlyUsedManager.getVariablesPoorlyUsedList();
    }
    
    public ObservableList<String> getConditionsToReviewList(){
        return conditionsToReviewManager.getConditionsToReviewList();
    }
    
    public ObservableList<String> getRepeatedFunctionsList(){
        return repeatedFunctionsCalledManager.getRepeatedFunctionsList();
    }
    
    public ObservableList<String> getDuplicatedCodeList(){
        return duplicatedCodeManager.getDuplicatedCodeList();
    }
}
