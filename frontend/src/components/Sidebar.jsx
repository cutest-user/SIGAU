function Sidebar({ pagina, onCambiarPagina }) {
    const opcionesGestion = [
        'Demandas',
        'Ejemplares',
        'Inspecciones',
        'Intervenciones',
        'Seguimiento'
    ]

    const opcionesConsulta = [
        'Certificaciones',
        'Reportes'
    ]

    return (
        <aside
            className="bg-light border-end p-3"
            style={{ width: '230px', minHeight: 'calc(100vh - 76px)' }}
        >
            <nav className="nav flex-column">
                <button
                    className={`nav-link text-start ${pagina === 'Inicio' ? 'active' : ''}`}
                    onClick={() => onCambiarPagina('Inicio')}
                >
                    Inicio
                </button>

                <div className="text-secondary small fw-bold mt-3 mb-2">
                    GESTIÓN
                </div>

                {opcionesGestion.map((opcion) => (
                    <button
                        key={opcion}
                        className={`nav-link text-start ${pagina === opcion ? 'active' : ''}`}
                        onClick={() => onCambiarPagina(opcion)}
                    >
                        {opcion}
                    </button>
                ))}

                <div className="text-secondary small fw-bold mt-3 mb-2">
                    CIERRE Y CONSULTA
                </div>

                {opcionesConsulta.map((opcion) => (
                    <button
                        key={opcion}
                        className={`nav-link text-start ${pagina === opcion ? 'active' : ''}`}
                        onClick={() => onCambiarPagina(opcion)}
                    >
                        {opcion}
                    </button>
                ))}
            </nav>
        </aside>
    )
}

export default Sidebar