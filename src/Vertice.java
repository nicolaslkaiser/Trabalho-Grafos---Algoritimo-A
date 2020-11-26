
//TRABALHO 2 GRAFOS - ALGORITIMO A*
//INTEGRANTES: MATHEUS BANISKI, NICOLAS KAISER, VINICIUS GARCIA

public class Vertice {

    public int linha;
    public int coluna;

    public int custo_g;  // DISTANCIA G: DO VERTICE ATUAL PARA COM O VERTICE INICIAÇL
    public int custo_h;  // DISTANCIA H: DO VERTICE ATUAL PARA COM O VERTICE FINAL
    public int custo_final;  // SOMA DAS DISTANCIAS G E H
    public boolean parede;  // BLOQUEADOR DA MATRIZ
    public Vertice vertice_pai;

    //---------------------CONSTRUTOR---------------------

    public Vertice(int linha, int coluna) {
        super();
        this.linha = linha;
        this.coluna = coluna;
    }

    //---------------------METODOS---------------------

    // EM RELAÇÃO AO VERTICE FINAL, PEGA A LINHA E A COLUNA DE UM VERTICE E REALIZA A OPERAÇÃO COM O VERTICE FINAL
    public void Calcular_Heuristica(Vertice vertice_final){
        this.custo_h = Math.abs(vertice_final.getLinha() - getLinha()) + Math.abs(vertice_final.getColuna() - getColuna());
    }

    // ATUALIZAR O CUSTO DO VERTICE BEM COMO PASSA ELE PARA O PAI DOS OUTROS
    public void Atualizacao_Vertice(Vertice vertice, int custo){
        setCusto_g(vertice.custo_g + custo);
        setVertice_pai(vertice);
        Calcular_Custo_Final();
    }

    // BOOLEANO QUE DETERMINA O MELHOR CAMINHO, SE O CUSTO G DAQUELE VERTICE SOMADO COM O CUSTO ENCONTRADO FOR MENOR QUE O CUSTO G DO OUTRO VERTICE
    // ATUALIZAMOS O CUSTO DESTE E RETORNAMOS ESTE PARA MELHOR CAMINHO CASO AO CONTRARIO RETORNAMOS FALSO INDICANDO QUE NÃO É UM MELHOR CAMINHO
    public boolean MelhorCaminho(Vertice vertice, int custo){
        if((vertice.getCusto_g()+ custo) < getCusto_g()){
            Atualizacao_Vertice(vertice,custo);
            return true;
        }return false;
    }

    public void Calcular_Custo_Final(){
        setCusto_final(getCusto_g()+getCusto_h());
    }

    // A PARTIR DA CLASSE OBJETO RESTORNA E COMPARA AS LINHAS E COLUNAS DOS VERTICES (EQUIVALENCIA DE OBJETOS)
    public boolean equals(Object aux){
        Vertice vertice_2 = (Vertice) aux;
        return this.getLinha() == vertice_2.getLinha() && this.getColuna() == vertice_2.getColuna();
    }

    // CONSTRUIDO PARA SER PASSADO A MAIN
    public String toString(){
        return ("[ " + linha + " , " + coluna + " ]");
    }

    //---------------------GETTERS E SETTERS---------------------

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public int getCusto_g() {
        return custo_g;
    }

    public void setCusto_g(int custo_g) {
        this.custo_g = custo_g;
    }

    public int getCusto_h() {
        return custo_h;
    }

    public void setCusto_final(int custo_final) {
        this.custo_final = custo_final;
    }

    public boolean isParede() {
        return parede;
    }

    public void setParede(boolean parede) {
        this.parede = parede;
    }

    public Vertice getVertice_pai() {
        return vertice_pai;
    }

    public void setVertice_pai(Vertice vertice_pai) {
        this.vertice_pai = vertice_pai;
    }

}