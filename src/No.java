public class No <X extends Comparable<X>>{
    private X info;
    private No esq, dir;
    private int qtd;

    public No (X i) {
        esq = null;
        dir = null;
        info = i;
    }

    public No (int qtd) {
        esq  = null;
        dir  = null;
        info = null;
        this.qtd  = qtd;
    }

    public No (No e, No d, X i, int q) {
        esq = e;
        dir = d;
        info = i;
        qtd = q;
    }

    public No (No e, No d, int q) {
        esq = e;
        dir = d;
        qtd = q;
    }

    public No (X i, int q) {
        esq = null;
        dir = null;
        info = i;
        qtd = q;
    }

    public No getEsq () {
        return esq;
    }

    public X getInfo () {
        return info;
    }

    public int getQtd() {
        return qtd;
    }

    public No getDir () {
        return dir;
    }

    public void setEsq (No e){
        esq = e;
    }

    public void setQtd(int q){
        qtd = q;
    }

    public void setInfo (X i) {
        info = i;
    }

    public void setDir (No d) {
        dir = d;
    }
}