
function PantallaDemandas({ onNuevaDemanda, onVerDetalle }) {
    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 className="h3 mb-1">Demandas</h2>
                    <p className="text-secondary mb-0">
                        Registro y seguimiento de demandas de arbolado urbano.
                    </p>
                </div>

                <button
                    className="btn btn-success"
                    type="button"
                    onClick={onNuevaDemanda}
                >
                    + Nueva demanda
                </button>
            </div>

            <div className="card">
                <div className="card-body">
                    <h3 className="h5 mb-3">Listado de demandas</h3>

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Fecha de ingreso</th>
                                <th>Motivo</th>
                                <th>Prioridad</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                            </thead>

                            <tbody>
                            <tr>
                                <td colSpan="6" className="text-center text-secondary py-5">
                                    No hay demandas registradas.
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PantallaDemandas