
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CommentedCodeManager {
    
    private final ObservableList<String> commentedCodeList = FXCollections.observableArrayList();

    public ObservableList<String> getCommentedCodeList() {
        return commentedCodeList;
    }
    
    
    
    
    
    private boolean findCommentedCodeInString (String codeLines){
        boolean commentedCodeFound = false;
        
        if(codeLines.startsWith("!")){
            if(!codeLines.contains("//")){
                commentedCodeFound = true;
            }                              
        }
        
        return commentedCodeFound;
    }
    
    
    public void writeCommentedCodeList(String lineNumber, String codeLine){
        if(findCommentedCodeInString(codeLine)){
            commentedCodeList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }
    
}
