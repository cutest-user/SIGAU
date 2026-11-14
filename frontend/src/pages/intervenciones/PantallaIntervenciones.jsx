import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, textoUbicacion } from '../../api/formatos'
import useAuth from '../../auth/useAuth'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import InsigniaEstado from '../../components/InsigniaEstado'
import useDatos from '../../hooks/useDatos'
import AccionesIntervencion from './AccionesIntervencion'

const ESTADOS = ['PENDIENTE', 'PLANIFICADA', 'EN_EJECUCION', 'REALIZADA', 'CANCELADA']

function PantallaIntervenciones({ onVerDemanda }) {
    const { esInspector } = useAuth()
    const cargar = useCallback(() => api.get('/api/intervenciones'), [])
    const { datos, error, cargando, recargar } = useDatos(cargar)
    const [filtro, setFiltro] = useState('')

    const intervenciones = (datos ?? [])
        .filter((i) => !filtro || i.estado === filtro)
        .sort((a, b) => b.id - a.id)

    return (
        <div>
            <div className="mb-4">
                <h2 className="h3 mb-1">Intervenciones</h2>
                <p className="text-secondary mb-0">
                    Tareas sobre los ejemplares. Las nuevas se agregan desde el detalle de cada demanda.
                </p>
            </div>

            <Alerta>{error}</Alerta>

            <div className="card">
                <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                        <h3 className="h5 mb-0">Listado</h3>
                        <select
                            className="form-select w-auto"
                            value={filtro}
                            onChange={(e) => setFiltro(e.target.value)}
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
                                    <th>#</th>
                                    <th>Demanda</th>
                                    <th>Ejemplar</th>
                                    <th>Tarea</th>
                                    <th>Estado</th>
                                    <th>Prevista</th>
                                    <th>Ejecutada</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {intervenciones.length === 0 && (
                                    <tr>
                                        <td colSpan="8" className="text-center text-secondary py-4">
                                            No hay intervenciones para mostrar.
                                        </td>
                                    </tr>
                                )}
                                {intervenciones.map((i) => (
                                    <tr key={i.id}>
                                        <td>{i.id}</td>
                                        <td>
                                            <button
                                                className="btn btn-link p-0"
                                                onClick={() => onVerDemanda(i.demanda.id)}
                                            >
                                                #{i.demanda.id}
                                            </button>
                                        </td>
                                        <td>
                                            #{i.ejemplar.id}
                                            <div className="text-secondary small">
                                                {textoUbicacion(i.ejemplar.ubicacion)}
                                            </div>
                                        </td>
                                        <td>{i.tipoIntervencion.descripcion || i.tipoIntervencion.nombre}</td>
                                        <td><InsigniaEstado valor={i.estado} /></td>
                                        <td>{formatearFecha(i.fechaPlanificada)}</td>
                                        <td>{formatearFecha(i.fechaEjecucion)}</td>
                                        <td>
                                            {esInspector && (
                                                <AccionesIntervencion intervencion={i} onCambio={recargar} />
                                            )}
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

export default PantallaIntervenciones
