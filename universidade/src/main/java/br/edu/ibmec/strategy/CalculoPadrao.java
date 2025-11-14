package br.edu.ibmec.strategy;

public class CalculoPadrao implements CalculoMensalidadeStrategy {

    @Override
    public double calcular(double valorBase) {
        return valorBase * 1.0;
    }

    @Override
    public String getDescricao() {
        return "Mensalidade Padrão - 100% do valor base";
    }
}
