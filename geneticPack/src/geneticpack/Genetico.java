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
    private int maxPoblacion = 100; // maximo numero de soluciones posibles
    private int maxGeneraciones=30; // maxiteraciones
    private double probMutacion=0.01;
    private int horaSist;
    private int diaSist;
    private String []diasSemana={"Sab","Dom","Lun","Mar","Mie","Jue","Vie",};
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
        this.horaSist=hora;
        this.diaSist=dia;
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
                    //System.out.println("gen "+j+"-"+h+":  "+gen.espacioLibre);
                    actualizarCaps(gen,pedidos.get(j).dia,pedidos.get(j).hora,pedidos.get(j).min);
                    //System.out.println("gen "+gen.espacioLibre);
                    crom.genes.add(gen); //generamos aleatoriamente su ruta
                }
                
            }
            reiniciarCapsDeciudades(ciudades);
            //crom.print();
            int fitness=calcFitness(crom);
            System.out.println("fit "+i+": "+fitness);
            fitnessTotal+=fitness;
            //System.out.println("fitness total "+i+": "+fitnessTotal);
            crom.fitness=fitness;
            cromosomas.add(crom);
        }
        //System.out.println("fitnessTotal antes de enviar :"+fitnessTotal);
        return fitnessTotal;
    }
    
    public void reiniciarCapsDeciudades(TreeMap<String,Ciudad> ciudades){
        for(Ciudad ciudad: ciudades.values()){
            ciudad.reiniciarCaps();
        }
    }
    
    public int calcFitness(Cromosoma crom){
        int fitness;
        int tiempoTotal=0;
        int espaciolibre=0;
        for(int i=0;i<crom.genes.size();i++){
            Gen gen=crom.genes.get(i);
            tiempoTotal+=gen.tiempo;
            espaciolibre+=gen.espacioLibre;
        }
        //System.out.println("espa: "+espaciolibre);
        //fitness=1*espaciolibre-5*tiempoTotal;
        fitness=4*48-4*tiempoTotal/crom.genes.size();
        //System.out.println("fit: "+fitness);
        
        return fitness;
    }
    public void actualizarCaps(Gen gen,int diaP,int horaP,int minP){ // este es hora y min del pedido////// dia-hora:00 / dia-hora:01
        Ruta ruta=gen.ruta;
        int diaK=diaP;
        for(int i=0;i<ruta.vuelos.size();i++){
            Vuelo vuelo=ruta.vuelos.get(i);
            Ciudad ciudadOrig=vuelo.getAeroOrig();
            Ciudad ciudadFin=vuelo.getAeroFin();
            int hSalida=vuelo.gethSalida();
            int hLlegada=vuelo.gethLlegada();
            int horaKey;
            
            
            //registramos su ingreso
            if(i==0){             
                if(hSalida<horaP) hSalida+=24;
                
                for(int hora=horaP;hora<=hSalida;hora++){
                    horaKey=hora%24;
                    if(hora!=horaP && horaKey==0) diaK++; // se paso al día siguiente
                    diaK=diaK%7;
                    String key=diasSemana[diaK]+"-"+horaKey+":00";
                    ciudadOrig.capTime.put(key,ciudadOrig.capTime.get(key)-1);  
                    if(hora!=hSalida){
                        String key2=diasSemana[diaK]+"-"+horaKey+":01";
                        ciudadOrig.capTime.put(key2,ciudadOrig.capTime.get(key2)-1);                       
                    }
                }
                //String keygen=diasSemana[diaK]+"-"+vuelo.gethSalida()+":01";
                //gen.espacioLibre+=ciudadOrig.capTime.get(keygen);                
            }
            
            //registramos su escala si lo hubiera
            if(i==1){
                 int hLlegadaEscala=ruta.vuelos.get(0).gethLlegada(); 
                 int hSalidaDeOrigen=ruta.vuelos.get(0).gethSalida(); 
                 int diasTrans=(hSalidaDeOrigen+ruta.vuelos.get(0).getTiempo()+
                         ruta.vuelos.get(0).getAeroFin().huso
                         -ruta.vuelos.get(0).getAeroOrig().huso)%24;
                 diaK=(diaK+diasTrans)%7; //se determina que dia llego a la ciudad escala
                 if(hSalida<hLlegadaEscala) hSalida+=24; // si la salida es al dia siguiente
                 for(int hora=hLlegadaEscala;hora<=hSalida;hora++){
                    horaKey=hora%24;
                    if(hora!=hLlegadaEscala && horaKey==0) diaK++; // se paso al día siguiente
                    diaK=diaK%7;
                    String key=diasSemana[diaK]+"-"+horaKey+":00";
                    ciudadOrig.capTime.put(key,ciudadOrig.capTime.get(key)-1);  
                    if(hora!=hSalida){
                        String key2=diasSemana[diaK]+"-"+horaKey+":01";
                        ciudadOrig.capTime.put(key2,ciudadOrig.capTime.get(key2)-1);                       
                    }                 
                 }
                String keygen=diasSemana[diaK]+"-"+vuelo.gethSalida()+":01";
                gen.espacioLibre+=ciudadOrig.capTime.get(keygen);                    
            }
            
            // registramos fin del destino de paquete
            
            if(i==ruta.vuelos.size()-1){
                 int diasTrans=(hSalida+ruta.vuelos.get(i).getTiempo()+
                         ruta.vuelos.get(i).getAeroFin().huso
                         -ruta.vuelos.get(i).getAeroOrig().huso)%24;
                 diaK=(diaK+diasTrans)%7; //se determina que dia llego a la ciudad escala                
                String Key=diasSemana[diaK]+"-"+vuelo.gethLlegada()+":00";
                ciudadFin.capTime.put(Key, ciudadFin.capTime.get(Key)-1);
            }

        }
    }
    
}
