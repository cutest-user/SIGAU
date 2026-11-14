import { useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, nombreCompleto } from '../../api/formatos'
import Alerta from '../../components/Alerta'
import InsigniaEstado from '../../components/InsigniaEstado'
import useAccion from '../../hooks/useAccion'

function PanelVerificaciones({ intervenciones, verificaciones, esInspector, abierta, onCambio }) {
    const [intervencionId, setIntervencionId] = useState('')
    const [resultado, setResultado] = useState('APROBADA')
    const [evidencia, setEvidencia] = useState('')
    const [observaciones, setObservaciones] = useState('')

    const { ejecutar, error, enviando } = useAccion(() => {
        setIntervencionId('')
        setEvidencia('')
        setObservaciones('')
        onCambio()
    })

    const realizadas = intervenciones.filter((i) => i.estado === 'REALIZADA')

    const registrar = (evento) => {
        evento.preventDefault()
        ejecutar(() => api.post('/api/verificaciones', {
            intervencion: { id: Number(intervencionId) },
            resultado,
            evidencia: evidencia || null,
            observaciones: observaciones || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Verificaciones</h3>

                <div className="table-responsive">
                    <table className="table align-middle">
                        <thead>
                        <tr>
                            <th>Intervención</th>
                            <th>Fecha</th>
                            <th>Resultado</th>
                            <th>Responsable</th>
                            <th>Observaciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        {verificaciones.length === 0 && (
                            <tr>
                                <td colSpan="5" className="text-secondary text-center py-3">
                                    Todavía no se registraron verificaciones.
                                </td>
                            </tr>
                        )}
                        {verificaciones.map((v) => (
                            <tr key={v.id}>
                                <td>#{v.intervencion.id}</td>
                                <td>{formatearFecha(v.fecha)}</td>
                                <td><InsigniaEstado valor={v.resultado} /></td>
                                <td>{nombreCompleto(v.usuario)}</td>
                                <td>{v.observaciones || '—'}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {esInspector && abierta && realizadas.length > 0 && (
                    <form onSubmit={registrar} className="row g-2 align-items-end">
                        <div className="col-md-3">
                            <label className="form-label">Intervención</label>
                            <select
                                className="form-select"
                                value={intervencionId}
                                onChange={(e) => setIntervencionId(e.target.value)}
                                required
                            >
                                <option value="" disabled>Seleccionar</option>
                                {realizadas.map((i) => (
                                    <option key={i.id} value={i.id}>
                                        #{i.id} · {i.tipoIntervencion.descripcion || i.tipoIntervencion.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">Resultado</label>
                            <select
                                className="form-select"
                                value={resultado}
                                onChange={(e) => setResultado(e.target.value)}
                            >
                                <option value="APROBADA">Aprobada</option>
                                <option value="OBSERVADA">Observada</option>
                            </select>
                        </div>

                        <div className="col-md-6">
                            <label className="form-label">Evidencia (enlace, opcional)</label>
                            <input
                                className="form-control"
                                value={evidencia}
                                maxLength={500}
                                onChange={(e) => setEvidencia(e.target.value)}
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
                                Registrar verificación
                            </button>
                        </div>
                    </form>
                )}

                <Alerta>{error}</Alerta>
            </div>
        </div>
    )
}

function PanelCertificacion({ demanda, certificacion, esInspector, onCambio }) {
    const [numero, setNumero] = useState('')
    const [estado, setEstado] = useState('PLANIFICADA')
    const [observaciones, setObservaciones] = useState('')
    const { ejecutar, error, enviando } = useAccion(onCambio)

    const certificar = (evento) => {
        evento.preventDefault()
        ejecutar(() => api.post('/api/certificaciones', {
            id: numero,
            demanda: { id: demanda.id },
            estado,
            observaciones: observaciones || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Certificación</h3>

                {certificacion ? (
                    <dl className="row mb-0">
                        <dt className="col-sm-3">Orden de trabajo</dt>
                        <dd className="col-sm-9">{certificacion.id}</dd>
                        <dt className="col-sm-3">Fecha</dt>
                        <dd className="col-sm-9">{formatearFecha(certificacion.fecha)}</dd>
                        <dt className="col-sm-3">Estado de la orden</dt>
                        <dd className="col-sm-9">{certificacion.estado}</dd>
                        <dt className="col-sm-3">Observaciones</dt>
                        <dd className="col-sm-9">{certificacion.observaciones || '—'}</dd>
                    </dl>
                ) : demanda.estado === 'EN_GESTION' && esInspector ? (
                    <form onSubmit={certificar} className="row g-2 align-items-end">
                        <div className="col-md-4">
                            <label className="form-label">Número de orden SAP</label>
                            <input
                                className="form-control"
                                value={numero}
                                maxLength={100}
                                onChange={(e) => setNumero(e.target.value)}
                                required
                            />
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">Estado de la orden</label>
                            <select
                                className="form-select"
                                value={estado}
                                onChange={(e) => setEstado(e.target.value)}
                            >
                                <option value="PLANIFICADA">Planificada</option>
                                <option value="EN_EJECUCION">En ejecución</option>
                            </select>
                        </div>

                        <div className="col-md-5">
                            <label className="form-label">Observaciones</label>
                            <input
                                className="form-control"
                                value={observaciones}
                                onChange={(e) => setObservaciones(e.target.value)}
                            />
                        </div>

                        <div className="col-12 text-secondary small">
                            Para certificar, todas las intervenciones no canceladas deben estar
                            realizadas y tener una verificación aprobada.
                        </div>

                        <div className="col-12">
                            <button className="btn btn-success" disabled={enviando}>
                                Certificar demanda
                            </button>
                        </div>
                    </form>
                ) : (
                    <p className="text-secondary mb-0">
                        {demanda.estado === 'CANCELADA'
                            ? 'La demanda fue cancelada, no genera certificación.'
                            : 'La demanda todavía no fue certificada.'}
                    </p>
                )}

                <Alerta>{error}</Alerta>
            </div>
        </div>
    )
}

function PanelDerivaciones({ demanda, derivaciones, onCambio }) {
    const [sistema, setSistema] = useState('')
    const [referencia, setReferencia] = useState('')
    const [observaciones, setObservaciones] = useState('')

    const { ejecutar, error, enviando } = useAccion(() => {
        setSistema('')
        setReferencia('')
        setObservaciones('')
        onCambio()
    })

    const derivar = (evento) => {
        evento.preventDefault()
        ejecutar(() => api.post('/api/derivaciones', {
            demanda: { id: demanda.id },
            sistemaDestino: sistema,
            referenciaExterna: referencia || null,
            observaciones: observaciones || null
        }))
    }

    return (
        <div className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Derivaciones</h3>

                <div className="table-responsive">
                    <table className="table align-middle">
                        <thead>
                        <tr>
                            <th>Sistema</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Referencia</th>
                            <th>Observaciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        {derivaciones.length === 0 && (
                            <tr>
                                <td colSpan="5" className="text-secondary text-center py-3">
                                    No hay derivaciones registradas.
                                </td>
                            </tr>
                        )}
                        {derivaciones.map((d) => (
                            <tr key={d.id}>
                                <td>{d.sistemaDestino}</td>
                                <td>{formatearFecha(d.fecha)}</td>
                                <td><InsigniaEstado valor={d.estado} /></td>
                                <td>{d.referenciaExterna || '—'}</td>
                                <td>{d.observaciones || '—'}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <form onSubmit={derivar} className="row g-2 align-items-end">
                    <div className="col-md-3">
                        <label className="form-label">Sistema de destino</label>
                        <select
                            className="form-select"
                            value={sistema}
                            onChange={(e) => setSistema(e.target.value)}
                            required
                        >
                            <option value="" disabled>Seleccionar</option>
                            <option value="147">147</option>
                            <option value="SAP">SAP</option>
                            <option value="ARBOPEDIA">Arbopedia</option>
                        </select>
                    </div>

                    <div className="col-md-3">
                        <label className="form-label">Referencia externa</label>
                        <input
                            className="form-control"
                            value={referencia}
                            maxLength={100}
                            onChange={(e) => setReferencia(e.target.value)}
                        />
                    </div>

                    <div className="col-md-4">
                        <label className="form-label">Observaciones</label>
                        <input
                            className="form-control"
                            value={observaciones}
                            onChange={(e) => setObservaciones(e.target.value)}
                        />
                    </div>

                    <div className="col-md-2">
                        <button className="btn btn-outline-success w-100" disabled={enviando}>
                            Derivar
                        </button>
                    </div>
                </form>

                <Alerta>{error}</Alerta>
            </div>
        </div>
    )
}

export { PanelVerificaciones, PanelCertificacion, PanelDerivaciones }
