import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, textoUbicacion } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import InsigniaEstado from '../../components/InsigniaEstado'
import useAccion from '../../hooks/useAccion'
import useDatos from '../../hooks/useDatos'

function PanelEjemplares({ demanda, asociaciones, abierta, onCambio }) {
    const cargar = useCallback(() => api.get('/api/ejemplares'), [])
    const { datos } = useDatos(cargar)
    const [ejemplarId, setEjemplarId] = useState('')
    const [observacion, setObservacion] = useState('')

    const { ejecutar, error, enviando } = useAccion(() => {
        setEjemplarId('')
        setObservacion('')
        onCambio()
    })

    const asociados = new Set(asociaciones.map((a) => a.ejemplar.id))
    const mismaZona = (ejemplar) =>
        demanda.ubicacion && ejemplar.ubicacion?.id === demanda.ubicacion.id ? 1 : 0

    const disponibles = (datos ?? [])
        .filter((e) => !asociados.has(e.id) && e.estado === 'ACTIVO')
        .sort((a, b) => mismaZona(b) - mismaZona(a))

    const asociar = (evento) => {
        evento.preventDefault()
        ejecutar(() => api.post('/api/demandas-ejemplares', {
            demandaId: demanda.id,
            ejemplarId: Number(ejemplarId),
            observacion: observacion || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Ejemplares involucrados</h3>

                <div className="table-responsive">
                    <table className="table align-middle">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Especie</th>
                            <th>Ubicación</th>
                            <th>Estado</th>
                            <th>Asociado</th>
                        </tr>
                        </thead>
                        <tbody>
                        {asociaciones.length === 0 && (
                            <tr>
                                <td colSpan="5" className="text-secondary text-center py-3">
                                    No hay ejemplares asociados.
                                </td>
                            </tr>
                        )}
                        {asociaciones.map((a) => (
                            <tr key={a.ejemplar.id}>
                                <td>{a.ejemplar.id}</td>
                                <td>{a.ejemplar.especie || 'Sin identificar'}</td>
                                <td>{textoUbicacion(a.ejemplar.ubicacion)}</td>
                                <td><InsigniaEstado valor={a.ejemplar.estado} /></td>
                                <td>{formatearFecha(a.fechaAsociacion)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {abierta && (
                    <form onSubmit={asociar} className="row g-2 align-items-end">
                        <div className="col-md-6">
                            <label className="form-label">Asociar ejemplar</label>
                            <select
                                className="form-select"
                                value={ejemplarId}
                                onChange={(e) => setEjemplarId(e.target.value)}
                                required
                            >
                                <option value="" disabled>Seleccionar ejemplar</option>
                                {disponibles.map((e) => (
                                    <option key={e.id} value={e.id}>
                                        #{e.id} · {e.especie || 'Sin identificar'} ·{' '}
                                        {textoUbicacion(e.ubicacion)}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-4">
                            <label className="form-label">Observación</label>
                            <input
                                className="form-control"
                                value={observacion}
                                maxLength={255}
                                onChange={(e) => setObservacion(e.target.value)}
                            />
                        </div>

                        <div className="col-md-2">
                            <button className="btn btn-outline-success w-100" disabled={enviando}>
                                Asociar
                            </button>
                        </div>

                        <div className="col-12 text-secondary small">
                            Si el árbol no figura en la lista, primero hay que registrarlo en Ejemplares.
                        </div>
                    </form>
                )}

                <Alerta>{error}</Alerta>
            </div>
        </div>
    )
}

export default PanelEjemplares
