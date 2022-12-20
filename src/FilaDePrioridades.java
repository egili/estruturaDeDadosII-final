/*
import java.util.ArrayList;
import java.util.List;

public class FilaDePrioridades implements Cloneable{ //implements Cloneable
    private List<No> lista;

// FILA QUE ESTÁ FUNCIONANDO

    public FilaDePrioridades (FilaDePrioridades modelo) throws Exception  
    {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.lista = copia(modelo.lista);
    }
    
    public void enfileirar(No item){
        for (int i = 0; i <lista.size(); i++){
            if(lista.get(i).getRepeticao() > item.getRepeticao()) {
                lista.add(i, item);
                return;
            }
        }
        lista.add(item);
    }

    //ANA: NÃO COLQUEI NO NOSS, PRECISA????
    public void enfileirarNaOrdem(No item){
        lista.add(item);
    }

 
    public No removerElemento() throws Exception {
        if(lista.isEmpty()) 
            throw new Exception("Erro - remover elemento. Lista vazia");

        return lista.remove(0);
    }

    public int tamanho(){ 
        return lista.size();
    }

    //PRECISA DISSO?? NÃO COLOQUEI
    public FilaDePrioridades(){   //método publico que será usado para a classe compactar
        lista = new ArrayList<No>(); //é instanciado um novo arraylist do tipo "No" e adiciona a lista 
    }

    //PRECISA DISSO?? NÃO COLOQUEI
    public ArrayList<String> showFila(){
        ArrayList<String> result = new ArrayList<>();
        for(No no : lista){ //declaramos o no e retornamos ele e sua frequência
            result.add(no.getInfo() + "");
            result.add(no.getRepeticao() + "");
        }
        return result;
    }
    
    private List copia (List<No> lista)   
    {
        if (lista==null) return null;

        List<No> result =  new ArrayList ();  

        for (No item: lista) {
            result.add( new No(item.getEsq(), item.getInfo(), item.getRepeticao(), item.getDir())); //adiciona o No com a sua informação, o que está a esuqerda a sua direita e a sua frequencia
        }

        return result;
    }

    public Object clone () 
    {
        FilaDePrioridades ret = null;

        try
        {
            ret = new FilaDePrioridades (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
}
*/
