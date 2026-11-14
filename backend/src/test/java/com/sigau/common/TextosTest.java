package com.sigau.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TextosTest {

    @Test
    void devuelveNuloSiElTextoEsNuloOVacio() {
        assertNull(Textos.limpiar(null));
        assertNull(Textos.limpiar("   "));
    }

    @Test
    void quitaEspaciosSobrantes() {
        assertEquals("Av. Rivadavia 1200", Textos.limpiar("  Av.   Rivadavia  1200 "));
    }
}
