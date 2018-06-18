/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Mapeos.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author aleja
 */
public class Detalle {
    
    private Integer idDetalle;
    private Venta venta;
    private Producto producto;
    private Integer cantidad;
    private double total;

    public Detalle() {
    }

    public Detalle(Venta venta, Producto producto, Integer cantidad, double total) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
   
      public List<Object[]> mtdObtenerLista(){
       List<Object[]> lista = null;
       try{
           mtdIniciarOperacion();
           Query query = sesion.createQuery("SELECT  d.Producto.nombre,d.Producto.marca,SUM(cantidad) as suma FROM Detalle as d GROUP BY d.Producto.idProducto ORDER BY suma DESC");
          // query.setMaxResults(2);
           lista = query.list();
       }finally{
           sesion.close();
       }
       
       return lista;
   }
   
   public Detalle mtdObtenerObjetoPorId(Integer id){
       Detalle sc = null;
       try{
           mtdIniciarOperacion();
           sc = (Detalle) sesion.get(Detalle.class, id);
       }finally{
           sesion.close();
       }
       return sc;
   }
    
    
}
