import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Descompactar {
        FilaPrioridade filaP = new FilaPrioridade();
        Arvore arvore;
        String binario, extencao, arquivo;

        public void extracao(String arquivoCompactado) throws Exception {
            String arquivo = arquivoCompactado.replace("",""); 
            String[] ItensDoArquivo = arquivo.split("\\."); 

            arquivo = ItensDoArquivo[0];
            extencao= "." + ItensDoArquivo[1];

            lerArquivo(arquivoCompactado);
            arvore = new Arvore((FilaPrioridade)filaP.clone());
            ArrayList<String> filaString = filaP.Fila();

            criarCaminhoDeExtracao();
        }

        private void lerArquivo(String arquivo) throws IOException {

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
                
                if(i%5==0){
                    byte[] b = new byte[4]; 
                    b[0] = (dados[i+1]);
                    b[1] = (dados[i+2]);
                    b[2] = (dados[i+3]);
                    b[3] = (dados[i+4]);

                    filaP.enfileirar(new No((int)dados[i], transformarArrayDeByteEmInt(b)));
                }
            }

            StringBuilder binariosDoArquivo = new StringBuilder();
            for (int i = indexAtual; i < dados.length; i++) {
                if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
                    indexAtual = i+3;
                    break;
                }

                if(Integer.toBinaryString(dados[i]).length()!=7){
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

        private void criarCaminhoDeExtracao() throws Exception{
            byte[] bytes = extrairBytesDaArvore();
            try {
                FileOutputStream outputStream = new FileOutputStream(arquivo + "(unzipped)" + extencao);
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
      
        public int transformarArrayDeByteEmInt(byte[] array){
            int valor = 0;
            for (byte b : array) {
                valor = (valor << 8) + (b & 0xFF);
            }

            return valor;
        }

    }