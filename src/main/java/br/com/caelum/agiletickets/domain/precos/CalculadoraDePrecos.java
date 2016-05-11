package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {
	 
		private static BigDecimal aumentoPorDisponibilidade(Sessao sessao, double percentualMinimo, double percentualAumento){
			
			if ((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= (percentualMinimo / 100)) {
				return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(percentualAumento / 100)));
			}
			
			return sessao.getPreco();
		}
		
		
		private static BigDecimal aumentoPorDuracao(Sessao sessao, Integer minutos, double percentualAumento){
              if(sessao.getDuracaoEmMinutos() > minutos){
				return sessao.getPreco().multiply(BigDecimal.valueOf(percentualAumento  / 100));
			}
              return BigDecimal.ZERO;
		}
	

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco = aumentoPorDisponibilidade(sessao, 5, 10);
			
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
	
			preco = aumentoPorDisponibilidade(sessao, 50, 20);
//			if(sessao.getDuracaoEmMinutos() > 60){
//				
//				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
//			}
//
//		}  
			preco = preco.add(aumentoPorDuracao(sessao, 60, 10));
		} else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}