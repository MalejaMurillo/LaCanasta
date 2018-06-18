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
public class Cliente {
    
    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String documento;
    
    public Cliente(){
        
    }

    public Cliente(String nombres, String apellidos, String documento) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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
    
    public Integer mtdGuardar(){
        Integer id = 0;
        try {
            mtdIniciarOperacion();
            id = (Integer)sesion.save(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        }finally{
            sesion.close();
        }
        return id;
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
   
      public List<Cliente> mtdObtenerLista(){
       List<Cliente> lista = null;
       try{
           mtdIniciarOperacion();
           lista = sesion.createQuery("FROM Sucursal").list();
       }finally{
           sesion.close();
       }
       
       return lista;
   }
   
   public Cliente mtdObtenerObjetoPorId(Integer id){
       Cliente sc = null;
       try{
           mtdIniciarOperacion();
           sc = (Cliente) sesion.get(Cliente.class, id);
       }finally{
           sesion.close();
       }
       return sc;
   }
}
