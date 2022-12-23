import java.util.ArrayList;
import java.util.List;

public class FilaPrioridade implements Cloneable {
    private List<No> fila;

    public FilaPrioridade() {   //método publico que será usado para a classe huffmann
        fila = new ArrayList<No>();
    }
    
    public FilaPrioridade (FilaPrioridade modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.fila = construtorCopia(modelo.fila);
    }
        
    public void addEnfileirado(No elementoAAdcionar) {
        for (int i = 0; i < fila.size(); i++){
            if(fila.get(i).getQtd() > elementoAAdcionar.getQtd()) { 
                fila.add(i, elementoAAdcionar);
                return;
            }
        }
        fila.add(elementoAAdcionar);
    }

    public void addElemento(No elementoAAdcionar){
        fila.add(elementoAAdcionar);
    }

    public No remover() throws Exception {
        if(fila.isEmpty()) 
            throw new Exception("Não e possivel remover um elemento, a fila esta vazia");

        return fila.remove(0);
    }

    public ArrayList<String> retFila() {
        ArrayList<String> result = new ArrayList<>();
        
        for(No no : fila) { 
            result.add(no.getInfo() + "");
            result.add(no.getQtd() + "");
        }
        return result;
    }
    
    public int size() { 
        return fila.size();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj ? true : (obj == null) || (!(obj instanceof FilaPrioridade)) || (obj.getClass() != FilaPrioridade.class) || (this.fila != ((FilaPrioridade) obj).fila) ? false : true;
    }
    
    @Override
    public int hashCode() {
        int ret = 31;
        try {
			ret = ret * 13 + new FilaPrioridade(this).hashCode();
		} catch (Exception ignored) {}
        return ret < 0 ? -ret : ret;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.fila);
    }
    
    private List construtorCopia (List<No> fila) {
        if (fila == null) 
            return null;

        List<No> result =  new ArrayList ();

        for (No elementoAAdcionar : fila) {
            result.add(
            new No(
                elementoAAdcionar.getEsq(),
                elementoAAdcionar.getDir(),
                elementoAAdcionar.getInfo(), 
                elementoAAdcionar.getQtd()
                )
            );
        }
        return result;
    }

    public Object clone () {
        FilaPrioridade ret = null;

        try {
            ret = new FilaPrioridade (this);
        }
        catch (Exception erro){
            System.err.println(erro.getMessage());
        }
        return ret;
    }
}