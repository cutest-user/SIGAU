import { useCallback, useState } from 'react'
import { api, mensajeDeError } from '../../api/cliente'
import { formatearFecha, textoUbicacion } from '../../api/formatos'
import { guardarUbicacion, UBICACION_VACIA } from '../../api/ubicaciones'
import Alerta from '../../components/Alerta'
import CamposUbicacion from '../../components/CamposUbicacion'
import Cargando from '../../components/Cargando'
import InsigniaEstado from '../../components/InsigniaEstado'
import LineaHistorial from '../../components/LineaHistorial'
import useDatos from '../../hooks/useDatos'

function normalizar(texto) {
    return texto
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .toLowerCase()
}

function HistorialEjemplar({ ejemplar, onCerrar }) {
    const cargar = useCallback(
        () => api.get(`/api/seguimiento/ejemplar/${ejemplar.id}/historial`),
        [ejemplar.id]
    )
    const { datos, error, cargando } = useDatos(cargar)

    return (
        <div className="card mb-4">
            <div className="card-body">
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="h5 mb-0">
                        Historial del ejemplar #{ejemplar.id}
                    </h3>
                    <button className="btn btn-sm btn-outline-secondary" onClick={onCerrar}>
                        Cerrar
                    </button>
                </div>

                <Alerta>{error}</Alerta>
                {cargando && <Cargando />}

                {datos && (
                    <div className="row g-4">
                        <div className="col-md-6">
                            <h4 className="h6">Demandas</h4>
                            {datos.demandas.length === 0 && (
                                <p className="text-secondary">Sin demandas asociadas.</p>
                            )}
                            <ul>
                                {datos.demandas.map((d) => (
                                    <li key={d.demanda.id}>
                                        #{d.demanda.id} · {d.demanda.motivo}{' '}
                                        <InsigniaEstado valor={d.demanda.estado} />
                                    </li>
                                ))}
                            </ul>

                            <h4 className="h6">Inspecciones</h4>
                            {datos.inspecciones.length === 0 && (
                                <p className="text-secondary">Sin inspecciones.</p>
                            )}
                            <ul>
                                {datos.inspecciones.map((i) => (
                                    <li key={i.id}>
                                        {formatearFecha(i.fecha)} · {i.diagnostico || 'Sin diagnóstico'}
                                    </li>
                                ))}
                            </ul>

                            <h4 className="h6">Intervenciones</h4>
                            {datos.intervenciones.length === 0 && (
                                <p className="text-secondary">Sin intervenciones.</p>
                            )}
                            <ul>
                                {datos.intervenciones.map((i) => (
                                    <li key={i.id}>
                                        #{i.id} · {i.tipoIntervencion.descripcion || i.tipoIntervencion.nombre}{' '}
                                        <InsigniaEstado valor={i.estado} />
                                    </li>
                                ))}
                            </ul>
                        </div>

                        <div className="col-md-6">
                            <h4 className="h6">Cambios registrados</h4>
                            <LineaHistorial cambios={datos.cambios} />
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}

function FormularioEjemplar({ onGuardado, onCancelar }) {
    const [especie, setEspecie] = useState('')
    const [altura, setAltura] = useState('')
    const [dap, setDap] = useState('')
    const [observaciones, setObservaciones] = useState('')
    const [ubicacion, setUbicacion] = useState(UBICACION_VACIA)
    const [ubicacionGuardada, setUbicacionGuardada] = useState(null)
    const [existentes, setExistentes] = useState([])
    const [error, setError] = useState('')
    const [enviando, setEnviando] = useState(false)

    const cambiarUbicacion = (valor) => {
        setUbicacion(valor)
        setUbicacionGuardada(null)
        setExistentes([])
    }

    const guardar = async (confirmado) => {
        setError('')
        setEnviando(true)

        try {
            const lugar = ubicacionGuardada ?? await guardarUbicacion(ubicacion)
            setUbicacionGuardada(lugar)

            if (!confirmado) {
                const cercanos = await api.get(`/api/ejemplares?ubicacionId=${lugar.id}`)

                if (cercanos.length > 0) {
                    setExistentes(cercanos)
                    setEnviando(false)
                    return
                }
            }

            await api.post('/api/ejemplares', {
                especie: especie || null,
                altura: altura === '' ? null : Number(altura),
                dap: dap === '' ? null : Number(dap),
                ubicacion: { id: lugar.id },
                observaciones: observaciones || null
            })

            onGuardado()
        } catch (fallo) {
            setError(mensajeDeError(fallo))
            setEnviando(false)
        }
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Nuevo ejemplar</h3>

                <Alerta>{error}</Alerta>

                {existentes.length > 0 && (
                    <div className="alert alert-warning">
                        <p className="mb-2">
                            Ya hay ejemplares registrados en {textoUbicacion(ubicacionGuardada)}:
                        </p>
                        <ul>
                            {existentes.map((e) => (
                                <li key={e.id}>
                                    #{e.id} · {e.especie || 'Sin identificar'}{' '}
                                    <InsigniaEstado valor={e.estado} />
                                </li>
                            ))}
                        </ul>
                        <button
                            className="btn btn-warning btn-sm"
                            type="button"
                            disabled={enviando}
                            onClick={() => guardar(true)}
                        >
                            Es otro árbol, registrar igual
                        </button>
                    </div>
                )}

                <form onSubmit={(e) => { e.preventDefault(); guardar(false) }}>
                    <div className="row g-3">
                        <div className="col-md-4">
                            <label className="form-label">Especie</label>
                            <input
                                className="form-control"
                                value={especie}
                                maxLength={100}
                                onChange={(e) => setEspecie(e.target.value)}
                                placeholder="Si se conoce"
                            />
                        </div>

                        <div className="col-6 col-md-4">
                            <label className="form-label">Altura del árbol (m)</label>
                            <input
                                type="number"
                                min="0"
                                step="0.01"
                                className="form-control"
                                value={altura}
                                onChange={(e) => setAltura(e.target.value)}
                            />
                        </div>

                        <div className="col-6 col-md-4">
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

                        <CamposUbicacion valor={ubicacion} onChange={cambiarUbicacion} />

                        <div className="col-12">
                            <label className="form-label">Observaciones</label>
                            <input
                                className="form-control"
                                value={observaciones}
                                onChange={(e) => setObservaciones(e.target.value)}
                            />
                        </div>
                    </div>

                    <div className="d-flex gap-2 mt-3">
                        <button className="btn btn-success" disabled={enviando}>
                            {enviando ? 'Guardando...' : 'Guardar ejemplar'}
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

function PantallaEjemplares() {
    const cargar = useCallback(() => api.get('/api/ejemplares'), [])
    const { datos, error, cargando, recargar } = useDatos(cargar)
    const [mostrandoForm, setMostrandoForm] = useState(false)
    const [seleccionado, setSeleccionado] = useState(null)
    const [busqueda, setBusqueda] = useState('')

    const texto = normalizar(busqueda.trim())
    const ejemplares = (datos ?? [])
        .filter((e) => !texto
            || normalizar(e.especie ?? '').includes(texto)
            || normalizar(textoUbicacion(e.ubicacion)).includes(texto))
        .sort((a, b) => b.id - a.id)

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                <div>
                    <h2 className="h3 mb-1">Ejemplares</h2>
                    <p className="text-secondary mb-0">
                        Árboles registrados y su historial de intervenciones.
                    </p>
                </div>

                {!mostrandoForm && (
                    <button className="btn btn-success" onClick={() => setMostrandoForm(true)}>
                        + Nuevo ejemplar
                    </button>
                )}
            </div>

            {mostrandoForm && (
                <FormularioEjemplar
                    onCancelar={() => setMostrandoForm(false)}
                    onGuardado={() => {
                        setMostrandoForm(false)
                        recargar()
                    }}
                />
            )}

            {seleccionado && (
                <HistorialEjemplar
                    ejemplar={seleccionado}
                    onCerrar={() => setSeleccionado(null)}
                />
            )}

            <Alerta>{error}</Alerta>

            <div className="card">
                <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                        <h3 className="h5 mb-0">Listado de ejemplares</h3>
                        <input
                            className="form-control w-auto"
                            placeholder="Buscar por especie o calle"
                            value={busqueda}
                            onChange={(e) => setBusqueda(e.target.value)}
                        />
                    </div>

                    {cargando ? (
                        <Cargando />
                    ) : (
                        <div className="table-responsive">
                            <table className="table table-hover align-middle">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Especie</th>
                                    <th>Ubicación</th>
                                    <th>Altura</th>
                                    <th>DAP</th>
                                    <th>Estado</th>
                                    <th>Alta</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {ejemplares.length === 0 && (
                                    <tr>
                                        <td colSpan="8" className="text-center text-secondary py-4">
                                            No hay ejemplares para mostrar.
                                        </td>
                                    </tr>
                                )}
                                {ejemplares.map((e) => (
                                    <tr key={e.id}>
                                        <td>{e.id}</td>
                                        <td>{e.especie || 'Sin identificar'}</td>
                                        <td>{textoUbicacion(e.ubicacion)}</td>
                                        <td>{e.altura ?? '—'}</td>
                                        <td>{e.dap ?? '—'}</td>
                                        <td><InsigniaEstado valor={e.estado} /></td>
                                        <td>{formatearFecha(e.fechaAlta)}</td>
                                        <td className="text-end">
                                            <button
                                                className="btn btn-sm btn-outline-success"
                                                onClick={() => setSeleccionado(e)}
                                            >
                                                Historial
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

export default PantallaEjemplares
