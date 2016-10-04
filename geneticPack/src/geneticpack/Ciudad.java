/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;


/**
 *
 * @author GUERRA
 */
public class Ciudad {
    private int id;
    private String codCiudad;
    private String pais;
    private String ciudad;
    private String abreviado;
    private String continente;
    public int huso;
    private int capacidadTotal=600;
    private String []dias={"Lun","Mar","Mie","Jue","Vie","Sab","Dom"}; 
    public ArrayList<String> vecinos= new ArrayList<>();
    public ArrayList<Vuelo> vuelos= new ArrayList<>();
    public HashMap<String,ArrayList<Ruta>> rutas = new HashMap<>(); // llave es el codigo de la ciudad destino, valor es las rutas posibles
    public HashMap<String,Integer> capTime = new HashMap<>(); // capacidades en el tiempo, key es : dia-hora:00 / dia-hora:01
    Ciudad(){       
        this.id = -1;
        this.codCiudad = "";
        this.pais = "";
        this.ciudad = "";
        this.abreviado = "";
        this.continente= "";
    }
    
    Ciudad(int vId, String vCodAeropuerto, String vCiudad, String vPais, String vAbreviado, String vContinente){
        this.id = vId;
        this.codCiudad = vCodAeropuerto;        
        this.ciudad = vCiudad;
        this.pais = vPais;
        this.abreviado = vAbreviado;
        this.continente = vContinente;
        //inicializar capacidades en tiempo
        for(int i=0;i<7;i++){
            for(int j=0;j<24;j++){
                String key=dias[i]+"-"+j+":00";
                capTime.put(key, capacidadTotal);
                String key2=dias[i]+"-"+j+":01";
                capTime.put(key2, capacidadTotal);                
            }         
            
        }
        
    }
    
    public double distancia(Ciudad n){
        //return Math.sqrt(Math.pow((n.getX() - this.getX()),2) + Math.pow((n.getY() - this.getY()),2));
        return 1.0;
    }
    
    public void print(){
        System.out.println(this.getCodAeropuerto() + ", " + this.getCiudad() + ", " + this.getPais()+ ", "+ this.getContinente()+"-"+this.huso);
        //for(int i=0;i<vecinos.size();i++) System.out.println(vecinos.get(i));
    }

    /**
     * @return the codCiudad
     */
    public String getCodAeropuerto() {
        return codCiudad;
    }

    /**
     * @param codAeropuerto the codCiudad to set
     */
    public void setCodAeropuerto(String codAeropuerto) {
        this.codCiudad = codAeropuerto;
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
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the abreviado
     */
    public String getAbreviado() {
        return abreviado;
    }

    /**
     * @param abreviado the abreviado to set
     */
    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    /**
     * @return the cantEspacioUsado
     */

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidadTotal;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidadTotal = capacidad;
    }

    /**
     * @return the vecinos
     */



    
}
