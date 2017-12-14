/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

/**
 *
 * @author Victor Deyneko <VADeyneko@gmail.com>
 */
import forms.core.Convertable;
import forms.core.FormParamProps;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.AppSettings;
import static forms.core.Validation.*;
import helpers.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;

/**
 *
 * @author admin
 */
@Named
@RequestScoped
public class AppSettingsForm implements Convertable<AppSettings> {

    private static final ResourceBundle ERRORS, LABELS;

    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");
        LABELS = ResourceBundle.getBundle("resources.labels");
    }

    @Inject
    protected HttpServletRequest request;

    protected String dPeriodBegin;
    protected String dPeriodEnd;
    protected String maxDaysAllowed;
    protected String minDaysAllowed;
    
    private int minDays;
    private int maxDays;
    
    protected Long id;

    private FormParamProps paramProps;

    public HttpServletRequest getRequest() {
        return request;
    }

    @PostConstruct
    public void onPostConstruct() {
        dPeriodBegin = request.getParameter("dateBegin");
        dPeriodEnd = request.getParameter("dateEnd");
        maxDaysAllowed = request.getParameter("maxDaysAllowed");
        minDaysAllowed = request.getParameter("minDaysAllowed");        
    }

   

    public String getdPeriodBegin() {       
        return dPeriodBegin;
    }

    public java.sql.Date getValidateddPeriodBegin() {
        return getDate(dPeriodBegin);
    }

    public String getdPeriodEnd() {
        return dPeriodEnd;
    }

    public java.sql.Date getValidateddPeriodEnd() {
        return getDate(dPeriodEnd);
    }

    public int getMinDays() {
        return minDays;
    }

    public int getMaxDays() {
        return maxDays;
    }
    
    

    public void validate() throws ValidationException {
        assertNotEmpty(dPeriodBegin);
        assertNotEmpty(dPeriodEnd);
        assertNotEmpty(maxDaysAllowed);
        assertNotEmpty(minDaysAllowed);
        
        try{
           minDays = Integer.parseInt(minDaysAllowed);
           maxDays  = Integer.parseInt(maxDaysAllowed);
        } catch (NumberFormatException e){
             throw new ValidationException(ERRORS.getString("error.can-not-parse-integer"));
        }
        
        if (minDays < 0  || maxDays < 0 )
                throw new ValidationException(ERRORS.getString("error.value-should-be-positive"));  
        
        if (minDays > maxDays )
                throw new ValidationException(ERRORS.getString("error.min-max-error"));       
        
        assertDate(dPeriodBegin);
        assertDate(dPeriodEnd);
    }

    private java.sql.Date getDate(String textDate) throws ValidationException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        formatter.applyPattern("dd.MM.yyyy");

        try {

            java.util.Date utilDate = formatter.parse(textDate);

            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            return sqlDate;
        } catch (IllegalArgumentException | ParseException e) {
            throw new ValidationException(ERRORS.getString("error.validation.wrong-date"));
        }

    }

    public FormParamProps initParamProps() {
        if (paramProps == null) {
            paramProps = new FormParamProps();

            paramProps.add("dPeriodBegin");
            paramProps.add("dPeriodEnd");

            paramProps.add("date-range0-container");
        }

        return paramProps;
    }

    @Override
    public AppSettings convertTo(Class<AppSettings> cls) throws ValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
