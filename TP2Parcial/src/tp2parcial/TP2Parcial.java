package tp2parcial;

import java.util.List;
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
    
    /*
    ACLARACION: NO SE ESTA UTILIZANDO LIBRERIAS EXTERNAS, REGEX (EXPRESIONES REGULARES) SON
    SECUENCIAS DE CARACTERES QUE BRINDA PATRONES PARA POSIBLES COINCIDENCIA DE PALABRAS, 
    SE UTILIZA LA FUNCION matches() NATIVO DE STRING PARA COMPARAR PALABRAS CON LAS REGEX.
    */
    private Nodo raiz;
    private Nodo nodoMR; //Nodo (palabra) que mas se repite, sin tener en cuenta monosílabos o articulos
    private String regexMonosilabas = "[b-df-hj-np-tv-xyzñ]*([aeiouáéíóúü]+)[b-df-hj-np-tv-xyzñ]*"; //se utiliza un regex para filtrar monosilabas (ej. sol)
    private List<String> articulos = List.of("el", "la", "los", "las", "un", "una", "unos", "unas", "lo", "al", "del", "para", "como", "que", "con"); //se utiliza una lista de Strings para los articulos
    private List<String> PPoPN = List.of(
    "realmente", "literalmente", "siempre",
    "basicamente", "obviamente", "claramente", "cosa", "fui", "osea", "súper", "entre", 
    "otras","totalmente", "absolutamente", "fundamentalmente",
    "digamos", "tipo", "viste", "cosas", "tema", "temas",
    "cuestion", "cuestiones", "hecho", "hechos"
    ); //Se pone un extenso listado de palabras poca inteligentes o palabras negativas
    
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
            
        }//ayuda a cerrar la recursividad
        
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
        nodoMR = null;
        
    }
    
    /**
     * Es para contar la cantidad de nodos (cantidad total de palabras sin repetirse)
     * @return retorna la acumulacion de nodos (todos) utiliza recursividad
     */
    
    public int cantidadNodos() {
        return cantidadRecursiva(raiz);
    }
    
    /**
     * 
     * @param nodo se brinda todos los nodos de forma recursiva hasta el final (para sumar su totalidad)
     * @return retorna el nodo actual + todos los nodos a la izquierda y derecha
     */
    
    private int cantidadRecursiva(Nodo nodo) {
        
        if (nodo == null) {
            return 0;
        }//ayuda a cerrar la recursividad
        
        return 1 + cantidadRecursiva(nodo.getIzquierdo()) + cantidadRecursiva(nodo.getDerecho());
        
    }

    /**
     * Este get es importante porque primero se debe cumplir la funcion masRepetido(Nodo nodo) y luego retorna el resultado
     * @return se retorna el nodo con mas frecuencia
     */
    public Nodo getNodoMR() {
        masRepetido(raiz);
        return nodoMR;
    }
    /**
     * La funcion se utiliza para determinar el nodo con mas frecuencia (la palabra que mas se repite).
     * Se utiliza el recorrido InOrden.
     * @param nodo brinda el nodo actual de la funcion recursiva
     */
    private void masRepetido(Nodo nodo) {
        
        if (nodo != null) {
            
            masRepetido(nodo.getIzquierdo());
            /**
             * Se tomara solamente las palabras que no sean monosilabas ni articulos
             */
            if (!articulos.contains(nodo.getPalabra()) && !nodo.getPalabra().matches(regexMonosilabas)) {
                /**
                 * Va comparando los contadores de los nodos para saber cual es el mayor y lo almacena en nodoMR
                 */
                if (nodoMR == null || nodo.getContador() > nodoMR.getContador()) {
                    nodoMR = nodo;
                }
                
            }
            
            masRepetido(nodo.getDerecho());
            
        }
 
    }
    
    /**
     * Se utiliza esta funcion intermedia para brindar la cantidad de palabras negativas en el discurso
     * Se toma la comparacion de un listado dentro de la clase arbol
     * @return retorna un arreglo de int, donde la primera posicion toma la cantidad de palabras negativas con repeticion y la segunda sin repetir
     * 
     */
    public int[] cantidadNegativas() {
        
        return negativasRecursiva(raiz);
        
    }

    /**
     * Funcion interna que utiliza la recursividad para generar un recorrido de PreOrden y calcular la cantidad de palabras
     * negativas segun frecuencia y sin repetir, termina la recursividad con el if(nodo == null)
     * @param nodo nodo actual dentro de la recursividad
     * @return retorna un arreglo de int, donde la primera posicion toma la cantidad de palabras negativas con repeticion y la segunda sin repetir
     */
    private int[] negativasRecursiva(Nodo nodo) {
        
        int[] sumar = {0,0};
        
        if (nodo == null) {
            return sumar;
        }
        
        
        /*
        La posicion 0 toma la cantidad de palabras negativas con su frecuencia(repeticion), 
        La posicion 1 toma la cantidad de palabras negativas sin tomar en cuenta si se repiten o no
        */
        if (PPoPN.contains(nodo.getPalabra())) {
            
            sumar[0] += nodo.getContador();
            sumar[1]++;
            
        }
        
        int[] izquierdo = negativasRecursiva(nodo.getIzquierdo());
        int[] derecho = negativasRecursiva(nodo.getDerecho());
        
        sumar[0] += izquierdo[0] + derecho[0];
        sumar[1] += derecho[1] + izquierdo[1];
        
        return sumar;
        
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
        int opcion = -1, cantidadDePalabras = 0;
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
                    cantidadDePalabras = entradaSeparada.length;
                    for (String ps : entradaSeparada) {
                        arbolABB.insertarNodo(ps);
                    }
                    
                    break;
                case 2:
                    if (entrada!=null) {
                        JOptionPane.showMessageDialog(null, entrada
                                + "\nLa cantidad de palabras en su totalidad es : \n"+ cantidadDePalabras +
                                "\nLa cantidad de palabras sin repetirse es : " + arbolABB.cantidadNodos());
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un texto primero");
                    }
                    
                    break;
                case 3:
                    if (entrada!=null) {
                        try {
                            if (arbolABB.getNodoMR().getContador() > 1) {
                                JOptionPane.showMessageDialog(null,entrada +"\nLa palabra que mas se repite en el discurso es:\n " + arbolABB.getNodoMR().getPalabra());
                            } else {
                                JOptionPane.showMessageDialog(null,entrada +"\nNo hay ninguna palabra que se repita en este discurso");
                            }
                            
                        } catch (java.lang.NullPointerException e) {
                            JOptionPane.showMessageDialog(null, "No hay palabras que no sean articulos o monosilabas, por lo tanto no se puede saber cual es la mas frecuente, coloca otro discurso");
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un texto primero");
                    }
                    
                    break;
                case 4:
                    if (entrada != null) {
                        JOptionPane.showMessageDialog(null, entrada
                                + "\nLa cantidad de palabras negativas tomando en cuenta la repeticion es : \n"+ arbolABB.cantidadNegativas()[0] +
                                "\nLa cantidad de palabras negativas sin repetirse es : " + arbolABB.cantidadNegativas()[1]);
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
