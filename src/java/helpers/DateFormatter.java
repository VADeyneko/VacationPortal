/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Victor Deyneko <VADeyneko@gmail.com>
 */
public  class DateFormatter {
    
    
    public static String formatDate(java.sql.Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String result = formatter.format(date);
        return result;
    }
    
     public static int daysBetween(Date d1, Date d2){
             return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
     }
    
}
