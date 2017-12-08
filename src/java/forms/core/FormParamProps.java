 
package forms.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Victor Deyneko <VADeyneko@gmail.com>
 * This is convinience class for setting and retrieving HTML form
 * params properties such as readonly, disabled, hidden
 */
public class FormParamProps {
    
    private Map<String, String[]> paramMap;

    public FormParamProps() {
        this.paramMap = new HashMap<>();
    }
    
    public void add(String paramName){
        String [] arr = {" "," "," "};          
        paramMap.put(paramName, arr);
    }
    
    public void setReadonly(String paramName, boolean flag){
        String [] arr = paramMap.get(paramName);
        arr[0] = flag ? "readonly" : "";
        paramMap.put(paramName, arr);
    }
    
   public void setDisabled(String paramName, boolean flag){
        String [] arr = paramMap.get(paramName);
        arr[1] = flag ? "disabled" : "";
        paramMap.put(paramName, arr);
    }
   
    public void setHidden(String paramName, boolean flag){
        String [] arr = paramMap.get(paramName);
        arr[2] = flag ? "hidden = \"true\"" : "";
        paramMap.put(paramName, arr);
    }
    
    public String getProps(String paramName){
        if(paramMap == null) return "";
         String [] arr = paramMap.get(paramName);
          if(arr == null) return "";
         return arr[0] + " " + arr[1]+ " " + arr[2];
    }
    
    public void setAllDisabled(boolean allFlag){
        for (String k : paramMap.keySet()){
               setDisabled(k, allFlag);
        }
    }
    
   public void setAllHidden(boolean allFlag){
        for (String k : paramMap.keySet()){
               setHidden(k, allFlag);
        }
    }
    
}
