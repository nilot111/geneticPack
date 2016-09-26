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
    
    public void print(){
        for(int i=0;i<vuelos.size();i++){
            System.out.print(vuelos.get(i).getAeroOrig().getCodAeropuerto()+"-"+
                            vuelos.get(i).getAeroFin().getCodAeropuerto()+"/");
        }
        System.out.println();
    }
}
