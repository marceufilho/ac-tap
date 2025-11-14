package br.edu.ibmec.strategy;

public class CalculoBolsista implements CalculoMensalidadeStrategy {

    @Override
    public double calcular(double valorBase) {
        // Returns 0% of base value (full scholarship)
        return valorBase * 0.0;
    }

    @Override
    public String getDescricao() {
        return "Mensalidade Bolsista - 0% do valor base (gratuito)";
    }
}
