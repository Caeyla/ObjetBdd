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
import static utilitaire.Utilitaire.getFieldValue;
import static utilitaire.UtilitaireAnnotation.getFieldNameByAnnotation;
import static utilitaire.UtilitaireAnnotation.getTableName;
import static utilitaire.UtilitaireAnnotation.isPrimaryKey;
import static utilitaire.UtilitaireAnnotation.tsyMiditra;

/**
 *
 * @author caeyla
 */
public class Update {
    public static <L extends ObjetBdd> String buildUpdateQuery(L ob,Field[] lsfd) throws Exception {
        String query = "UPDATE " + getTableName(ob) + " set ";
        String columnValue = "";
        String valueTest = "";
        String whereClause = "";
        for (Field field : lsfd) {
            valueTest = getFieldValue(ob,field);
            if (isPrimaryKey(field)) {
                if (valueTest == null) {
                    throw new NullPointerException("primary key null");
                }
                whereClause = " where " + getFieldNameByAnnotation(field) + "=" + valueTest + "";
            } else if (!isPrimaryKey(field) && tsyMiditra(field)) {
                if (valueTest != null) {
                    columnValue += getFieldNameByAnnotation(field) + "=" + valueTest + ","; //Miantso fonction hafa 
                }
            }
        }
        columnValue = columnValue.substring(0, columnValue.length() - 1);
        query += columnValue + whereClause;
        System.out.println("--------------------INFO UPDATE ----------------");
        System.out.println("UPDATE : " + query);
        System.out.println("--------------------INFO UPDATE----------------");
        return query;
    }

    public static <L extends ObjetBdd>void update(Connection c,L ob) throws Exception {
        Class cl = ob.getClass();
        Field[] lsfd = cl.getDeclaredFields();
        Statement stm = c.createStatement();
        int pdt = stm.executeUpdate(buildUpdateQuery(ob,lsfd));
        stm.close();
        c.close();
    }
}
