/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author GUERRA
 */
public class GeneticPack {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        Lectura lector= new Lectura();
        ArrayList<Aeropuerto> aeropuertos= new ArrayList<>();
        ArrayList<Vuelo> vuelos= new ArrayList<>();
        ArrayList<Pedido> pedidos= new ArrayList<>();
        lector.leerArchivos("Extras/_aeropuertos.OACI.txt", "Extras/_plan_vuelo.txt",
                            "Extras/_pedidos.txt", vuelos, aeropuertos, pedidos);
        asignarTipovuelo(vuelos,aeropuertos);
        for(int i=0;i<aeropuertos.size();i++) aeropuertos.get(i).print();
        //for(int i=0;i<vuelos.size();i++) vuelos.get(i).print();
        Genetico algoritmo= new Genetico();
        algoritmo.ejecutar(aeropuertos,vuelos,pedidos);
    }
    
    
    
    public static void asignarTipovuelo(ArrayList<Vuelo> vuelos,ArrayList<Aeropuerto> aeropuertos){
        int nVuelos=vuelos.size();
        int nAeros= aeropuertos.size();
        int nOrig=0,nFin=0;
        for(int i=0;i<nVuelos;i++){
            int orig=-2,dest=-2; // 1=America del sur , 2=Europa
            for(int j=0;j<nAeros;j++){
                if(vuelos.get(i).getOrigen().compareTo(aeropuertos.get(j).getCodAeropuerto())==0){// encontré el aeropuerto orig
                    if(aeropuertos.get(j).getContinente().equals("America del Sur"))orig=1;
                    else orig=2;
                    nOrig=j;
                }
                if(vuelos.get(i).getDestino().compareTo(aeropuertos.get(j).getCodAeropuerto())==0){ //encontré el aeropuerto fin
                    if(aeropuertos.get(j).getContinente().equals("America del Sur")) dest=1;
                    else dest=2;
                    nFin=j;
                }
                if(orig+dest>=2) {// ya se encontró ambos
                    if(orig==dest) {
                        vuelos.get(i).setTipoVuelo("Continental");
                        vuelos.get(i).setTiempo(12);
                    }
                    else {
                        vuelos.get(i).setTipoVuelo("Interncontinental");
                        vuelos.get(i).setTiempo(24);
                    }
                    //agrego vecinos de mi aeropuerto
                    if(!aeropuertos.get(nOrig).vecinos.contains(nFin))aeropuertos.get(nOrig).vecinos.add(nFin);
                    break;
                } 
                
            }         
        }
    }
}
