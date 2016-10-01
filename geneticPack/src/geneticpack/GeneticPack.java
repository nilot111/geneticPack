/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

/**
 *
 * @author GUERRA  
 * //arreglar generar rutasOF !! 
 */
public class GeneticPack {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        int hora=0,dia=0;
        Lectura lector= new Lectura();
        TreeMap<String,Aeropuerto> aeropuertos= new TreeMap<>();
        ArrayList<Vuelo> vuelos= new ArrayList<>();
        ArrayList<Pedido> pedidos= new ArrayList<>();
        lector.leerArchivos("Extras/_aeropuertos.OACI.txt", "Extras/_plan_vuelo.txt",
                            "Extras/_pedidos_N.txt", vuelos, aeropuertos, pedidos);
        obtenerHorayDia(hora,dia);
        asignarTipovuelo(vuelos,aeropuertos);
        System.out.print("Dia: "+dia+" Hora: "+hora);
        //for(int i=0;i<aeropuertos.size();i++) aeropuertos.get(i).print();
        //for(int i=0;i<vuelos.size();i++) vuelos.get(i).print();
        Genetico algoritmo= new Genetico();
        algoritmo.ejecutar(aeropuertos,vuelos,pedidos);
        //imprimirAeros(aeropuertos);
    }
    
    public static void obtenerHorayDia(int hora,int dia){
        Calendar calendario = Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia=calendario.get(Calendar.DAY_OF_WEEK);
    }
    
    public static void imprimirAeros(ArrayList<Aeropuerto> aeropuertos){
        for(int i=0;i<aeropuertos.size();i++){
            //System.out.println(aeropuertos.get(i).getCiudad()+" cant: "+aeropuertos.get(i).getCantEspacioUsado());
            System.out.println(aeropuertos.get(i).getCiudad());
        }
    }
    
    public static void asignarTipovuelo(ArrayList<Vuelo> vuelos,TreeMap<String,Aeropuerto> aeropuertos){
        int nVuelos=vuelos.size();
        int nAeros= aeropuertos.size();
        int nOrig=0,nFin=0;
        for(int i=0;i<nVuelos;i++){
            int orig=-2,dest=-2; // 1=America del sur , 2=Europa
            String origen=vuelos.get(i).getOrigen();
            String destino=vuelos.get(i).getDestino();
            if(aeropuertos.get(origen).getContinente().equals(aeropuertos.get(destino).getContinente())){
                vuelos.get(i).setTipoVuelo("Continental");
                vuelos.get(i).setTiempo(12);
            }    
            else {
                vuelos.get(i).setTipoVuelo("Intercontinental");
                vuelos.get(i).setTiempo(24);
            }
            if(!aeropuertos.get(origen).vecinos.contains(destino))aeropuertos.get(origen).vecinos.add(destino);//agrego vecinos
            aeropuertos.get(origen).vuelos.add(vuelos.get(i));// agrego vuelos del aeropuerto
            
            vuelos.get(i).setAeroOrig(aeropuertos.get(origen));//agrego aeropuerto origen
            vuelos.get(i).setAeroFin(aeropuertos.get(destino)); // agrego aeropuerto fin            
            


                
     
        }
    }
}
