/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author GUERRA
 */
public class Genetico {
    private int maxPoblacion = 300; // maximo numero de soluciones posibles
    private int maxGeneraciones=100; // maxiteraciones
    private double probMutacion=0.01;
    private ArrayList<Ruta> universoRutas= new ArrayList<>();
    private ArrayList<Cromosoma> cromosomas= new ArrayList<>();
    Genetico(){
        
    }
    public void ejecutar(ArrayList<Aeropuerto> aeropuertos, ArrayList<Vuelo> vuelos,
                         ArrayList<Pedido> pedidos){
        //generarRutas(aeropuertos);
        int fitnessTotal=generarPoblacion(pedidos,aeropuertos);
        //ArrayList<Ruta> rutas= generarRutasOF("SPIM","SGAS",aeropuertos);
        //for(int i=0;i<rutas.size();i++) rutas.get(i).print();
        reproduccion(fitnessTotal);
    }
    
    public void reproduccion(int fitnessTotal){
        Cromosoma bestCrom= new Cromosoma();
        bestCrom.fitness=0;
        for(int i=0;i<maxGeneraciones;i++){
            int auxFitnesstotal=0;
            
            ArrayList<Cromosoma> offspring= new ArrayList<>();
            for(int j=0;j<maxPoblacion;j++){
                Random semilla = new Random(); // seleccionar padre
                Random semilla2 = new Random();// seleccionar madre
                int pospadre=semilla.nextInt(fitnessTotal);
                int posmadre=semilla2.nextInt(fitnessTotal);
                int encPadre=0,encMadre=0,sumafit=0; // simulamos ruleta de selecciones de padres
                while(sumafit<=pospadre){
                    sumafit+=cromosomas.get(encPadre++).fitness;
                }
                sumafit=0;
                while(sumafit<=posmadre){
                    sumafit+=cromosomas.get(encMadre++).fitness;
                }
                Cromosoma hijo= crossover(cromosomas.get(--encPadre),cromosomas.get(--encMadre)
                                        ,cromosomas.get(0).alelos.size());
                mutacion(hijo);
                hijo.fitness=calcFitness(hijo);
                if(hijo.fitness>bestCrom.fitness) bestCrom=hijo;
                auxFitnesstotal+=hijo.fitness;
                offspring.add(hijo);
            }
            fitnessTotal=auxFitnesstotal;
            //System.out.println("generacion-"+i+" Fitnessprom= "+fitnessTotal/maxPoblacion);
            for(int h=0;h<maxPoblacion;h++) // reemplazo de nueva generacion
                cromosomas.set(h, offspring.get(h));
        }
        
        for(int i=0;i<bestCrom.alelos.size();i++){
            Ruta rutasPacki=bestCrom.alelos.get(i);
            System.out.print("Paquete "+i+":");
            Aeropuerto aeroPackO=rutasPacki.vuelos.get(0).getAeroOrig(); // asigno espacio usado
            Aeropuerto aeroPackF=rutasPacki.vuelos.get(0).getAeroFin();
            aeroPackO.setCantEspacioUsado(aeroPackO.getCantEspacioUsado()+1);
            aeroPackF.setCantEspacioUsado(aeroPackF.getCantEspacioUsado()+1);
            for(int j=0;j<rutasPacki.vuelos.size();j++){
                Vuelo vuelo= rutasPacki.vuelos.get(j);
                System.out.print(vuelo.getOrigen()+"-"+vuelo.getDestino()+"//");
                if(j>0){
                    Aeropuerto aeroDest=rutasPacki.vuelos.get(j).getAeroFin();
                    aeroDest.setCantEspacioUsado(aeroDest.getCantEspacioUsado()+1);
                }
            }
            System.out.println("------Tiempo: "+bestCrom.tiempos.get(i));
            
        }
    }
    
    public void mutacion(Cromosoma crom){
        if(Math.random()<=probMutacion){// prob mutation
            Random paqueteRand = new Random();
            Random rutaRand = new Random();
            int cromNumber = rutaRand.nextInt(maxPoblacion);
            int aleloNumber = paqueteRand.nextInt(crom.alelos.size());
            crom.alelos.set(aleloNumber, cromosomas.get(cromNumber).alelos.get(aleloNumber));
        }    
    }
    public Cromosoma crossover(Cromosoma padre,Cromosoma madre,int nPaquetes) {
        
        Cromosoma hijo=new Cromosoma();
       
        int posIni = (int) (Math.random() * nPaquetes);
        int aux=posIni;
        int posFin = (int) (Math.random() * nPaquetes);
        if(posIni>posFin){
            posIni=posFin;
            posFin=aux;
        }
        for(int i=0;i<nPaquetes;i++) {
            hijo.alelos.add(madre.alelos.get(i));
            hijo.pedidos.add(madre.pedidos.get(i));
        } // inicializo hijo con madre
        //System.out.println(posIni);
        //System.out.println(posFin);
        for (int i = 0; i < nPaquetes; i++) {
            if(i<posIni || i>posFin) {
                hijo.alelos.set(i,padre.alelos.get(i));
                hijo.pedidos.set(i,padre.pedidos.get(i) );
            } //hereda del padre
        }
        return hijo;       
    } 
    public int generarPoblacion(ArrayList<Pedido> pedidos,ArrayList<Aeropuerto> aeropuertos){
        int fitnessTotal=0;
        for(int i=0;i<maxPoblacion;i++){
            Cromosoma crom= new Cromosoma();
            for(int j=0;j<pedidos.size();j++){
                ArrayList<Ruta> rutasOF=generarRutasOF(pedidos.get(j).getOrigen(),
                                        pedidos.get(j).getDestino(),aeropuertos,pedidos.get(j).getHora());
                Random rn = new Random();
                for(int h=0;h<pedidos.get(j).getCant();h++){             
                    crom.alelos.add(rutasOF.get(rn.nextInt(rutasOF.size()))); //generamos aleatoriamente su ruta
                    crom.pedidos.add(pedidos.get(j));
                }
                
            }
            int fitness=calcFitness(crom);
            fitnessTotal+=fitness;
            crom.fitness=fitness;
            cromosomas.add(crom);
        }
        return fitnessTotal;
    }
    public int calcFitness(Cromosoma crom){
        int fitness=0;
        int tiempoTotal=0;
        for(int i=0;i<crom.alelos.size();i++){
            Ruta ruta= crom.alelos.get(i);
            int tiempo=0;
            //System.out.println(crom.pedidos.size());
            tiempo+=ruta.vuelos.get(0).gethSalida()-crom.pedidos.get(i).getHora();
            if(tiempo<0) tiempo+=24;
            for(int j=0;j<ruta.vuelos.size();j++){
                Vuelo vuelo=ruta.vuelos.get(j);
                tiempo+=vuelo.getTiempo();//se suma tiempo de vuelo

                if(j>0){ // si hay escala
                    int tEspera=vuelo.gethSalida()-ruta.vuelos.get(j-1).gethLlegada();//tiempo de espera
                    if(tEspera<0) tEspera+=24; // en caso el vuelo sea tomado el dia siguiente
                    tiempo+=tEspera;
                }
            }
            tiempoTotal+=tiempo;
            crom.tiempos.add(tiempo);           
        }
        fitness=48*crom.alelos.size()-tiempoTotal;
        return fitness;
    }
    public ArrayList<Ruta> generarRutasOF(String origen, String fin,
                                ArrayList<Aeropuerto> aeropuertos,int hora){
        ArrayList<Ruta> rutasOF= new ArrayList<>();
        Boolean esCont=esContinental(origen,fin,aeropuertos);
        int tMax=(esCont)?24:48;
        for(int i=0;i<aeropuertos.size();i++){
            if(origen.equals(aeropuertos.get(i).getCodAeropuerto())){ // aeropuerto1 es origen
                for(int j=0;j<aeropuertos.get(i).vuelos.size();j++){
                    Vuelo vuelo1=aeropuertos.get(i).vuelos.get(j);
                    Aeropuerto aero2=vuelo1.getAeroFin();
                    if(aero2.getCodAeropuerto().equals(fin)){ //si aeropuerto 2 es el destino
                        Ruta ruta1 = new Ruta();
                        ruta1.vuelos.add(vuelo1);
                        rutasOF.add(ruta1);                        
                    }                    
                    if(esCont) continue;
                    for(int h=0;h<aero2.vuelos.size();h++){
                        Vuelo vuelo2=aero2.vuelos.get(h);
                        Aeropuerto aero3=vuelo2.getAeroFin();
                        
                        if(aero3.getCodAeropuerto().equals(fin)){
                            int tiempo=0;
                            tiempo+=vuelo1.gethSalida()-hora;
                            if(tiempo<0) tiempo+=24;
                            tiempo+=vuelo1.getTiempo();
                            int tEspera=vuelo2.gethSalida()-vuelo1.gethLlegada();//tiempo de espera
                            if(tEspera<0) tEspera+=24; // en caso el vuelo sea tomado el dia siguiente    
                            tiempo+=tEspera;
                            tiempo+=vuelo2.getTiempo();
                            
                            if(tiempo>tMax) continue;
                            Ruta ruta2 = new Ruta();
                            ruta2.vuelos.add(vuelo1);
                            ruta2.vuelos.add(vuelo2);  
                            rutasOF.add(ruta2);                           
                        }

                    }
                }
            }
        }
        return rutasOF;
    }    
    public Boolean esContinental(String origen,String fin,ArrayList<Aeropuerto> aeropuertos){
        Boolean cont=false;
        int nOrig=-1,nfin=-1;
        for(int i=0;i<aeropuertos.size();i++){
            if(aeropuertos.get(i).getCodAeropuerto().equals(origen)) nOrig=i;
            if(aeropuertos.get(i).getCodAeropuerto().equals(fin)) nfin=i;
            if(nOrig>=0 && nfin>=0) break;
        }
        if(aeropuertos.get(nOrig).getContinente().equals(aeropuertos.get(nfin).getContinente())) // si son mismo continente
            cont=true;
        return cont;
    }
   /*
    public ArrayList<Ruta> generarRutasOF(String origen, String fin,
                                ArrayList<Aeropuerto> aeropuertos){
        ArrayList<Ruta> rutasOF= new ArrayList<>();
        for(int i=0;i<aeropuertos.size();i++){
            if(origen.equals(aeropuertos.get(i).getCodAeropuerto())){
                for(int j=0;j<aeropuertos.get(i).vecinos.size();j++){
                    Aeropuerto aeroJ=aeropuertos.get(aeropuertos.get(i).vecinos.get(j));
                    if(aeroJ.getCodAeropuerto().equals(fin)){
                        Ruta ruta2 = new Ruta();
                        ruta2.aeropuertos.add(aeropuertos.get(i));
                        ruta2.aeropuertos.add(aeroJ);
                        rutasOF.add(ruta2);                        
                    }                    
                    
                    for(int h=0;h<aeroJ.vecinos.size();h++){
                        Aeropuerto aeroH=aeropuertos.get(aeroJ.vecinos.get(h));
                        if(aeroH.getCodAeropuerto().equals(fin)){
                            Ruta ruta3 = new Ruta();
                            ruta3.aeropuertos.add(aeropuertos.get(i));
                            ruta3.aeropuertos.add(aeroJ);  
                            ruta3.aeropuertos.add(aeroH);
                            rutasOF.add(ruta3);                           
                        }

                    }
                }
            }
        }
        return rutasOF;
    }
    */

}
