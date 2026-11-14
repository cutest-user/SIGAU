package com.sigau.certificacion;

import com.sigau.certificacion.repository.CertificacionRepository;
import com.sigau.certificacion.service.CertificacionService;
import com.sigau.common.Estados;
import com.sigau.common.exception.RecursoDuplicadoException;
import com.sigau.common.exception.ReglaNegocioException;
import com.sigau.config.UsuarioActual;
import com.sigau.demanda.Demanda;
import com.sigau.demanda.repository.DemandaRepository;
import com.sigau.historial.service.HistorialService;
import com.sigau.intervencion.Intervencion;
import com.sigau.intervencion.repository.IntervencionRepository;
import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import com.sigau.verificacion.repository.VerificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificacionServiceTest {

    @Mock
    private CertificacionRepository certificacionRepository;

    @Mock
    private DemandaRepository demandaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private IntervencionRepository intervencionRepository;

    @Mock
    private VerificacionRepository verificacionRepository;

    @Mock
    private HistorialService historialService;

    @Mock
    private UsuarioActual usuarioActual;

    @InjectMocks
    private CertificacionService certificacionService;

    private Demanda demanda;

    @BeforeEach
    void preparar() {
        demanda = new Demanda();
        ReflectionTestUtils.setField(demanda, "id", 3);
        demanda.setEstado(Estados.DEMANDA_EN_GESTION);

        lenient().when(demandaRepository.findById(3)).thenReturn(Optional.of(demanda));
        lenient().when(usuarioActual.id()).thenReturn(9);
        lenient().when(usuarioRepository.findById(9)).thenReturn(Optional.of(new Usuario()));
    }

    private Certificacion pedido(String numero) {
        Certificacion certificacion = new Certificacion();
        certificacion.setId(numero);
        certificacion.setDemanda(demanda);
        return certificacion;
    }

    private Intervencion intervencion(Integer id, String estado) {
        Intervencion intervencion = new Intervencion();
        intervencion.setId(id);
        intervencion.setEstado(estado);
        return intervencion;
    }

    @Test
    void rechazaUnNumeroDeOrdenYaRegistrado() {
        when(certificacionRepository.existsById("OT-100")).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class,
                () -> certificacionService.guardar(pedido("OT-100")));
    }

    @Test
    void rechazaDemandasConIntervencionesSinRealizar() {
        when(intervencionRepository.findByDemanda_Id(3))
                .thenReturn(List.of(intervencion(5, Estados.INTERVENCION_PLANIFICADA)));

        assertThrows(ReglaNegocioException.class,
                () -> certificacionService.guardar(pedido("OT-101")));

        verify(certificacionRepository, never()).save(any());
    }

    @Test
    void rechazaIntervencionesSinVerificacionAprobada() {
        when(intervencionRepository.findByDemanda_Id(3))
                .thenReturn(List.of(intervencion(5, Estados.INTERVENCION_REALIZADA)));
        when(verificacionRepository.existsByIntervencion_IdAndResultado(
                5, Estados.VERIFICACION_APROBADA)).thenReturn(false);

        assertThrows(ReglaNegocioException.class,
                () -> certificacionService.guardar(pedido("OT-102")));
    }

    @Test
    void certificaYCierraLaDemandaCuandoTodoEstaVerificado() {
        when(intervencionRepository.findByDemanda_Id(3)).thenReturn(List.of(
                intervencion(5, Estados.INTERVENCION_REALIZADA),
                intervencion(6, Estados.INTERVENCION_CANCELADA)));
        when(verificacionRepository.existsByIntervencion_IdAndResultado(
                5, Estados.VERIFICACION_APROBADA)).thenReturn(true);
        when(certificacionRepository.save(any(Certificacion.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Certificacion resultado = certificacionService.guardar(pedido(" OT-103 "));

        assertEquals("OT-103", resultado.getId());
        assertEquals(Estados.CERTIFICACION_PLANIFICADA, resultado.getEstado());
        assertNotNull(resultado.getFecha());
        assertEquals(Estados.DEMANDA_CERTIFICADA, demanda.getEstado());
        assertNotNull(demanda.getFechaCierre());

        verify(historialService).registrar(
                HistorialService.DEMANDA, 3,
                Estados.DEMANDA_EN_GESTION, Estados.DEMANDA_CERTIFICADA,
                null, "Certificada con la orden OT-103");
    }
}
