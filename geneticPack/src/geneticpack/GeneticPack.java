/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;



import java.util.ArrayList;
import java.util.Calendar;

import java.util.TreeMap;
import javafx.util.Pair;

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
        int hora,dia;
        Lectura lector= new Lectura();
        TreeMap<String,Ciudad> ciudades= new TreeMap<>();//MAP KEY-Codigo Ciudad y el VALUE-Objeto Ciudad
        ArrayList<Vuelo> vuelos= new ArrayList<>();
        ArrayList<Pedido> pedidos= new ArrayList<>();
        lector.leerArchivos("Extras/_aeropuertos.OACI.txt", "Extras/_plan_vuelo.txt",
                            "Extras/_pedidos_N.txt", vuelos, ciudades, pedidos);
        asignarTipovuelo(vuelos,ciudades);
        generarRutas(ciudades);
        Calendar calendario = Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia= calendario.get(Calendar.DAY_OF_WEEK);
        
        //System.out.println("Dia: "+dia+" Hora: "+hora);
   
        /*
        for(Ciudad ciudad : ciudades.values()) {
            ciudad.print();
            for(ArrayList<Ruta> rutas : ciudad.rutas.values()){
                for(int i=0;i<rutas.size();i++){
                        //System.out.print("Ruta "+i+":");
                        rutas.get(i).print();
                }
                    
            }
        }*/
    
        //for(int i=0;i<vuelos.size();i++) vuelos.get(i).print();
        Genetico algoritmo= new Genetico();
        algoritmo.ejecutar(ciudades,vuelos,pedidos);
        //imprimirAeros(ciudades);
    }
    
    public static void generarRutas(TreeMap<String,Ciudad> ciudades){
        int tEspera;
        int tiempoRuta;
        for(Ciudad ciudad : ciudades.values()) {
            ArrayList<Vuelo>vuelos = ciudad.vuelos;
            for(int i=0;i<vuelos.size();i++){
                String destino=vuelos.get(i).getDestino();
                if(!ciudad.rutas.containsKey(destino)){ // si todavia no tiene ninguna ruta ese destino
                    ArrayList<Ruta> rutas= new ArrayList<>();
                    rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                    ciudad.rutas.put(destino, rutas);                    
                }
                else{
                    ArrayList<Ruta> rutas=ciudad.rutas.get(destino);
                    rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                    ciudad.rutas.put(destino,rutas);    
                }
                
                // caso con Escala
                Ciudad ciudadIntermedia=vuelos.get(i).getAeroFin();
                for(int j=0;j<ciudadIntermedia.vuelos.size();j++){
                    Vuelo vuelo2=ciudadIntermedia.vuelos.get(j);
                    String destino2=vuelo2.getDestino();
                    if(ciudad.getContinente().equals(ciudades.get(destino2).getContinente())) continue;
                    tEspera=vuelo2.gethSalida()-vuelos.get(i).gethLlegada();
                    if(tEspera<0)tEspera+=24;
                    tiempoRuta=vuelos.get(i).getTiempo()+vuelo2.getTiempo()+tEspera;
                    if(tiempoRuta>48) continue; // si se demora más de 48 horas, no tomar en cuenta
                    if(!ciudad.rutas.containsKey(destino2)){ // si todavia no tiene ninguna ruta ese destino
                        ArrayList<Ruta> rutas= new ArrayList<>();
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2, rutas);                    
                    }
                    else{
                        ArrayList<Ruta> rutas=ciudad.rutas.get(destino2);
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2,rutas);    
                    }
                }
            }
        }        
    }
    public static void imprimirAeros(ArrayList<Ciudad> aeropuertos){
        for(int i=0;i<aeropuertos.size();i++){
            //System.out.println(ciudades.get(i).getCiudad()+" cant: "+ciudades.get(i).getCantEspacioUsado());
            System.out.println(aeropuertos.get(i).getCiudad());
        }
    }
    
    public static void asignarTipovuelo(ArrayList<Vuelo> vuelos,TreeMap<String,Ciudad> ciudades){
        int nVuelos=vuelos.size();
        int nAeros= ciudades.size();
        int nOrig=0,nFin=0;
        for(int i=0;i<nVuelos;i++){
            String origen=vuelos.get(i).getOrigen();
            String destino=vuelos.get(i).getDestino();
            if(ciudades.get(origen).getContinente().equals(ciudades.get(destino).getContinente())){
                vuelos.get(i).setTipoVuelo("Continental");
                vuelos.get(i).setTiempo(12);
            }    
            else {
                vuelos.get(i).setTipoVuelo("Intercontinental");
                vuelos.get(i).setTiempo(24);
            }
            if(!ciudades.get(origen).vecinos.contains(destino))ciudades.get(origen).vecinos.add(destino);//agrego vecinos
            ciudades.get(origen).vuelos.add(vuelos.get(i));// agrego vuelos del aeropuerto
            vuelos.get(i).setAeroOrig(ciudades.get(origen));//agrego aeropuerto origen
            vuelos.get(i).setAeroFin(ciudades.get(destino)); // agrego aeropuerto fin            
            


                
     
        }
    }
}
