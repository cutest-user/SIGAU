package com.sigau.common;

public final class Textos {

    private Textos() {
    }

    public static String limpiar(String valor) {
        if (valor == null) {
            return null;
        }

        String resultado = valor.trim().replaceAll("\\s+", " ");

        return resultado.isEmpty() ? null : resultado;
    }
}
