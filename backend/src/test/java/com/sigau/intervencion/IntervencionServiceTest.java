package com.sigau.intervencion;

import com.sigau.common.Estados;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.ejemplar.Ejemplar;
import com.sigau.ejemplar.repository.EjemplarRepository;
import com.sigau.historial.service.HistorialService;
import com.sigau.intervencion.dto.CambioEstadoIntervencionRequest;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.intervencion.repository.TipoIntervencionRepository;
import com.sigau.intervencion.service.IntervencionService;
import com.sigau.relacion.demandaejemplar.repository.DemandaEjemplarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntervencionServiceTest {

    @Mock
    private IntervencionRepository intervencionRepository;

    @Mock
    private DemandaRepository demandaRepository;

    @Mock
    private EjemplarRepository ejemplarRepository;

    @Mock
    private TipoIntervencionRepository tipoIntervencionRepository;

    @Mock
    private DemandaEjemplarRepository demandaEjemplarRepository;

    @Mock
    private HistorialService historialService;

    @InjectMocks
    private IntervencionService intervencionService;

    private Ejemplar ejemplar() {
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setId(7);
        ejemplar.setEstado(Estados.EJEMPLAR_ACTIVO);
        return ejemplar;
    }

    private Intervencion intervencion(
            String tipoNombre, String estado, Ejemplar ejemplar, String estadoDemanda) {

        Demanda demanda = new Demanda();
        demanda.setEstado(estadoDemanda);

        TipoIntervencion tipo = new TipoIntervencion();
        tipo.setNombre(tipoNombre);

        Intervencion intervencion = new Intervencion();
        intervencion.setId(1);
        intervencion.setDemanda(demanda);
        intervencion.setEjemplar(ejemplar);
        intervencion.setTipoIntervencion(tipo);
        intervencion.setEstado(estado);
        return intervencion;
    }

    private CambioEstadoIntervencionRequest pedido(String estado) {
        CambioEstadoIntervencionRequest request = new CambioEstadoIntervencionRequest();
        request.setEstado(estado);
        return request;
    }

    @Test
    void laExtraccionRealizadaPasaElEjemplarAExtraido() {
        Ejemplar ejemplar = ejemplar();
        Intervencion intervencion = intervencion(
                "EXTRACCION", Estados.INTERVENCION_PLANIFICADA,
                ejemplar, Estados.DEMANDA_EN_GESTION);

        when(intervencionRepository.findById(1)).thenReturn(Optional.of(intervencion));
        when(intervencionRepository.save(any(Intervencion.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Intervencion resultado = intervencionService.cambiarEstado(
                1, pedido("realizada"));

        assertEquals(Estados.INTERVENCION_REALIZADA, resultado.getEstado());
        assertNotNull(resultado.getFechaEjecucion());
        assertEquals(Estados.EJEMPLAR_EXTRAIDO, ejemplar.getEstado());

        verify(ejemplarRepository).save(ejemplar);
        verify(historialService).registrar(
                HistorialService.EJEMPLAR, 7,
                Estados.EJEMPLAR_ACTIVO, Estados.EJEMPLAR_EXTRAIDO,
                1, "Extracción realizada");
    }

    @Test
    void unaPodaRealizadaNoModificaElEjemplar() {
        Ejemplar ejemplar = ejemplar();
        Intervencion intervencion = intervencion(
                "PODA", Estados.INTERVENCION_EN_EJECUCION,
                ejemplar, Estados.DEMANDA_EN_GESTION);

        when(intervencionRepository.findById(1)).thenReturn(Optional.of(intervencion));
        when(intervencionRepository.save(any(Intervencion.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        intervencionService.cambiarEstado(1, pedido("REALIZADA"));

        assertEquals(Estados.EJEMPLAR_ACTIVO, ejemplar.getEstado());
        verify(ejemplarRepository, never()).save(any());
    }

    @Test
    void noPermiteSaltearEstados() {
        Intervencion intervencion = intervencion(
                "PODA", Estados.INTERVENCION_PENDIENTE,
                ejemplar(), Estados.DEMANDA_EN_GESTION);

        when(intervencionRepository.findById(1)).thenReturn(Optional.of(intervencion));

        assertThrows(ReglaNegocioException.class,
                () -> intervencionService.cambiarEstado(1, pedido("REALIZADA")));

        verify(intervencionRepository, never()).save(any());
    }

    @Test
    void noEjecutaMientrasLaIntervencionPreviaNoEstaRealizada() {
        Ejemplar ejemplar = ejemplar();
        Intervencion previa = intervencion(
                "EXTRACCION", Estados.INTERVENCION_PLANIFICADA,
                ejemplar, Estados.DEMANDA_EN_GESTION);
        previa.setId(2);

        Intervencion intervencion = intervencion(
                "REPARACION_VEREDA", Estados.INTERVENCION_PLANIFICADA,
                ejemplar, Estados.DEMANDA_EN_GESTION);
        intervencion.setIntervencionPrevia(previa);

        when(intervencionRepository.findById(1)).thenReturn(Optional.of(intervencion));

        assertThrows(ReglaNegocioException.class,
                () -> intervencionService.cambiarEstado(1, pedido("EN_EJECUCION")));
    }

    @Test
    void planificarExigeUnaFechaPrevista() {
        Intervencion intervencion = intervencion(
                "PODA", Estados.INTERVENCION_PENDIENTE,
                ejemplar(), Estados.DEMANDA_EN_GESTION);

        when(intervencionRepository.findById(1)).thenReturn(Optional.of(intervencion));

        assertThrows(ReglaNegocioException.class,
                () -> intervencionService.cambiarEstado(1, pedido("PLANIFICADA")));

        CambioEstadoIntervencionRequest conFecha = pedido("PLANIFICADA");
        conFecha.setFechaPlanificada(LocalDate.now().plusDays(3));
        when(intervencionRepository.save(any(Intervencion.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Intervencion resultado = intervencionService.cambiarEstado(1, conFecha);

        assertEquals(Estados.INTERVENCION_PLANIFICADA, resultado.getEstado());
    }

    @Test
    void noModificaIntervencionesDeUnaDemandaCancelada() {
        Intervencion intervencion = intervencion(
                "PODA", Estados.INTERVENCION_PENDIENTE,
                ejemplar(), Estados.DEMANDA_CANCELADA);

        when(intervencionRepository.findById(1)).thenReturn(Optional.of(intervencion));

        assertThrows(ReglaNegocioException.class,
                () -> intervencionService.cambiarEstado(1, pedido("PLANIFICADA")));
    }
}
