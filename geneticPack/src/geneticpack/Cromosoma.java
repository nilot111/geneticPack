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
public class Cromosoma {
    public ArrayList<Gen> genes = new ArrayList<>();
    public int fitness=0;
    
    public void print(){
        for(int i=0;i<genes.size();i++){
            Gen gen=genes.get(i);
            gen.ruta.print();
        }
    }
}
