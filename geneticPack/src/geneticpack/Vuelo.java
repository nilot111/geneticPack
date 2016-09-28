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
public class Vuelo {
    private int hSalida;
    private int mSalida;
    private int hLlegada;
    private int mLlegada;
    private String origen;
    private String destino;
    private String tipoVuelo;
    private int tiempo;
    private int id;
    private int husoO;
    private int husoD;
    private int capacidad=0;
    private int cantEspacioUsado=0;
    private Aeropuerto aeroOrig;
    private Aeropuerto aeroFin;
    Vuelo(){
        
        hSalida = 0;
        mSalida = 0;
        hLlegada = 0;
        mLlegada = 0;
        origen = "";
        destino = "";
        tipoVuelo = "";
        tiempo = 0;
    }
    
    Vuelo(int vHSalida, int mHSalida, int vHLlegada, int vMLlegada, String vOrigen, String vDestino){
        hSalida = vHSalida;
        mSalida = mHSalida;
        hLlegada = vHLlegada;
        mLlegada = vMLlegada;
        origen = vOrigen;
        destino = vDestino;
    }
    
    public void print(){
        System.out.println(origen+"-"+destino+"-"+tiempo);
    }

    /**
     * @return the hSalida
     */
    public int gethSalida() {
        return hSalida;
    }

    /**
     * @param hSalida the hSalida to set
     */
    public void sethSalida(int hSalida) {
        this.hSalida = hSalida;
    }

    /**
     * @return the mSalida
     */
    public int getmSalida() {
        return mSalida;
    }

    /**
     * @param mSalida the mSalida to set
     */
    public void setmSalida(int mSalida) {
        this.mSalida = mSalida;
    }

    /**
     * @return the hLlegada
     */
    public int gethLlegada() {
        return hLlegada;
    }

    /**
     * @param hLlegada the hLlegada to set
     */
    public void sethLlegada(int hLlegada) {
        this.hLlegada = hLlegada;
    }

    /**
     * @return the mLlegada
     */
    public int getmLlegada() {
        return mLlegada;
    }

    /**
     * @param mLlegada the mLlegada to set
     */
    public void setmLlegada(int mLlegada) {
        this.mLlegada = mLlegada;
    }

    /**
     * @return the origen
     */
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
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the tipoVuelo
     */
    public String getTipoVuelo() {
        return tipoVuelo;
    }

    /**
     * @param tipoVuelo the tipoVuelo to set
     */
    public void setTipoVuelo(String tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    /**
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the aeroOrig
     */
    public Aeropuerto getAeroOrig() {
        return aeroOrig;
    }

    /**
     * @param aeroOrig the aeroOrig to set
     */
    public void setAeroOrig(Aeropuerto aeroOrig) {
        this.aeroOrig = aeroOrig;
    }

    /**
     * @return the aeroFin
     */
    public Aeropuerto getAeroFin() {
        return aeroFin;
    }

    /**
     * @param aeroFin the aeroFin to set
     */
    public void setAeroFin(Aeropuerto aeroFin) {
        this.aeroFin = aeroFin;
    }

    /**
     * @return the cantEspacioUsado
     */
    public int getCantEspacioUsado() {
        return cantEspacioUsado;
    }

    /**
     * @param cantEspacioUsado the cantEspacioUsado to set
     */
    public void setCantEspacioUsado(int cantEspacioUsado) {
        this.cantEspacioUsado = cantEspacioUsado;
    }
}
