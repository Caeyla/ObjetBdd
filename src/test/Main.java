/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import conn.ConnectionDeliver;

/**
 *
 * @author caeyla
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Personne p=new Personne();
       // Maison m=new Maison().select(ConnectionDeliver.getConnection()).get(0);
        p.setAge(100);
       // p.setM(m);
        p.setNom("JAH");
        //p.insert(ConnectionDeliver.getConnection());
        //System.out.println(p.select(ConnectionDeliver.getConnection()).get(0).getNom());
    }
}
