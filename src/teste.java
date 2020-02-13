public class teste {
    public static void main(String[] args){
        CRUDProduto controller = new CRUDProduto();
        controller.cadastraPacote("Triunfo de umburana", 33.50, 6);
        controller.cadastraPacote("Triunfo cristal", 25, 6);
        System.out.println(controller.toStringPacotes());
        System.out.println(controller.getPrecoVendaPacote("Triunfo de umburana"));
        controller.setPrecoVendaPacote("Triunfo de umburana", 37.0);
        System.out.println(controller.getPrecoVendaPacote("Triunfo de umburana"));
        System.out.println(controller.getPrecoVendaProduto("Triunfo de umburana"));
        controller.adicionaPacotes("Triunfo de umburana", 10);
        System.out.println(controller.getUnidades("Triunfo de umburana"));
        System.out.println(controller.getPacotes("Triunfo de umburana"));
        controller.adicionaUnidades("Triunfo de umburana", 5);
        System.out.println(controller.getUnidades("Triunfo de umburana"));
        System.out.println(controller.getPacotes("Triunfo de umburana"));
        controller.adicionaUnidades("Triunfo de umburana", 2);
        System.out.println(controller.getUnidades("Triunfo de umburana"));
        System.out.println(controller.getPacotes("Triunfo de umburana"));
        System.out.println(controller.venderPacote("Triunfo de umburana", 37, 6).toString());
        System.out.println(controller.getUnidades("Triunfo de umburana"));
        System.out.println(controller.getPacotes("Triunfo de umburana"));    
        System.out.println(controller.venderUnidade("Triunfo de umburana", 7, 31).toString());
        System.out.println(controller.getUnidades("Triunfo de umburana"));
        System.out.println(controller.getPacotes("Triunfo de umburana"));  
    }
}
