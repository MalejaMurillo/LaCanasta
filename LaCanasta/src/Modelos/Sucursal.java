
package Modelos;

import Mapeos.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Sucursal implements Serializable{
    
    
    private Integer idSucursal;
    private String ciudad;
    private String direccion;
    private String telefono;
    private Set misCajas;
    
    public Sucursal(){
        
    }

    public Sucursal(String ciudad, String direccion, String telefono) {
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the misCajas
     */
    public Set getMisCajas() {
        return misCajas;
    }

    /**
     * @param misCajas the misCajas to set
     */
    public void setMisCajas(Set misCajas) {
        this.misCajas = misCajas;
    }
    
    @Override
    public String toString(){
        return ciudad;
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
   
   public List<Sucursal> mtdObtenerLista(){
       List<Sucursal> lista = null;
       try{
           mtdIniciarOperacion();
           lista = sesion.createQuery("FROM Sucursal").list();
       }finally{
           sesion.close();
       }
       
       return lista;
   }
   
   public Sucursal mtdObtenerObjetoPorId(Integer id){
       Sucursal sc = null;
       try{
           mtdIniciarOperacion();
           sc = (Sucursal) sesion.get(Sucursal.class, id);
       }finally{
           sesion.close();
       }
       return sc;
   }
    
}
