
//TRABALHO 2 GRAFOS - ALGORITIMO A*
//INTEGRANTES: MATHEUS BANISKI, NICOLAS KAISER, VINICIUS GARCIA

import java.util.*;

public class AlgoritimoA {

    // CRIACAO DO ALGORITIMO LEVANDO EM CONTA UMA MATRIZ DE L LINHAS E C COLUNAS, AO QUAL PODE POSSUIR OBSTACULOS ENTRE OS VERTICES

    public static final int custo_horizontal_vertical = 10; // DEFINIDO PARA O CUSTO HORIZONTAL E VERTICAL A SER COMPARADO DO VERTICE ATUAL
    public static final int custo_diagonal = 14; // DEFINIDO PARA O CUSTO DIAGONAL A SER COMPARADO COM O VERTICE ATUAL
    public int custo_hv;
    public int custo_d;

    public Vertice[][] matriz;
    public PriorityQueue<Vertice> lista_aberta;
    public Set<Vertice> lista_fechada;

    public Vertice vertice_inicial;
    public Vertice vertice_final;


    //---------------------CONSTRUTOR---------------------

    public AlgoritimoA (int linha, int coluna, Vertice vertice_inicial, Vertice vertice_final, int custo_hv, int custo_d){
        this.custo_hv = custo_hv;
        this.custo_d = custo_d;
        setVertice_inicial(vertice_inicial);
        setVertice_final(vertice_final);
        this.matriz = new Vertice[linha][coluna];
        this.lista_aberta = new PriorityQueue<Vertice>(new Comparator<Vertice>() {
            @Override
            public int compare(Vertice vertice_1, Vertice vertice_2) {
                return Integer.compare(vertice_1.custo_final , vertice_2.custo_final); // COMPARA OS CUSTOS DE DOIS VERTICES
            }
        });
        SetVertices();
        this.lista_fechada = new HashSet<>();

    }

    // NOVO CONSTRUTOR AO QUAL PASSAMOS PARA A MAIN, JÁ QUE OS CUSTOS FORAM PREVIAMENTE DEFINIDOS

    public AlgoritimoA(int linha, int coluna, Vertice vertice_inicial, Vertice vertice_final){
        this(linha,coluna,vertice_inicial,vertice_final,custo_horizontal_vertical,custo_diagonal);
    }

    //---------------------GETTERS E SETTERS---------------------


    public int getCusto_hv() {
        return custo_hv;
    }

    public int getCusto_d() {
        return custo_d;
    }

    public Vertice[][] getMatriz() {
        return matriz;
    }

    public PriorityQueue<Vertice> getLista_aberta() {
        return lista_aberta;
    }

    public Set<Vertice> getLista_fechada() {
        return lista_fechada;
    }

    public void setVertice_inicial(Vertice vertice_inicial) {
        this.vertice_inicial = vertice_inicial;
    }

    public Vertice getVertice_final() {
        return vertice_final;
    }

    public void setVertice_final(Vertice vertice_final) {
        this.vertice_final = vertice_final;
    }

    public void setParede(int linha, int coluna){
        this.matriz[linha][coluna].setParede(true);
    }

    public boolean Vazia(PriorityQueue<Vertice> lista_aberta){
        return lista_aberta.size() == 0;
    }

    public boolean VerticeFinal(Vertice vertice_atual){
        return vertice_atual.equals(vertice_final);
    }


    //---------------------METODOS---------------------

    // DEFINE OS VERTICES DA MATRIZ BEM COMO O CALUCULO DE SEUAS HEURISTICAS BASEADO NO VERTICE FINAL
    public void SetVertices(){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                Vertice vertice = new Vertice(i,j);
                vertice.Calcular_Heuristica(getVertice_final());
                this.matriz[i][j] = vertice;
            }
        }
    }

    // DEFINE UM ARRAY DE PAREDES (OBSTACULOS) DA MATRIZ QUE SERA PERCORRIDA
    // UM ARRAY DE PAREDES POSSUI X PAREDES, CADA PAREDE POSSUI SUAS COORDENADAS DE LINHA E COLUNA, A LINHA VEM DO ELEMENTO I POSICAO ZERO E A COLUNA
    // DO ELEMENTO I POSISAO 1
    public void SetParedes(int[][] paredeArray){
        for (int i = 0; i < paredeArray.length ; i++) {
            int linha = paredeArray[i][0];
            int coluna = paredeArray[i][1];
            setParede(linha,coluna);
        }
    }

    public List<Vertice> AcharCaminho(){
        lista_aberta.add(vertice_inicial);

        // ENQUANTO A LISTA ABERTA NAO ESTIVER VAZIA
        while(!Vazia(lista_aberta)){
            Vertice vertice_atual = lista_aberta.poll(); // PEGA OS ELEMENTOS DA LISTA ABERTA (OS VERTICES QUE NAO FORAM ANALISADOS)
            lista_fechada.add(vertice_atual); // ADICIONA NA LISA FECHADA, OU SEJA, JA FOI ANALISADO

            // COMPARA O VERTICE FINAL COM O ATUAL, SE FOR VERDADEIRO PASSA O VERTICE ATUAL PARA A CLASSE CAMINHO
            // CASO CONTRARIO VAI PARA A CLASSE ADICIONAR_VERTICES_ADJACENTES
            if(VerticeFinal(vertice_atual)){
                return Caminho(vertice_atual);
            }
            else{
                // ADICIONA E RELACIONA OPERAÇÕES COM VERTICES ADJACENTES (VIZINHOS)
                adicionar_diagonal_Linha_Cima(vertice_atual);
                adicionar_diagonal_Linha_Meio(vertice_atual);
                adicionar_Diagonal_Linha_Inferior(vertice_atual);
            }
        }
        return new ArrayList<Vertice>();
    }

    // LISTA DE VERTICES QUE UTILIZOU ATÉ CHEGAR AO VERTICE FINAL
    // COMECA ESSA LISTA PELO VERTICE FINAL E 'PUXA' OS VERTICES PAIS PARA RETORNAR O CAMINHO REALIZADO
    public List<Vertice> Caminho(Vertice vertice_atual){
        List<Vertice> caminho = new ArrayList<>();
        caminho.add(vertice_atual);
        Vertice vertice_pai;

        while((vertice_pai = vertice_atual.getVertice_pai()) != null){
            caminho.add(0,vertice_pai);
            vertice_atual = vertice_pai;
        }

        return caminho;
    }

    public void adicionar_Diagonal_Linha_Inferior(Vertice vertice_atual){
        int linha = vertice_atual.getLinha();
        int coluna = vertice_atual.getColuna();
        int Linha_Inferior = linha + 1;

        if(Linha_Inferior < getMatriz().length){
            //PEGA A COLUNA ANTERIOR E POSTERIOR
            if(coluna - 1 >= 0 ){
                // PERMITE A MOVIMENTACAO DIAGONAL
                VerificaVertice(vertice_atual,coluna - 1, Linha_Inferior, getCusto_d());
            } // GETMATRIZ[0] REFERENTE AO LENGTH DA SEGUNDA CASA DO TABULEIRO PROPOSTO
            if(coluna + 1 < getMatriz()[0].length){
                // PERMITE A MOVIMENTACAO DIAGONAL
                VerificaVertice(vertice_atual,coluna + 1,Linha_Inferior,getCusto_d());
            }
            // PERMITE AS OUTRAS ORIENMTACOES EXISTENTES
            VerificaVertice(vertice_atual,coluna,Linha_Inferior,getCusto_hv());
        }
    }

    public void adicionar_diagonal_Linha_Cima(Vertice vertice_atual){
        int linha = vertice_atual.getLinha();
        int coluna = vertice_atual.getColuna();
        int Linha_Cima = linha - 1;

        if(Linha_Cima >= 0){
            if(coluna - 1 >= 0 ){
                VerificaVertice(vertice_atual,coluna - 1,Linha_Cima, getCusto_d());
            }
            if(coluna + 1 < getMatriz()[0].length){
                VerificaVertice(vertice_atual,coluna + 1,Linha_Cima,getCusto_d());
            }
            VerificaVertice(vertice_atual,coluna,Linha_Cima,getCusto_hv());
        }
    }

    public void adicionar_diagonal_Linha_Meio(Vertice vertice_atual){
        // TRABALHA COM O TABULEIRO SOMENTE EM HORIZONBTAL E VERTIAL
        int linha = vertice_atual.getLinha();
        int coluna = vertice_atual.getColuna();

        if(coluna - 1 >= 0){
            VerificaVertice(vertice_atual,coluna - 1,linha,getCusto_hv());
        }
        if(coluna + 1 < getMatriz()[0].length){
            VerificaVertice(vertice_atual,coluna + 1, linha, getCusto_hv());
        }
    }


    public void VerificaVertice (Vertice vertice_atual, int coluna, int linha, int custo){
        Vertice vertice_adjacente = getMatriz()[linha][coluna];

        // SE O VERTICE ADJACENTE ANALISADO NAO E UMA PAREDE E ELE NAO ESTA TAMEBM NA LISTA FECHADA ACEITAMOS A VERIFICACAO
        if(!vertice_adjacente.isParede() && !getLista_fechada().contains(vertice_adjacente)){

            // SE O VERTICE NAO ESTA NA LISTA ABERTA ACEITAMOS A VERIFICACAO
            if(!getLista_aberta().contains(vertice_adjacente)){
                vertice_adjacente.Atualizacao_Vertice(vertice_atual,custo); // ATUALIZA O VERTICE EM SUA CLASSE CONFORME O CUSTO FINAL E O VERTCE PAI
                getLista_aberta().add(vertice_adjacente); //ADICIONA O VERTICE NA LISTA ABERTA - ESTA SENDO ANALISADO
            }

            else{
                boolean troca = vertice_adjacente.MelhorCaminho(vertice_atual,custo);
                // SE O VERTICE ADJACENTE FOR DETERMINADO COMO UM MELHOR CAMINHO
                if(troca){
                    getLista_aberta().remove(vertice_adjacente); // REMOVE E ADICIONADA O VERTICE ADJACENTE PARA A LISTA ABERTA PRIORITARIA
                    getLista_aberta().add(vertice_adjacente); // ORDENA O CONTEUDO DO VERTICE CONFORME O NOVO CUSTO FINAL MODIFICADO
                }
            }
        }
    }
}
