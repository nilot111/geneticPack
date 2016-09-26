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
public class Genetico {
    private int MAXPOPULATION = 500;
    private ArrayList<Ruta> universoRutas= new ArrayList<>();
    
    Genetico(){
        
    }
    public void ejecutar(ArrayList<Aeropuerto> aeropuertos, ArrayList<Vuelo> vuelos,
                         ArrayList<Pedido> pedidos){
        generarRutas(aeropuertos);
        
    }
    
    public void generarRutas(ArrayList<Aeropuerto> aeropuertos){
        int nAeropuertos=aeropuertos.size();
        for(int i=0;i<nAeropuertos;i++){
            
        }
    }
}
