package queiroz.larissa.projetointegrador.model;

public class Compartimento {
   public String hora; // Hora no formato HH:mm
   public String nome; // Nome do medicamento
   public String qtd; // Quantidade do medicamento
   public String desc; // Descrição do medicamento
   public String data; // Data do medicamento
   public String freq; // Frequência do medicamento como string (ex: "A cada 6 horas")
   public String freqInt; // Frequência do medicamento como string representando o intervalo em horas (ex: "6")

   // Método para obter a hora como um valor inteiro
   public int getHoras() {
      String[] horaEMin = this.hora.split(":"); // Divide a string da hora em horas e minutos
      int horas = Integer.parseInt(horaEMin[0]); // Converte a parte das horas para inteiro
      return horas; // Retorna as horas
   }

   // Método para obter os minutos como um valor inteiro
   public int getMinutos() {
      String[] horaEMin = this.hora.split(":"); // Divide a string da hora em horas e minutos
      int min = Integer.parseInt(horaEMin[1]); // Converte a parte dos minutos para inteiro
      return min; // Retorna os minutos
   }

   // Método para obter a quantidade do medicamento como um valor inteiro
   public int getQtd() {
      int qtdInt; // Declara uma variável inteira para a quantidade
      qtdInt = Integer.parseInt(this.qtd); // Converte a quantidade para inteiro
      return qtdInt; // Retorna a quantidade
   }

   // Método para obter a frequência do medicamento como um valor double
   public double getFreqInt() {
      double freqInt; // Declara uma variável de ponto flutuante para a frequência
      freqInt = Double.valueOf(this.freqInt); // Converte a frequência para double
      return freqInt; // Retorna a frequência
   }

   // Método para diminuir a quantidade do medicamento
   public void diminuiQtd() {
      int novaQtd; // Declara uma variável inteira para a nova quantidade

      if (this.getQtd() <= 0) { // Se a quantidade atual for menor ou igual a zero
         novaQtd = 0; // Define a nova quantidade como zero
      } else {
         novaQtd = this.getQtd() - 1; // Caso contrário, diminui a quantidade em 1
      }

      String qtdStr = String.valueOf(novaQtd); // Converte a nova quantidade para string
      this.qtd = qtdStr; // Atualiza a quantidade no objeto
   }

   // Método para obter a frequência em milissegundos
   public long getFreqInMillis() {
      double freqMilli; // Declara uma variável de ponto flutuante para a frequência em milissegundos
      freqMilli = (this.getFreqInt() * 3600000L); // Converte a frequência em horas para milissegundos
      return (long) freqMilli; // Retorna a frequência em milissegundos como um valor long
   }

   // Método comentado, possivelmente para futura implementação
   //public int getQtdInt(){}
}
