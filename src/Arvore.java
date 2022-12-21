import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Arvore implements Cloneable {
	private No raiz = null;

	private String preOrdem (No r) {
		if (r == null) 
			return "";

		return r.getInfo() + " " +
		       this.preOrdem(r.getEsq()) + " " +
			   this.preOrdem(r.getDir());
	}

	private String inOrdem (No r) {
		if (r == null) 
			return "";

		return this.inOrdem(r.getEsq()) + " " +
		       r.getInfo() + " " +
			   this.inOrdem(r.getDir());
	}

	private String posOrdem (No r) {
		if (r == null) 
			return "";

		return this.posOrdem(r.getEsq()) + " " +
			   this.posOrdem(r.getDir()) + " " +
			   r.getInfo();
	}

	@Override
	public String toString () {
		String pre=this.preOrdem(this.raiz),
		       in =this.inOrdem (this.raiz),
			   pos=this.posOrdem(this.raiz);

		return "Pré-ordem: " + (pre.equals("")?"árvore vazia":pre) + "\n" +
		       "In-ordem : " + (in .equals("")?"árvore vazia":in ) + "\n" +
			   "Pós-ordem: " + (pos.equals("")?"árvore vazia":pos);
	}

	private boolean equals (No raizA, No raizB) {
		if (raizA == null && raizB != null) 
			return false;
		
		if (raizA != null && raizB == null) 
			return false;
		
		if (raizA == null && raizB == null) 
			return true;

		if (!raizA.getInfo().equals(raizB.getInfo())) 
			return false;

		return equals (raizA.getEsq(),raizB.getEsq()) &&
			   equals (raizA.getDir(),raizB.getDir());
	}

	@Override
    public boolean equals (Object obj) {
		if (obj == this) 
			return true;
		
		if (obj == null)
			return false;
		
		if (this.getClass()!=obj.getClass()) 
			return false;

		Arvore arv = (Arvore)obj;

		return equals(this.raiz,arv.raiz);
	}

    private int hashCode (No raiz) {
		int ret = 2;

		if (raiz != null) {
			ret = 5 * ret + raiz.getInfo().hashCode();
			ret = 5 * ret + hashCode (raiz.getEsq());
			ret = 5 * ret + hashCode (raiz.getDir());
		}
		return ret;
	}

	@Override
	public int hashCode () {
		return hashCode (this.raiz);
	}

	private No Copia (No raiz)
	{
		if (raiz==null) return null;

		return new No (Copia(raiz.getEsq()),
				       Copia(raiz.getDir()),
			           raiz.getInfo(),
					   raiz.getQtd()
			          );
	}

	public Arvore (Arvore arvore) throws Exception {
		if (arvore == null) throw new Exception ("arvore ausente");

		this.raiz = Copia(arvore.raiz);
	}

	//Criação da árvore pela fila
	public Arvore (FilaPrioridade fila) { 
		try {
			while (fila.dimensao() >= 2) { 
					No no = new No(null);
					no.setEsq(fila.remover());
					no.setDir(fila.remover());
					no.setQtd(no.getDir().getQtd() + no.getEsq().getQtd());
					fila.adicionar(no);
			}
		this.raiz = fila.remover(); 
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//hasmap --> cria uma chave pra cada item armazenado
	private void toHashMap(No n, Map<Byte, String> map, String codigo) {
		if(n.getDir() == null && n.getEsq() == null)  //Chegou em uma folha
				map.put((Byte)n.getInfo(), codigo);
		
		if(n.getDir() != null)
			toHashMap(n.getDir(), map, codigo + "1"); //Toda vez que vai para direira 1

		if(n.getEsq() != null)
			toHashMap(n.getEsq(), map, codigo + "0"); //Vai para esquerda 0
	}

	//retorna o map já que o método é privado
	public Map<Byte, String> toHashMap() {
		Map<Byte, String> map = new HashMap<>();
		toHashMap(this.raiz, map, "");
		return map;
	}

	public Object clone () {
		Arvore ret = null;
		try {
			ret = new Arvore (this);
		}
		catch (Exception erro) {}
		return ret;
	}
	
	public ArrayList<Comparable> EncontarByte(String binario) {
		No no = this.raiz;
		ArrayList<Comparable> ret = new ArrayList<>();

		for (int i = 0; i < binario.length(); i++) { //procura a folha
			if (binario.charAt(i) == '0') {
				no = no.getEsq(); //Vai para esquerda pois é 0
			} 
			else {
				no = no.getDir(); //Vai para direita pois é 1
			}
			if (no.getInfo() != null) { //Acha informação e coloca na array 
				ret.add(no.getInfo());
				no = this.raiz;
			}
		}
		return ret;
	}
}

