/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Mapeos.HibernateUtil;
import Modelos.Caja;
import Modelos.Sucursal;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author aleja
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Sucursal sucursal = new Sucursal();
        //sucursal.setIdSucursal(1);
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Sucursal sc = (Sucursal) session.get(Sucursal.class, 1);
        
        for(Object c : sc.getMisCajas()){
            Caja ca = (Caja) c;
            System.out.println(ca.getNoCaja());
        }
        
        /*Caja caja = new Caja(101,sc);
 
        session.beginTransaction();
        session.save(caja);
        session.getTransaction().commit();
        session.close();
 */
        
        
    }
    
}
