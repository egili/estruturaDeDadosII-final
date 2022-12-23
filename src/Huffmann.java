import java.io.*;
import java.util.*;

public class Huffmann {
	
	public class Compressor {
	
		byte[] arquivoDeBytes;
		FilaPrioridade fila = new FilaPrioridade();
		ArvoreHuffmann arvore;
	
		public void compress(String arquivo) throws Exception {
			readFiles(arquivo);
			newListaPrioridade();
			arvore = new ArvoreHuffmann((FilaPrioridade)fila.clone());
			newCompressed(arquivo);
		}
	
		private void readFiles(String arquivo) throws  Exception{
			File newFIle = new File(arquivo);
			RandomAccessFile accessArquivo = new RandomAccessFile(newFIle, "rw");
			this.arquivoDeBytes = new byte[(int)accessArquivo.length()];
			accessArquivo.read(this.arquivoDeBytes);
			accessArquivo.close();
		}
	
		private String addBytesArvore(ArvoreHuffmann a){
			StringBuilder mapDeBytes = new StringBuilder();
			Map<Byte, String>  map = a.toHashMap();

			for (byte arquivoDeBytes : this.arquivoDeBytes) {
				mapDeBytes.append(map.get(arquivoDeBytes));
			}

			return mapDeBytes.toString();
		}
	
		private void newListaPrioridade() {
			Map<Byte, Integer> qtdBytes = new HashMap<>();
		
			for (byte arquivoDeBytes : this.arquivoDeBytes) {
				if(!qtdBytes.containsKey(arquivoDeBytes)) { 
					qtdBytes.put(arquivoDeBytes, 1);
				} else {
					qtdBytes.put(arquivoDeBytes,qtdBytes.get(arquivoDeBytes) + 1); 
				}
			}
		
			qtdBytes.forEach((key, value) -> fila.addEnfileirado(new No(key, value)));
		}
	
		private void newCompressed(String path){ 
			StringBuilder dados = new StringBuilder(addBytesArvore(arvore));
			
			try {
				File arquivo = new File(path + ".zip");
				RandomAccessFile arquivoDeEscita = new RandomAccessFile(arquivo.getAbsolutePath(),"rw");

				ArrayList<String> filaString = fila.retFila();

				for (int i = 0; i < filaString.size(); i++) {
					if(i % 2 == 0)
						arquivoDeEscita.writeByte((byte)Integer.parseInt(filaString.get(i))); 
					else 
						arquivoDeEscita.writeInt(Integer.parseInt(filaString.get(i)));
				}

				arquivoDeEscita.write(Byte.MIN_VALUE);
				arquivoDeEscita.write(Byte.MIN_VALUE);
				arquivoDeEscita.write(Byte.MIN_VALUE);

				StringBuilder auxiliar = new StringBuilder();
				int contador = 0;

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

				arquivoDeEscita.close();
			} catch (IOException e) {
				System.out.println("Erro - gerar arquivo compactado");
				e.printStackTrace();
			}
		}
	}

	public class Descompressor {
		
		FilaPrioridade filaP = new FilaPrioridade();
		ArvoreHuffmann arvore;
		String binario, extensao, arquivo;
	
		public void unzip(String arquivoCompactado) throws Exception {
			String arquivo = arquivoCompactado.replace("",""); 
			String[] ItensDoArquivo = arquivo.split("\\."); 

			arquivo = ItensDoArquivo[0];
			extensao= "." + ItensDoArquivo[1];

			readFiles(arquivoCompactado);
			arvore = new ArvoreHuffmann((FilaPrioridade)filaP.clone());
			ArrayList<String> filaString = filaP.retFila();

			newUnzipper();
		}

		private void readFiles(String arquivo) throws IOException {

			RandomAccessFile readFile = new RandomAccessFile(arquivo, "rw");
			byte[] dados = new byte[(int)readFile.length()];
			readFile.read(dados); 
			Map<Integer, Integer> frequenciaDosBytes = new HashMap<>();

			int indexAtual = 0;
			
			for (int i = 0; i < dados.length; i+=5) {
				if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
					indexAtual = i+3;
					break;
				}
				
				if(i % 5 == 0){
					byte[] b = new byte[4]; 
					b[0] = (dados[i + 1]);
					b[1] = (dados[i + 2]);
					b[2] = (dados[i + 3]);
					b[3] = (dados[i + 4]);

					filaP.addElemento(new No((int)dados[i], HuffmannHelper.converterArrByteArrInt(b)));
				}
			}

			StringBuilder binariosDoArquivo = new StringBuilder();
			
			for (int i = indexAtual; i < dados.length; i++) {
				if(dados[i] == -128 && dados[i+1] == -128 && dados[i+2] == -128){
					indexAtual = i+3;
					break;
				}

				if(Integer.toBinaryString(dados[i]).length() != 7){
					StringBuilder zerosQueFaltam = new StringBuilder();

					int diferenca = Integer.toBinaryString(dados[i]).length() - 7;
					
					if (diferenca < 0) diferenca = diferenca * -1;
					for (int j = 0; j < diferenca; j++) {
						zerosQueFaltam.append("0");
					}

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

			if(salvarNumeroDzero>0){
				for (int i = 0; i < binariosDoArquivo.length()-salvarNumeroDzero; i++) {
					binariosOriginais.append(binariosDoArquivo.charAt(i));
				}
			}else binariosOriginais = binariosDoArquivo;

			binario = binariosOriginais.toString();
		}

		private void newUnzipper() throws Exception {
			
			byte[] bytes = extrairBytesDaArvore();
			
			try {
				FileOutputStream outputStream = new FileOutputStream(arquivo + " Descompactado" + extensao);
				outputStream.write(bytes);
			} catch (IOException e) {
				System.out.println("Erro - criar caminho de extração");
				e.printStackTrace();
			}
		}

		private byte[] extrairBytesDaArvore() {
			String binarioDescompactado = this.binario;
			ArrayList<Comparable> bytesAlt = new ArrayList<>();
			ArrayList<Comparable> resultado = arvore.EncontarByte(binarioDescompactado);
			
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