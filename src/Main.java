    public class Main {
        public static void main(String[] args) {

            try{
                Compactador c = new Compactador();
                Compactador.Compressor comp = c.new Compressor(); 
                Compactador.Descompressor descomp = c.new Descompressor();
                
                comp.compress("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\WhatsApp Image 2022-03-11 at 13.09.31.jpeg");
                descomp.unzip("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\WhatsApp Image 2022-03-11 at 13.09.31.jpeg.compress");
            }
            catch (Exception e) {
                 throw new RuntimeException(e);
         }
        }
    }
