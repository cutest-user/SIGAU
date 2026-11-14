import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import InsigniaEstado from '../../components/InsigniaEstado'
import AccionesIntervencion from '../intervenciones/AccionesIntervencion'
import useAccion from '../../hooks/useAccion'
import useDatos from '../../hooks/useDatos'

function PanelIntervenciones({ demanda, intervenciones, asociaciones, abierta, esInspector, onCambio }) {
    const cargarTipos = useCallback(() => api.get('/api/tipos-intervencion'), [])
    const tipos = useDatos(cargarTipos)
    const [ejemplarId, setEjemplarId] = useState('')
    const [tipoId, setTipoId] = useState('')
    const [previaId, setPreviaId] = useState('')
    const [fechaPlanificada, setFechaPlanificada] = useState('')
    const [observaciones, setObservaciones] = useState('')

    const { ejecutar, error, enviando } = useAccion(() => {
        setTipoId('')
        setPreviaId('')
        setFechaPlanificada('')
        setObservaciones('')
        onCambio()
    })

    const posiblesPrevias = intervenciones.filter(
        (i) => String(i.ejemplar.id) === ejemplarId && i.estado !== 'CANCELADA'
    )

    const crear = (evento) => {
        evento.preventDefault()
        ejecutar(() => api.post('/api/intervenciones', {
            demanda: { id: demanda.id },
            ejemplar: { id: Number(ejemplarId) },
            tipoIntervencion: { id: Number(tipoId) },
            intervencionPrevia: previaId ? { id: Number(previaId) } : null,
            fechaPlanificada: fechaPlanificada || null,
            observaciones: observaciones || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Intervenciones</h3>

                <div className="table-responsive">
                    <table className="table align-middle">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Ejemplar</th>
                            <th>Tarea</th>
                            <th>Estado</th>
                            <th>Prevista</th>
                            <th>Ejecutada</th>
                            <th>Depende de</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {intervenciones.length === 0 && (
                            <tr>
                                <td colSpan="8" className="text-secondary text-center py-3">
                                    No hay intervenciones registradas.
                                </td>
                            </tr>
                        )}
                        {intervenciones.map((i) => (
                            <tr key={i.id}>
                                <td>{i.id}</td>
                                <td>#{i.ejemplar.id}</td>
                                <td>{i.tipoIntervencion.descripcion || i.tipoIntervencion.nombre}</td>
                                <td><InsigniaEstado valor={i.estado} /></td>
                                <td>{formatearFecha(i.fechaPlanificada)}</td>
                                <td>{formatearFecha(i.fechaEjecucion)}</td>
                                <td>{i.intervencionPrevia ? `#${i.intervencionPrevia.id}` : '—'}</td>
                                <td>
                                    {esInspector && abierta && (
                                        <AccionesIntervencion intervencion={i} onCambio={onCambio} />
                                    )}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {esInspector && abierta && asociaciones.length > 0 && (
                    <form onSubmit={crear} className="row g-2 align-items-end">
                        <div className="col-md-3">
                            <label className="form-label">Ejemplar</label>
                            <select
                                className="form-select"
                                value={ejemplarId}
                                onChange={(e) => {
                                    setEjemplarId(e.target.value)
                                    setPreviaId('')
                                }}
                                required
                            >
                                <option value="" disabled>Seleccionar</option>
                                {asociaciones.map((a) => (
                                    <option key={a.ejemplar.id} value={a.ejemplar.id}>
                                        #{a.ejemplar.id} · {a.ejemplar.especie || 'Sin identificar'}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">Tarea</label>
                            <select
                                className="form-select"
                                value={tipoId}
                                onChange={(e) => setTipoId(e.target.value)}
                                required
                            >
                                <option value="" disabled>Seleccionar</option>
                                {(tipos.datos ?? []).map((t) => (
                                    <option key={t.id} value={t.id}>
                                        {t.descripcion || t.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">Depende de</label>
                            <select
                                className="form-select"
                                value={previaId}
                                onChange={(e) => setPreviaId(e.target.value)}
                                disabled={posiblesPrevias.length === 0}
                            >
                                <option value="">Ninguna</option>
                                {posiblesPrevias.map((i) => (
                                    <option key={i.id} value={i.id}>
                                        #{i.id} · {i.tipoIntervencion.descripcion || i.tipoIntervencion.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">Fecha prevista</label>
                            <input
                                type="date"
                                className="form-control"
                                value={fechaPlanificada}
                                onChange={(e) => setFechaPlanificada(e.target.value)}
                            />
                        </div>

                        <div className="col-md-9">
                            <label className="form-label">Observaciones</label>
                            <input
                                className="form-control"
                                value={observaciones}
                                onChange={(e) => setObservaciones(e.target.value)}
                            />
                        </div>

                        <div className="col-md-3">
                            <button className="btn btn-outline-success w-100" disabled={enviando}>
                                Agregar intervención
                            </button>
                        </div>
                    </form>
                )}

                <Alerta>{error}</Alerta>
            </div>
        </div>
    )
}

export default PanelIntervenciones
