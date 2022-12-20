public class No <X extends Comparable<X>>
{
    private No esq, dir;
	private int qtdRep;
    private X  info;

    public No (X i) {
        this.esq  = null;
        this.dir  = null;
        this.info = i;
    }

    public No (int repeticao) {
        this.esq  = null;
        this.dir  = null;
        this.info = null;
        this.qtdRep = repeticao;
    }

    public No (No e, No d, X i, int q) {
        this.esq  = e;
        this.dir  = d;
        this.info = i;
        this.qtdRep = q;
        
    }

    public No (No e, No d, int q) {
        this.esq = e;
        this.dir = d;
        this.qtdRep = q;
    }

    public No (X i, int q) {
        this.esq = null;
        this.dir = null;
        this.info = i;
        this.qtdRep = q;
        
    }

    public No getEsq () {
        return this.esq;
    }
     
    public No getDir () {
        return this.dir;
    }

    public X getInfo () {
        return this.info;
    }

    public int getQtdR() {
        return qtdRep;
    }

    public void setEsq (No e) {
        this.esq = e;
    }
    
    public void setDir (No d) {
        this.dir = d;
    }

    public void setQtdR(int q){
        this.qtdRep = q;
    }

    public void setInfo (X i) {
        this.info = i;
    }
}
