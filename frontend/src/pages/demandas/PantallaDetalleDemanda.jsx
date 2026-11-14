import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import { formatearFecha, nombreCompleto, textoUbicacion } from '../../api/formatos'
import useAuth from '../../auth/useAuth'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import InsigniaEstado from '../../components/InsigniaEstado'
import LineaHistorial from '../../components/LineaHistorial'
import useAccion from '../../hooks/useAccion'
import useDatos from '../../hooks/useDatos'
import PanelEjemplares from './PanelEjemplares'
import PanelIntervenciones from './PanelIntervenciones'
import { PanelCertificacion, PanelDerivaciones, PanelVerificaciones } from './PanelCierre'

function PantallaDetalleDemanda({ id, onVolver }) {
    const { esInspector } = useAuth()

    const cargar = useCallback(async () => {
        const [seguimiento, derivaciones] = await Promise.all([
            api.get(`/api/seguimiento/demanda/${id}`),
            api.get('/api/derivaciones')
        ])

        return {
            ...seguimiento,
            derivaciones: derivaciones.filter((d) => d.demanda?.id === id)
        }
    }, [id])

    const { datos, error, cargando, recargar } = useDatos(cargar)

    const [cancelando, setCancelando] = useState(false)
    const [motivoCancelacion, setMotivoCancelacion] = useState('')

    const accion = useAccion(() => {
        setCancelando(false)
        setMotivoCancelacion('')
        recargar()
    })

    if (cargando) return <Cargando />

    if (!datos) {
        return (
            <div>
                <Alerta>{error}</Alerta>
                <button className="btn btn-outline-secondary" onClick={onVolver}>
                    Volver al listado
                </button>
            </div>
        )
    }

    const { demanda } = datos
    const abierta = demanda.estado === 'INGRESADA' || demanda.estado === 'EN_GESTION'

    const cancelar = (evento) => {
        evento.preventDefault()
        accion.ejecutar(() => api.patch(
            `/api/demandas/${demanda.id}/cancelacion`,
            { observacion: motivoCancelacion }
        ))
    }

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                <div>
                    <h2 className="h3 mb-1">
                        Demanda #{demanda.id} <InsigniaEstado valor={demanda.estado} />
                    </h2>
                    <p className="text-secondary mb-0">
                        Información y seguimiento de la demanda.
                    </p>
                </div>

                <div className="d-flex gap-2">
                    {abierta && !cancelando && (
                        <button
                            type="button"
                            className="btn btn-outline-danger"
                            onClick={() => setCancelando(true)}
                        >
                            Cancelar demanda
                        </button>
                    )}
                    <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={onVolver}
                    >
                        Volver al listado
                    </button>
                </div>
            </div>

            <Alerta>{error}</Alerta>

            {cancelando && (
                <form onSubmit={cancelar} className="card border-danger mb-4">
                    <div className="card-body">
                        <label className="form-label">Motivo de la cancelación</label>
                        <textarea
                            className="form-control mb-2"
                            rows="2"
                            maxLength={500}
                            value={motivoCancelacion}
                            onChange={(e) => setMotivoCancelacion(e.target.value)}
                            required
                        />
                        <Alerta>{accion.error}</Alerta>
                        <button className="btn btn-danger me-2" disabled={accion.enviando}>
                            Confirmar cancelación
                        </button>
                        <button
                            type="button"
                            className="btn btn-outline-secondary"
                            onClick={() => setCancelando(false)}
                        >
                            Volver
                        </button>
                    </div>
                </form>
            )}

            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="h5 mb-3">Información general</h3>

                    <dl className="row mb-0">
                        <dt className="col-sm-3">Origen</dt>
                        <dd className="col-sm-9">{demanda.origen?.nombre}</dd>
                        <dt className="col-sm-3">Motivo</dt>
                        <dd className="col-sm-9">{demanda.motivo}</dd>
                        <dt className="col-sm-3">Descripción</dt>
                        <dd className="col-sm-9">{demanda.descripcion || '—'}</dd>
                        <dt className="col-sm-3">Ubicación</dt>
                        <dd className="col-sm-9">
                            {textoUbicacion(demanda.ubicacion)}
                            {demanda.ubicacion?.referencia && ` (${demanda.ubicacion.referencia})`}
                        </dd>
                        <dt className="col-sm-3">Prioridad</dt>
                        <dd className="col-sm-9"><InsigniaEstado valor={demanda.prioridad} /></dd>
                        <dt className="col-sm-3">Registrada por</dt>
                        <dd className="col-sm-9">{nombreCompleto(demanda.usuario)}</dd>
                        <dt className="col-sm-3">Ingreso</dt>
                        <dd className="col-sm-9">{formatearFecha(demanda.fecha)}</dd>
                        <dt className="col-sm-3">Cierre</dt>
                        <dd className="col-sm-9">{formatearFecha(demanda.fechaCierre)}</dd>
                        <dt className="col-sm-3">Observaciones</dt>
                        <dd className="col-sm-9">{demanda.observaciones || '—'}</dd>
                    </dl>
                </div>
            </div>

            <PanelEjemplares
                demanda={demanda}
                asociaciones={datos.ejemplares}
                abierta={abierta}
                onCambio={recargar}
            />

            <PanelIntervenciones
                demanda={demanda}
                intervenciones={datos.intervenciones}
                asociaciones={datos.ejemplares}
                abierta={abierta}
                esInspector={esInspector}
                onCambio={recargar}
            />

            <PanelVerificaciones
                intervenciones={datos.intervenciones}
                verificaciones={datos.verificaciones}
                esInspector={esInspector}
                abierta={abierta}
                onCambio={recargar}
            />

            <PanelCertificacion
                demanda={demanda}
                certificacion={datos.certificacion}
                esInspector={esInspector}
                onCambio={recargar}
            />

            <PanelDerivaciones
                demanda={demanda}
                derivaciones={datos.derivaciones}
                onCambio={recargar}
            />

            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="h5 mb-3">Historial</h3>
                    <LineaHistorial cambios={datos.cambios} />
                </div>
            </div>
        </div>
    )
}

export default PantallaDetalleDemanda
