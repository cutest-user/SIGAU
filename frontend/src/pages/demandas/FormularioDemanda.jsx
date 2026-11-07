function FormularioDemanda({ onCancelar }) {
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
                    <form>
                        <div className="row g-3">

                            <div className="col-md-6">
                                <label className="form-label">Origen de la demanda</label>
                                <select className="form-select" defaultValue="">
                                    <option value="" disabled>Seleccionar origen</option>
                                    <option value="RECLAMO_CIUDADANO">Reclamo ciudadano / 147</option>
                                    <option value="EMERGENCIA">Emergencia</option>
                                    <option value="GERENCIA">Gerencia</option>
                                    <option value="OFICIO">Oficio</option>
                                    <option value="PLANIFICACION">Planificación</option>
                                </select>
                            </div>

                            <div className="col-md-6">
                                <label className="form-label">Motivo</label>
                                <select className="form-select" defaultValue="">
                                    <option value="" disabled>Seleccionar motivo</option>
                                    <option value="PODA">Poda</option>
                                    <option value="EXTRACCION">Extracción</option>
                                    <option value="PLANTACION">Plantación</option>
                                    <option value="APERTURA_PLANTERA">Apertura de plantera</option>
                                    <option value="CORTE_RAIZ">Corte de raíz</option>
                                    <option value="EXTRACCION_CEPA">Extracción de cepa</option>
                                    <option value="REPARACION_VEREDA">Reparación de vereda</option>
                                </select>
                            </div>

                            <div className="col-12">
                                <label className="form-label">Descripción</label>
                                <textarea
                                    className="form-control"
                                    rows="3"
                                    placeholder="Descripción de la demanda"
                                />
                            </div>

                            <div className="col-md-6">
                                <label className="form-label">Prioridad</label>
                                <select className="form-select" defaultValue="MEDIA">
                                    <option value="BAJA">Baja</option>
                                    <option value="MEDIA">Media</option>
                                    <option value="ALTA">Alta</option>
                                </select>
                            </div>

                            <div className="col-md-6">
                                <label className="form-label">Estado</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    value="INGRESADA"
                                    readOnly
                                />
                            </div>

                            <div className="col-md-6">
                                <label className="form-label">Calle</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Ej.: Fraga"
                                />
                            </div>

                            <div className="col-md-3">
                                <label className="form-label">Altura</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Ej.: 717"
                                />
                            </div>

                            <div className="col-md-3">
                                <label className="form-label">Referencia</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Ej.: LD"
                                />
                            </div>

                            <div className="col-12 d-flex justify-content-end gap-2 mt-4">
                                <button
                                    type="button"
                                    className="btn btn-outline-secondary"
                                    onClick={onCancelar}
                                >
                                    Cancelar
                                </button>
                                <button type="button" className="btn btn-success">
                                    Guardar demanda
                                </button>
                            </div>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}
export default FormularioDemanda