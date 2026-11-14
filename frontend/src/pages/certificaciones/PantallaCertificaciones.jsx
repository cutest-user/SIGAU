import { useCallback } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, nombreCompleto } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import useDatos from '../../hooks/useDatos'

function PantallaCertificaciones({ onVerDemanda }) {
    const cargar = useCallback(() => api.get('/api/certificaciones'), [])
    const { datos, error, cargando } = useDatos(cargar)

    return (
        <div>
            <div className="mb-4">
                <h2 className="h3 mb-1">Certificaciones</h2>
                <p className="text-secondary mb-0">
                    Órdenes de trabajo con las que se cierran las demandas.
                </p>
            </div>

            <Alerta>{error}</Alerta>

            <div className="card">
                <div className="card-body">
                    {cargando ? (
                        <Cargando />
                    ) : (
                        <div className="table-responsive">
                            <table className="table table-hover align-middle">
                                <thead>
                                <tr>
                                    <th>Orden</th>
                                    <th>Demanda</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th>Responsable</th>
                                    <th>Observaciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                {(datos ?? []).length === 0 && (
                                    <tr>
                                        <td colSpan="6" className="text-center text-secondary py-4">
                                            No hay certificaciones registradas.
                                        </td>
                                    </tr>
                                )}
                                {(datos ?? []).map((c) => (
                                    <tr key={c.id}>
                                        <td>{c.id}</td>
                                        <td>
                                            <button
                                                className="btn btn-link p-0"
                                                onClick={() => onVerDemanda(c.demanda.id)}
                                            >
                                                #{c.demanda.id}
                                            </button>
                                        </td>
                                        <td>{formatearFecha(c.fecha)}</td>
                                        <td>{c.estado}</td>
                                        <td>{nombreCompleto(c.usuario)}</td>
                                        <td>{c.observaciones || '—'}</td>
                                    </tr>
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

export default PantallaCertificaciones
