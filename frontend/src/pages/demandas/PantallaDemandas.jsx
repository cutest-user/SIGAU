import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, textoUbicacion } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import InsigniaEstado from '../../components/InsigniaEstado'
import useDatos from '../../hooks/useDatos'

const ESTADOS = ['INGRESADA', 'EN_GESTION', 'CERTIFICADA', 'CANCELADA']

function PantallaDemandas({ onNuevaDemanda, onVerDetalle }) {
    const cargar = useCallback(() => api.get('/api/demandas'), [])
    const { datos, error, cargando } = useDatos(cargar)
    const [filtroEstado, setFiltroEstado] = useState('')

    const demandas = (datos ?? [])
        .filter((demanda) => !filtroEstado || demanda.estado === filtroEstado)
        .sort((a, b) => b.id - a.id)

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                <div>
                    <h2 className="h3 mb-1">Demandas</h2>
                    <p className="text-secondary mb-0">
                        Registro y seguimiento de demandas de arbolado urbano.
                    </p>
                </div>

                <button
                    className="btn btn-success"
                    type="button"
                    onClick={onNuevaDemanda}
                >
                    + Nueva demanda
                </button>
            </div>

            <Alerta>{error}</Alerta>

            <div className="card">
                <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                        <h3 className="h5 mb-0">Listado de demandas</h3>

                        <select
                            className="form-select w-auto"
                            value={filtroEstado}
                            onChange={(e) => setFiltroEstado(e.target.value)}
                            aria-label="Filtrar por estado"
                        >
                            <option value="">Todos los estados</option>
                            {ESTADOS.map((estado) => (
                                <option key={estado} value={estado}>
                                    {estado.replace('_', ' ')}
                                </option>
                            ))}
                        </select>
                    </div>

                    {cargando ? (
                        <Cargando />
                    ) : (
                        <div className="table-responsive">
                            <table className="table table-hover align-middle">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Ingreso</th>
                                    <th>Motivo</th>
                                    <th>Ubicación</th>
                                    <th>Prioridad</th>
                                    <th>Estado</th>
                                    <th></th>
                                </tr>
                                </thead>

                                <tbody>
                                {demandas.length === 0 && (
                                    <tr>
                                        <td colSpan="7" className="text-center text-secondary py-5">
                                            No hay demandas para mostrar.
                                        </td>
                                    </tr>
                                )}

                                {demandas.map((demanda) => (
                                    <tr key={demanda.id}>
                                        <td>{demanda.id}</td>
                                        <td>{formatearFecha(demanda.fecha)}</td>
                                        <td>{demanda.motivo}</td>
                                        <td>{textoUbicacion(demanda.ubicacion)}</td>
                                        <td><InsigniaEstado valor={demanda.prioridad} /></td>
                                        <td><InsigniaEstado valor={demanda.estado} /></td>
                                        <td className="text-end">
                                            <button
                                                className="btn btn-sm btn-outline-success"
                                                type="button"
                                                onClick={() => onVerDetalle(demanda.id)}
                                            >
                                                Ver
                                            </button>
                                        </td>
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

export default PantallaDemandas
