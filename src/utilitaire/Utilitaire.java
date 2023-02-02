/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import annot.Datetime;
import annot.FkAnnotation;
import annot.PrimaryKeyAnnotation;
import annot.TableAnnotation;
import annot.TsyMiditraAnnotation;
import dao.ObjetBdd;
import exception.TsyChaineException;
import exception.TsyDatyException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utilitaire.UtilitaireAnnotation.*;


/**
 *
 * @author caeyla
 */
public class Utilitaire {
  
    public static <L> String getFieldValue(L ob,Field fd) throws Exception {
        Method md = null;
        String value = null;
            if(isFk(fd)){
                //ILAY FK AO ANATY CLASS METY TSY MITOVY ANARANA AMIN"ILAY PK ANY TABLE ORIGINE 
                //ALAINA ALOHA LE OBJET FK   
                md=ob.getClass().getMethod("get" + upperFirst(fd.getName()));
                ObjetBdd ob2=(ObjetBdd) md.invoke(ob);
                if(ob2==null){
                    return null;
                }else{
                //ZAY VAO ALAINA LE AO ANATINY PAR ANNOTATION NY ANARANY                
                    fd=ob2.getClass().getDeclaredField(getFieldNameByAnnotation(fd));
                    return value =getFieldValue(ob2,fd);
                }
            }
            else{  
                md=ob.getClass().getMethod("get" + upperFirst(getFieldNameByAnnotation(fd)));
//                System.out.println("--------------------INFO GET FIELD VALUE----------------");
//                System.out.println(md);
//                System.out.println("--------------------INFO GET FIELD VALUE----------------");
                try {
                    value=md.invoke(ob).toString();
                } catch (NullPointerException e) {
//                    System.out.println("--------------------INFO GET FIELD VALUE----------------");
//                    System.err.println("THIS METHOD "+md.getName()+" RETURN NULL ");
//                    System.out.println("--------------------INFO GET FIELD VALUE----------------");
                }
            }
        return correctFormat(ob,value, fd, md);
    }
     public static<L> String correctFormat(L ob,String value,Field fd,Method md) throws Exception{
        if(value==null)return null;
         try {
            value = formatEnChaine(value, fd);
        } catch (TsyChaineException tsch) {
            try {
                value = formatEnDate(md.invoke(ob), fd);
            } catch (TsyDatyException tsydt) {
                value =formatEnNombre(value, fd);
            }
        }catch(NullPointerException nexp){
            value=null;
        } 
        catch (Exception e) {
            throw e;
        }
        return value;
    }
    
    public static String formatEnChaine(String value, Field fd) throws Exception {
        String valiny = value;
        if (fd.getType() == String.class || fd.getType() == Character.class || fd.getType() == char.class) {
            valiny = "'" + value + "'";
        }else {
            throw new TsyChaineException("Tsy de type chaine ito");
        }
        return valiny;
    }
    
    public static  String formatEnNombre(String value, Field fd){
        if (fd.getType() == int.class || fd.getType() == float.class || fd.getType() == double.class || fd.getType()== Number.class) {
            if(value.compareToIgnoreCase("-1")==0 || value.compareToIgnoreCase("-1.0")==0 ){
                return null;
            }
        } 
        return value;
    }

    public static String formatEnDate(Object value, Field fd) throws TsyDatyException, ParseException {
        String valiny = "";
        if (fd.getType() == java.util.Date.class) {
            
            java.util.Date dt=(java.util.Date)value;
           if(isDtTime(fd)){
               valiny = "to_timestamp('" + dt.getDate()+"-"+(dt.getMonth()+1)+"-"+(dt.getYear()+1900) + " "+dt.getHours()+":"+dt.getMinutes()+"', 'DD-MM-yyyy HH24:MI')";
           }else{
            valiny = "to_date('" + dt.getDate()+"-"+(dt.getMonth()+1)+"-"+(dt.getYear()+1900) + "', 'DD-MM-yyyy')";
           }
        } else {
            throw new TsyDatyException("Tsy de type daty na timestamp ny ato");
        }
        return valiny;
    }

    public static String upperFirst(String toUpper) {
        char[] decouper = toUpper.toCharArray();
        String prl = String.valueOf(decouper[0]).toUpperCase();
        char[] enmaj = prl.toCharArray();
        decouper[0] = enmaj[0];
        return String.valueOf(decouper);
    }
    
    public static  void getRightType(ResultSet rslt,Object ob,Field fd,Method md,int icol) throws Exception{   
        if (fd.getType() == int.class) {
            md.invoke(ob, rslt.getInt(icol));
        } else if (fd.getType() == double.class) {
            md.invoke(ob, rslt.getDouble(icol));
        } else if (fd.getType() == byte.class) {
            md.invoke(ob, rslt.getBoolean(icol));
        } else if (fd.getType() == String.class) {
            md.invoke(ob, rslt.getString(icol));
        } else if (fd.getType() == float.class) {
            md.invoke(ob, rslt.getFloat(icol));
        } else if (fd.getType() == java.util.Date.class) {
            if(isDtTime(fd))md.invoke(ob, rslt.getTimestamp(icol));
            else{md.invoke(ob, rslt.getDate(icol));}
        }else if(fd.getType().isArray()){
            md.invoke(ob,rslt.getArray(icol));
        }
    }
   
}
