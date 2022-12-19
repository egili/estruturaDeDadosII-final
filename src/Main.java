    public class Main {
        public static void main(String[] args) {

            try{
                Compactar compac = new Compactar();
                Descompactar desc = new Descompactar();
                compac.compactar("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\WhatsApp Image 2022-03-11 at 13.09.31.jpeg");
                desc.extracao("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\WhatsApp Image 2022-03-11 at 13.09.31.jpeg.compress");
            }
            catch (Exception e) {
                 throw new RuntimeException(e);
         }
        }
    }
