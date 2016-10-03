/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.util.ArrayList;

/**
 *
 * @author GUERRA
 */
public class Ruta {
    public ArrayList<Vuelo> vuelos = new ArrayList<>();
    public int tiempo=0;
    Ruta(){
        
    }
    Ruta(Vuelo vuel,int tiemp){
        vuelos.add(vuel);
        tiempo=tiemp;
    }    
    
    Ruta(Vuelo vuel1, Vuelo vuel2,int tiemp){
        vuelos.add(vuel1);
        vuelos.add(vuel2);
        tiempo=tiemp;
    }       
    public void print(){
        for(int i=0;i<vuelos.size();i++){
            System.out.print(vuelos.get(i).getAeroOrig().getCodAeropuerto()+"-"+
                            vuelos.get(i).getAeroFin().getCodAeropuerto()+"/");
        }
        System.out.println(" Tiempo: "+tiempo);
    }
}
