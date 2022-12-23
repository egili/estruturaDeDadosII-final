import java.io.*;
import java.util.*;

public class Huffmann {
	
	public class Compressor {
	
	    byte[] arquivoDeBytes;
	    FilaPrioridade fila = new FilaPrioridade();
	    ArvoreHuffmann arvore;
	
	//Compactação do arquivo que foi passado
	    public void compress(String arquivo) throws Exception {
	    	readFiles(arquivo);
	        newListaPrioridade();
	        arvore = new ArvoreHuffmann((FilaPrioridade)fila.clone());
	        newCompressed(arquivo);
	    }
	

	    private void readFiles(String arquivo) throws  Exception{
	       File newFIle = new File(arquivo); 
	       RandomAccessFile accessArquivo = new RandomAccessFile(newFIle, "rw"); // Lê e escreve o caminho do arquivo
	       this.arquivoDeBytes = new byte[(int)accessArquivo.length()]; //Leitura de qtd de bytes do arquivo
	       accessArquivo.read(this.arquivoDeBytes);// Leitura de um byte de dados do arquivo
	       accessArquivo.close(); // Fecha a instancia
	   }
	
	   // Criação do map com bytes da arvore e adiciona na string
       private String addBytesArvore(ArvoreHuffmann a){
	       StringBuilder mapDeBytes = new StringBuilder();
	       Map<Byte, String>  map = a.toHashMap();
		// em cada byte do map, vai pegar esse valor e add na string (pega o caminho da arvore e cria um map de bytes)
	       for (byte arquivoDeBytes : this.arquivoDeBytes) {
	           mapDeBytes.append(map.get(arquivoDeBytes));
	       }
	       return mapDeBytes.toString();
       }
	
	// Cria Lista de Prioridade
	private void newListaPrioridade() {
	    Map<Byte, Integer> qtdBytes = new HashMap<>();
	
	// Verifica se o byte tem interação (key que o hashMap cria)
	    for (byte arquivoDeBytes : this.arquivoDeBytes) {
	        if(!qtdBytes.containsKey(arquivoDeBytes)) { 
			// Faz a soma do valor da key
	        	qtdBytes.put(arquivoDeBytes, 1);
			// Existindo, anda uma casa pra direita e a prioridade fica maior
	        } else {
			// Se não existir, adiciona o byte com valor 1
	        	qtdBytes.put(arquivoDeBytes,qtdBytes.get(arquivoDeBytes) + 1); 
	        }
	    }
	
		// Em cada key do map, gera o novo nó e o enfileira
		// E cada nó tem sua key e valor
	    qtdBytes.forEach((key, value) -> fila.addEnfileirado(new No(key, value)));
	}
	

	// Ocorre a geração do arquivo a ser compactado
	    private void newCompressed(String path){ 
	        StringBuilder dados = new StringBuilder(addBytesArvore(arvore));
	        
	        try {
				// Vai criar o arquivo e abrir ele pra escrita e seguindo seu caminho
	            File arquivo = new File(path + ".zip");
	            RandomAccessFile arquivoDeEscita = new RandomAccessFile(arquivo.getAbsolutePath(),"rw");
	
				//Começa escrevendo os bytes e ver a quantidade deles (frequencia)
	            ArrayList<String> filaString = fila.retFila();
	
	            for (int i = 0; i < filaString.size(); i++) {
	                if(i%2==0){ 
	                    arquivoDeEscita.writeByte((byte)Integer.parseInt(filaString.get(i))); 
	                }
	                else {
	                    arquivoDeEscita.writeInt(Integer.parseInt(filaString.get(i)));
	                }
	            }
	
				// Escrever tres bytes 128 para separar
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	

				// Aqui é o metodo auxiliar que usamos lá em cima 
	            StringBuilder auxiliar = new StringBuilder();
	            int contador = 0;
	

				// Faz criao de grupo, ve o tamanho dos dados
	            if(!(dados.length() % 7 == 0)){
	                while (!(dados.length() % 7 == 0)){
	                    dados.append("0");
	                    contador++;
	                }
	            }
	
	            for (int i = 0; i < dados.length(); i++) {
	                auxiliar.append(dados.charAt(i));
	                
	                if((i+1) % 7 == 0){
	                    byte b = HuffmannHelper.converterStringByte(auxiliar.toString());
	                    arquivoDeEscita.write(b);
	                    auxiliar = new StringBuilder();
	                }
	            }
	
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            
	            arquivoDeEscita.write(contador);
	
			// Fecha o processo
	            arquivoDeEscita.close();
	        } catch (IOException e) {
	            System.out.println("Erro ao gerar o arquivo compactado");
	            e.printStackTrace();
	        }
	    }
	}

	public class Descompressor {
		
	    FilaPrioridade filaP = new FilaPrioridade();
	    ArvoreHuffmann arvore;
	    String binario, extensao, nomeArquivo;
	
	    public void unzip(String compactado) throws Exception {
	        String arquivo = compactado.replace("",""); // Procura uma string de um caracter especifico
	        String[] ItensDoArquivo = arquivo.split("\\."); // Formatação no caminho do arquivo
	
	        nomeArquivo = ItensDoArquivo[0];
	        extensao = "." + ItensDoArquivo[1];
	
			// Le o arquivo compactado
	        readFiles(compactado);
	        arvore = new ArvoreHuffmann((FilaPrioridade)filaP.clone());
	        ArrayList<String> filaString = filaP.retFila();
	
	        newUnzipper();
	    }
	
	    private void readFiles(String arquivo) throws IOException {
	
			// Leitura dos valores dos bytes
	        RandomAccessFile readFile = new RandomAccessFile(arquivo, "rw");
	        byte[] dados = new byte[(int)readFile.length()];
	        readFile.read(dados); // Pega os bytes lidos e joga no vetor de dados
	        Map<Integer, Integer> frequenciaDosBytes = new HashMap<>();
	
			// Salva aonde o index parou para marcar aonde parou
	        int indexAtual = 0;
	        
			// Percorre o array em busca dos bytes
	        for (int i = 0; i < dados.length; i+=5) {
	            if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
	                indexAtual = i+3;
	                break;
	            }
	            //
				// Se forem divisiveis por 5 são enfileirados
	            if(i % 5 == 0){
	                byte[] b = new byte[4]; 
	                b[0] = (dados[i + 1]);
	                b[1] = (dados[i + 2]);
	                b[2] = (dados[i + 3]);
	                b[3] = (dados[i + 4]);
	
	                filaP.addElemento(new No((int)dados[i], HuffmannHelper.converterArrByteArrInt(b)));
	            }
	        }
	
			// Faz a leitura do caminho da arvore
	        StringBuilder binariosDoArquivo = new StringBuilder();	        
	        for (int i = indexAtual; i < dados.length; i++) {
	            if(dados[i] == -128 && dados[i+1] == -128 && dados[i+2] == -128){
	                indexAtual = i+3;
	                break;
	            }
	
	            if(Integer.toBinaryString(dados[i]).length() != 7){
	                StringBuilder zerosQueFaltam = new StringBuilder();
	
					// Forma grupos de 7
	                int diferenca = Integer.toBinaryString(dados[i]).length() - 7;
	                
                    //recupera os bytes
                    //aplicando o modulo para sempre ter numeros positivos
                    //adiciona os 0

	                if (diferenca < 0) diferenca = diferenca * -1;
	                for (int j = 0; j < diferenca; j++) {
	                    zerosQueFaltam.append("0");
	                }
	
					// vou adicionando os binarios convertidos
                    // dentro do binarios do arquivo

	                zerosQueFaltam.append(Integer.toBinaryString(dados[i]));
	                binariosDoArquivo.append(zerosQueFaltam);
	                zerosQueFaltam = new StringBuilder();
	            }
	            else{
	                binariosDoArquivo.append(Integer.toBinaryString(dados[i]));
	            }
	        }
	
			
	        int salvarNumeroDzero = dados[indexAtual];
	        StringBuilder binariosOriginais = new StringBuilder();
	
			// Remove os zeros do final do arquivo
	        if(salvarNumeroDzero>0){
	            for (int i = 0; i < binariosDoArquivo.length()-salvarNumeroDzero; i++) {
	                binariosOriginais.append(binariosDoArquivo.charAt(i));
	            }
	        }else binariosOriginais = binariosDoArquivo;
	
	        binario = binariosOriginais.toString();
	    }
	
	    private void newUnzipper() throws Exception {
	        
	    	byte[] bytes = extrairBytesDaArvore(); // extrai os bytes da arvore e insere em um array de bytes
	        
	        try {
              //E a funcao que torna possivel abrir qualquer tipo de arquivo na main passando um caminho do pc
	            FileOutputStream outputStream = new FileOutputStream("Unzipped - "+ nomeArquivo + extensao);
	            outputStream.write(bytes);

	        } catch (IOException e) {
	            System.out.println("Erro ao criar caminho de extração");
	            e.printStackTrace();
	        }
	    }
	
	    private byte[] extrairBytesDaArvore() {
	        String binarioDescompactado = this.binario;
	        ArrayList<Comparable> bytesAlt = new ArrayList<>();
			// Esse resultado vai receber os bytes que vão montar a arvore novamente
	        ArrayList<Comparable> resultado = arvore.EncontarByte(binarioDescompactado);
			// Faz a comparação de bytes que existe no resultado e copia o byte b para o bytesAlt

	        for (Comparable b: resultado) {
	            bytesAlt.add(b);
	        }
	
	        byte[] bytes = new byte[bytesAlt.size()];
	        
	        for (int i = 0; i < bytesAlt.size(); i++) {
	            bytes[i] = (byte) Integer.parseInt(bytesAlt.get(i).toString());
	        }
	        return bytes;
	    }
	}
}