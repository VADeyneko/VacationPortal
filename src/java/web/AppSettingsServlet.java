/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.AppSettingsDao;
import forms.AppSettingsForm;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.AppSettings;
import web.core.AbstractServlet;
 
/**
 *
 * @author Victor Deyneko <VADeyneko@gmail.com>
 */
@WebServlet(name = "AppSettingsServlet", urlPatterns = {"/appSettings","/appSettings/save"})
public class AppSettingsServlet extends AbstractServlet {

    @Inject
    protected HttpServletRequest request;

    @Inject
    protected AppSettingsForm form;

    private String action;
 
    protected @EJB
    AppSettingsDao dao;
    
     protected   AppSettings settings;

    @Override
    protected void doGet() throws ServletException, IOException {
        action = request.getServletPath().substring(1);
        settings = dao.getSettingsInstance();
        request.setAttribute("appSettings", settings);
           forward("settings/appSettings");
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        
        
        try{
            form.validate();
            settings.setdPeriodBegin(form.getValidateddPeriodBegin());
            settings.setdPeriodEnd(form.getValidateddPeriodEnd());
            settings.setMaxDaysAllowed(form.getMaxDays());
            settings.setMinDaysAllowed(form.getMinDays());
            dao.update(settings);    
        }catch (ValidationException v){
             request.setAttribute("error", v.getLocalizedMessage());
             doGet();
        }
//        catch (Exception e){
//             request.setAttribute("error", e.getLocalizedMessage());
//             doGet();
//        }
         
         request.setAttribute("saved", true);
        doGet();
    }
}
