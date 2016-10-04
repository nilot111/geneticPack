/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author GUERRA
 */
public class Lectura {
    public void leerArchivos(String archAeropuertos,String archVuelos,String archPedidos,String archHusos,ArrayList<Vuelo> vuelos,
                            TreeMap<String,Ciudad>  aeropuertos, ArrayList<Pedido> pedidos){
        leerAeropuertos(archAeropuertos,aeropuertos);
        leerVuelos(archVuelos,vuelos);
        leerPedidos(archPedidos,pedidos);
        leerHusos(archHusos,aeropuertos);
    }
    
    public void leerHusos(String archHusos,TreeMap<String,Ciudad>  ciudades){
        String line;
        String [] value;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(archHusos));
            try {
                while ((line = br.readLine()) != null){
                    value = line.trim().split("\t");
                    String ciud=value[1];
                    int huso=Integer.parseInt(value[2]);
                    ciudades.get(ciud).huso=huso;
                }
            } catch (IOException ex) {
                Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void leerAeropuertos(String archAeropuertos,TreeMap<String,Ciudad> aeropuertos){
        
        String line;
        String [] value;
        String continente="",codAeropuerto,ciudad,pais,abreviado;
        int id;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(archAeropuertos));
            while ((line = br.readLine()) != null)
            {
                if (line.contains("America del Sur"))
                {                    
                    continente = "America del Sur";
                    break;
                }
            }
             while ((line = br.readLine()) != null)
            {                
                if (line.contains("Europa"))
                {                    
                    //CONTINENTE
                    continente = "Europa";
                    continue;
                }                
                value = line.trim().split("\t");
                if (value[0].isEmpty())
                    continue;
                
                id = Integer.parseInt(value[0]);
                codAeropuerto = value[1];
                ciudad = value[2];
                pais = value[3];
                abreviado = value[4];
                Ciudad aero= new Ciudad(id, codAeropuerto, ciudad, pais, abreviado, continente);
                aeropuertos.put(codAeropuerto,aero);
            }
             br.close();
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void leerVuelos(String archVuelos,ArrayList<Vuelo> vuelos){
        try {
            String line;
            String [] value;
            String origen, destino, continente = "";
            int hSalida, mSalida, hLlegada, mLlegada;               
            BufferedReader br = new BufferedReader(new FileReader(archVuelos));
            while ((line = br.readLine()) != null)
            {
                value = line.trim().split("-|:");
                
                origen = value[0];
                destino = value[1];
                hSalida = Integer.parseInt(value[2]);
                mSalida = Integer.parseInt(value[3]);
                hLlegada = Integer.parseInt(value[4]);
                mLlegada = Integer.parseInt(value[5]);
                Vuelo vuel=new Vuelo(hSalida, mSalida, hLlegada, mLlegada, origen, destino); 
                vuelos.add(vuel);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void leerPedidos(String archPedidos,ArrayList<Pedido> pedidos){
        try {
            String line;
            String [] value;
            String origen, destino;
            int cant; 
            int hora,min;
            int dia,mes,año;
            BufferedReader br = new BufferedReader(new FileReader(archPedidos));
            while ((line = br.readLine()) != null)
            {
                value = line.trim().split("-");
                if (value[0].isEmpty())
                    continue;
                origen = value[0];
                destino = value[1];
                cant = Integer.parseInt(value[2]);
                String horaMin=value[3];
                String fecha=value[4];
                value=horaMin.trim().split(":");
                hora=Integer.parseInt(value[0]);
                min=Integer.parseInt(value[1]);
                value=fecha.trim().split("/");
                dia=Integer.parseInt(value[0]);
                mes=Integer.parseInt(value[1]);
                año=Integer.parseInt(value[2]);
                Pedido ped = new Pedido(origen,destino,cant,hora,min,dia,mes,año);
                pedidos.add(ped);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
