package br.com.duxusdesafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;

@ExtendWith(MockitoExtension.class)
public class TesteApiService {

    private final static LocalDate data1993 = LocalDate.of(1993,1, 1);
    private final static LocalDate data1995 = LocalDate.of(1995,1, 1);

    @Spy
    private ApiService apiService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    static Stream<Arguments> testTimeDaDataParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();
        Time timeChicagoBullsDe1995 = dadosParaTesteApiService.getTimeChicagoBullsDe1995();
        Time timeDetroidPistonsDe1993 = dadosParaTesteApiService.getTimeDetroidPistonsDe1993();

        return Stream.of(
                arguments(data1995, todosOsTimes, timeChicagoBullsDe1995),
                arguments(data1993, todosOsTimes, timeDetroidPistonsDe1993)
        );
    }

    @ParameterizedTest
    @MethodSource("testTimeDaDataParams")
    public void testTimeDaData(LocalDate data, List<Time> todosOsTimes, Time esperado) {
        Time timeRetornado = apiService.timeDaData(data, todosOsTimes);
        assertEquals(esperado, timeRetornado);
    }

    static Stream<Arguments> testIntegranteMaisUsadoParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();
        
        return Stream.of(
                arguments(data1993, data1995, todosOsTimes, dadosParaTesteApiService.getDenis_rodman())
        );
    }

    @ParameterizedTest
    @MethodSource("testIntegranteMaisUsadoParams")
    public void testIntegranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Integrante esperado) {
        Integrante integranteRetornado = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, integranteRetornado);
    }

    static Stream<Arguments> testTimeMaisComumParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        List<String> integrantesEsperados = Arrays.asList(
                dadosParaTesteApiService.getDenis_rodman().getNome(),
                dadosParaTesteApiService.getMichael_jordan().getNome(),
                dadosParaTesteApiService.getScottie_pippen().getNome()
        );

        return Stream.of(
                arguments(data1993, data1995, todosOsTimes, integrantesEsperados)
        );
    }

    @ParameterizedTest
    @MethodSource("testTimeMaisComumParams")
    public void testIntegrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, List<String> esperado) {
        List<String> nomeDosIntegrantesDoTimeMaisComum = apiService.integrantesDoTimeMaisComum(dataInicial, dataFinal, todosOsTimes);
        
        if (nomeDosIntegrantesDoTimeMaisComum != null) {
            nomeDosIntegrantesDoTimeMaisComum.sort(Comparator.naturalOrder());
        }

        assertEquals(esperado, nomeDosIntegrantesDoTimeMaisComum);
    }

    static Stream<Arguments> testFuncaoMaisComumParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        return Stream.of(
                arguments(data1993, data1995, todosOsTimes, "ala")
        );
    }

    @ParameterizedTest
    @MethodSource("testFuncaoMaisComumParams")
    public void testFuncaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, String esperado) {
        String funcaoMaisComum = apiService.funcaoMaisComum(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, funcaoMaisComum);
    }

    static Stream<Arguments> testFranquiaMaisFamosaParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        return Stream.of(
                arguments(data1993, data1995, todosOsTimes, dadosParaTesteApiService.getFranquiaNBA())
        );
    }

    @ParameterizedTest
    @MethodSource("testFranquiaMaisFamosaParams")
    public void testFranquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, String esperado) {
        String franquiaMaisFamosa = apiService.franquiaMaisFamosa(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, franquiaMaisFamosa);
    }

    static Stream<Arguments> testContagemPorFranquiaParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        Map<String, Long> esperado = new HashMap<>();
        esperado.put(dadosParaTesteApiService.getFranquiaNBA(), 3L);

        return Stream.of(
                arguments(data1993, data1995, todosOsTimes, esperado)
        );
    }

    @ParameterizedTest
    @MethodSource("testContagemPorFranquiaParams")
    public void testContagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Map<String, Long> esperado) {
        Map<String, Long> contagemPorFranquia = apiService.contagemPorFranquia(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, contagemPorFranquia);
    }

    static Stream<Arguments> testContagemPorFuncaoParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        Map<String, Long> esperado = new HashMap<>();
        esperado.put("ala", 2L);
        esperado.put("ala-pivô", 1L);

        return Stream.of(
                arguments(data1993, data1995, todosOsTimes, esperado)
        );
    }

    @ParameterizedTest
    @MethodSource("testContagemPorFuncaoParams")
    public void testContagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Map<String, Long> esperado) {
        Map<String, Long> contagemPorFuncao = apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, contagemPorFuncao);
    }

}