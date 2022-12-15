public class FilaPrioridade<T> {
	
	int quantosElementosEstaoNaFila;
	int capacidade;
	T[] fila;

	public FilaPrioridade() throws Exception {
		this(10);
	}
	
	public FilaPrioridade(int capacidadeInicial) throws Exception {
		if(capacidadeInicial <= 0)
			throw new Exception ("O tamananho da fila precisa ser um numero positivo");
		
		capacidade = capacidadeInicial;
		quantosElementosEstaoNaFila = 0;
		
	}
	
	public boolean estaCheio() {
		if (quantosElementosEstaoNaFila == capacidade -1 )
			return true;
		return false;
	}
	
	public void adicionar(T elementoAAdcionar) throws Exception { // um segundo parametro para definir a prioridade do elemento adicionado
	
		if(estaCheio()) {
			this.redimensionsar();
		}
		
		Comparable<T> chave = (Comparable<T>) elementoAAdcionar;
		int i;
		for (i = 0; i < this.quantosElementosEstaoNaFila; i++) {
			if(chave.compareTo(this.fila[i]) < 0) {
				break;
			}
		}
		//add elemento
	}
		
	
	//public T remover() { não entendi pq do T nem o fato de não haver parametro
	
	public void remover(int posicao) {
		for (int i = posicao; i< capacidade - 1; i++){
			fila[i] = fila[i+1];
		}
		capacidade--;
	}
	
	public T verProximoElementoASerRemovido() { // nao remove, tem necessidade de ter esse metodo ??
		
	}
	
	public void redimensionsar (/*FilaPrioridade<T> filaARedimensionar*/) {
		T[] elementosNovos = (T[]) new Object[this.fila.length * 2];
		for (int i = 0; i < this.fila.length; i++){
			 elementosNovos[i] = this.fila[i];
		}
		this.fila = elementosNovos;	
		// quando estiver completamente preenchido aumenta a capacidade da fila
	}
	
	
	@Override
	public String toString() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		FilaPrioridade<T> FilaE = (FilaPrioridade<T>) obj;
		
		if(this.quantosElementosEstaoNaFila != FilaE.quantosElementosEstaoNaFila) return false;
		if(this.capacidade != FilaE.capacidade) return false;
		if(this.fila != FilaE.fila) return false;

	}
	
	
	@Override
	public int hashCode() {
		int ret = 666;
	    ret = 13 * ret + new Integer(this.capacidade).hashCode();
	    ret = 13 * ret + new Integer(this.quantosElementosEstaoNaFila).hashCode();
	    //ret = 13 * ret + new T(this.fila).hashCode(); --> Não sei fazer 
	    
	    if (ret < 0) ret = -ret;
	    return ret;
		
	}
}