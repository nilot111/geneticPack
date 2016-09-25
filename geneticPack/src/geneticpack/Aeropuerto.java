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
public class Aeropuerto {
    private int id;
    private String codAeropuerto;
    private String pais;
    private String ciudad;
    private String abreviado;
    private String continente;
    private int huso;
    
    Aeropuerto(){       
        this.id = -1;
        this.codAeropuerto = "";
        this.pais = "";
        this.ciudad = "";
        this.abreviado = "";
        this.huso = 0;
    }
    
    Aeropuerto(int vId, String vCodAeropuerto, String vCiudad, String vPais, String vAbreviado, String vContinente){
        this.id = vId;
        this.codAeropuerto = vCodAeropuerto;        
        this.ciudad = vCiudad;
        this.pais = vPais;
        this.abreviado = vAbreviado;
        this.continente = vContinente;
    }
    
    public double distancia(Aeropuerto n){
        //return Math.sqrt(Math.pow((n.getX() - this.getX()),2) + Math.pow((n.getY() - this.getY()),2));
        return 1.0;
    }
    
    public void print(){
        System.out.println(this.getCodAeropuerto() + ", " + this.ciudad + ", " + this.pais);
    }

    /**
     * @return the codAeropuerto
     */
    public String getCodAeropuerto() {
        return codAeropuerto;
    }

    /**
     * @param codAeropuerto the codAeropuerto to set
     */
    public void setCodAeropuerto(String codAeropuerto) {
        this.codAeropuerto = codAeropuerto;
    }

    /**
     * @return the continente
     */
    public String getContinente() {
        return continente;
    }

    /**
     * @param continente the continente to set
     */
    public void setContinente(String continente) {
        this.continente = continente;
    }

    /**
     * @return the huso
     */
    public int getHuso() {
        return huso;
    }

    /**
     * @param huso the huso to set
     */
    public void setHuso(int huso) {
        this.huso = huso;
    }    
}
