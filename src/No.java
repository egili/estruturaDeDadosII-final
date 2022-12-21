public class No <X extends Comparable<X>>
{
    private X  info;
    private No esq, dir;
    private int qtd;

    public No (X i) {
        this.esq  = null;
        this.dir  = null;
        this.info = i;
    }

    public No (int qtd) {
        this.esq  = null;
        this.dir  = null;
        this.info = null;
        this.qtd  = qtd;
    }

    public No (No e, No d, X i, int q) {
        this.esq  = e;
        this.dir  = d;
        this.info = i;
        this.qtd  = q;
    }

    public No (No e, No d, int q) {
        this.esq = e;
        this.dir = d;
        this.qtd = q;
    }

    public No (X i, int q) {
        this.esq  = null;
        this.dir  = null;
        this.info = i;
        this.qtd  = q;
    }

    public No getEsq () {
        return this.esq;
    }

    public X getInfo () {
        return this.info;
    }

    public int getQtd() {
        return qtd;
    }

    public No getDir () {
        return this.dir;
    }

    public void setEsq (No e)
    {
        this.esq = e;
    }

    public void setQtd(int q){
        this.qtd = q;
    }

    public void setInfo (X i) {
        this.info=i;
    }

    public void setDir (No d) {
        this.dir=d;
    }
}