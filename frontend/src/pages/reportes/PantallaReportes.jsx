import { useCallback } from 'react'
import { api } from '../../api/cliente'
import { textoEstado } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import useDatos from '../../hooks/useDatos'

function TablaConteo({ titulo, conteo, total }) {
    const filas = Object.entries(conteo ?? {})

    return (
        <div className="card h-100">
            <div className="card-body">
                <h3 className="h5 mb-3">{titulo}</h3>

                <table className="table table-sm mb-0">
                    <tbody>
                    {filas.length === 0 && (
                        <tr>
                            <td className="text-secondary">Sin datos.</td>
                        </tr>
                    )}
                    {filas.map(([clave, cantidad]) => (
                        <tr key={clave}>
                            <td>{textoEstado(clave)}</td>
                            <td className="text-end">{cantidad}</td>
                        </tr>
                    ))}
                    </tbody>
                    {total !== undefined && (
                        <tfoot>
                        <tr>
                            <th>Total</th>
                            <th className="text-end">{total}</th>
                        </tr>
                        </tfoot>
                    )}
                </table>
            </div>
        </div>
    )
}

function PantallaReportes() {
    const cargar = useCallback(() => api.get('/api/reportes'), [])
    const { datos, error, cargando } = useDatos(cargar)

    return (
        <div>
            <div className="mb-4">
                <h2 className="h3 mb-1">Reportes</h2>
                <p className="text-secondary mb-0">
                    Información básica para el seguimiento de la gestión del arbolado.
                </p>
            </div>

            <Alerta>{error}</Alerta>
            {cargando && <Cargando />}

            {datos && (
                <div className="row g-3">
                    <div className="col-md-6 col-lg-4">
                        <TablaConteo
                            titulo="Demandas por estado"
                            conteo={datos.demandasPorEstado}
                            total={datos.totalDemandas}
                        />
                    </div>
                    <div className="col-md-6 col-lg-4">
                        <TablaConteo
                            titulo="Demandas por origen"
                            conteo={datos.demandasPorOrigen}
                        />
                    </div>
                    <div className="col-md-6 col-lg-4">
                        <TablaConteo
                            titulo="Intervenciones por estado"
                            conteo={datos.intervencionesPorEstado}
                            total={datos.totalIntervenciones}
                        />
                    </div>
                    <div className="col-md-6 col-lg-4">
                        <TablaConteo
                            titulo="Intervenciones por tipo"
                            conteo={datos.intervencionesPorTipo}
                        />
                    </div>
                    <div className="col-md-6 col-lg-4">
                        <TablaConteo
                            titulo="Ejemplares por estado"
                            conteo={datos.ejemplaresPorEstado}
                            total={datos.totalEjemplares}
                        />
                    </div>
                    <div className="col-md-6 col-lg-4">
                        <div className="card h-100">
                            <div className="card-body">
                                <h3 className="h5 mb-3">Inspecciones</h3>
                                <div className="display-6">{datos.totalInspecciones}</div>
                                <div className="text-secondary">realizadas en total</div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    )
}

export default PantallaReportes
