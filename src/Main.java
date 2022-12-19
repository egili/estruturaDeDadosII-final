    public class Main {
        public static void main(String[] args) {


            try{

                Compactar compac = new Compactar();
                Descompactar desc = new Descompactar();
                compac.compactar("C:\\Users\\adria\\Downloads\\Compactador\\src\\file.txt");
                desc.extracao("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\file.txt.compactar");
            }
            catch (Exception e) {
                 throw new RuntimeException(e);
         }
        }
    }
