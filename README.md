# funcional-programming-with-java
Repository for sample of funcional programming

MAP---------------------------------------->
package com.br.glass;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.br.glass.utils.FloatUtils;
import com.br.glass.utils.HttpUtils;
import com.google.gson.Gson;

public class Main {
	
	static Map<String, Object> mapRetorno = new HashMap<>();
	
	public static void main(String[] args) {
		String URL = "";
		
		Map<String, Object> http = HttpUtils.getGetConnection(URL);
		
		if((boolean) http.get("success")) {
			String returnCall = http.get("Return").toString();
						
			Gson gson = new Gson();
			
			try {
				Map<String, Object> mapJson = gson.fromJson(returnCall, Map.class);
				
				Map<String, Object> spcLocalIdentificacaoPessoa = getSpcLocalIdentificacaoPessoa(mapJson);
				
				Map<String, Object> spcNacionalHeaderConsulta = getNacionalHeaderConsulta(mapJson);
				Map<String, Object> spcLocalScoreCrediscores = getSpcLocalScoreCrediscores(mapJson);
				
				setSpcLocalIdentificacaoPessoa(spcLocalIdentificacaoPessoa);
				setSpcNacionalHeaderConsulta(spcNacionalHeaderConsulta);
				setSpcLocalScoreCrediscores(spcLocalScoreCrediscores);
				
				System.out.println("Retorno : " + mapRetorno);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Map<String, Object> getSpcLocalIdentificacaoPessoa(Map<String, Object> mapJson) {
		
		Map<String, Object> map = (Map<String, Object>) mapJson.get("spclocal");
		return (Map<String, Object>) map.get("identificacaoPessoa");
	}
	
	private static void setSpcLocalIdentificacaoPessoa(Map<String, Object> spcLocalIdentificacaoPessoa) {
		mapRetorno.put("nomePessoa", spcLocalIdentificacaoPessoa.get("nomePessoa"));
		Map<String, Object> sexoPessoa = (Map<String, Object>) spcLocalIdentificacaoPessoa.get("sexoPessoa");
		mapRetorno.put("sexoPessoa", sexoPessoa.get("value"));
		mapRetorno.put("dataNascimento", spcLocalIdentificacaoPessoa.get("dataNascimento"));
	}
	
	private static Map<String, Object> getNacionalHeaderConsulta(Map<String, Object> mapJson) {
		
		Map<String, Object> map = (Map<String, Object>) mapJson.get("spcnacional");
		return (Map<String, Object>) map.get("headerConsulta");
	}
	
	private static void setSpcNacionalHeaderConsulta(Map<String, Object> spcLocalIdentificacaoPessoa) {
		mapRetorno.put("cpfcnpjpessoa", spcLocalIdentificacaoPessoa.get("cpfcnpjpessoa"));
	}

	private static Map<String, Object> getSpcLocalScoreCrediscores(Map<String, Object> mapJson) {
		Map<String, Object> spclocal = (Map<String, Object>) mapJson.get("spclocal");
		
		Map<String, Object> score = (Map<String, Object>) spclocal.get("score");
		
		List<Map<String, Object>> crediscoresServer = (List<Map<String, Object>>) score.get("crediscoresServer");
		
		Map<String, Object> escoreCalculado = (Map<String, Object>) crediscoresServer.get(0);
		
		return escoreCalculado;
	}
	
	private static void setSpcLocalScoreCrediscores(Map<String, Object> spcLocalIdentificacaoPessoa) {
		mapRetorno.put("escoreCalculado", spcLocalIdentificacaoPessoa.get("escoreCalculado"));
	}
	
	public static void main_OLD(String [] args){
		String URL = "";
		
		Map<String, Object> http = HttpUtils.getGetConnection(URL);
		
		if((boolean) http.get("success")) {
			String returnCall = http.get("Return").toString();
			
			Map<String, Object> mapRetorno = new HashMap<>();
			
			Gson gson = new Gson();
	
			try {
				Map<String, Object> mapJson = gson.fromJson(returnCall, Map.class);
				
				Map<String, Object> veicular = returnVeicular(mapJson);
				
				Map<String, Object> returnVeicularAgregadoRegistros = returnVeicularAgregadoRegistros(veicular);
				
				List<Map<String, Object>> returnVeicularPrecificadorOcorrencias = returnVeicularPrecificadorOcorrencias(veicular);
				
				Map<String, Object> returnHeader = returnHeader(mapJson);
				
				Map<String, Object> returnHeaderDadosRetorno = returnHeaderDadosRetorno(returnHeader);

				setVeicularPrecificadorOcorrencias(mapRetorno, returnVeicularPrecificadorOcorrencias);
				
				setHeaderDadosRetorno(mapRetorno, returnHeaderDadosRetorno);
				
				setVeicularAgregadoRegistros(mapRetorno, returnVeicularAgregadoRegistros);
				
				
			} catch (Exception e) {
			    e.printStackTrace();
			}
			
			System.out.println("mapRetorno: " + mapRetorno);
		}
		
	}

	private static void setVeicularPrecificadorOcorrencias(Map<String, Object> mapRetorno,
			List<Map<String, Object>> returnVeicularPrecificadorOcorrencias) {
		
		FloatUtils floatUtils = new FloatUtils();
		
		Optional<Map<String, Object>> veiculoMaiorPreco = returnVeicularPrecificadorOcorrencias.stream()
				.max(Comparator.comparingDouble(veiculo ->  floatUtils.unFormatDecimal(veiculo.get("PRECO").toString())));
		
		Map<String, Object> veiculo = veiculoMaiorPreco.get();
		
		mapRetorno.put("PRECO", veiculo.get("PRECO"));
		mapRetorno.put("QUANTIDADE_DE_OCORRENCIAS", returnVeicularPrecificadorOcorrencias.size());
	}

	private static void setHeaderDadosRetorno(Map<String, Object> mapRetorno, Map<String, Object> returnHeaderDadosRetorno) {
		mapRetorno.put("LEILAO", returnHeaderDadosRetorno.get("LEILAO"));
		mapRetorno.put("ALERTAS", returnHeaderDadosRetorno.get("ALERTAS"));
		mapRetorno.put("ROUBO_FURTO", returnHeaderDadosRetorno.get("ROUBO_FURTO"));
	}

	private static void setVeicularAgregadoRegistros(Map<String, Object> mapRetorno, Map<String, Object> returnVeicularAgregadoRegistros) {
		mapRetorno.put("PLACA", returnVeicularAgregadoRegistros.get("PLACA"));
		mapRetorno.put("TIPO_DE_VEICULO", returnVeicularAgregadoRegistros.get("TIPO_DE_VEICULO"));
		mapRetorno.put("MARCA_MODELO", returnVeicularAgregadoRegistros.get("MARCA_MODELO"));
		mapRetorno.put("COR", returnVeicularAgregadoRegistros.get("COR"));
		mapRetorno.put("ANO_MODELO", returnVeicularAgregadoRegistros.get("ANO_MODELO"));
		mapRetorno.put("COMBUSTIVEL", returnVeicularAgregadoRegistros.get("COMBUSTIVEL"));
		mapRetorno.put("CHASSI", returnVeicularAgregadoRegistros.get("CHASSI"));
	}

	private static Map<String, Object> returnVeicular(Map<String, Object> mapJson) {
		return (Map<String, Object>) mapJson.get("VEICULAR");
	}
	
	private static Map<String, Object> returnHeader(Map<String, Object> mapJson) {
		return (Map<String, Object>) mapJson.get("HEADER");
	}
	
	private static Map<String, Object> returnHeaderDadosRetorno(Map<String, Object> header) {
		return (Map<String, Object>) header.get("DADOS_RETORNADOS");
	}

	private static Map<String, Object> returnVeicularAgregadoRegistros(Map<String, Object> veicular) {
		
		Map<String, Object> agregados = (Map<String, Object>) veicular.get("AGREGADOS");
		
		List<Map<String, Object>> registros = (List<Map<String, Object>>) agregados.get("REGISTROS");
		
		return (Map<String, Object>) registros.get(0);
	}
	
	private static List<Map<String, Object>> returnVeicularPrecificadorOcorrencias(Map<String, Object> veicular) {
		
		Map<String, Object> precificador = (Map<String, Object>) veicular.get("PRECIFICADOR");
		
		return (List<Map<String, Object>>) precificador.get("OCORRENCIAS");
	}

}


MAP----------------------------------------//

SQL Queries

SELECT * FROM SeguradoraComissao WHERE seg_codigo = 23;

SELECT * FROM Seguradoras s WHERE NOT EXISTS(SELECT 1 FROM SeguradoraComissao sc WHERE s.seg_codigo = sc.seg_codigo)

SELECT * FROM SeguradoraComissao sc WHERE NOT EXISTS(SELECT 1 FROM Seguradoras s WHERE s.seg_codigo = sc.seg_codigo)

SELECT COUNT(*) AS Quantidade, seg_codigo FROM SeguradoraComissao;

SELECT sc.sec_codigo, s.seg_descricao FROM SeguradoraComissao sc INNER JOIN Seguradoras s ON s.seg_codigo = sc.seg_codigo
