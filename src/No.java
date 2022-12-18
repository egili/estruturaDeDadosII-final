public class No <X extends Comparable<X>>
{

    private X  info;

    private int repeticao;
    private No esq,dir;

    public No (X i)
    {
        this.esq =null;
        this.info=i;
        this.dir =null;
    }

    public No (int repeticao)
    {
        this.esq =null;
        this.info=null;
        this.dir =null;
        this.repeticao = repeticao;
    }

    public No (No e, X i, int f, No d)
    {
        this.esq =e;
        this.info=i;
        this.repeticao = f;
        this.dir =d;
    }

    public No (No e, int f, No d)
    {
        this.esq =e;
        this.repeticao=f;
        this.dir =d;
    }

    public No ( X i, int f)
    {
        this.esq =null;
        this.info=i;
        this.repeticao = f;
        this.dir =null;
    }

    public No getEsq ()
    {
        return this.esq;
    }

    public X getInfo ()
    {
        return this.info;
    }

    public int getRepeticao() {
        return repeticao;
    }

    public No getDir ()
    {
        return this.dir;
    }

    public void setEsq (No e)
    {
        this.esq=e;
    }

    public void setRepeticao(int f){
        this.repeticao = f;
    }

    public void setInfo (X i)
    {
        this.info=i;
    }

    public void setDir (No d)
    {
        this.dir=d;
    }
}
