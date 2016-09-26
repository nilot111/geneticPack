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
    private int hora=7;
    
    Pedido(String origen, String destino, int cant){
        this.origen=origen;
        this.destino=destino;
        this.cant=cant;
    }

    /**
     * @return the id
     */


    /**
     * @return the origen
     */
    
    public void print(){
        System.out.println(origen+"-"+destino+"-"+cant);
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
}
