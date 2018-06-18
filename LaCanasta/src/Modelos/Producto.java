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
public class Producto {
    
    private Integer idProducto;
    private String nombre;
    private String marca;
    private Double valorUnitario;
    private Integer cantidad;
    
    public Producto(){
        
    }

    public Producto(String nombre, String marca, Double valorUnitario, Integer cantidad) {
        this.nombre = nombre;
        this.marca = marca;
        this.valorUnitario = valorUnitario;
        this.cantidad = cantidad;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
   
      public List<Producto> mtdObtenerLista(){
       List<Producto> lista = null;
       try{
           mtdIniciarOperacion();
           lista = sesion.createQuery("FROM Producto").list();
       }finally{
           sesion.close();
       }
       
       return lista;
   }
   
   public Producto mtdObtenerObjetoPorId(Integer id){
       Producto sc = null;
       try{
           mtdIniciarOperacion();
           sc = (Producto) sesion.get(Producto.class, id);
       }finally{
           sesion.close();
       }
       return sc;
   }
    
}
