package br.edu.ibmec.strategy;

public class CalculoComDesconto implements CalculoMensalidadeStrategy {

    @Override
    public double calcular(double valorBase) {
        return valorBase * 0.5;
    }

    @Override
    public String getDescricao() {
        return "Mensalidade com Desconto - 50% do valor base";
    }
}
