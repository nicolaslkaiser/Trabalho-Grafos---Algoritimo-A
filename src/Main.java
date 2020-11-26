
//TRABALHO 2 GRAFOS - ALGORITIMO A*
//INTEGRANTES: MATHEUS BANISKI, NICOLAS KAISER, VINICIUS GARCIA

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        int i = 1;

        int inicial_linha;
        int inicial_coluna;
        int final_linha;
        int final_coluna;

        System.out.println("\n-------------VERTICE INICIAL-------------");
        System.out.println("NUMERO DE LINHAS: ");
        inicial_linha = teclado.nextInt();
        System.out.println("NUMERO DE COLUNAS: ");
        inicial_coluna = teclado.nextInt();

        System.out.println("\n-------------VERTICE FINAL-------------");
        System.out.println("NUMERO DE LINHAS: ");
        final_linha = teclado.nextInt();
        System.out.println("NUMERO DE COLUNAS: ");
        final_coluna = teclado.nextInt();

        Vertice vertice_inicial = new Vertice(inicial_linha,inicial_coluna);
        Vertice vertice_final = new Vertice(final_linha,final_coluna);

        AlgoritimoA teste = new AlgoritimoA(6,7,vertice_inicial,vertice_final);

        int[][] paredes = new int[][]{{1,3} , {2,3} , {3,3}};

        teste.SetParedes(paredes);

        List<Vertice> caminho = teste.AcharCaminho();

        System.out.println("\nMELHOR CAMINHO ENTRE AS COORDENADAS [" + inicial_linha + "," + inicial_coluna + "] PARA [" + final_linha + "," + final_coluna + "]");

        // PEGA DA LISTA CAMINHO POIS CONTEM OS VERTICES QUE FORAM UTILIZADOS PARA O MELHOR CAMINHO ENCONTRADO
        for (Vertice vertice : caminho) {
            System.out.println("PASSO " + i + ": " + vertice);
            i++;
        }

    }
}
