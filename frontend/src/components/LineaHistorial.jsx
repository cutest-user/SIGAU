import { formatearFecha, textoEstado } from '../api/formatos'

const ETIQUETAS = {
    DEMANDA: 'Demanda',
    EJEMPLAR: 'Ejemplar',
    INTERVENCION: 'Intervención'
}

function LineaHistorial({ cambios }) {
    if (!cambios || cambios.length === 0) {
        return <p className="text-secondary mb-0">Todavía no hay cambios registrados.</p>
    }

    return (
        <ul className="list-group list-group-flush">
            {cambios.map((cambio) => (
                <li className="list-group-item px-0" key={cambio.id}>
                    <div className="d-flex justify-content-between flex-wrap">
                        <strong>
                            {ETIQUETAS[cambio.entidad] ?? cambio.entidad} #{cambio.entidadId}
                        </strong>
                        <span className="text-secondary small">
                            {formatearFecha(cambio.fecha)}
                        </span>
                    </div>

                    <div>
                        {cambio.estadoAnterior
                            ? `${textoEstado(cambio.estadoAnterior)} → ${textoEstado(cambio.estadoNuevo)}`
                            : textoEstado(cambio.estadoNuevo)}
                    </div>

                    {cambio.observacion && (
                        <div className="text-secondary small">{cambio.observacion}</div>
                    )}
                </li>
            ))}
        </ul>
    )
}

export default LineaHistorial
