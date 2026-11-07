function PantallaDetalleDemanda({ demanda, onVolver }) {
    return (
        <div>
            {/* Encabezado */}
            <div className="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 className="h3 mb-1">
                        Detalle de demanda #{demanda.id}
                    </h2>
                    <p className="text-secondary mb-0">
                        Información y seguimiento de la demanda.
                    </p>
                </div>

                <button
                    type="button"
                    className="btn btn-outline-secondary"
                    onClick={onVolver}
                >
                    Volver al listado
                </button>
            </div>
            {/* Información general */}
            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="h5 mb-3">Información general</h3>

                    <div className="row g-3">
                        <div className="col-md-6">
                            <label className="form-label text-secondary">
                                Origen
                            </label>
                            <p className="mb-0">{demanda.origen}</p>
                        </div>

                        <div className="col-md-6">
                            <label className="form-label text-secondary">
                                Motivo
                            </label>
                            <p className="mb-0">{demanda.motivo}</p>
                        </div>

                        <div className="col-md-6">
                            <label className="form-label text-secondary">
                                Fecha de ingreso
                            </label>
                            <p className="mb-0">{demanda.fechaIngreso}</p>
                        </div>

                        <div className="col-md-6">
                            <label className="form-label text-secondary">
                                Prioridad
                            </label>
                            <p className="mb-0">{demanda.prioridad}</p>
                        </div>

                        <div className="col-md-6">
                            <label className="form-label text-secondary">
                                Estado
                            </label>
                            <p className="mb-0">{demanda.estado}</p>
                        </div>

                        <div className="col-12">
                            <label className="form-label text-secondary">
                                Descripción
                            </label>
                            <p className="mb-0">{demanda.descripcion || 'Sin descripción'}</p>
                        </div>
                    </div>
                </div>
            </div>
            {/* Ubicación */}
            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="h5 mb-3">Ubicación</h3>

                    <div className="row g-3">
                        <div className="col-md-4">
                            <label className="form-label text-secondary">
                                Calle
                            </label>
                            <p className="mb-0">{demanda.calle}</p>
                        </div>

                        <div className="col-md-4">
                            <label className="form-label text-secondary">
                                Altura
                            </label>
                            <p className="mb-0">{demanda.altura}</p>
                        </div>

                        <div className="col-md-4">
                            <label className="form-label text-secondary">
                                Referencia
                            </label>
                            <p className="mb-0">{demanda.referencia || 'Sin referencia'}</p>
                        </div>
                    </div>
                </div>
            </div>
            {/* Ejemplares asociados */}
            <div className="card mb-4">
                <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center mb-3">
                        <h3 className="h5 mb-0">Ejemplares asociados</h3>
                        <span className="badge bg-secondary">
                {demanda.ejemplares?.length || 0} ejemplares
            </span>
                    </div>

                    {demanda.ejemplares?.length > 0 ? (
                        <div className="table-responsive">
                            <table className="table table-hover align-middle mb-0">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Especie</th>
                                    <th>Ubicación</th>
                                </tr>
                                </thead>
                                <tbody>
                                {demanda.ejemplares.map((ejemplar) => (
                                    <tr key={ejemplar.id}>
                                        <td>{ejemplar.id}</td>
                                        <td>{ejemplar.especie}</td>
                                        <td>{ejemplar.ubicacion}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    ) : (
                        <p className="text-secondary mb-0">
                            No hay ejemplares asociados a esta demanda.
                        </p>
                    )}
                </div>
            </div>
            {/* Seguimiento */}
            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="h5 mb-3">Seguimiento</h3>

                    {demanda.intervenciones?.length > 0 ? (
                        <div className="table-responsive">
                            <table className="table table-hover align-middle">
                                <thead>
                                <tr>
                                    <th>Intervención</th>
                                    <th>Estado</th>
                                    <th>Verificaciones</th>
                                </tr>
                                </thead>

                                <tbody>
                                {demanda.intervenciones.map((intervencion) => (
                                    <tr key={intervencion.id}>
                                        <td>{intervencion.tipo}</td>
                                        <td>{intervencion.estado}</td>
                                        <td>
                                            {intervencion.verificaciones?.length || 0}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    ) : (
                        <p className="text-secondary mb-0">
                            Todavía no hay intervenciones registradas para esta demanda.
                        </p>
                    )}
                </div>
            </div>
        </div>
    )
}

export default PantallaDetalleDemanda