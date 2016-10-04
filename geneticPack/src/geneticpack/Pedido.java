/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

/**
 *
 * @author GUERRA
 */
public class Pedido {
    private String origen;
    private String destino;
    private int cant;
    public int hora;
    public int min;
    public int dia;
    public int mes;
    public int año;
    
    Pedido(){
        
    }
    Pedido(String origen, String destino, int cant,int hora, int min,int dia,int mes,int año){
        this.origen=origen;
        this.destino=destino;
        this.cant=cant;
        this.hora=hora;
        this.min=min;
        this.dia=dia;
        this.mes=mes;
        this.año=año;
    }

    /**
     * @return the id
     */


    /**
     * @return the origen
     */
    
    public void print(){
        System.out.println(origen+"-"+destino+"-"+cant+"-"+hora+":"+min);
    }
    
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @return the cant
     */
    public int getCant() {
        return cant;
    }

    /**
     * @return the hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(int hora) {
        this.hora = hora;
    }
}
