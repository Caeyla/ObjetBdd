/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import conn.ConnectionDeliver;
import dao.ObjetBdd;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static utilitaire.Utilitaire.getFieldValue;
import static utilitaire.Utilitaire.getRightType;
import static utilitaire.Utilitaire.upperFirst;
import static utilitaire.UtilitaireAnnotation.getFieldNameByAnnotation;
import static utilitaire.UtilitaireAnnotation.getTableName;
import static utilitaire.UtilitaireAnnotation.isFk;
import static utilitaire.UtilitaireAnnotation.tsyMiditra;

/**
 *
 * @author caeyla
 */
public class Select{
    
    public static <L extends ObjetBdd>  String buildSelectQuery(L ob,Field[] lsfd) throws Exception {
        
        String query= "SELECT  * FROM  " + getTableName(ob) + " ";
        String valueTest = "";
        String whereClause = " WHERE 1=1 AND ";
        for (Field field : lsfd) {
            if(!tsyMiditra(field)){
                valueTest = getFieldValue(ob,field);
                if (valueTest != null) {
                    whereClause += getFieldNameByAnnotation(field)+ "="+valueTest+" AND "; //Miantso fonction hafa 
                }
            }
        }
        whereClause = whereClause.substring(0, whereClause.length() - 4);
        query +=whereClause;
        System.out.println("--------------------INFO SELECT----------------");
        System.out.println("SELECT : " + query);
        System.out.println("--------------------INFO SELECT----------------");
        return query;
    }

    public static <L extends ObjetBdd>   ArrayList<L> select(Class<L> clazz,Connection connection,L ob) throws Exception {
        Field[] lsfd=ob.getClass().getDeclaredFields();
        Statement stm = null;
        ResultSet rslt=null;
       try{
        stm = connection.createStatement();
        rslt=stm.executeQuery(buildSelectQuery(ob,lsfd));
        ArrayList<L> valiny =getContenu(clazz,ob,rslt,connection);
        System.out.println("--------------------INFO SELECT----------------");
        System.out.println("NB OF LINES FROM SELECT QUERY  ON  "+ob.getClass()+" IS "+valiny.size());
        System.out.println("--------------------INFO SELECT----------------");
        return valiny;
       }catch(Exception e){
           throw  e;
       }finally{
           if(stm!=null)stm.close();
           if(rslt!=null)rslt.close();
       }
    }
    
    public static  <L extends ObjetBdd>  ArrayList<L> getContenu(Class<L> clazz,L ob,ResultSet rslt,Connection c) throws Exception {
        Field[] fd = clazz.getDeclaredFields();
        int size = rslt.getMetaData().getColumnCount();
        Method md;
        int cpt = 0;
        int icol = 1;
        int ifield=0;
        if(size!=fd.length){
            System.out.println("---------------------INFO GET CONTENU----------------------");
            System.out.println("tsy mitovy ny isan'ny "
                    + "colonne anaty base sy ny champ anaty class col: "+size+" champ: "+fd.length+" jereo sao olana ");
            System.out.println("---------------------INFO GET CONTENU----------------------");
        }
        L instance=null;
        ArrayList<L> list=new ArrayList<>();
        while (rslt.next()) {
            instance= clazz.newInstance();
            while (icol<=size) {
                if(tsyMiditra(fd[ifield])){
                    System.out.println("---------------------INFO GET CONTENU----------------------");
                    System.out.println("tsy alaina anaty base ito champ ito "+fd[ifield].getName());
                    System.out.println("---------------------INFO GET CONTENU----------------------");
                    ifield++;
                    continue;
                }
                if(isFk(fd[ifield])){
                    ObjetBdd ob2=(ObjetBdd) fd[ifield].getType().newInstance();
                    Field fdOfFk=ob2.getClass().getDeclaredField(getFieldNameByAnnotation(fd[ifield]));
                    md=ob2.getClass().getDeclaredMethod("set" + upperFirst(getFieldNameByAnnotation(fdOfFk)),
                        fdOfFk.getType());
                    getRightType(rslt, ob2, fdOfFk, md, icol);
                    md = clazz.getDeclaredMethod("set" + upperFirst(fd[ifield].getName()),
                        fd[ifield].getType());
                    ob2=(ObjetBdd)ob2.select(c,false).get(0);
                    md.invoke(instance,ob2);
                }
                else{             
                    md = clazz.getDeclaredMethod("set" + upperFirst(fd[ifield].getName()),
                        fd[ifield].getType());
                    getRightType(rslt, instance,fd[ifield], md, icol);
                }
                icol = icol + 1;
                ifield++;
            }
            list.add(instance);
            ifield=0;
            icol=1;
            cpt++;
        }
        return list;
    }
}
