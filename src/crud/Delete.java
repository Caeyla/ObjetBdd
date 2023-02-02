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

import static utilitaire.UtilitaireAnnotation.*;

/**
 *
 * @author caeyla
 */
public class Delete {
    public String buildDeleteQuery(ObjetBdd ob,Field[] lsfd) throws Exception {
        String query = "Delete from  " + getTableName(ob) + "  ";
//        String columnValue = "";
        String valueTest = "";
        String whereClause = "";
        for (Field field : lsfd) {
            valueTest = getFieldValue(ob,field);
            if (isPrimaryKey(field)) {
                if (valueTest == null) {
                    throw new NullPointerException("primary key null");
                }
                whereClause = " where " + getFieldNameByAnnotation(field)+ "=" + valueTest + "";
            }
//            else if (!isPrimaryKey(field) && tsyMiditra(field)) {
//                if (valueTest != null) {
//                    columnValue += field.getName() + "=" + valueTest + ","; //Miantso fonction hafa 
//                }
//            }
        }
//        columnValue = columnValue.substring(0, columnValue.length() - 1);
        query += whereClause;
        System.out.println("--------------------INFO DELETE----------------");
        System.out.println("DELETE : " + query);
        System.out.println("--------------------INFO DELETE----------------");
        return query;
    }

    public void delete(Connection c,ObjetBdd ob) throws Exception {
        Field[] lsfd = this.getClass().getDeclaredFields();
        Statement stm = c.createStatement();
        int updt = stm.executeUpdate(buildDeleteQuery(ob,lsfd));
        stm.close();
    }

}
