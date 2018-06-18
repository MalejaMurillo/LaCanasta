/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Mapeos.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author aleja
 */
public class Venta {

    private Integer idVenta;
    private Date fecha;
    private Cliente cliente;
    private Programacion programacion;

    public Venta() {

    }

    public Venta(Date fecha, Cliente cliente, Programacion programacion) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.programacion = programacion;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    private Session sesion;
    private Transaction tx;

    private void mtdIniciarOperacion() {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    private void mtdManejarExcepcion(HibernateException he) {
        tx.rollback();
        throw new HibernateException("Ocurrio un error en la capa de acceso a datos", he);
    }

    public Integer mtdGuardar() {
        Integer id = 0;
        try {
            mtdIniciarOperacion();
            id = (Integer) sesion.save(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return id;
    }

    public void mtdActualizar() {
        try {
            mtdIniciarOperacion();
            sesion.update(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public void mtdEliminar() {
        try {
            mtdIniciarOperacion();
            sesion.delete(this);
            tx.commit();
        } catch (HibernateException he) {
            mtdManejarExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public List<Object[]> mtdObtenerLista() {
        List<Object[]> lista = null;
        try {
            mtdIniciarOperacion();
            lista = sesion.createQuery("SELECT v.Cliente.nombres,v.Cliente.documento,COUNT(*) as compras FROM Venta as v GROUP BY v.Cliente.idCliente ORDER BY compras DESC").list();
        } finally {
            sesion.close();
        }

        return lista;
    }

    public List<Object[]> mtdObtenerLista2() {
        List<Object[]> lista = null;
        try {
            mtdIniciarOperacion();
            lista = sesion.createQuery("SELECT v.Programacion.Cajero.nombres,v.Programacion.Cajero.apellidos,v.Programacion.Cajero.documento,COUNT(*) as cant FROM Venta as v GROUP BY v.Programacion.Cajero.idCajero").list();
        } finally {
            sesion.close();
        }

        return lista;
    }

    public Venta mtdObtenerObjetoPorId(Integer id) {
        Venta sc = null;
        try {
            mtdIniciarOperacion();
            sc = (Venta) sesion.get(Venta.class, id);
        } finally {
            sesion.close();
        }
        return sc;
    }
}
