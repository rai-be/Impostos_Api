package com.test.api_impostos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

    @SpringBootTest // O @SpringBootTest cria uma instância completa do Spring Boot, o que significa que tudo, incluindo os beans e o contexto da aplicação, é carregado.
    public class ImportoServiceTest {
        @Mock
        private ImpostoRepository impostoRepository; // Mockando o repositório

        @Autowired
        private ImpostoService impostoService; // Serviço que quero testar

        private Imposto imposto;

        @BeforeEach
        public void setUp() {
            imposto = new Imposto("3", "ICMS", "Imposto sobre Circulação de Mercadorias e Serviços", 18.0);
        }

        @Test
        public void testCadastrarTipoImposto() {

            Mockito.when(impostoRepository.save(Mockito.any(Imposto.class))).thenReturn(imposto);

            Imposto resultado = impostoService.cadastrar(imposto);

            assertNotNull(resultado);
            assertEquals("ICMS", resultado.getNome());
            Mockito.verify(impostoRepository, Mockito.times(1)).save(imposto);
        }

        @Test
        public void testCalcularImposto() {
            double valorBase = 1000.0;
            Mockito.when(impostoRepository.findById(imposto.getId())).thenReturn(Optional.of(imposto));

            double valorImposto = impostoService.calcularImposto(imposto.getId(), valorBase); // Chamando o serviço para calcular o imposto

            assertEquals(180.0, valorImposto);
        }

        @Test
        public void testBuscarImpostoPorId() {
            Mockito.when(impostoRepository.findById(imposto.getId())).thenReturn(Optional.of(imposto)); // Simulando (mockando) o comportamento do repositório

            Imposto resultado = impostoService.buscarPorId(imposto.getId()); // Chamando o serviço para buscar o imposto

            assertNotNull(resultado);
            assertEquals("ICMS", resultado.getNome());
        }

        @Test
        public void testExcluirImposto() {

            Mockito.when(impostoRepository.findById(imposto.getId())).thenReturn(Optional.of(imposto));

            impostoService.excluir(imposto.getId());

            Mockito.verify(impostoRepository, Mockito.times(1)).deleteById(imposto.getId());
        }
    }


