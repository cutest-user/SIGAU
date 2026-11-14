function CamposUbicacion({ valor, onChange, obligatoria = true }) {
    const cambiar = (campo) => (evento) =>
        onChange({ ...valor, [campo]: evento.target.value })

    return (
        <>
            <div className="col-md-6">
                <label className="form-label">Calle</label>
                <input
                    className="form-control"
                    value={valor.calle}
                    onChange={cambiar('calle')}
                    required={obligatoria}
                    maxLength={150}
                />
            </div>

            <div className="col-md-3 col-6">
                <label className="form-label">Altura</label>
                <input
                    className="form-control"
                    value={valor.numero}
                    onChange={cambiar('numero')}
                    maxLength={20}
                />
            </div>

            <div className="col-md-3 col-6">
                <label className="form-label">Comuna</label>
                <input
                    className="form-control"
                    value={valor.comuna}
                    onChange={cambiar('comuna')}
                    maxLength={50}
                />
            </div>

            <div className="col-md-6">
                <label className="form-label">Barrio</label>
                <input
                    className="form-control"
                    value={valor.barrio}
                    onChange={cambiar('barrio')}
                    maxLength={100}
                />
            </div>

            <div className="col-md-6">
                <label className="form-label">Referencia</label>
                <input
                    className="form-control"
                    value={valor.referencia}
                    onChange={cambiar('referencia')}
                    maxLength={255}
                    placeholder="Entre calles, frente a..."
                />
            </div>
        </>
    )
}

export default CamposUbicacion
