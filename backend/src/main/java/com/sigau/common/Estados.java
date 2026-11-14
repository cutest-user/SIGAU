package com.sigau.common;

import java.util.Set;

public final class Estados {

    private Estados() {
    }

    public static final String EJEMPLAR_ACTIVO = "ACTIVO";
    public static final String EJEMPLAR_EXTRAIDO = "EXTRAIDO";

    public static final String DEMANDA_INGRESADA = "INGRESADA";
    public static final String DEMANDA_EN_GESTION = "EN_GESTION";
    public static final String DEMANDA_CERTIFICADA = "CERTIFICADA";
    public static final String DEMANDA_CANCELADA = "CANCELADA";

    public static final String INTERVENCION_PENDIENTE = "PENDIENTE";
    public static final String INTERVENCION_PLANIFICADA = "PLANIFICADA";
    public static final String INTERVENCION_EN_EJECUCION = "EN_EJECUCION";
    public static final String INTERVENCION_REALIZADA = "REALIZADA";
    public static final String INTERVENCION_CANCELADA = "CANCELADA";

    public static final String INSPECCION_REALIZADA = "REALIZADA";

    public static final String VERIFICACION_APROBADA = "APROBADA";
    public static final String VERIFICACION_OBSERVADA = "OBSERVADA";

    public static final String DERIVACION_PENDIENTE = "PENDIENTE";

    public static final String CERTIFICACION_PLANIFICADA = "PLANIFICADA";

    public static final Set<String> RESULTADOS_VERIFICACION =
            Set.of(VERIFICACION_APROBADA, VERIFICACION_OBSERVADA);

    public static final Set<String> SISTEMAS_DESTINO =
            Set.of("147", "SAP", "ARBOPEDIA");

    public static final Set<String> ESTADOS_INTERVENCION = Set.of(
            INTERVENCION_PENDIENTE,
            INTERVENCION_PLANIFICADA,
            INTERVENCION_EN_EJECUCION,
            INTERVENCION_REALIZADA,
            INTERVENCION_CANCELADA);
}
