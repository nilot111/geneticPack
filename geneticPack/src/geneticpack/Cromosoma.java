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
    public ArrayList<Ruta> alelos = new ArrayList<>();
    public ArrayList<Integer> tiempos = new ArrayList<>();
    public ArrayList<Pedido> pedidos = new ArrayList<>();
    public int fitness=0;
}
