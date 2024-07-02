package queiroz.larissa.projetointegrador.model;

public class Compartimento {
   public String hora;
   public String nome;
   public String qtd;
   public String desc;
   public String data;
   public String freq;
   public String freqInt;

   public int getHoras() {
      String[] horaEMin = this.hora.split(":");
      int horas = Integer.parseInt(horaEMin[0]);
      return horas;
   }

   public int getMinutos() {
      String[] horaEMin = this.hora.split(":");
      int min = Integer.parseInt(horaEMin[1]);

      if (min == 0){
         min = 00;
      }
      return min;
   }

   public int getQtd(){
      int qtdInt;
      qtdInt = Integer.parseInt(this.qtd);
      return qtdInt;
   }

   public double getFreqInt(){
      double freqInt;
      freqInt = Double.valueOf(this.freqInt);
      return freqInt;
   }

   public void diminuiQtd(){
       int novaQtd = this.getQtd() - 1;
       String qtdStr = String.valueOf(novaQtd);
       this.qtd = qtdStr;
   }

   public long getFreqInMillis(){
      double freqMilli;

      freqMilli = this.getFreqInt() * (1000 * 60 * 60);

      return (long) freqMilli;
   }
   //public int getQtdInt(){}
}
