package tp2parcial;

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
    
    
    
}



class TP2Parcial {

    
    public static void main(String[] args) {
        
    }
    
}
