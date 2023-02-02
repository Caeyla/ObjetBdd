package dao;

import conn.ConnectionDeliver;
import crud.Insert;
import crud.Select;
import crud.SelectWhere;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author caeyla
 */
public class ObjetBdd<L extends ObjetBdd>  {
    public void insert(Connection c,boolean closeConnection) throws Exception{
       try {
        Insert.insert(c, this);
       } catch (Exception e) {
           throw  e;
       }finally{
           if(closeConnection && c!=null)c.close();
       }
   }
    
    public ArrayList<L> select(Connection c,boolean closeConnection) throws Exception{
       try {
           Class<L> clazz=(Class<L>)this.getClass();
           return Select.select(clazz,c,(L)this);
       } catch (Exception e) {
           throw e;
       }finally{
          if(closeConnection && c!=null)c.close();
       }
   }
   
   public ArrayList<L> selectWhere(Connection c,String clause,boolean closeConnection) throws Exception{
       try {
           Class<L> clazz=(Class<L>)this.getClass();
           return SelectWhere.selectWhere(clazz, c,(L)this, clause);
       } catch (Exception e) {
           throw e;
       }finally{
           if(closeConnection && c!=null)c.close();
       }
   }
    
    public void update(Connection c,boolean closeConnection)throws Exception{
        try {
            
        } catch (Exception e) {
            
        }finally{
             if(closeConnection && c!=null)c.close();
        }
    }
    
}