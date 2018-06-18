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
public class Programacion {
    
    private Integer idProgramacion;
    private Date fechaInicio;
    private Date fechaFinal;
    private Date horaEntrada;
    private Date horaSalida;
    private Cajero cajero;
    private Caja caja;
    
    public Programacion(){
        
    }

    public Programacion(Date fechaInicio, Date fechaFinal, Date horaEntrada, Date horaSalida, Cajero cajero, Caja caja) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.cajero = cajero;
        this.caja = caja;
    }

    public Integer getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }
    
    @Override
    public String toString(){
        return fechaInicio.toString();
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
   
      public List<Programacion> mtdObtenerLista(){
       List<Programacion> lista = null;
       try{
           mtdIniciarOperacion();
           lista = sesion.createQuery("FROM Programacion").list();
       }finally{
           sesion.close();
       }
       
       return lista;
   }
   
   public Programacion mtdObtenerObjetoPorId(Integer id){
       Programacion sc = null;
       try{
           mtdIniciarOperacion();
           sc = (Programacion) sesion.get(Programacion.class, id);
       }finally{
           sesion.close();
       }
       return sc;
   }
    
}
