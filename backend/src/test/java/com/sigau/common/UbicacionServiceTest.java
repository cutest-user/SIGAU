package com.sigau.common;

import com.sigau.common.dto.UbicacionRequest;
import com.sigau.common.repository.UbicacionRepository;
import com.sigau.common.service.UbicacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UbicacionServiceTest {

    @Mock
    private UbicacionRepository ubicacionRepository;

    @InjectMocks
    private UbicacionService ubicacionService;

    private UbicacionRequest pedido(String calle, String numero, String barrio) {
        UbicacionRequest request = new UbicacionRequest();
        request.setCalle(calle);
        request.setNumero(numero);
        request.setBarrio(barrio);
        return request;
    }

    @Test
    void reutilizaUnaDireccionYaRegistrada() {
        Ubicacion existente = new Ubicacion();
        existente.setCalle("Av. Rivadavia");

        when(ubicacionRepository.buscarIguales("Av. Rivadavia", "1200", ""))
                .thenReturn(List.of(existente));

        Ubicacion resultado = ubicacionService.guardar(
                pedido("  Av.  Rivadavia ", "1200", null));

        assertSame(existente, resultado);
        verify(ubicacionRepository, never()).save(any());
    }

    @Test
    void guardaLaDireccionNuevaSinEspaciosSobrantes() {
        when(ubicacionRepository.buscarIguales("Av. Corrientes", "500", "Almagro"))
                .thenReturn(List.of());
        when(ubicacionRepository.save(any(Ubicacion.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Ubicacion resultado = ubicacionService.guardar(
                pedido("  Av.   Corrientes ", " 500 ", "Almagro"));

        assertEquals("Av. Corrientes", resultado.getCalle());
        assertEquals("500", resultado.getNumero());
        assertEquals("Almagro", resultado.getBarrio());
    }
}
