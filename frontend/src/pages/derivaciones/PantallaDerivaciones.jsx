import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import InsigniaEstado from '../../components/InsigniaEstado'
import useAccion from '../../hooks/useAccion'
import useDatos from '../../hooks/useDatos'

const ESTADOS = ['PENDIENTE', 'ENVIADA', 'CONFIRMADA', 'RECHAZADA']

function FilaDerivacion({ derivacion, onVerDemanda, onCambio }) {
    const [estado, setEstado] = useState(derivacion.estado)
    const [referencia, setReferencia] = useState(derivacion.referenciaExterna ?? '')
    const { ejecutar, error, enviando } = useAccion(onCambio)

    const actualizar = () =>
        ejecutar(() => api.patch(`/api/derivaciones/${derivacion.id}/estado`, {
            estado,
            referenciaExterna: referencia || null
        }))

    return (
        <tr>
            <td>{derivacion.id}</td>
            <td>
                <button className="btn btn-link p-0" onClick={() => onVerDemanda(derivacion.demanda.id)}>
                    #{derivacion.demanda.id}
                </button>
            </td>
            <td>{derivacion.sistemaDestino}</td>
            <td>{formatearFecha(derivacion.fecha)}</td>
            <td><InsigniaEstado valor={derivacion.estado} /></td>
            <td>
                <div className="d-flex gap-1 flex-wrap">
                    <select
                        className="form-select form-select-sm w-auto"
                        value={estado}
                        onChange={(e) => setEstado(e.target.value)}
                    >
                        {ESTADOS.map((e) => <option key={e} value={e}>{e}</option>)}
                    </select>
                    <input
                        className="form-control form-control-sm w-auto"
                        placeholder="Referencia"
                        value={referencia}
                        maxLength={100}
                        onChange={(e) => setReferencia(e.target.value)}
                    />
                    <button
                        className="btn btn-sm btn-outline-success"
                        disabled={enviando}
                        onClick={actualizar}
                    >
                        Guardar
                    </button>
                </div>
                {error && <div className="text-danger small">{error}</div>}
            </td>
        </tr>
    )
}

function PantallaDerivaciones({ onVerDemanda }) {
    const cargar = useCallback(() => api.get('/api/derivaciones'), [])
    const { datos, error, cargando, recargar } = useDatos(cargar)
    const derivaciones = [...(datos ?? [])].sort((a, b) => b.id - a.id)

    return (
        <div>
            <div className="mb-4">
                <h2 className="h3 mb-1">Derivaciones</h2>
                <p className="text-secondary mb-0">
                    Envíos de demandas a otros sistemas (147, SAP, Arbopedia). Se crean desde el detalle de la demanda.
                </p>
            </div>

            <Alerta>{error}</Alerta>

            <div className="card">
                <div className="card-body">
                    {cargando ? (
                        <Cargando />
                    ) : (
                        <div className="table-responsive">
                            <table className="table align-middle">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Demanda</th>
                                    <th>Sistema</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th>Actualizar</th>
                                </tr>
                                </thead>
                                <tbody>
                                {derivaciones.length === 0 && (
                                    <tr>
                                        <td colSpan="6" className="text-center text-secondary py-4">
                                            No hay derivaciones registradas.
                                        </td>
                                    </tr>
                                )}
                                {derivaciones.map((d) => (
                                    <FilaDerivacion
                                        key={d.id}
                                        derivacion={d}
                                        onVerDemanda={onVerDemanda}
                                        onCambio={recargar}
                                    />
                                ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default PantallaDerivaciones
