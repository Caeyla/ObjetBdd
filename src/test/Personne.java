/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import annot.FkAnnotation;
import annot.TableAnnotation;
import dao.ObjetBdd;

/**
 *
 * @author caeyla
 */
@TableAnnotation(nomTable = "personne")
public class Personne extends ObjetBdd<Personne>{
    String nom;
    int age;
   @FkAnnotation(fieldName = "idMaison")
    Maison  m;

    public Personne() {
        age=-1;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Maison getM() {
        return m;
    }

    public void setM(Maison m) {
        this.m = m;
    }
    
}
