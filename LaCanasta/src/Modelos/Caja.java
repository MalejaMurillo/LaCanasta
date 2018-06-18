/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Mapeos.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author aleja
 */
public class Caja implements Serializable{
    
    private Integer idCaja;
    private Integer noCaja;
    private Sucursal Sucursal;
    
    public Caja(){
        
    }

    public Caja(Integer noCaja, Sucursal Sucursal) {
        this.noCaja = noCaja;
        this.Sucursal = Sucursal;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public Integer getNoCaja() {
        return noCaja;
    }

    public void setNoCaja(Integer noCaja) {
        this.noCaja = noCaja;
    }

    public Sucursal getSucursal() {
        return Sucursal;
    }

    public void setSucursal(Sucursal Sucursal) {
        this.Sucursal = Sucursal;
    }
    
    @Override
    public String toString(){
        return  noCaja.toString();
    }
  
    private Session sesion;
    private Transaction tx;
    
    private void mtdIniciarOperacion(){
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }
    private void mtdManejarExcepcion(HibernateException he){
        tx.rollback();
        throw new HibernateException("Ocurrio un error en la capa de acceso a datos", he);
    }
    
    public void mtdGuardar(){
        try {
            mtdIniciarOperacion();
            sesion.save(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
    }
    
        public void mtdActualizar(){
        try {
            mtdIniciarOperacion();
            sesion.update(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
    }
    
   public void mtdEliminar(){
       try {
            mtdIniciarOperacion();
            sesion.delete(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
   }
    
      public List<Caja> mtdObtenerLista(){
       List<Object[]> lista = null;
       List<Caja> listaCajas = new ArrayList();
       try{
           mtdIniciarOperacion();
           listaCajas = sesion.createQuery("FROM Caja").list();
//           lista = sesion.createQuery("select c.idCaja, c.noCaja, c.Sucursal FROM Caja AS c").list();
//           for (int i = 0; i < lista.size(); i++) {
//               Caja c = new Caja();
//               
//           }
         
       }finally{
           sesion.close();
       }
       
       return listaCajas;
   }
   
   public Caja mtdObtenerObjetoPorId(Integer id){
       Caja cj = null;
       try{
           mtdIniciarOperacion();
           cj = (Caja) sesion.get(Caja.class, id);
       }finally{
           sesion.close();
       }
       return cj;
   }
}
