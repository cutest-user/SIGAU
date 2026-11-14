import { useState } from 'react'
import { api } from '../../api/cliente'
import useAccion from '../../hooks/useAccion'

function AccionesIntervencion({ intervencion, onCambio }) {
    const [fecha, setFecha] = useState('')
    const { ejecutar, error, enviando } = useAccion(onCambio)
    const { estado } = intervencion

    if (estado === 'REALIZADA' || estado === 'CANCELADA') return null

    const cambiar = (nuevoEstado, extra = {}) =>
        ejecutar(() => api.patch(
            `/api/intervenciones/${intervencion.id}/estado`,
            { estado: nuevoEstado, ...extra }
        ))

    return (
        <div className="d-flex flex-wrap gap-1 align-items-center">
            {estado === 'PENDIENTE' && (
                <>
                    <input
                        type="date"
                        className="form-control form-control-sm w-auto"
                        value={fecha}
                        onChange={(e) => setFecha(e.target.value)}
                        aria-label="Fecha prevista"
                    />
                    <button
                        className="btn btn-sm btn-outline-info"
                        type="button"
                        disabled={enviando || !(fecha || intervencion.fechaPlanificada)}
                        onClick={() => cambiar('PLANIFICADA', { fechaPlanificada: fecha || undefined })}
                    >
                        Planificar
                    </button>
                </>
            )}

            {estado === 'PLANIFICADA' && (
                <button
                    className="btn btn-sm btn-outline-warning"
                    type="button"
                    disabled={enviando}
                    onClick={() => cambiar('EN_EJECUCION')}
                >
                    Iniciar
                </button>
            )}

            {(estado === 'PLANIFICADA' || estado === 'EN_EJECUCION') && (
                <button
                    className="btn btn-sm btn-outline-success"
                    type="button"
                    disabled={enviando}
                    onClick={() => cambiar('REALIZADA')}
                >
                    Realizada
                </button>
            )}

            <button
                className="btn btn-sm btn-outline-danger"
                type="button"
                disabled={enviando}
                onClick={() => cambiar('CANCELADA')}
            >
                Cancelar
            </button>

            {error && <div className="text-danger small w-100">{error}</div>}
        </div>
    )
}

export default AccionesIntervencion
