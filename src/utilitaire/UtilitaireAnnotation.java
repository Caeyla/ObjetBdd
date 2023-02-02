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
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caeyla
 */
public class UtilitaireAnnotation {
     public static  boolean isPrimaryKey(Field field) {
        PrimaryKeyAnnotation pkAnnotation = null;
        pkAnnotation = (PrimaryKeyAnnotation) field.getAnnotation(PrimaryKeyAnnotation.class);
          return (pkAnnotation!=null);
    }
     
    public static String getFieldNameByAnnotation(Field fd) {
        Annotation [] liste=fd.getAnnotations();
        Method  md;
        for (Annotation annotation : liste) {
            try{
              if(isFk(fd)){
                 return getFkName(fd);
              }
              md=annotation.annotationType().getMethod("nomColonne");
        
              if(md.invoke(annotation).toString()!=null && md.invoke(annotation).toString().compareToIgnoreCase("")!=0 )
                    return md.invoke(annotation).toString();
            }catch(NoSuchMethodException e){
                continue;
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ObjetBdd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ObjetBdd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ObjetBdd.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return fd.getName();
    }
    public static boolean tsyMiditra(Field field) {
        TsyMiditraAnnotation tsmiditra=null;
        tsmiditra=(TsyMiditraAnnotation)field.getAnnotation(TsyMiditraAnnotation.class);
        return (tsmiditra!=null);
    }
    
    public static  boolean  isFk(Field fd){
        if(fd.isAnnotationPresent(FkAnnotation.class)) return true;
        return false;
    }
    
    public static  String getFkName(Field fd){
        FkAnnotation fkannot=fd.getAnnotation(FkAnnotation.class);
        return fkannot.fieldName();
    }
    public static  boolean isDtTime(Field fd){
        if(fd.isAnnotationPresent(Datetime.class))return true;
        return false;
    }
    public static String getTableName(ObjetBdd ob) {
        Class cl = ob.getClass();
        TableAnnotation tba = null;
        tba = (TableAnnotation) cl.getAnnotation(TableAnnotation.class);
        if(tba!=null) return tba.nomTable();
        return ob.getClass().getSimpleName();
    }   

}
