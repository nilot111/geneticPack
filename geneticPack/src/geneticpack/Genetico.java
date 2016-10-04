/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpack;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author GUERRA
 */
public class Genetico {
    private int maxPoblacion = 200; // maximo numero de soluciones posibles
    private int maxGeneraciones=50; // maxiteraciones
    private double probMutacion=0.01;
    private int hora;
    private int dia;
    private String []dias={"Lun","Mar","Mie","Jue","Vie","Sab","Dom"}; // dia -2 
    private ArrayList<Ruta> universoRutas= new ArrayList<>();
    private ArrayList<Cromosoma> cromosomas= new ArrayList<>();
    Genetico(){
        
    }
    public void ejecutar(TreeMap<String,Ciudad> aeropuertos, ArrayList<Vuelo> vuelos,
                         ArrayList<Pedido> pedidos,int hora,int dia){
        //generarRutas(aeropuertos);
        int fitnessTotal=generarPoblacion(pedidos,aeropuertos);
        System.out.println("fitness Total: "+fitnessTotal);
        //ArrayList<Ruta> rutas= generarRutasOF("SPIM","SGAS",aeropuertos);
        //for(int i=0;i<rutas.size();i++) rutas.get(i).print();
        reproduccion(fitnessTotal);
        this.hora=hora;
        this.dia=dia;
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
                                        ,cromosomas.get(0).genes.size());
                mutacion(hijo);
                hijo.fitness=calcFitness(hijo);
                if(hijo.fitness>bestCrom.fitness) bestCrom=hijo;
                auxFitnesstotal+=hijo.fitness;
                offspring.add(hijo);
            }
            fitnessTotal=auxFitnesstotal;
            System.out.println("generacion-"+i+" Fitnessprom= "+fitnessTotal/maxPoblacion);
            for(int h=0;h<maxPoblacion;h++) // reemplazo de nueva generacion
                cromosomas.set(h, offspring.get(h));
        }
        /*
        int tiempoTotal=0;
        for(int i=0;i<bestCrom.genes.size();i++){
            Ruta rutasPacki=bestCrom.genes.get(i).ruta;
            
            System.out.print("Paquete "+i+":");
            //Aeropuerto aeroPackO=rutasPacki.vuelos.get(0).getAeroOrig(); 
            //Aeropuerto aeroPackF=rutasPacki.vuelos.get(0).getAeroFin();
            //aeroPackO.setCantEspacioUsado(aeroPackO.getCantEspacioUsado()+1);// asigno espacio usado
            //aeroPackF.setCantEspacioUsado(aeroPackF.getCantEspacioUsado()+1);
            for(int j=0;j<rutasPacki.vuelos.size();j++){
                Vuelo vuelo= rutasPacki.vuelos.get(j);
                System.out.print(vuelo.getOrigen()+"-"+vuelo.getDestino()+"//");
                if(j>0){
                    Aeropuerto aeroDest=rutasPacki.vuelos.get(j).getAeroFin();
                    aeroDest.setCantEspacioUsado(aeroDest.getCantEspacioUsado()+1);
                }
            }
            System.out.println("------Tiempo: "+bestCrom.genes.get(i).tiempo);
            tiempoTotal+=bestCrom.genes.get(i).tiempo;
            
        }
        System.out.println("Tiempo total de entrega de paquetes: "+tiempoTotal);
        */
    }
    
    public void mutacion(Cromosoma crom){
        if(Math.random()<=probMutacion){// prob mutation
            Random paqueteRand = new Random();
            Random rutaRand = new Random();
            int cromNumber = rutaRand.nextInt(maxPoblacion);
            int aleloNumber = paqueteRand.nextInt(crom.genes.size());
            crom.genes.set(aleloNumber, cromosomas.get(cromNumber).genes.get(aleloNumber));
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
            hijo.genes.add(madre.genes.get(i));
        } // inicializo hijo con madre
        //System.out.println(posIni);
        //System.out.println(posFin);
        for (int i = 0; i < nPaquetes; i++) {
            if(i<posIni || i>posFin) {
                hijo.genes.set(i,padre.genes.get(i));
            } //hereda del padre
        }
        return hijo;       
    } 
    public int generarPoblacion(ArrayList<Pedido> pedidos,TreeMap<String,Ciudad> ciudades){
        int fitnessTotal=0;
        for(int i=0;i<maxPoblacion;i++){
            Cromosoma crom= new Cromosoma();
            for(int j=0;j<pedidos.size();j++){
                String origen=pedidos.get(j).getOrigen();
                String destino=pedidos.get(j).getDestino();
                ArrayList<Ruta> rutasOF=ciudades.get(origen).rutas.get(destino);
                Random rn = new Random();
                for(int h=0;h<pedidos.get(j).getCant();h++){       // por paquete se genera un alelo     
                    Ruta ruta=rutasOF.get(rn.nextInt(rutasOF.size()));
                    Gen gen= new Gen();
                    gen.ruta=ruta;
                    gen.tiempo=ruta.tiempo;
                    gen.pedido=pedidos.get(j);
                    actualizarCaps(ruta,pedidos.get(j).dia,pedidos.get(j).hora,pedidos.get(j).min);
                    crom.genes.add(gen); //generamos aleatoriamente su ruta
                }
                
            }
            //crom.print();
            int fitness=calcFitness(crom);
            //System.out.println(fitness);
            fitnessTotal+=fitness;
            //System.out.println(fitnessTotal);
            crom.fitness=fitness;
            cromosomas.add(crom);
        }
        return fitnessTotal;
    }
    public int calcFitness(Cromosoma crom){
        int fitness;
        int tiempoTotal=0;
        for(int i=0;i<crom.genes.size();i++){
            tiempoTotal+=crom.genes.get(i).tiempo;
        }
        //System.out.println(tiempoTotal);
        fitness=10000*crom.genes.size()-4*tiempoTotal;
        return fitness;
    }
    public void actualizarCaps(Ruta ruta,int dia,int hora,int min){
        for(int i=0;i<ruta.vuelos.size();i++){
            Vuelo vuelo=ruta.vuelos.get(i);
            
        }
    }
}
