import { useCallback, useState } from 'react'
import { api, mensajeDeError } from '../../api/cliente'
import { formatearFecha, textoUbicacion } from '../../api/formatos'
import { guardarUbicacion, UBICACION_VACIA } from '../../api/ubicaciones'
import Alerta from '../../components/Alerta'
import CamposUbicacion from '../../components/CamposUbicacion'
import InsigniaEstado from '../../components/InsigniaEstado'
import useDatos from '../../hooks/useDatos'

function FormularioDemanda({ onCancelar, onGuardada }) {
    const cargarOrigenes = useCallback(() => api.get('/api/origenes-demanda'), [])
    const cargarTipos = useCallback(() => api.get('/api/tipos-intervencion'), [])
    const origenes = useDatos(cargarOrigenes)
    const tipos = useDatos(cargarTipos)

    const [origenId, setOrigenId] = useState('')
    const [motivo, setMotivo] = useState('')
    const [descripcion, setDescripcion] = useState('')
    const [prioridad, setPrioridad] = useState('MEDIA')
    const [observaciones, setObservaciones] = useState('')
    const [ubicacion, setUbicacion] = useState(UBICACION_VACIA)
    const [ubicacionGuardada, setUbicacionGuardada] = useState(null)
    const [similares, setSimilares] = useState([])
    const [error, setError] = useState('')
    const [enviando, setEnviando] = useState(false)

    const cambiarUbicacion = (valor) => {
        setUbicacion(valor)
        setUbicacionGuardada(null)
        setSimilares([])
    }

    const guardar = async (confirmado) => {
        setError('')
        setEnviando(true)

        try {
            const lugar = ubicacionGuardada ?? await guardarUbicacion(ubicacion)
            setUbicacionGuardada(lugar)

            if (!confirmado) {
                const abiertas = await api.get(
                    `/api/demandas/posibles-duplicadas?ubicacionId=${lugar.id}`
                )

                if (abiertas.length > 0) {
                    setSimilares(abiertas)
                    setEnviando(false)
                    return
                }
            }

            const demanda = await api.post('/api/demandas', {
                origenId: Number(origenId),
                ubicacionId: lugar.id,
                motivo,
                descripcion: descripcion || null,
                prioridad,
                observaciones: observaciones || null
            })

            onGuardada(demanda.id)
        } catch (fallo) {
            setError(mensajeDeError(fallo))
            setEnviando(false)
        }
    }

    const enviar = (evento) => {
        evento.preventDefault()
        guardar(false)
    }

    return (
        <div>
            <div className="mb-4">
                <h2 className="h3 mb-1">Nueva demanda</h2>
                <p className="text-secondary mb-0">
                    Registro de una nueva demanda de arbolado urbano.
                </p>
            </div>

            <div className="card">
                <div className="card-body">
                    <Alerta>{error || origenes.error || tipos.error}</Alerta>

                    {similares.length > 0 && (
                        <div className="alert alert-warning">
                            <p className="mb-2">
                                Ya hay demandas abiertas en esta ubicación
                                ({textoUbicacion(ubicacionGuardada)}). Revisá que no se trate
                                del mismo pedido antes de registrar una nueva.
                            </p>

                            <ul className="mb-3">
                                {similares.map((demanda) => (
                                    <li key={demanda.id}>
                                        #{demanda.id} · {demanda.motivo} ·{' '}
                                        {formatearFecha(demanda.fecha)}{' '}
                                        <InsigniaEstado valor={demanda.estado} />
                                    </li>
                                ))}
                            </ul>

                            <button
                                className="btn btn-warning btn-sm me-2"
                                type="button"
                                disabled={enviando}
                                onClick={() => guardar(true)}
                            >
                                Registrar de todos modos
                            </button>
                            <button
                                className="btn btn-outline-secondary btn-sm"
                                type="button"
                                onClick={onCancelar}
                            >
                                Volver
                            </button>
                        </div>
                    )}

                    <form onSubmit={enviar}>
                        <div className="row g-3">
                            <div className="col-md-6">
                                <label className="form-label">Origen de la demanda</label>
                                <select
                                    className="form-select"
                                    value={origenId}
                                    onChange={(e) => setOrigenId(e.target.value)}
                                    required
                                >
                                    <option value="" disabled>Seleccionar origen</option>
                                    {(origenes.datos ?? []).map((origen) => (
                                        <option key={origen.id} value={origen.id}>
                                            {origen.descripcion || origen.nombre}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="col-md-6">
                                <label className="form-label">Motivo</label>
                                <select
                                    className="form-select"
                                    value={motivo}
                                    onChange={(e) => setMotivo(e.target.value)}
                                    required
                                >
                                    <option value="" disabled>Seleccionar motivo</option>
                                    {(tipos.datos ?? []).map((tipo) => (
                                        <option key={tipo.id} value={tipo.nombre}>
                                            {tipo.descripcion || tipo.nombre}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="col-12">
                                <label className="form-label">Descripción</label>
                                <textarea
                                    className="form-control"
                                    rows="3"
                                    value={descripcion}
                                    onChange={(e) => setDescripcion(e.target.value)}
                                    placeholder="Descripción de la demanda"
                                />
                            </div>

                            <div className="col-md-6">
                                <label className="form-label">Prioridad</label>
                                <select
                                    className="form-select"
                                    value={prioridad}
                                    onChange={(e) => setPrioridad(e.target.value)}
                                >
                                    <option value="BAJA">Baja</option>
                                    <option value="MEDIA">Media</option>
                                    <option value="ALTA">Alta</option>
                                </select>
                            </div>

                            <div className="col-12">
                                <h3 className="h6 mt-2 mb-0">Ubicación</h3>
                            </div>

                            <CamposUbicacion valor={ubicacion} onChange={cambiarUbicacion} />

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

                        <div className="d-flex gap-2 mt-4">
                            <button className="btn btn-success" disabled={enviando}>
                                {enviando ? 'Guardando...' : 'Guardar demanda'}
                            </button>
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={onCancelar}
                            >
                                Cancelar
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default FormularioDemanda
