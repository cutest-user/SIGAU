import { useCallback } from 'react'
import { api } from '../../api/cliente'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import useDatos from '../../hooks/useDatos'

const ACCESOS = [
    { nombre: 'Demandas', texto: 'Registro y seguimiento de demandas.' },
    { nombre: 'Ejemplares', texto: 'Árboles registrados y su historial.' },
    { nombre: 'Seguimiento', texto: 'Tareas pendientes y trazabilidad.' }
]

function Inicio({ onIr }) {
    const cargar = useCallback(() => api.get('/api/reportes'), [])
    const { datos, error, cargando } = useDatos(cargar)

    const resumen = datos && [
        { titulo: 'Demandas', valor: datos.totalDemandas },
        { titulo: 'Ejemplares', valor: datos.totalEjemplares },
        { titulo: 'Inspecciones', valor: datos.totalInspecciones },
        { titulo: 'Intervenciones', valor: datos.totalIntervenciones }
    ]

    return (
        <div>
            <h2 className="h3 mb-4">Panel principal</h2>

            <Alerta>{error}</Alerta>
            {cargando && <Cargando />}

            {resumen && (
                <div className="row g-3 mb-4">
                    {resumen.map((item) => (
                        <div className="col-6 col-md-3" key={item.titulo}>
                            <div className="card text-center h-100">
                                <div className="card-body">
                                    <div className="display-6">{item.valor}</div>
                                    <div className="text-secondary">{item.titulo}</div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}

            <div className="row g-3">
                {ACCESOS.map((acceso) => (
                    <div className="col-md-4" key={acceso.nombre}>
                        <div className="card h-100">
                            <div className="card-body">
                                <h3 className="h5 card-title">{acceso.nombre}</h3>
                                <p className="card-text">{acceso.texto}</p>
                                <button
                                    className="btn btn-outline-success"
                                    onClick={() => onIr(acceso.nombre)}
                                >
                                    Ir a {acceso.nombre.toLowerCase()}
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Inicio
