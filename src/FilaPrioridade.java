import java.util.ArrayList;
import java.util.List;

public class FilaPrioridade implements Cloneable {

	private List<No> fila;

    public FilaPrioridade(int capacidadeInicial) throws Exception {

        if(capacidadeInicial <= 0)
            throw new Exception("O tamananho da fila precisa ser um numero positivo");

        this.fila = new ArrayList<No>(capacidadeInicial);
    }

	public FilaPrioridade() {
        this.fila = new ArrayList<No>(10);
	}
	
	public FilaPrioridade(FilaPrioridade filaACopiar) throws Exception {

		if(filaACopiar == null)
			throw new Exception ("A fila a copiar estava nula");
		
		this.fila = constructorCopia(filaACopiar.fila);
	}

	public void adicionar(No elementoAAdcionar) { 
		for (int i = 0; i < fila.size(); i++) {
			if(fila.get(i).getQtd() > elementoAAdcionar.getQtd()) {
				fila.add(elementoAAdcionar);
			}		
		}
		fila.add(elementoAAdcionar);
	}
	
    public void enfileirar(No elementoAAdcionar){
        fila.add(elementoAAdcionar);
    }
	
	public No remover() throws Exception {
		if(fila.isEmpty()) {
			throw new Exception ("Nao e possivel retirar nenhum elemento, a fila esta vazia");
		}
		return fila.remove(0);
	}	
	
    public int getTamanho(){ 
        return fila.size();
    }
	
    public ArrayList<String> fila(){
        ArrayList<String> result = new ArrayList<>();

        for(No no : fila){ 
            result.add(no.getInfo() + "");
            result.add(no.getQtd() + "");
        }
        return result;
    }
	
	private List constructorCopia (List<No> fila) {

        if (fila == null) 
        	return null;

        List<No> result = new ArrayList(); 

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
            ret = new FilaPrioridade(this);
        }
        catch (Exception erro){
            System.err.println(erro.getMessage());
        }
        return ret;
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
    	return String.valueOf(this.fila());
    }
}