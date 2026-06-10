package tp2parcial;

import javax.swing.JOptionPane;

/**
 * Esta clase representa cada nodo del arbol.
 * Permite saber qué palabras se utilizaron y su frecuencia en el texto.
*/
class Nodo {
    private String palabra;
    private int contador;
    private Nodo izquierdo;
    private Nodo derecho;
    
    /**
     * 
     * @param palabra se guardaran las palabras utilizadas en el texto, una palabra por nodo.   
     */
    
    public Nodo(String palabra) {
        this.palabra = palabra;
        this.contador = 1;
        this.izquierdo = null;
        this.derecho = null;
        
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getContador() {
        return contador;
    }

    public void sumarContador() {
        contador++;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }   
    
}

/**
 * Es un arbol binario de busqueda, las palabras se guardaran segun orden alfabetico.
 * Se utiliza este arbol porque es mas eficiente para la busqueda de palabras y contar su frecuencia.
 * Tiene una eficiencia de O(log n)
 */

class Arbol {
    
    private Nodo raiz;
    /**
     * Se utiliza la funcion insertarNodo de manera publica para utilizarse en el sistema
     * Se realiza insercion por recursividad (inRecursivo), es decir, se va repitiendo la funcion en si misma hasta que se cumpla la condicion
     * @param palabra se inserta la palabra en el nodo 
     */
    public void insertarNodo(String palabra) {
        raiz = inRecursivo(raiz, palabra);
    }
    /**
     * 
     * @param nodo envia el nuevo nodo a implementar en el arbol
     * @param palabra envia la palabra a guardar en el nodo
     * @return retorna el nodo siguiente de la comparacion para su recursividad, el mismo nodo enviado, o crea un nuevo nodo si se le envia un null como nodo
     */
    private Nodo inRecursivo(Nodo nodo, String palabra) {
        

        if (nodo == null) {
            
            return new Nodo(palabra);
            
        }
        
        int comparacion = palabra.compareTo(nodo.getPalabra()); //0 si es igual (suma frecuencia), -1 si es menor alfabeticamente, 1 si es mayor
        
        if (comparacion == 0) {
            
           nodo.sumarContador();
            
        } else if (comparacion < 0) {
            
            nodo.setIzquierdo(inRecursivo(nodo.getIzquierdo(),palabra));
            
        } else {
            
            nodo.setDerecho(inRecursivo(nodo.getDerecho(),palabra));
            
        } 
        
        return nodo;
    }
    
    /**
     * Como indica su nombre, es para liberar todo nodo creado del arbol.
     * Se utlizara principalmente cuando se quiera trabajar con un nuevo discurso.
     */
    
    public void liberarArbol() {
        
        raiz = null;
        
    }

    
}


/**
 * esta clase contendrá el main donde se llevará a cabo el menu para las distintas opciones del sistema
 * 
 * 
 */
class TP2Parcial {
    
    public static void main(String[] args) {
        
        Arbol arbolABB = new Arbol();
        int opcion = -1;
        String entrada = null;
        String sistema = "#########################################\n"
                + "____________________________________\n"  
                + "Dime como hablas y sabré muchas cosas\n"
                + "____________________________________\n"
                + "#########################################\n";
        JOptionPane.showMessageDialog(null, sistema);
        
        do {
            
            try {
                opcion =Integer.parseInt(JOptionPane.showInputDialog("Se brinda las opciones disponibles:"
                        + "\n1.Ingresar un discurso"
                        + "\n2.Saber la cantidad de palabras usadas"
                        + "\n3.Saber la palabra que más se repite"
                        + "\n4.Saber cuántas palabras negativas o poco inteligentes se usan"
                        + "\n0.Salir", "1"));
            } catch (java.lang.NumberFormatException e) {
                opcion = -1;
            }
            
            switch (opcion) {
                
                case 1:
                    entrada = JOptionPane.showInputDialog("Ingrese el discurso deseado : ");
                    while(entrada.length()<6) {
                        entrada = JOptionPane.showInputDialog("Coloque un discurso mas largo por favor : ");
                    }
                    
                    arbolABB.liberarArbol(); //MUY IMPORTANTE LIBERARLO, SI NO SIGUE AGREGANDO NODOS
                    
                    
                    /**
                     * Se crea un arreglo de String para tomar la entrada pero por palabras.
                     * Se toma en cuenta que todo quede en minuscula (esto ayuda a las comparaciones)
                     * Además, se utiliza split (para separar) con el regex \W* (https://www.regexroo.com/word-characters) que se utiliza para separar las palabras, sin tomar en cuenta signos de puntuacion ni espacios.
                     * Con un for each se agregaran todas las palabras al arbol mediante su funcion insertarNodo
                     */
                    String[] entradaSeparada = entrada.toLowerCase().split("\\W+");
                    for (String ps : entradaSeparada) {
                        arbolABB.insertarNodo(ps);
                    }
                    
                    break;
                case 2:
                    if (entrada!=null) {
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un texto primero");
                    }
                    
                    break;
                case 3:
                    if (entrada!=null) {
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un texto primero");
                    }
                    
                    break;
                case 4:
                    if (entrada != null) {

                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un texto primero");
                    }
                    
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "POR FAVOR COLOQUE UN VALOR VALIDO, ENTRE LA OPCION 0 AL 4");
                
            }
            
            
        } while(opcion != 0);
        
        sistema = "#########################################\n"
                + "____________________________________\n"  
                + "GRACIAS POR UTILIZAR NUESTRO SISTEMA\n"
                + "____________________________________\n"
                + "#########################################\n";
        JOptionPane.showMessageDialog(null, sistema);
        
        
    }
    
}
