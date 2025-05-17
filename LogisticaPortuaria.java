//significa que estamos importando todas as classes do pacote java.util (arrayList, list, etc.)
import java.util.*;

public class LogisticaPortuaria {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    //pergunta quantos containers o usuário quer digitar
    System.out.println("Digite o número de containers:");
    int quantidadeContainers = scanner.nextInt();

    //criar a lista de volumes dos containers
    List<Integer> containers = new ArrayList<>();
    System.out.println("Digite os volumes dos " + quantidadeContainers + " containers:");
    
    for (int i = 0; i < quantidadeContainers; i++) {
      System.out.print("Volume do container " + (i + 1) + ": ");
      int volume = scanner.nextInt();
      containers.add(volume);
    }

    //entrada da capacidade dos armazéns
    System.out.println("\nDigite a capacidade fixa de armazenamento dos armazéns (Carga X):");

    //"\n" é utilizado para representar uma quebra de linha, ou seja, para inserir um novo parágrafo dentro de um texto
    int capacidadeArmazem = scanner.nextInt();

    //entrada da capacidade dos caminhões
    System.out.println("Digite a capacidade fixa de transporte dos caminhões (Carga Y):");
    int capacidadeCaminhao = scanner.nextInt();

    //alocar containers nos armazéns
    List<Integer> volumesArmazens = alocarVolumes(containers, capacidadeArmazem);
    System.out.println("\nVolumes nos armazéns: " + volumesArmazens);
    System.out.println("Número de armazéns necessários: " + volumesArmazens.size());

    //alocar volumes nos caminhões
    List<Integer> volumesCaminhoes = alocarVolumes(volumesArmazens, capacidadeCaminhao);
    System.out.println("\nVolumes nos caminhões: " + volumesCaminhoes);
    System.out.println("Número de caminhões necessários: " + volumesCaminhoes.size());
    scanner.close();
  }
  
  //alocar volumes em bins com capacidade fixa usando FFD e algoritmo guloso
  public static List<Integer> alocarVolumes(List<Integer> volumes, int capacidade) {
    List<Integer> ordenados = new ArrayList<>(volumes);
    Collections.sort(ordenados, Collections.reverseOrder());
    List<Integer> bins = new ArrayList<>();

    for (int volume : ordenados) {
      boolean alocado = false;
      for (int i = 0; i < bins.size(); i++) {
        if (bins.get(i) + volume <= capacidade) {
          bins.set(i, bins.get(i) + volume);
          alocado = true;
          break;
        }
      }
      if (!alocado) {
        bins.add(volume);
      }
    }

    return bins;
  }
}