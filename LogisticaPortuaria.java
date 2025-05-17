import java.util.*;
// significa que estamos importando todas as classes do pacote java.util (ArrayList, List, Scanner, etc.)

public class LogisticaPortuaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // pergunta quantos containers o usuário quer digitar
        System.out.println("Digite o número de containers:");
        int quantidadeContainers = scanner.nextInt();

        // criar a lista de volumes dos containers
        List<Integer> containers = new ArrayList<>();
        System.out.println("Digite os volumes dos " + quantidadeContainers + " containers:");
        for (int i = 0; i < quantidadeContainers; i++) {
            System.out.print("Volume do container " + (i + 1) + ": ");
            int volume = scanner.nextInt();
            containers.add(volume);
        }

        // entrada da capacidade dos armazéns
        System.out.println("\nDigite a capacidade fixa de armazenamento dos armazéns (Carga X):");
        int capacidadeArmazem = scanner.nextInt();

        // entrada da capacidade dos caminhões
        System.out.println("Digite a capacidade fixa de transporte dos caminhões (Carga Y):");
        int capacidadeCaminhao = scanner.nextInt();

        // alocar containers nos armazéns
        List<List<Integer>> armazens = alocarVolumes(containers, capacidadeArmazem);
        System.out.println("\n Conteúdo dos armazéns:");
        for (int i = 0; i < armazens.size(); i++) {
            List<Integer> bin = armazens.get(i); 
            // bin representa um armazém, com os volumes dos containers dentro dele
            int soma = bin.stream().mapToInt(Integer::intValue).sum(); 
            // calcula o total de volume no armazém
            System.out.println("Armazém " + (i + 1) + ": " + bin + " (Total: " + soma + ")");
        }
        System.out.println("Número de armazéns necessários: " + armazens.size());

        // converter cada armazém em um volume total para alocar nos caminhões
        List<Integer> volumesArmazens = new ArrayList<>();
        for (List<Integer> bin : armazens) {
            // para cada armazém, somamos os volumes dos containers armazenados nele
            int soma = bin.stream().mapToInt(Integer::intValue).sum();
            volumesArmazens.add(soma); 
            // essa soma representa a carga que precisará ser transportada por um caminhão
        }

        // alocar volumes dos armazéns nos caminhões
        List<List<Integer>> caminhoes = alocarVolumes(volumesArmazens, capacidadeCaminhao);
        System.out.println("\n Conteúdo dos caminhões:");
        for (int i = 0; i < caminhoes.size(); i++) {
            List<Integer> bin = caminhoes.get(i); 
            // bin representa um caminhão, com os volumes dos armazéns que ele está transportando
            int soma = bin.stream().mapToInt(Integer::intValue).sum(); 
            // calcula o total de carga transportada por este caminhão
            System.out.println("Caminhão " + (i + 1) + ": " + bin + " (Total: " + soma + ")");
        }
        System.out.println("Número de caminhões necessários: " + caminhoes.size());

        scanner.close();
    }

    // alocar volumes em bins com capacidade fixa usando FFD (First-Fit Decreasing) e algoritmo guloso
    public static List<List<Integer>> alocarVolumes(List<Integer> volumes, int capacidade) {
        // cria uma cópia da lista e ordena do maior para o menor volume (estratégia FFD)
        List<Integer> ordenados = new ArrayList<>(volumes);
        Collections.sort(ordenados, Collections.reverseOrder());

        // cada bin será representado como uma lista de inteiros
        // se um bin contém [8, 2], isso significa que ele armazena volumes 8 e 2
        List<List<Integer>> bins = new ArrayList<>();

        // percorre cada volume e tenta alocar em um bin já existente
        for (int volume : ordenados) {
            boolean alocado = false;

            for (List<Integer> bin : bins) {
                // calcula a soma atual dos volumes neste bin
                int soma = bin.stream().mapToInt(Integer::intValue).sum();

                // se ainda couber o novo volume neste bin, aloca ele aqui
                if (soma + volume <= capacidade) {
                    bin.add(volume);
                    // adiciona o volume ao bin existente
                    alocado = true;
                    break; 
                    // sai do laço porque o volume já foi alocado
                }
            }

            // se não conseguiu alocar em nenhum bin existente, cria um novo bin
            if (!alocado) {
                List<Integer> novoBin = new ArrayList<>(); 
                // cria um novo bin
                novoBin.add(volume); 
                // coloca o volume dentro dele
                bins.add(novoBin); 
                // adiciona esse bin à lista de bins
            }
        }

        // retorna a lista de bins, onde cada bin é uma lista com os volumes que ele contém
        return bins;
    }
}
