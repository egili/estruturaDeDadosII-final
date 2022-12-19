import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Descompactar {
        String binario;
        FilaDePrioridades filaDePrioridades = new FilaDePrioridades();
        Arvore arvore;
        String extencao;
        String Arquivo;

        public void extracao(String arquivoCompactado) throws Exception {
            String arquivo = arquivoCompactado.replace("",""); // procurar uma string de um caracter especifico
            String[] ItensDoArquivo = arquivo.split("\\."); // formatar caminho do arquivo

            Arquivo = ItensDoArquivo[0];
            extencao= "." + ItensDoArquivo[1];

            lerArquivo(arquivoCompactado);
            arvore = new Arvore((FilaDePrioridades)filaDePrioridades.clone());
            ArrayList<String> filaString = filaDePrioridades.showFila();

            criarCaminhoDeExtracao();
        }

        private void lerArquivo(String arquivo) throws IOException { //lendo os valores em bytes

            RandomAccessFile readFile = new RandomAccessFile(arquivo, "rw");
            byte[] dados = new byte[(int)readFile.length()];
            readFile.read(dados);   //popular os dados com os dados do arquivo
            Map<Integer, Integer> frequenciaDosBytes = new HashMap<>();

            // salvar o index atual para demarcar de onde parou
            int indexAtual = 0;

            // percorre o array e encontra os bytes
            for (int i = 0; i < dados.length; i+=5) {
                if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
                    indexAtual = i+3;
                    break;
                }
                // se os bytes forem divisiveis por 5 serao enfileirados
                if(i%5==0){
//                frequenciaDosBytes.put((int)dados[i], (int)dados[i+1]);
                    byte[] b = new byte[4]; //monta um byte inteiro
                    b[0] = (dados[i+1]);
                    b[1] = (dados[i+2]);
                    b[2] = (dados[i+3]);
                    b[3] = (dados[i+4]);

                    filaDePrioridades.enfileirarNaOrdem(new No((int)dados[i], transformarArrayDeByteEmInt(b)));
                }
            }

            // leitura do caminho da arvore
            StringBuilder binariosDoArquivo = new StringBuilder();
            for (int i = indexAtual; i < dados.length; i++) {
                if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
                    indexAtual = i+3;
                    break;
                }

                if(Integer.toBinaryString(dados[i]).length()!=7){
                    StringBuilder zerosQueFaltam = new StringBuilder();

                    //forma grupos de 7
                    int diferenca = Integer.toBinaryString(dados[i]).length() - 7;

                    //recupera os bytes
                    // aplicando o modulo para sempre ter numeros positivos
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

            // removendo os zeros do final do arquivo
            if(salvarNumeroDzero>0){
                for (int i = 0; i < binariosDoArquivo.length()-salvarNumeroDzero; i++) {
                    binariosOriginais.append(binariosDoArquivo.charAt(i));
                }
            }else binariosOriginais = binariosDoArquivo;


            binario = binariosOriginais.toString();

        }

        private void criarCaminhoDeExtracao() throws Exception{
            byte[] bytes = extrairBytesDaArvore(); // extrai os bytes da arvore e insere em um array de bytes
            try {
                // e a funcao que torna possivel abir qualquer tipo de arquivo na main passando um caminho do pc
                FileOutputStream outputStream = new FileOutputStream(Arquivo+"(unzipped)"+extencao);
                outputStream.write(bytes);// escreve o array de byte
            } catch (IOException e) {
                System.out.println("Erro - criar caminho de extração");
                e.printStackTrace();
            }
        }

        private byte[] extrairBytesDaArvore() {
            String binarioDescompactado = this.binario;
            ArrayList<Comparable> bytesAlt = new ArrayList<>();
            // resultado recebe os bytes que vao montar o arquivo de volta
            // atraves dos binarios (0101010)
            ArrayList<Comparable> resultado = arvore.findByte(binarioDescompactado);
            //compara se byte existe no resultado e copia o byte b para o bytesAlt
            for (Comparable b:
                    resultado ) {
                bytesAlt.add(b);
            }

            byte[] bytes = new byte[bytesAlt.size()];
            for (int i = 0; i < bytesAlt.size(); i++) {
                bytes[i] = (byte) Integer.parseInt(bytesAlt.get(i).toString());
            }
            return bytes;
        }
       //tranformando bytes em inteiro
        public int transformarArrayDeByteEmInt(byte[] array){
            int valor = 0;
            for (byte b : array) {
                // para cada byte adiciono o 0xFF nele
                // para poder somar dentro da variavel
                valor = (valor << 8) + (b & 0xFF);
            }

            return valor;
        }

    }