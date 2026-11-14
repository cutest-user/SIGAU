import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, nombreCompleto, textoUbicacion } from '../../api/formatos'
import useAuth from '../../auth/useAuth'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import useAccion from '../../hooks/useAccion'
import useDatos from '../../hooks/useDatos'

function Evidencias({ inspeccion, esInspector, onCerrar }) {
    const cargar = useCallback(
        () => api.get(`/api/evidencias-inspeccion/inspeccion/${inspeccion.id}`),
        [inspeccion.id]
    )
    const { datos, error, cargando, recargar } = useDatos(cargar)
    const [url, setUrl] = useState('')
    const [descripcion, setDescripcion] = useState('')

    const accion = useAccion(() => {
        setUrl('')
        setDescripcion('')
        recargar()
    })

    const agregar = (evento) => {
        evento.preventDefault()
        accion.ejecutar(() => api.post('/api/evidencias-inspeccion', {
            inspeccionId: inspeccion.id,
            url,
            descripcion: descripcion || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="h5 mb-0">Evidencias de la inspección #{inspeccion.id}</h3>
                    <button className="btn btn-sm btn-outline-secondary" onClick={onCerrar}>
                        Cerrar
                    </button>
                </div>

                <Alerta>{error}</Alerta>
                {cargando && <Cargando />}

                {datos && datos.length === 0 && (
                    <p className="text-secondary">No hay evidencias cargadas.</p>
                )}

                {datos && datos.length > 0 && (
                    <ul>
                        {datos.map((evidencia) => (
                            <li key={evidencia.id}>
                                <a href={evidencia.url} target="_blank" rel="noreferrer">
                                    {evidencia.descripcion || evidencia.url}
                                </a>{' '}
                                <span className="text-secondary small">
                                    {formatearFecha(evidencia.fecha)}
                                </span>
                            </li>
                        ))}
                    </ul>
                )}

                {esInspector && (
                    <form onSubmit={agregar} className="row g-2 align-items-end">
                        <div className="col-md-6">
                            <label className="form-label">Enlace a la fotografía o archivo</label>
                            <input
                                className="form-control"
                                value={url}
                                maxLength={500}
                                onChange={(e) => setUrl(e.target.value)}
                                required
                            />
                        </div>
                        <div className="col-md-4">
                            <label className="form-label">Descripción</label>
                            <input
                                className="form-control"
                                value={descripcion}
                                maxLength={255}
                                onChange={(e) => setDescripcion(e.target.value)}
                            />
                        </div>
                        <div className="col-md-2">
                            <button className="btn btn-outline-success w-100" disabled={accion.enviando}>
                                Agregar
                            </button>
                        </div>
                    </form>
                )}

                <Alerta>{accion.error}</Alerta>
            </div>
        </div>
    )
}

function FormularioInspeccion({ ejemplares, onGuardada, onCancelar }) {
    const [ejemplarId, setEjemplarId] = useState('')
    const [especie, setEspecie] = useState('')
    const [altura, setAltura] = useState('')
    const [dap, setDap] = useState('')
    const [diagnostico, setDiagnostico] = useState('')
    const [observaciones, setObservaciones] = useState('')

    const accion = useAccion(onGuardada)

    const enviar = (evento) => {
        evento.preventDefault()
        accion.ejecutar(() => api.post('/api/inspecciones', {
            ejemplar: { id: Number(ejemplarId) },
            especieIdentificada: especie || null,
            altura: altura === '' ? null : Number(altura),
            dap: dap === '' ? null : Number(dap),
            diagnostico: diagnostico || null,
            observaciones: observaciones || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Nueva inspección</h3>

                <form onSubmit={enviar}>
                    <div className="row g-3">
                        <div className="col-md-6">
                            <label className="form-label">Ejemplar</label>
                            <select
                                className="form-select"
                                value={ejemplarId}
                                onChange={(e) => setEjemplarId(e.target.value)}
                                required
                            >
                                <option value="" disabled>Seleccionar ejemplar</option>
                                {ejemplares.map((e) => (
                                    <option key={e.id} value={e.id}>
                                        #{e.id} · {e.especie || 'Sin identificar'} · {textoUbicacion(e.ubicacion)}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-6">
                            <label className="form-label">Especie identificada</label>
                            <input
                                className="form-control"
                                value={especie}
                                maxLength={100}
                                onChange={(e) => setEspecie(e.target.value)}
                            />
                        </div>

                        <div className="col-6 col-md-3">
                            <label className="form-label">Altura (m)</label>
                            <input
                                type="number"
                                min="0"
                                step="0.01"
                                className="form-control"
                                value={altura}
                                onChange={(e) => setAltura(e.target.value)}
                            />
                        </div>

                        <div className="col-6 col-md-3">
                            <label className="form-label">DAP (cm)</label>
                            <input
                                type="number"
                                min="0"
                                step="0.01"
                                className="form-control"
                                value={dap}
                                onChange={(e) => setDap(e.target.value)}
                            />
                        </div>

                        <div className="col-md-6">
                            <label className="form-label">Diagnóstico</label>
                            <input
                                className="form-control"
                                value={diagnostico}
                                onChange={(e) => setDiagnostico(e.target.value)}
                            />
                        </div>

                        <div className="col-12">
                            <label className="form-label">Observaciones</label>
                            <textarea
                                className="form-control"
                                rows="2"
                                value={observaciones}
                                onChange={(e) => setObservaciones(e.target.value)}
                            />
                        </div>
                    </div>

                    <Alerta>{accion.error}</Alerta>

                    <div className="d-flex gap-2 mt-3">
                        <button className="btn btn-success" disabled={accion.enviando}>
                            {accion.enviando ? 'Guardando...' : 'Guardar inspección'}
                        </button>
                        <button className="btn btn-outline-secondary" type="button" onClick={onCancelar}>
                            Cancelar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}

function PantallaInspecciones() {
    const { esInspector } = useAuth()
    const cargar = useCallback(async () => {
        const [inspecciones, ejemplares] = await Promise.all([
            api.get('/api/inspecciones'),
            api.get('/api/ejemplares')
        ])
        return { inspecciones, ejemplares }
    }, [])
    const { datos, error, cargando, recargar } = useDatos(cargar)
    const [mostrandoForm, setMostrandoForm] = useState(false)
    const [seleccionada, setSeleccionada] = useState(null)

    const inspecciones = [...(datos?.inspecciones ?? [])].sort((a, b) => b.id - a.id)

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                <div>
                    <h2 className="h3 mb-1">Inspecciones</h2>
                    <p className="text-secondary mb-0">
                        Evaluaciones técnicas realizadas sobre los ejemplares.
                    </p>
                </div>

                {esInspector && !mostrandoForm && (
                    <button className="btn btn-success" onClick={() => setMostrandoForm(true)}>
                        + Nueva inspección
                    </button>
                )}
            </div>

            <Alerta>{error}</Alerta>

            {mostrandoForm && datos && (
                <FormularioInspeccion
                    ejemplares={datos.ejemplares}
                    onCancelar={() => setMostrandoForm(false)}
                    onGuardada={() => {
                        setMostrandoForm(false)
                        recargar()
                    }}
                />
            )}

            {seleccionada && (
                <Evidencias
                    inspeccion={seleccionada}
                    esInspector={esInspector}
                    onCerrar={() => setSeleccionada(null)}
                />
            )}

            <div className="card">
                <div className="card-body">
                    {cargando ? (
                        <Cargando />
                    ) : (
                        <div className="table-responsive">
                            <table className="table table-hover align-middle">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Fecha</th>
                                    <th>Ejemplar</th>
                                    <th>Inspector</th>
                                    <th>Diagnóstico</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {inspecciones.length === 0 && (
                                    <tr>
                                        <td colSpan="6" className="text-center text-secondary py-4">
                                            No hay inspecciones registradas.
                                        </td>
                                    </tr>
                                )}
                                {inspecciones.map((i) => (
                                    <tr key={i.id}>
                                        <td>{i.id}</td>
                                        <td>{formatearFecha(i.fecha)}</td>
                                        <td>
                                            #{i.ejemplar.id} · {i.especieIdentificada || i.ejemplar.especie || 'Sin identificar'}
                                        </td>
                                        <td>{nombreCompleto(i.inspector)}</td>
                                        <td>{i.diagnostico || '—'}</td>
                                        <td className="text-end">
                                            <button
                                                className="btn btn-sm btn-outline-success"
                                                onClick={() => setSeleccionada(i)}
                                            >
                                                Evidencias
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

export default PantallaInspecciones
