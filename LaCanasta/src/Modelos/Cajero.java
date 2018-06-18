/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Mapeos.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author aleja
 */
public class Cajero {
    
    private Integer idCajero;
    private String nombres;
    private String apellidos;
    private String documento;
    private String telefono;
    
    public Cajero(){
        
    }

    public Cajero(String nombres, String apellidos, String documento, String telefono) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.telefono = telefono;
    }

    public Integer getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Integer idCajero) {
        this.idCajero = idCajero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String toString(){
        return nombres + " " + apellidos;
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
   
      public List<Cajero> mtdObtenerLista(){
       List<Cajero> lista = null;
       try{
           mtdIniciarOperacion();
           lista = sesion.createQuery("FROM Cajero").list();
       }finally{
           sesion.close();
       }
       
       return lista;
   }
   
   public Cajero mtdObtenerObjetoPorId(Integer id){
       Cajero cjr = null;
       try{
           mtdIniciarOperacion();
           cjr = (Cajero) sesion.get(Cajero.class, id);
       }finally{
           sesion.close();
       }
       return cjr;
   }
}


    