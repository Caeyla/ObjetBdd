/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import static crud.Select.buildSelectQuery;
import static crud.Select.getContenu;
import dao.ObjetBdd;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static utilitaire.UtilitaireAnnotation.getTableName;

/**
 *
 * @author caeyla
 */
public class SelectWhere {
    public static <L extends ObjetBdd>   ArrayList<L> selectWhere(Class<L> clazz,Connection connection,L ob,String clause) throws Exception {
        Field[] lsfd=ob.getClass().getDeclaredFields();
        Statement stm = null;
        ResultSet rslt=null;
       try{
        stm = connection.createStatement();
        String query="SELECT  * FROM  " + getTableName(ob) + " where "+clause;
        rslt=stm.executeQuery(query);
        ArrayList<L> valiny =getContenu(clazz,ob,rslt,connection);
        System.out.println("--------------------INFO SELECT----------------");
           System.out.println("QUERY "+query);
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
}
