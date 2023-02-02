/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import conn.ConnectionDeliver;
import dao.ObjetBdd;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import static utilitaire.UtilitaireAnnotation.*;
import static utilitaire.Utilitaire.*;
/**
 *
 * @author caeyla
 */
public class Insert {
      public static void insert(Connection c,ObjetBdd ob) throws Exception {
        Statement stm = null;
        try {
            stm = c.createStatement();
            Field[] lsfd = ob.getClass().getDeclaredFields();
            int updt = stm.executeUpdate(buildInsertQuery(ob,lsfd));
        } catch (Exception e) {
            throw e;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    public static String buildInsertQuery(ObjetBdd ob,Field[] lsfd) throws Exception {
        String query = "INSERT INTO " + getTableName(ob) + " ";
        String columnListe = "";
        String valueListe = "";
        String valueTest = "";
        for (Field field : lsfd) {
            if (!isPrimaryKey(field) && !tsyMiditra(field)) {
                valueTest = getFieldValue(ob,field);
                if (valueTest != null) {
                    columnListe += getFieldNameByAnnotation(field) + ","; //Miantso fonction hafa 
                    valueListe += valueTest + ",";
                }
            }
        }
        columnListe = columnListe.substring(0, columnListe.length() - 1);
        valueListe = valueListe.substring(0, valueListe.length() - 1);
        query += "(" + columnListe + ") values (" + valueListe + ")";
        System.out.println("--------------------INFO INSERT----------------");
        System.out.println("INSERT : " + query);
        System.out.println("--------------------INFO INSERT----------------");
        return query;
    }
}
