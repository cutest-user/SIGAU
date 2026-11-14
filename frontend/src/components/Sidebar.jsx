import useAuth from '../auth/useAuth'

const OPCIONES_GESTION = [
    'Demandas',
    'Ejemplares',
    'Inspecciones',
    'Intervenciones',
    'Seguimiento'
]

const OPCIONES_CONSULTA = [
    'Derivaciones',
    'Certificaciones',
    'Reportes'
]

function Sidebar({ pagina, abierto, onCambiarPagina }) {
    const { esAdministrativo } = useAuth()

    const boton = (opcion) => (
        <button
            key={opcion}
            className={`nav-link text-start ${pagina === opcion ? 'active' : ''}`}
            onClick={() => onCambiarPagina(opcion)}
        >
            {opcion}
        </button>
    )

    return (
        <aside
            className={`bg-light border-end p-3 ${abierto ? 'd-block' : 'd-none d-md-block'}`}
            style={{ minWidth: '230px' }}
        >
            <nav className="nav flex-column">
                {boton('Inicio')}

                <div className="text-secondary small fw-bold mt-3 mb-2">
                    GESTIÓN
                </div>

                {OPCIONES_GESTION.map(boton)}

                <div className="text-secondary small fw-bold mt-3 mb-2">
                    CIERRE Y CONSULTA
                </div>

                {OPCIONES_CONSULTA.map(boton)}

                {esAdministrativo && (
                    <>
                        <div className="text-secondary small fw-bold mt-3 mb-2">
                            ADMINISTRACIÓN
                        </div>

                        {boton('Usuarios')}
                    </>
                )}
            </nav>
        </aside>
    )
}

export default Sidebar
