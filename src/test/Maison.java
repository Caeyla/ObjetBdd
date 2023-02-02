/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import annot.TableAnnotation;
import dao.ObjetBdd;

/**
 *
 * @author caeyla
 */
//@TableAnnotation(nomTable = "maison")
public class Maison extends ObjetBdd<Maison>{
    String idMaison;

    public String getIdMaison() {
        return idMaison;
    }

    public void setIdMaison(String idMaison) {
        this.idMaison = idMaison;
    }
    
    
    
}
