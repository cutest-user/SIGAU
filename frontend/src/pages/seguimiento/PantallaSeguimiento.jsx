import { useCallback } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, textoEstado, textoUbicacion } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import InsigniaEstado from '../../components/InsigniaEstado'
import useDatos from '../../hooks/useDatos'

function PantallaSeguimiento({ onVerDemanda }) {
    const cargar = useCallback(async () => {
        const [pendientes, reporte] = await Promise.all([
            api.get('/api/seguimiento/intervenciones/pendientes'),
            api.get('/api/reportes')
        ])
        return { pendientes, reporte }
    }, [])
    const { datos, error, cargando } = useDatos(cargar)

    if (cargando) return <Cargando />

    const porEstado = Object.entries(datos?.reporte.demandasPorEstado ?? {})

    return (
        <div>
            <div className="mb-4">
                <h2 className="h3 mb-1">Seguimiento</h2>
                <p className="text-secondary mb-0">
                    Estado de las demandas y tareas que todavía no se completaron.
                </p>
            </div>

            <Alerta>{error}</Alerta>

            {datos && (
                <>
                    <div className="row g-3 mb-4">
                        {porEstado.map(([estado, cantidad]) => (
                            <div className="col-6 col-md-3" key={estado}>
                                <div className="card text-center h-100">
                                    <div className="card-body">
                                        <div className="display-6">{cantidad}</div>
                                        <div className="text-secondary">{textoEstado(estado)}</div>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div className="card">
                        <div className="card-body">
                            <h3 className="h5 mb-3">Intervenciones pendientes</h3>

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
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {datos.pendientes.length === 0 && (
                                        <tr>
                                            <td colSpan="6" className="text-center text-secondary py-4">
                                                No hay intervenciones pendientes.
                                            </td>
                                        </tr>
                                    )}
                                    {datos.pendientes.map((i) => (
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
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default PantallaSeguimiento
