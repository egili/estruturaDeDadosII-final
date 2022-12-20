import java.util.ArrayList;
import java.util.List;


// Fila Ana Adaptada

public class FilaPrioridade implements Cloneable {
	private List<No> fila;

	public FilaPrioridade() {
		this.fila = new ArrayList<No>(10);
	}
	
	public FilaPrioridade(FilaPrioridade capacidadeInicial) throws Exception {
		if(capacidadeInicial == null)
			throw new Exception ("O tamananho da fila precisa ser um numero positivo");
		
		this.fila = Copia(capacidadeInicial.fila);
	}

	public void adicionar(No elementoAAdcionar) { 
		for (int i = 0; i < fila.size(); i++) {
			if(fila.get(i).getQtdR() > elementoAAdcionar.getQtdR()) {
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
	
    public int dimensao(){ 
        return fila.size();
    }
	
    public ArrayList<String> Fila(){
        ArrayList<String> result = new ArrayList<>();
        for(No no : fila){ 
            result.add(no.getInfo() + "");
            result.add(no.getQtdR() + "");
        }
        return result;
    }
	
	private List Copia (List<No> fila)  
    {
        if (fila == null) 
        	return null;

        List<No> result =  new ArrayList (); 

        for (No elementoAAdcionar: fila) {
            result.add(new No(elementoAAdcionar.getEsq(), elementoAAdcionar.getDir(), elementoAAdcionar.getInfo(), elementoAAdcionar.getQtdR()));
        }

        return result;
    }

    public Object clone () {
    	FilaPrioridade ret = null;

        try {
            ret = new FilaPrioridade (this);
        }
        catch (Exception erro)
        { }
        
        return ret;
    }
}